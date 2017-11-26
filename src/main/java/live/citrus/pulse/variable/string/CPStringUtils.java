package live.citrus.pulse.variable.string;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import live.citrus.pulse.log.CPLogger;

public class CPStringUtils
{
    /**
     * MD5文字列の作成
     * 
     * @param stringValue
     * @return
     */
    public static String generateMD5(String stringValue)
    {
        String result = stringValue;
        
        try
        {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(stringValue.getBytes());
            byte[] hash = md5.digest();
            result = CPStringUtils.hexString(hash);
        }
        catch (NoSuchAlgorithmException e)
        {
            CPLogger.debug(e);
        }
        
        return result;
    }
    
    /**
     * 16進数文字列に変換
     * 
     * @param byteValues
     * @return
     */
    public static String hexString(byte[] byteValues)
    {
        String value = "";
        int size = byteValues.length;
        for (int i = 0; i < size; i++)
        {
            int n = byteValues[i];
            if (n < 0)
            {
                n += 256;
            }
            String hexString = Integer.toHexString(n);
            if (hexString.length() == 1)
            {
                hexString = "0" + hexString;
            }
            value += hexString;
        }
        return value;
    }

    
    /**
     * snakeケースに変換
     * 
     * @param stringValue
     * @return
     */
    public static String toSnakeCase(String stringValue)
    {
        // 回しながら append
        StringBuilder stringBuilder = new StringBuilder();
        int length = stringValue.length();
        for (int i = 0; i < length; i++)
        {
            char c = stringValue.charAt(i);
            if (Character.isUpperCase(c) == true)
            {
                if (stringBuilder.length() != 0)
                {
                    stringBuilder.append("_");
                }
                stringBuilder.append(Character.toLowerCase(c));
            }
            else
            {
                stringBuilder.append(c);
            }
        }

        String result = stringBuilder.toString();
        return result;
    }
    
    /**
     * lower-camelケースに変換
     * 
     * @param stringValue
     * @return
     */
    public static String toLowerCamelCase(String stringValue)
    {
        // 一旦 lower-case へ変換
        String lowerCase = stringValue.toLowerCase();
        
        // _ で分割しておく
        String[] splits = lowerCase.split("_");
        
        // 回しながら append
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < splits.length; i++)
        {
            if (i == 0)
            {
                stringBuilder.append(splits[i]);
            }
            else
            {
                char c = splits[i].charAt(0);
                stringBuilder.append(Character.toUpperCase(c));
                stringBuilder.append(splits[i].substring(1));
            }
        }
        
        String result = stringBuilder.toString();
        return result;
    }

    /**
     * upper-camelケースに変換
     * 
     * @param stringValue
     * @return
     */
    public static String toUpperCamelCase(String stringValue)
    {
        // _ で分割しておく
        String[] splits = stringValue.split("_");
        
        // 回しながら append
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < splits.length; i++)
        {
            String part = splits[i];
            if (splits.length > 1)
            {
                part = part.toLowerCase();
            }

            char c = part.charAt(0);
            stringBuilder.append(Character.toUpperCase(c));
            stringBuilder.append(part.substring(1));
        }
        
        String result = stringBuilder.toString();
        return result;
    }
    
    /**
     * クラス名を分割する
     * <br />
     * <br />HogeNameEntity なら、Hoge Name Entityに分割される
     * 
     * @param clazz
     * @return
     */
    public static <T> List<String> splitClassName(Class<T> clazz)
    {
        return CPStringUtils.splitClassName(clazz.getSimpleName());
    }

    /**
     * クラス名を分割する
     * <br />
     * <br />HogeNameEntity なら、Hoge Name Entityに分割される
     * 
     * @param className
     * @return
     */
    public static List<String> splitClassName(String className)
    {
        List<String> results = new ArrayList<String>(4);
        int length = className.length();
        int start = 0;
        int end = 0;
        for (int i = 0; i < length; i++)
        {
            if (Character.isUpperCase(className.charAt(i)) == true)
            {
                end = i;
                if (end > start)
                {
                    results.add(className.substring(start, end));
                }
                start = i;
            }
        }
        results.add(className.substring(start));
        return results;
    }
    
    /**
     * 文字列の中で最大値を取得
     * 
     * @param values
     * @return
     */
    public static int maxLength(String[] values)
    {
        int max = 0;
        for (String val : values)
        {
            int length = val.length();
            if (max < length)
            {
                max = length;
            }
        }
        return max;
    }
    
    /**
     * 文字列の中で最小値を取得
     * 
     * @param values
     * @return
     */
    public static int minLength(String[] values)
    {
        int min = Integer.MAX_VALUE;
        for (String val : values)
        {
            int length = val.length();
            if (min > length)
            {
                min = length;
            }
        }
        return min;
    }
    
    /**
     * 数値を文字列化する、0の場合は""に
     * 
     * @param value
     */
    public static String toStringZeroToBlank(Integer value)
    {
        String result = value.toString();
        if (result.equals("0") == true)
        {
            result = "";
        }
        return result;
    }
}
