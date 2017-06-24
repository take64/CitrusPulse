package live.citrus.pulse.database.object;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import live.citrus.pulse.fx.CPFX;
import live.citrus.pulse.fx.node.CPFxParent;
import live.citrus.pulse.log.CPLogger;
import live.citrus.pulse.variable.date.CPDateUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public abstract class CPObjectDatabaseRecord
{
    /** 管理テーブル **/
    private CPObjectDatabaseTable table;
    
    /** プライマリーキー相当 **/
    public Object primaryKey; 
    
    
    /**
     * フィールド内容の補完
     */
    abstract public void columnComplete();
    
    /**
     * trueの場合はレコードが生き、falseは死に
     * @return
     */
    public boolean enabled()
    {
        return true;
    }
    
    /**
     * 画面からフィールドデータをbind
     * 
     * @param pane
     */
    public void bindFromPane(CPFxParent pane)
    {
        try
        {
            Field[] thisFields = this.getClass().getFields();
            
            for(Field thisField : thisFields)
            {
                // アノテーションの貼ってないフィールドは飛ばす
                if(thisField.isAnnotationPresent(CPFX.class) == false)
                {
                    continue;
                }

                // アノテーションの値からフィールド名を取得
                CPFX cpfx = thisField.getAnnotationsByType(CPFX.class)[0];
                String fieldName = cpfx.value();

                // ペーンのフィールドを取得
                Field paneField = pane.getClass().getDeclaredField(fieldName);

                // ペーンのフィールドがFXMLではなければスルー
                if(paneField.isAnnotationPresent(FXML.class) == false)
                {
                    continue;
                }

                // ペーンの値
                Object value = CPObjectDatabaseRecord.callPaneFieldValue(pane, paneField);

                // 変換
                value = CPObjectDatabaseRecord.convertValueForField(value, thisField);

                // 値設定
                if(value != null && value.equals("") == false)
                {
                    thisField.set(this, value);
                }
            }
        }
        catch(Exception e)
        {
            CPLogger.debug(e);
        }
    }

    /**
     * JSONからフィールドデータをbind
     * 
     * @param jsonObject
     */
    public void bindFromJSON(JSONObject jsonObject)
    {
        try
        {
            Field[] thisFields = this.getClass().getFields();
            
            for(Field thisField : thisFields)
            {
                // アノテーションの貼ってあるフィールド
                if(thisField.isAnnotationPresent(CPOD.class) == true)
                {
                    // アクセスON
                    thisField.setAccessible(true);
                    Class<?> thisFieldClass = thisField.getType();
                    
                    // アノテーションの値からフィールド名を取得
                    CPOD cpod = thisField.getAnnotationsByType(CPOD.class)[0];
                    String fieldName = cpod.value();
                    // JSONのデータを取得
                    if(jsonObject.isNull(fieldName) == true)
                    {
                        continue;
                    }
                    Object value = jsonObject.get(fieldName);
                    // JSONArrayの場合に分解
                    if(value instanceof JSONArray)
                    {
                        value = ((JSONArray)value).toList();
                    }
                    else if(value instanceof JSONObject)
                    {
                        value = ((JSONObject)value).toMap();
                    }
                    else if(value instanceof String && thisFieldClass == Integer.class)
                    {
                        value = Integer.valueOf((String)value);
                    }
                    else if(value instanceof Integer && thisFieldClass == String.class)
                    {
                        value = value.toString();
                    }
                    else if(value instanceof Integer && thisFieldClass == Long.class)
                    {
                        value = ((Integer) value).longValue();
                    }
                    else if(value instanceof Integer && thisFieldClass == Double.class)
                    {
                        value = ((Integer) value).doubleValue();
                    }
                    else if(value instanceof Long && thisFieldClass == Integer.class)
                    {
                        value = ((Long) value).intValue();
                    }
                    
                    // フィールドに設定
                    thisField.set(this, value);
                }
            }
        }
        catch(Exception e)
        {
            CPLogger.debug(e);
        }
    }
    
    /**
     * テーブルの設定
     * 
     * @param table
     */
    public void setTable(CPObjectDatabaseTable table)
    {
        this.table = table;
    }
    
    /**
     * MAP形式に変換
     * 
     * @return
     */
    public Map<String, Object> toMap()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        
        try
        {
            Field[] fields = this.getClass().getFields();
            for(Field field : fields)
            {
                // staticフィールドはスルー
                if(Modifier.isStatic(field.getModifiers()) == true)
                {
                    continue;
                }
                
                map.put(field.getName(), field.get(this));
            }
        }
        catch(Exception e)
        {
            CPLogger.debug(e);
        }
        
        return map;
    }

    /**
     * コールバックの取得
     * 
     * @return
     */
    public CPObjectDatabaseCallback callLoadingCallback()
    {
        CPObjectDatabaseCallback callback = null;
        
        try
        {
            Field field = this.getClass().getField("loadingCallback");
            callback = (CPObjectDatabaseCallback) field.get(this);
        }
        catch(NoSuchFieldException e)
        {
//            CPLogger.debug(this.getClass().getSimpleName() + " : loadingCallback が存在しません");
        }
        catch(Exception e)
        {
            CPLogger.debug(e);
        }
        
        return callback;
    }


    /**
     * ペーンからフィールドの値を取得する
     *
     * @param pane      ペーン
     * @param paneField ペーンフィールド
     * @return          ペーンフィールドの値
     * @throws IllegalAccessException
     */
    private static Object callPaneFieldValue(CPFxParent pane, Field paneField) throws IllegalAccessException
    {
        paneField.setAccessible(true);

        // ペーンのパーツ
        Object object = paneField.get(pane);

        // ペーンの値
        Object value = null;

        // ChoiceBox
        if(paneField.getType() == ChoiceBox.class)
        {
            value = ((ChoiceBox<?>)object).getValue();
        }
        // CheckBox
        else if(paneField.getType() == CheckBox.class)
        {
            value = ((CheckBox)object).isSelected() == true ? 1 : 0;
        }
        // RadioButton
        else if(paneField.getType() == RadioButton.class)
        {
            value = ((RadioButton)object).isSelected() == true ? 1 : 0;
        }
        // DatePicker
        else if(paneField.getType() == DatePicker.class)
        {
            value = CPDateUtils.format("yyyy-MM-dd", ((DatePicker)object).getValue());
        }

        if(value.equals("null") == true)
        {
            value = null;
        }

        return value;
    }


    /**
     * 値をフィールドによって変換する
     *
     * @param value     値
     * @param thisField 対象フィールド
     * @return          変換値
     */
    private static Object convertValueForField(Object value, Field thisField)
    {
        Class<?> thisFieldClass = thisField.getType();

        if(value instanceof Integer)
        {
            // Integer -> String
            if(thisFieldClass == String.class)
            {
                value = value.toString();
            }
        }
        else if(value instanceof String)
        {
            // String -> Integer
            if(thisFieldClass == Integer.class)
            {
                value = Integer.valueOf((String)value);
            }
        }

        return value;
    }

}
