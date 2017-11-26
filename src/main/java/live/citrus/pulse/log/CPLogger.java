package live.citrus.pulse.log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CPLogger
{
    public static SimpleDateFormat dateFormar = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
    
    public static void debug()
    {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        CPLogger.debug(elements[2].toString());
    }
    
    public static void debug(String logString)
    {
        Date date = new Date();
        System.out.println("[" + CPLogger.dateFormar.format(date) + "] " + logString);
    }
    public static void debug(String format, Object ... args)
    {
        System.out.printf(format, args);
    }
    public static void debug(Exception e)
    {
        e.printStackTrace();
    }
    public static void debugJson(String jsonString)
    {
        String replaced = jsonString;
        replaced = replaced.replaceAll("\"", "");
        replaced = replaced.replaceAll("\\{", "\\{\n");
        replaced = replaced.replaceAll("\\[", "\\[\n");
        replaced = replaced.replaceAll(",", ",\n");
        replaced = replaced.replaceAll("\\},", "\n\\},");
        replaced = replaced.replaceAll("\\],", "\n\\],");
        replaced = replaced.replaceAll("\\}\n", "\n\\}\n");
        replaced = replaced.replaceAll("\\]\n", "\n\\]\n");
        String[] splits = replaced.split("\n");
        
        StringBuilder stringBuilder = new StringBuilder();
        int indent = 0;
        for (String split : splits)
        {
            if ((split.startsWith("]") == true) || (split.endsWith("]") == true)
                    || (split.startsWith("}") == true) || (split.endsWith("}") == true))
            {
                indent--;
            }
            
            for (int i = 0; i < indent; i++)
            {
                stringBuilder.append("    ");
            }
            stringBuilder.append(split);
            stringBuilder.append("\n");
            
            if (split.startsWith("[") == true || split.endsWith("[") == true
                    || split.startsWith("{") == true || split.endsWith("{") == true)
            {
                indent++;
            }
        }
        
        CPLogger.debug(stringBuilder.toString());
    }
}
