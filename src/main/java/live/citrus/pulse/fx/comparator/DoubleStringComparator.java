package live.citrus.pulse.fx.comparator;

import java.util.Comparator;

/**
 * 浮動小数点文字列のソート
 * 
 * @author take64
 *
 */
public class DoubleStringComparator implements Comparator<String>
{
    public int compare(String o1, String o2)
    {
        if(o1.equals("") == true) { o1 = "0.0"; }
        if(o2.equals("") == true) { o2 = "0.0"; }
        return Double.valueOf(o1).compareTo(Double.valueOf(o2));
    }
}
