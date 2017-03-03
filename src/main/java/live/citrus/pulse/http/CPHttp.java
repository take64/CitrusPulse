package live.citrus.pulse.http;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import live.citrus.pulse.log.CPLogger;

/**
 * HTTP処理系
 * 
 * @author take64
 *
 */
public class CPHttp
{
    /**
     * URLを開く
     * 
     * @param urlString
     */
    public static void openURL(String urlString)
    {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
        {
            try
            {
                desktop.browse(new URL(urlString).toURI());
            }
            catch (Exception e) 
            {
                CPLogger.debug(e);
            }
        }
    }

    /**
     * HTTPからGETメソッドでファイルにアクセスし文字列を取得
     * 
     * @param urlString
     * @return
     */
    public static String getString(String urlString)
    {
        String result = "";
        try
        {
            result = CPHttp.getString(new URL(urlString));
        }
        catch (Exception e)
        {
            CPLogger.debug(e);
        }
        return result;
    }
    
    /**
     * HTTPからGETメソッドでファイルにアクセスし文字列を取得
     * 
     * @param url
     * @return
     */
    public static String getString(URL url)
    {
        StringBuilder stringBuilder = new StringBuilder("");
        try
        {
            HttpURLConnection connect = (HttpURLConnection)url.openConnection();
            connect.setRequestMethod("GET");
            InputStream inputStream = connect.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line;
            while((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();
            inputStream.close();
        }
        catch (Exception e)
        {
            CPLogger.debug(e);
        }
        return stringBuilder.toString();
    }
}
