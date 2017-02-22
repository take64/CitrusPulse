package live.citrus.pulse.fx.node;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import live.citrus.pulse.database.object.CPObjectDatabaseRecord;
import live.citrus.pulse.fx.CPFX;
import live.citrus.pulse.fx.CPFxUtils;
import live.citrus.pulse.fx.stage.CPFxStage;
import live.citrus.pulse.log.CPLogger;

public abstract class CPFxParent implements Initializable
{
    /** 接続しているステージ **/
    public CPFxStage parentStage;
    
    /** データロード済みかどうか **/
    protected boolean dataLoaded = true;
    
    /** 対応するペーン **/
    public AnchorPane childPane = null;
    
    /**
     * 初期化
     */
    public void initialize(URL location, ResourceBundle resources)
    {
        this.initializeScreen();
        this.initializeEvent();
    }

    /**
     * 画面の初期化
     */
    abstract public void initializeScreen();
    
    /**
     * イベントの初期化
     */
    abstract public void initializeEvent();
    
    /**
     * 画面の再読み込み
     */
    abstract public void reloadScreen();
    
    /**
     * オブジェクトの読み込み
     */
    abstract public void loadObject(Object object);
    
    /**
     * 内容の変更
     */
    abstract public void change();
    
    /**
     * 再描画
     */
    abstract public void reload();
    
    /**
     * 保存
     */
    abstract public void save();
    
    /**
     * キャンセル
     */
    abstract public void cancel();
    
    
    
    /**
     * 画面タイトルの変更
     * 
     * @param title
     */
    public void setTitle(String title)
    {
        if(this.parentStage != null)
        {
            this.parentStage.setTitle(title);
        }
    }
    
    /**
     * ペーンの読み込み
     * 
     * @param clazz
     * @param fxmlname
     * @param root
     * @return 
     */
    public static <T> CPFxParent loadPane(Class<T> clazz, String fxmlname, Object root)
    {
        CPFxParent pane = null;
        try
        {
            URL url = clazz.getResource(fxmlname);
            CPLogger.debug("load fxml : " + url.toString());
            String fxmlSource = CPFxUtils.loadFxml(url);
            InputStream inputStream = new ByteArrayInputStream(fxmlSource.getBytes());
            
            FXMLLoader loader = new FXMLLoader();
            AnchorPane child = (AnchorPane)loader.load(inputStream);
            T controller = loader.getController();
            pane = (CPFxParent)controller;
            pane.childPane = child;
            if(root instanceof Pane)
            {
                ((Pane)root).getChildren().add(child);
            }
            else if(root instanceof ScrollPane)
            {
                ((ScrollPane)root).setContent(child);
            }
        }
        catch(Exception e)
        {
            CPLogger.debug(e);
        }
        return pane;
    }
    
    /**
     * entityからpaneにbind
     * 
     * @param entity
     */
    @SuppressWarnings("unchecked")
    public void bindFromEntity(CPObjectDatabaseRecord entity)
    {
        try
        {
            Field[] thisFields = this.getClass().getDeclaredFields();
            
            for(Field thisField : thisFields)
            {
                // アノテーションの貼ってあるフィールド
                if(thisField.isAnnotationPresent(FXML.class) == true)
                {
                    // フィールド名を取得
                    String fieldName = thisField.getName();
                    
                    // entityのフィールドを取得
                    Field entityField = null;
                    if(entity != null)
                    {
                        Field[] entityFields = entity.getClass().getDeclaredFields();
                        for(Field field : entityFields)
                        {
                            CPFX cpfx = field.getAnnotation(CPFX.class);
                            if(cpfx != null)
                            {
                                String cpfxFieldName = cpfx.value();
                                if(fieldName.equals(cpfxFieldName) == true)
                                {
                                    entityField = field;
                                    break;
                                }
                            }
                        }
                    }
                    if(entityField != null && entityField.isAnnotationPresent(CPFX.class) == true)
                    {
                        thisField.setAccessible(true);
                        entityField.setAccessible(true);
                        Object value = null;
                        if(entity != null)
                        {
                            value = entityField.get(entity);
                        }
                        
                        Object object = thisField.get(this);
                        
                        // ChoiceBox
                        if(thisField.getType() == ChoiceBox.class)
                        {
                            ParameterizedType paramType = (ParameterizedType)thisField.getGenericType();
                            for(Type classType : paramType.getActualTypeArguments())
                            {
                                if(String.class.getName().equals(classType.getTypeName()) == true)
                                {
                                    ((ChoiceBox<String>)object).setValue(String.valueOf(value));
                                    break;
                                }
                            }
                        }
                        // CheckBox
                        else if(thisField.getType() == CheckBox.class)
                        {
                            ((CheckBox)object).setSelected(
                                    ((Integer)value).equals(Integer.valueOf(1))
                                    );
                        }
                        // RadioButton
                        else if(thisField.getType() == RadioButton.class)
                        {
                            ((RadioButton)object).setSelected(
                                    ((Integer)value).equals(Integer.valueOf(1))
                                    );
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
}
