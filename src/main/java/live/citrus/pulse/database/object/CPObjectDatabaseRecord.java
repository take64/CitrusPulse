package live.citrus.pulse.database.object;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import live.citrus.pulse.fx.CPFX;
import live.citrus.pulse.fx.node.CPFxParent;
import live.citrus.pulse.log.CPLogger;

public abstract class CPObjectDatabaseRecord// implements Cloneable
{
    /** 管理テーブル **/
    private CPObjectDatabaseTable table;
    
    /** プライマリーキー相当 **/
    public Object primaryKey; 
    
    
//    public static Map<String, String> mapping;
    
    /**
     * フィールド内容の補完
     */
    abstract public void columnComplete();
    
//    /**
//     * データのバインドをする
//     * 
//     * @param jsonObject
//     * @param record
//     */
//    public void bind(JSONObject jsonObject)
//    {
////        Map<String, Object> object = jsonObject.toMap();
////        Map<String, String> mapping = this.callMapping();
//        
////        if(mapping == null)
////        {
//            this.bindFromJSON(jsonObject);
////            return ;
////        }
////        
////        for(Entry<String, String> entry : mapping.entrySet())
////        {            
////            try
////            {
////                final Field field = this.getClass().getField(entry.getKey());
////                
////                Object value = object.get(entry.getValue());
////                final Class<?> classType = field.getType();
////                
////                // 受け取った値がnullの場合
////                if(value == null)
////                {
////                }
////                // 受け取った型と設定先の型が違う場合
////                else if(classType != value.getClass())
////                {
////                    // String => Integer
////                    if(value instanceof String && classType == Integer.class)
////                    {
////                        value = Integer.valueOf((String)value);
////                    }
////                    // Integer => String
////                    else if(value instanceof Integer && classType == String.class)
////                    {
////                        value = String.valueOf((Integer)value);
////                    }
////                    // Integer => Long
////                    else if(value instanceof Integer && classType == Long.class)
////                    {
////                        value = Long.valueOf((Integer)value);
////                    }
////                }
////                field.set(this, value);
////            }
////            catch(Exception e)
////            {
////                CPLogger.debug("load property : " + entry.getKey());
////                CPLogger.debug(e);
////                CPLogger.debug(object.toString());
////            }
////        }
//    }

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
                // アノテーションの貼ってあるフィールド
                if(thisField.isAnnotationPresent(CPFX.class) == true)
                {
                    Class<?> thisFieldClass = thisField.getType();
                    
                    // アノテーションの値からフィールド名を取得
                    CPFX cpfx = thisField.getAnnotationsByType(CPFX.class)[0];
                    String fieldName = cpfx.value();
                    // ペーンのフィールドを取得
                    Field paneField = pane.getClass().getDeclaredField(fieldName);
                    if(paneField.isAnnotationPresent(FXML.class) == true)
                    {
                        paneField.setAccessible(true);
                        
                        // ペーンのパーツ
                        Object object = paneField.get(pane);
                        
                        // ペーンの値
                        Object value = null;
                        
                        // フィールドのクラスによって取得方法を変える
                        // ChoiceBox
                        if(paneField.getType() == ChoiceBox.class)
                        {
                            value = ((ChoiceBox<?>)object).getValue();
                        }
                        // CheckBox
                        else if(paneField.getType() == CheckBox.class)
                        {
                            value = Integer.valueOf(((CheckBox)object).isSelected() == true ? 1 : 0);
                        }
                        // RadioButton
                        else if(paneField.getType() == RadioButton.class)
                        {
                            value = Integer.valueOf(((RadioButton)object).isSelected() == true ? 1 : 0);
                        }
                        
                        if(value.equals("null") == true)
                        {
                            value = null;
                        }
                        
                        // 変換
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
                        
                        // 値設定
                        if(value != null && value.equals("") == false)
                        {
                            thisField.set(this, value);
                        }
                    }
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
     * @param pane
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
                        value = ((Integer)value).toString();
                    }
                    else if(value instanceof Integer && thisFieldClass == Long.class)
                    {
                        value = Long.valueOf(((Integer)value).longValue());
                    }
                    else if(value instanceof Integer && thisFieldClass == Double.class)
                    {
                        value = Double.valueOf(((Integer)value).doubleValue());
                    }
                    else if(value instanceof Long && thisFieldClass == Integer.class)
                    {
                        value = Integer.valueOf(((Long)value).intValue());
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
    
//    /**
//     * マッピングの取得
//     * 
//     * @return
//     */
//    @SuppressWarnings("unchecked")
//    public Map<String, String> callMapping()
//    {
//        Map<String, String> mapping = null;
//        
//        try
//        {
//            Field field = this.getClass().getField("mapping");
//            mapping = (Map<String, String>) field.get(this);
//            CPLogger.debug("mainng size : " + Integer.valueOf(mapping.size()));
//        }
//        catch(NoSuchFieldException e)
//        {
//            CPLogger.debug(this.getClass().getSimpleName() + " : mapping が存在しません");
//        }
//        catch(Exception e)
//        {
//            CPLogger.debug(e);
//        }
//        
//        return mapping;
//    }

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
    

//    @Override
//    public Object clone() throws CloneNotSupportedException
//    {
//        return super.clone();
//    }
}
