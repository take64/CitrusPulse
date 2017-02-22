package live.citrus.pulse.bean;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;

import live.citrus.pulse.log.CPLogger;
import live.citrus.pulse.variable.string.CPStringUtils;

@SuppressWarnings("serial")
public class CPBean implements Serializable
{

    public CPBean(HashMap<String, Object> map)
    {
        // フィールドを取得し動的設定
        Field[] fields = this.getClass().getFields();
        for(Field field : fields)
        {
            try
            {
                String fieldKey = field.getName();
                fieldKey = CPStringUtils.toSnakeCase(fieldKey);
                
                Object value = map.get(fieldKey);
                field.set(this, String.valueOf(value));
            }
            catch (Exception e)
            {
                CPLogger.debug(e);
            }
        }
    }
    
    /**
     * to string
     */
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        
        Field[] fields = this.getClass().getFields();
        for(Field field : fields)
        {
            String fieldKey = field.getName();
            Object fieldVal;
            try
            {
                fieldVal = field.get(this);
            }
            catch (Exception e)
            {
                CPLogger.debug(e);
                fieldVal = "NULL";
            }
            stringBuilder.append(fieldKey).append(":").append(String.valueOf(fieldVal)).append(", ");
        }

        String result = stringBuilder.toString();
        return result;
    }
}
