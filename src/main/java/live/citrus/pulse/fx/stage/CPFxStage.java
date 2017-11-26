package live.citrus.pulse.fx.stage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import live.citrus.pulse.fx.CPFxUtils;
import live.citrus.pulse.fx.node.CPFxParent;
import live.citrus.pulse.log.CPLogger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;

public class CPFxStage extends Stage
{
    /** 内包するペーン **/
    public CPFxParent innerPane;
    
    /** シーン **/
    public Scene scene;
    
    /** 読み込み済みかどうか **/
    protected boolean loaded = false;
    
    /**
     * 読み込み済みかどうか
     * 
     * @return
     */
    public boolean isLoaded()
    {
        return this.loaded;
    }
    
    /**
     * ペーンの読み込み
     * 
     * @param clazz
     * @param fxmlname
     */
    public <T> void loadPane(Class<T> clazz, String fxmlname)
    {
        try
        {
            if (this.loaded == false)
            {
                URL url = clazz.getResource(fxmlname);
                CPLogger.debug("load fxml : " + url.toString());
                String fxmlSource = CPFxUtils.loadFxml(url);
//                CPLogger.debug(fxmlSource);
                InputStream inputStream = new ByteArrayInputStream(fxmlSource.getBytes());
                FXMLLoader loader = new FXMLLoader();
                Parent parent = loader.load(inputStream);
                T controller = loader.getController();
                this.innerPane = (CPFxParent)controller;
                this.innerPane.parentStage = this;
                
                this.scene = new Scene(parent);
                
                this.setScene(this.scene);
                
                this.loaded = true;                
            }
        }
        catch(Exception e)
        {
            CPLogger.debug(e);
        }
    }

    /**
     * ペーンの読み込み
     * @param <T>
     * 
     * @param clazz
     * @param cssname
     */
    public <T> void loadStylesheet(Class<T> clazz, String cssname)
    {
        if (this.loaded == true)
        {
            URL url = clazz.getResource(cssname);
            String style = url.toExternalForm();
            CPLogger.debug("load css  : " + url.toString());
            this.scene.getStylesheets().add(style);
        }
        else
        {
            CPLogger.debug("load css  : not");
        }
    }
    
    /**
     * 汎用オブジェクト読み込み
     * 
     * @param object
     */
    public void loadObject(Object object)
    {
        try
        {
            this.innerPane.loadObject(object);
        }
        catch(Exception e)
        {
            CPLogger.debug(e);
        }
    }
    
    /**
     * show時にchangeもする
     */
    public void showAndChange()
    {
        try
        {
            this.innerPane.change();
            this.show();
            this.toFront();
        }
        catch(Exception e)
        {
            CPLogger.debug(e);
        }
    }
    
    /**
     * show時にrefreshもする
     */
    public void showAndRefresh()
    {
        try
        {
            this.innerPane.loadObject(null);
            this.show();
            this.toFront();
        }
        catch(Exception e)
        {
            CPLogger.debug(e);
        }
    }
    
    /**
     * show時にrefreshもする
     */
    public void showAndRefresh(Object object)
    {
        try
        {
            this.innerPane.loadObject(object);
            this.show();
            this.toFront();
        }
        catch(Exception e)
        {
            CPLogger.debug(e);
        }
    }
}
