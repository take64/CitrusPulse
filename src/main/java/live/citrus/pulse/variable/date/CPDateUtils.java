package live.citrus.pulse.variable.date;

import live.citrus.pulse.log.CPLogger;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日付ユーティリティ
 * 
 * <br />format => http://docs.oracle.com/javase/jp/6/api/java/text/SimpleDateFormat.html
 * 
 * @author take64
 * 
 */
public class CPDateUtils
{
    /** フォーマットキャッシュ **/
    private static Map<String, SimpleDateFormat> formatDates = new HashMap<String, SimpleDateFormat>();

    /** フォーマットキャッシュ **/
    private static Map<String, DateTimeFormatter> formatLocalDates = new HashMap<String, DateTimeFormatter>();
    
    /**
     * 現在時間をフォーマット済み文字列で取得
     * 
     * @param formatString
     * @return
     */
    public static String now(String formatString)
    {
        Date date = new Date();
        
        String result = CPDateUtils.format(formatString, date);
        
        return result;
    }

    /**
     * 指定時間をフォーマット済み文字列で取得
     * 
     * @param formatString
     * @param date
     * @return
     */
    public static String format(String formatString, Date date)
    {
        SimpleDateFormat format = CPDateUtils.callFormatDate(formatString);
        
        String result = format.format(date);
        
        return result;
    }

    /**
     * 指定時間をフォーマット済み文字列で取得
     * 
     * @param formatString
     * @param date
     * @return
     */
    public static String format(String formatString, LocalDate date)
    {
        DateTimeFormatter format = CPDateUtils.callFormatLocalDate(formatString);
        
        String result = "";
        if(date != null)
        {
            result = format.format(date);
        }
        
        return result;
    }
    
    /**
     * 日付文字列をパース
     * 
     * @param formatString
     * @param dateString
     * @return
     */
    public static Date parse(String formatString, String dateString)
    {
        SimpleDateFormat format = CPDateUtils.callFormatDate(formatString);
        Date date = null;
        try
        {
            date = format.parse(dateString);
        }
        catch (Exception e)
        {
            CPLogger.debug(e);
        }
        if(date == null)
        {
            date = new Date();
        }
        return date;
    }
    
    /**
     * キャッシュ済みフォーマットの取得
     * 
     * @param formatString
     * @return
     */
    public static SimpleDateFormat callFormatDate(String formatString)
    {
        SimpleDateFormat format = CPDateUtils.formatDates.get(formatString);
        
        if(format == null)
        {
            format = new SimpleDateFormat(formatString);
            CPDateUtils.formatDates.put(formatString, format);
        }
        
        return format;
    }
    
    /**
     * キャッシュ済みフォーマットの取得
     * 
     * @param formatString
     * @return
     */
    public static DateTimeFormatter callFormatLocalDate(String formatString)
    {
        DateTimeFormatter format = CPDateUtils.formatLocalDates.get(formatString);
        
        if(format == null)
        {
            
            format = DateTimeFormatter.ofPattern(formatString);
            CPDateUtils.formatLocalDates.put(formatString, format);
        }
        
        return format;
    }

    /**
     * 日付の加算
     * 
     * @param date
     * @param amount
     * @return
     */
    public static Date addDay(Date date, int amount)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, amount);
        return calendar.getTime();
    }

    /**
     * 分の加算
     * 
     * @param date
     * @param amount
     * @return
     */
    public static Date addMinute(Date date, int amount)
    {
        return CPDateUtils.addSecond(date, (amount * 60));
    }

    /**
     * 秒の加算
     * 
     * @param date
     * @param amount
     * @return
     */
    public static Date addSecond(Date date, int amount)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, amount);
        return calendar.getTime();
    }

    /**
     * n分後
     * 
     * @param minute
     * @return
     */
    public static Date afterMinute(int minute)
    {
        return CPDateUtils.afterSecond((minute * 60));
    }

    /**
     * n秒後
     * 
     * @param second
     * @return
     */
    public static Date afterSecond(int second)
    {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }
    
    /**
     * 日付の差分を文字列で取得
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static String diffString(Date date1, Date date2)
    {
        return CPDateUtils.timeString(date1.getTime() - date2.getTime());
    }
    
    /**
     * 時間数値を文字列に変更
     * 
     * @param millisecond
     * @return
     */
    public static String timeString(long millisecond)
    {
        long second = millisecond / 1000;
        return String.format("%d:%02d:%02d", (second / 3600), ((second / 60) % 60), (second % 60));
    }
    
    /**
     * yyyyMMdd日付文字列をyyyy-MM-dd日付文字列に
     * 
     * @param dateString
     * @return
     */
    public static String joinHyphen(String dateString)
    {
        String yyyy = dateString.substring(0, 4);
        String MM = dateString.substring(4, 6);
        String dd = dateString.substring(6, 8);
        return yyyy + "-" + MM + "-" + dd;
    }
    
    /**
     * 時間数値を文字列に変更
     * 
     * @param millisecond
     * @return
     */
    public static String timeString(String format, long millisecond)
    {
        long second = millisecond / 1000;
        long minute = ((second / 60) % 60);
        long hour = (second / 3600) % 24;
        long day = (second / (3600 * 24));
        return String.format(format, (second % 60), minute, hour, day);
    }
    
    /**
     * Date を LocalDate に変換
     * 
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(Date date)
    {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zone).toLocalDate();
        return localDate;
    }
    
}
