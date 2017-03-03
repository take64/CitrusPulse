package live.citrus.pulse.fx.comparator;

import java.util.Comparator;

/**
 * 数字文字列のソート用
 * 
 * @author take64
 *
 */
public class IntegerStringComparator implements Comparator<String>
{
    public int compare(String o1, String o2)
    {
        if(o1.equals("") == true) { o1 = "0"; }
        if(o2.equals("") == true) { o2 = "0"; }
        return Integer.valueOf(o1).compareTo(Integer.valueOf(o2));
    }
}
