package live.citrus.pulse.base;

import java.io.File;

import live.citrus.pulse.log.CPLogger;

/**
 * デレゲート
 * 
 * @author take64
 *
 */
public abstract class CPDelegate
{
//    public static String appPath = CPEnvironment.callExecPath();
//    public static String homePath = CPEnvironment.callHomePath();
    
    /** アプリディレクトリ **/
    public static String appDir;
    
    /**
     * アプリのmain(String[] args)から移譲してくる
     * <br />
     * <br />アプリのディレクトリなどを取得
     * 
     * @param clazz
     * @param args
     */
    public static void launch(Class<?> clazz, String[] args)
    {
        // classファイルタイプ
        // classFileName: /net/besidesplus/java/fgfp/gui/AppMain.class
        // classFilePath: /Users/take64/Dropbox/Workspace/Java/fgfp/target/classes/net/besidesplus/java/fgfp/gui/AppMain.class
        // jarFilePath: /Users/take64/Dropbox/Workspace/Java/fgfp/target/classes/net/besidesplus/java/fgfp/gui/AppMain.class
        // jarFileName: AppMain

        // jarファイルタイプ
        // classFileName: /net/besidesplus/java/fgfp/gui/AppMain.class
        // classFilePath: file:/Users/take64/Dropbox/Workspace/Java/fgfp/build/dist/fgfp.jar!/net/besidesplus/java/fgfp/gui/AppMain.class
        // jarFilePath: file:/Users/take64/Dropbox/Workspace/Java/fgfp/build/dist/fgfp.jar
        // jarFileName: fgfp

        final String classFileName = "/" + clazz.getName().replaceAll("\\.", "/") + ".class";
        CPLogger.debug("classFileName: " + classFileName);
        
        final String classFilePath = clazz.getResource(classFileName).getPath();
        CPLogger.debug("classFilePath: " + classFilePath);
        
        final File jarFilePath = new File(classFilePath.replaceFirst("!/.*$", ""));
        CPLogger.debug("jarFilePath: " + jarFilePath);
        
        final String jarFileName = jarFilePath.getName().replaceFirst("\\..+$", "");
        CPLogger.debug("jarFileName: " + jarFileName);
        
        // アプリディレクトリの取得
        String appDir = "";
        // jarファイルタイプ
        if (jarFilePath.toString().endsWith(".jar") == true)
        {
            appDir = jarFilePath.toString().replaceAll(jarFileName + ".jar", "");
        }
        // classファイルタイプ
        else if (jarFilePath.toString().endsWith(".class") == true)
        {
            appDir = jarFilePath.toString().replaceAll(classFileName, "/");
        }
        CPDelegate.appDir = appDir;
        CPLogger.debug("appDir: " + appDir);
    }
    
    /**
     * アプリのスタート処理
     */
    public abstract void startup();
}
