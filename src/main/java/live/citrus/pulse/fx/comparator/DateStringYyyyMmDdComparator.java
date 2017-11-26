package live.citrus.pulse.fx.comparator;

import java.util.Comparator;

import live.citrus.pulse.variable.date.CPDateUtils;

/**
 * 日付文字列のソート用
 * 
 * @author take64
 *
 */
public class DateStringYyyyMmDdComparator implements Comparator<String>
{
    public int compare(String o1, String o2)
    {
        if (o1 == null || o2 == null || o1.equals("") == true || o2.equals("") == true)
        {
            return 1;
        }
        return CPDateUtils.parse("yyyy-MM-dd", o1).compareTo(CPDateUtils.parse("yyyy-MM-dd", o2));
    }
}
