package live.citrus.pulse.variable.numeric;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class CPNumericUtils
{
    /** フォーマットキャッシュ **/
    public static Map<String, DecimalFormat> formats = new HashMap<String, DecimalFormat>();
    
    /**
     * 加算( Integer )
     * 
     * @param integer1
     * @param integer2
     * @return
     */
    public static Integer plus(Integer value1, Integer value2)
    {
        int result = value1.intValue() + value2.intValue();
        return Integer.valueOf(result);
    }
    
    /**
     * 加算( Double )
     * 
     * @param integer1
     * @param integer2
     * @return
     */
    public static Double plus(Double value1, Double value2)
    {
        BigDecimal result = (new BigDecimal(value1)).add(new BigDecimal(value2));
        return Double.valueOf(result.doubleValue());
    }
    
    /**
     * 減算( Integer )
     * 
     * @param integer1
     * @param integer2
     * @return
     */
    public static Integer minus(Integer value1, Integer value2)
    {
        int result = value1.intValue() - value2.intValue();
        return Integer.valueOf(result);
    }
    
    /**
     * パーセントの算出
     * 
     * @param numerator 分子
     * @param denominator 分母
     * @return
     */
    public static BigDecimal percent(Integer numerator, Integer denominator, int scale)
    {
        double percent = (numerator.doubleValue() / denominator.doubleValue());
        BigDecimal decimal = new BigDecimal(percent);
        decimal.setScale(scale, BigDecimal.ROUND_HALF_UP);
        return decimal;
    }
    
    /**
     * パーセントの算出(100掛けておく)
     * 
     * @param numerator 分子
     * @param denominator 分母
     * @return
     */
    public static BigDecimal percentX100(Integer numerator, Integer denominator, int scale)
    {
        double percent = (numerator.doubleValue() / denominator.doubleValue()) * 100;
        BigDecimal decimal = new BigDecimal(percent);
        decimal.setScale(scale, BigDecimal.ROUND_HALF_UP);
        return decimal;
    }
    
    /**
     * 数値からフォーマット文字列を取得
     * 
     * @param int
     * @return
     */
    public static String format(int integer)
    {
        return CPNumericUtils.format("#,###", Integer.valueOf(integer));
    }
    
    /**
     * 数値からフォーマット文字列を取得
     * 
     * @param integer
     * @return
     */
    public static String format(Integer integer)
    {
        return CPNumericUtils.format("#,###", integer);
    }
    
    /**
     * 浮動小数点数値からフォーマット文字列を取得
     * 
     * @param doubleValue
     * @return
     */
    public static String format(Double doubleValue)
    {
        return CPNumericUtils.format("#,###.###", doubleValue);
    }
    
    /**
     * 数値からフォーマット文字列を取得
     * 
     * @param formatString
     * @param integer
     * @return
     */
    public static String format(String formatString, Integer integer)
    {
        DecimalFormat format = CPNumericUtils.callFormat(formatString);
        
        return format.format(integer.doubleValue());
    }
    
    /**
     * 浮動小数点数値からフォーマット文字列を取得
     * 
     * @param formatString
     * @param doubleValue
     * @return
     */
    public static String format(String formatString, Double doubleValue)
    {
        DecimalFormat format = CPNumericUtils.callFormat(formatString);
        
        return format.format(doubleValue.doubleValue());
    }

    /**
     * キャッシュ済みフォーマットの取得
     * 
     * @param formatString
     * @return
     */
    public static DecimalFormat callFormat(String formatString)
    {
        DecimalFormat format = CPNumericUtils.formats.get(formatString);
        
        if(format == null)
        {
            format = new DecimalFormat(formatString);
            CPNumericUtils.formats.put(formatString, format);
        }
        
        return format;
    }
}
