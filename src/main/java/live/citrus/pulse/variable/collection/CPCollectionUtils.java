package live.citrus.pulse.variable.collection;

import java.lang.reflect.Field;
import java.util.List;

import live.citrus.pulse.log.CPLogger;

/**
 * コレクションユーティリティー
 * 
 * @author take64
 *
 */
public class CPCollectionUtils
{
    /**
     * コレクション内の検索
     * @param <T>
     * 
     * @param collectionList
     * @param searchKey
     * @param compareValue
     * @return
     */
    public static <T> T search(List<T> collectionList, String searchKey, Object compareValue)
    {
        T result = null;
        try
        {
            for(T one : collectionList)
            {
                Field field = one.getClass().getField(searchKey);
                Object fieldValue = field.get(one);
                if(fieldValue.equals(compareValue) == true)
                {
                    result = one;
                    break;
                }
            }
        }
        catch(Exception e)
        {
            CPLogger.debug(e);
        }
        return result;
    }
}
