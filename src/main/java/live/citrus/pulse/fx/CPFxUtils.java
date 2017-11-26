package live.citrus.pulse.fx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import live.citrus.pulse.log.CPLogger;

public class CPFxUtils
{
    /** fxmlファイルのキャッシュ **/
    public static Map<URL, String> fxmlCaches = new HashMap<URL, String>(20);
    
    
    /**
     * fxmlの読み込み
     * @param <T>
     * 
     * @param stage
     * @param filename
     * @return
     */
    public static <T> Scene fxmlLoad(Stage stage, String filename)
    {
        Scene scene = null;
        try
        {
            URL url = stage.getClass().getResource(filename);
            FXMLLoader loader = new FXMLLoader(url);
            loader.setController(stage);
            scene = new Scene((Parent)loader.load());
            stage.setScene(scene);
        }
        catch (IOException e)
        {
            CPLogger.debug(e);
        }
        
        return scene;
    }

    

    /**
     * ファイルを読み込んで文字列で返す
     * 
     * @param filepath
     * @return
     */
    public static String loadFxml(URL filepath)
    {
        // キャッシュ読み込みを試みる
        String result = CPFxUtils.fxmlCaches.get(filepath);
        if (result != null)
        {
            CPLogger.debug("    -> use cache");
            return result;
        }
        
        StringBuilder stringBuilder = new StringBuilder();
        
        try
        {
            InputStream inputStream = filepath.openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line;
            while((line = bufferedReader.readLine()) != null)
            {
                // includeを飛ばす
                if (line.contains("<fx:include") == true)
                {
                    CPLogger.debug("    -> include through : " + line.replaceAll("  ", ""));
                    continue;
                }
                
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();
            inputStream.close();
        }
        catch (Exception e)
        {
            CPLogger.debug(e);
        }
        
        result = stringBuilder.toString();
        
        // ファイルキャッシュする
        CPFxUtils.fxmlCaches.put(filepath, result);
        
        return result;
    }
    
    /**
     * 背景色の取得
     * 
     * @param color
     * @return
     */
    public static Background callBackgroundColor(Color color)
    {
        return new Background(new BackgroundFill(color, null, null));
    }
    
    /**
     * 色付きボーダーの取得
     * 
     * @param color
     * @return
     */
    public static Border callSolidBorder(Color color)
    {
        return new Border(new BorderStroke(color, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, null));
    }
}
