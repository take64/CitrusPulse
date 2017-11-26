package live.citrus.pulse.variable.numeric;

import java.math.BigDecimal;

/**
 * Decimal関係のユーティリティ
 * 
 * @author take64
 *
 */
public class CPDecimalUtils
{
    /**
     * 指定幅に収まる様にスケーリングする
     * 
     * @param bigDecimal
     * @param length
     * @return
     */
    public static BigDecimal scalingWithLength(BigDecimal bigDecimal, int length)
    {
        return CPDecimalUtils.scalingWithLength(bigDecimal, 3, length, BigDecimal.ROUND_HALF_UP);
    }
    
    
    /**
     * 指定幅に収まる様にスケーリングする
     * 
     * @param bigDecimal
     * @param length
     * @param roundingMode
     * @return
     */
    public static BigDecimal scalingWithLength(BigDecimal bigDecimal, int maxScale, int length, int roundingMode)
    {
        int width = 0;
        // 以上系
        if (bigDecimal.compareTo(new BigDecimal(999)) == 1)
        {
            width = 3;
        }
        else if (bigDecimal.compareTo(new BigDecimal(99)) == 1)
        {
            width = 2;
        }
        else if (bigDecimal.compareTo(new BigDecimal(9)) == 1)
        {
            width = 1;
        }
        else if (bigDecimal.compareTo(new BigDecimal(-999)) == -1)
        {
            width = 5;
        }
        else if (bigDecimal.compareTo(new BigDecimal(-99)) == -1)
        {
            width = 4;
        }
        else if (bigDecimal.compareTo(new BigDecimal(-9)) == -1)
        {
            width = 3;
        }
        int newScale = length - width;
        
        newScale = Math.min(newScale, maxScale);
        
        return bigDecimal.setScale(newScale, roundingMode);
    }

}
