package live.citrus.pulse.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import live.citrus.pulse.log.CPLogger;

public class CPFile
{
    /**
     * ファイルを読み込んで文字列で返す
     * 
     * @param filepath
     * @return
     * @throws IOException
     */
    public static String loadText(String filepath)
    {
        File file = new File(filepath);
        StringBuilder stringBuilder = new StringBuilder();
        
        try
        {
            if(CPFile.canReadable(file) == true)
            {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line;
                while((line = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                fileReader.close();            
            }
        }
        catch (Exception e)
        {
            CPLogger.debug(e);
        }
        
        String result = stringBuilder.toString();
        return result;
    }
    
//    /**
//     * 文字列をファイルに保存
//     * 
//     * @param filepath
//     * @param contentString
//     */
//    public static void saveText(URL filepath, String contentString)
//    {
//        try
//        {
//
//            CPLogger.debug("==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ");
//            CPLogger.debug(filepath.getPath());
////            CPLogger.debug(filepath.getScheme());
//            CPLogger.debug(filepath.getHost());
//            CPLogger.debug(filepath.getUserInfo());
//            CPLogger.debug(filepath.getQuery());
////            CPLogger.debug(filepath.toURL().toString());
//            
//            CPLogger.debug("==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ==== ");
//        }
//        catch(Exception e)
//        {
//            
//        }
//        
////        File dir = new File(dirpath);
////        String filepath = dirpath + filename;
////
////        if(dir.exists() == false)
////        {
////            dir.mkdirs();
////        }
////        CPFile.saveText(filepath, contentString);
//    }
    
    /**
     * 文字列をファイルに保存
     * 
     * @param dirpath
     * @param filename
     * @param contentString
     */
    public static void saveText(String dirpath, String filename, String contentString)
    {
        File dir = new File(dirpath);
        String filepath = dirpath + filename;

        if(dir.exists() == false)
        {
            dir.mkdirs();
        }
        CPFile.saveText(filepath, contentString);
    }
    
    /**
     * ファイルの保存
     * 
     * @param filepath
     * @param contentString
     * @return
     */
    public static void saveText(String filepath, String contentString)
    {
        File file = new File(filepath);

        boolean result = false;
        try
        {
            if(CPFile.canWritable(file) == true)
            {
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                
                bufferedWriter.write(contentString);
                
                bufferedWriter.close();
                fileWriter.close();
                
                result = true;
            }
        }
        catch (Exception e)
        {
            CPLogger.debug(e);
        }

        String status = "success";
        if(result == false)
        {
            status = "failed ";
        }
        CPLogger.debug("file save [ " + status + " ] => " + filepath + " : " + CPFileSize.sizeString(contentString));
    }
    
    
    /**
     * ファイルが読み込めるか
     * 
     * @param file
     * @return
     */
    public static boolean canReadable(File file)
    {
        boolean result = false;
        if(file.exists() == true)
        {
            if(file.isFile() == true && file.canRead() == true)
            {
                result = true;
            }
        }
        return result;
    }
    

    /**
     * ファイルが書き込めるか
     * 
     * @param file
     * @return
     * @throws IOException 
     */
    public static boolean canWritable(File file) throws IOException
    {
        boolean result = false;
        file.createNewFile();
        if(file.exists() == true)
        {
            if(file.isFile() == true && file.canWrite() == true)
            {
                result = true;
            }
        }
        return result;
    }
    
    
    /**
     * ファイルもしくはディレクトリの存在チェック
     * 
     * @param filepath
     * @return
     */
    public static boolean exist(String filepath)
    {
        File file = new File(filepath);
        return file.exists();
    }
    
    
    /**
     * ファイルもしくはディレクトリの存在チェック(複数一括チェック)
     * 
     * @param filepaths
     * @return
     */
    public static boolean exists(String[] filepaths)
    {
        List<String> list = new ArrayList<String>(filepaths.length);
        for(String filepath : filepaths)
        {
            list.add(filepath);
        }
        
        return CPFile.exists(list);
    }
    
    /**
     * ファイルもしくはディレクトリの存在チェック(複数一括チェック)
     * 
     * @param filepaths
     * @return
     */
    public static boolean exists(List<String> filepaths)
    {
        boolean result = true;
        for(String filepath : filepaths)
        {
            if(CPFile.exist(filepath) == false)
            {
                CPLogger.debug("file not found : " + filepath);
                result = false;
                break;
            }
        }
        return result;
    }
}
