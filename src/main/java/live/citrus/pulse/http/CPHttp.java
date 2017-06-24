package live.citrus.pulse.http;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;

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
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE) == true)
        {
            try
            {
                // エンコード
                urlString = URLEncoder.encode(urlString, "UTF-8");
                // 規定文字列の戻し
                urlString = urlString.replaceAll("%2F", "/");
                urlString = urlString.replaceAll("%3A", ":");
                
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


    /**
     * HTTPからPOSTメソッドでファイルにアクセスし文字列を取得
     * 
     * @param urlString
     * @param parameters
     * @return
     */
    public static String postString(String urlString, Map<String, String> parameters)
    {
        String result = "";
        try
        {
            result = CPHttp.postString(new URL(urlString), parameters);
        }
        catch (Exception e)
        {
            CPLogger.debug(e);
        }
        return result;
    }
    
    /**
     * HTTPからPOSTメソッドでファイルにアクセスし文字列を取得
     * 
     * @param url
     * @param parameters
     * @return
     */
    public static String postString(URL url, Map<String, String> parameters)
    {
        StringBuilder stringBuilder = new StringBuilder("");
        try
        {
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            
            // パラメータ
            if(parameters != null)
            {
                String parameter = "";
                for(Entry<String, String> entry : parameters.entrySet())
                {
                    if(parameter.equals("") == false)
                    {
                        parameter += "&";
                    }
                    parameter += (entry.getKey() + "=" + entry.getValue());
                }
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream(),StandardCharsets.UTF_8);
                outputStreamWriter.write(parameter);
                outputStreamWriter.flush();
                outputStreamWriter.close();
            }
            
            InputStream inputStream = connection.getInputStream();
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
