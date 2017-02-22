package live.citrus.pulse.base;

public class CPEnvironment
{
    /**
     * ユーザーのホームパス
     * 
     * @return
     */
    public static String callHomePath()
    {
        String path = System.getProperty("user.home") + "/"; 
        return path;
    }
    
    /**
     * アプリの実行パス
     * 
     * @return
     */
    public static String callExecPath()
    {
        String[] paths = System.getProperty("java.class.path").split(":"); 
        return paths[0];
    }
}
