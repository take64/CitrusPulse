package live.citrus.pulse.http;

import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import live.citrus.pulse.file.CPFileSize;
import live.citrus.pulse.log.CPLogger;

/**
 * Http通信データ保持
 * 
 * @author take64
 *
 */
public class CPHttpData
{
    public String host;
    public String uri;
    public String method;
    public String contentType;
    public byte[] query;
    public byte[] body;
    
    
    /**
     * constructor
     * 
     * @param host
     * @param uri
     * @param method
     * @param contentType
     * @param body
     */
    public CPHttpData(String host, String uri, String method, String contentType, byte[] query, byte[] body)
    {
        this.host = host;
        this.uri = uri;
        this.method = method;
        this.contentType = contentType;
        this.query = query;
        this.body = body;
    }
    
    /**
     * uriをセグメント分けして取得
     * 
     * @return
     */
    public List<String> callUriSegments()
    {
        String[] segments = this.uri.split("/");
        List<String> uriSegments = new ArrayList<String>(segments.length);
        for(String one : segments)
        {
            uriSegments.add(one);
        }
        return uriSegments;
    }
    
    /**
     * クエリ内容を文字列データ化
     * 
     * @return
     */
    public String callQuery()
    {
        String result = new String(this.query);
        try
        {
            result = URLDecoder.decode(result, "UTF-8");
        }
        catch (Exception e)
        {
            CPLogger.debug(e);
        }
        return result;
    }
    
    /**
     * クエリ内容をマップ化
     * 
     * @return
     */
    public Map<String, String> callQueries()
    {
        Map<String, String> result = new HashMap<String, String>(4);
        
        String query = this.callQuery();
        String[] parts = query.split("&");
        for(String one : parts)
        {
            String[] part = one.split("=");
            result.put(part[0], part[1]);
        }
        
        return result;
    }
    
    /**
     * データ内容を文字列データ化
     * 
     * @return
     */
    public String callBody()
    {
        String result = new String(this.body);
        return result;
    }
    
    public String toString()
    {
        return this.host + " -> " 
                + this.uri + " :: " 
                + this.method + " : " 
                + this.contentType + " : " 
                + CPFileSize.sizeString(body);
    }
    
    /**
     * URIで処理を分ける
     * 
     * @param httpData
     * @param object
     * @return
     */
    public static boolean uriMethodBalancer(CPHttpData httpData, Object object)
    {
        boolean result = false;
        
        // クラスチェック
        if(CPHttpData.isUrlPatternClass(httpData, object.getClass()) == true)
        {
            // メソッドチェック
            Method[] methods = object.getClass().getMethods();
            for(Method method : methods)
            {
                if(CPHttpData.isUrlPatternMethod(httpData, method) == true)
                {
                    // 合致すれば実行
                    try
                    {
//                        CPLoggerTimer.start();
                        method.invoke(object, httpData);
//                        CPLoggerTimer.end();
                        result = true;
                        break;
                    }
                    catch(Exception e)
                    {
                        CPLogger.debug(e);
                    }
                }
            }
        }
        return result;
    }
    
    /**
     * クラスがCPHttpUriPatternに合致するかどうか
     * 
     * @param httpData
     * @param clazz
     * @return
     */
    public static boolean isUrlPatternClass(CPHttpData httpData, Class<? extends Object> clazz)
    {
        List<String> uriSegments = httpData.callUriSegments();
        
        boolean result = false;
        
        if(clazz.isAnnotationPresent(CPHttpUriPattern.class) == true)
        {
            CPHttpUriPattern uriPattern = (CPHttpUriPattern)clazz.getAnnotationsByType(CPHttpUriPattern.class)[0];
            String[] uriPatternValue = uriPattern.value();
            int index = Integer.parseInt(uriPatternValue[0]);
            String pattern = uriPatternValue[1];
            if(uriSegments.size() > index)
            {
                String segment = uriSegments.get(index);
                if(pattern.startsWith("*") == true && pattern.endsWith("*") == true)
                {
                    if(segment.contains(pattern) == true) { result = true; }
                }
                else if(pattern.startsWith("*") == true)
                {
                    if(segment.endsWith(pattern) == true) { result = true; }
                }
                else if(pattern.endsWith("*") == true)
                {
                    if(segment.startsWith(pattern) == true) { result = true; }
                }
                else
                {
                    if(segment.equals(pattern) == true) { result = true; }
                }
            }
        }
        return result;
    }
    

    /**
     * メソッドがCPHttpUriPatternに合致するかどうか
     * 
     * @param httpData
     * @param clazz
     * @return
     */
    public static boolean isUrlPatternMethod(CPHttpData httpData, Method method)
    {
        List<String> uriSegments = httpData.callUriSegments();
        
        boolean result = false;
        
        if(method.isAnnotationPresent(CPHttpUriPattern.class) == true)
        {
            CPHttpUriPattern uriPattern = (CPHttpUriPattern)method.getAnnotationsByType(CPHttpUriPattern.class)[0];
            String[] uriPatternValue = uriPattern.value();
            int index = Integer.parseInt(uriPatternValue[0]);
            String pattern = uriPatternValue[1];
            if(uriSegments.size() > index)
            {
                String segment = uriSegments.get(index);
                if(pattern.startsWith("*") == true && pattern.endsWith("*") == true)
                {
                    if(segment.contains(pattern) == true) { result = true; }
                }
                else if(pattern.startsWith("*") == true)
                {
                    if(segment.endsWith(pattern) == true) { result = true; }
                }
                else if(pattern.endsWith("*") == true)
                {
                    if(segment.startsWith(pattern) == true) { result = true; }
                }
                else
                {
                    if(segment.equals(pattern) == true) { result = true; }
                }
            }
        }
        return result;
    }
}
