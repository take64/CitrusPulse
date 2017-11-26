package live.citrus.pulse.database.object;

import live.citrus.pulse.file.CPFile;
import live.citrus.pulse.log.CPLogger;
import live.citrus.pulse.variable.string.CPStringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CPObjectDatabaseManager
{
    /** 保持テーブル **/
    private Map<String, CPObjectDatabaseTable> tables;
    
    /** データ保存ディレクトリ **/
    private String directory;
    
    /** データ保存時のテーブル接頭辞 **/
    private Map<String, String> tableMapping;




    /**
     * directory
     * 
     * @param directory
     * @param tableMapping
     */
    public CPObjectDatabaseManager(String directory, Map<String, String> tableMapping)
    {
        this.directory = directory;
        this.tables = Collections.synchronizedMap(new HashMap<String, CPObjectDatabaseTable>());
        this.tableMapping = tableMapping;
    };

    /**
     * テーブル呼び出し、なければ作成
     * @param <T>
     * @param clazz
     */
    public <T> CPObjectDatabaseTable callTable(Class<T> clazz)
    {
        return this.callTable(clazz.getSimpleName());
    }

    /**
     * テーブル呼び出し、なければ作成
     * 
     * @param entityName
     */
    private CPObjectDatabaseTable callTable(String entityName)
    {
        // 既存テーブルを取得
        CPObjectDatabaseTable table = this.tables.get(entityName);
        
        // なければ生成
        if (table == null)
        {
            // 生成
            table = new CPObjectDatabaseTable(entityName);
            table.manager = this;

            // 設定
            this.tables.put(entityName, table);   
        }
        
        // 返却
        return table;
    }

    /**
     * テーブル利用可能か確認
     * 
     * @param tableName
     */
    public boolean availableTable(String tableName)
    {
        boolean result = true;
        
        // テーブルを取得
        CPObjectDatabaseTable table = this.tables.get(tableName);
        
        // テーブルがない
        if (table == null)
        {
             result = false;
        }
        // テーブルはあるが、処理中
        else if (table.callProcess() == true)
        {
            result = false;
        }
        
        // 返却
        return result;
    }



    /**
     * テーブル利用可能か確認
     *
     * @param tableNames
     */
    public boolean availableTable(String[] tableNames)
    {
        boolean result = true;
        
        // はぁ〜、回レ回レ回レ回レ回レ回レ回レ回レ回レ
        for (String tableName : tableNames)
        {
            if (this.availableTable(tableName) == false)
            {
                result = false;
                break;
            }
        }
        
        // 返却
        return result;
    }
    
    /**
     * テーブルデータをJSONファイルから取得
     * 
     * @param clazz : recordクラス
     */
    public void loadJSONFile(Class<?> clazz)
    {
        CPLogger.debug("database load start : " + clazz.getSimpleName());
        try
        {            
            // entity名を取得
            String entityName = clazz.getSimpleName();  
            
            // ファイルパス取得
            String filepath = this.callJSONFilepath(entityName);
            
            // ファイル内のJSON文字列を読み込む
            String jsonString = CPFile.loadText(filepath);
            
            // 対象テーブルの取得
            CPObjectDatabaseTable table = this.callTable(entityName);
            table.replaceJSONSource(jsonString);
            
            // テーブルにデータをbind
            this.bindJSONString(clazz);
        }
        catch(Exception e)
        {
            CPLogger.debug(e);
        }
        CPLogger.debug("database load e n d : " + clazz.getSimpleName());
    }
    
    /**
     * テーブルに保持してあるデータをbind
     * 
     * @param clazz
     */
    public void bindJSONString(Class<?> clazz)
    {
        // entity名を取得
        String entityName = clazz.getSimpleName();
        
        // 対象テーブルの取得
        CPObjectDatabaseTable table = this.callTable(entityName);
        
        // JSONString
        String jsonString = table.callJSONSource();
        
        // 対象テーブルを一旦空にする
        table.callRecords().clear();
        // ロックする
        table.startProcess();
        
        try
        {            
            // JSONObject
            if (jsonString.startsWith("{") == true)
            {
                JSONObject jsonObject = new JSONObject(jsonString);
                this.parseJSONObject(jsonObject, table, clazz);
            }
            // JSONArray
            else if (jsonString.startsWith("[") == true)
            {
                JSONArray jsonArray = new JSONArray(jsonString);

                int length = jsonArray.length();
                for (int i = 0; i < length; i++)
                {
                    this.parseJSONObject(jsonArray.getJSONObject(i), table, clazz);
                }
            }
        }
        catch(Exception e)
        {
            CPLogger.debug(e);
        }
        table.endProcess();
    }
    
    /**
     * JSONObjectのパース
     * 
     * @param jsonObject
     * @param table
     * @param clazz
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    private <T> void parseJSONObject(JSONObject jsonObject, CPObjectDatabaseTable table, Class<T> clazz) throws InstantiationException, IllegalAccessException
    {
        CPObjectDatabaseRecord record = (CPObjectDatabaseRecord)clazz.newInstance();
        
        // カラム数制約
        CPObjectDatabaseCallback callback = record.callLoadingCallback();
        if (callback != null && callback.validate(jsonObject) == false)
        {
            record = null;
            return;
        }
        
        record.bindFromJSON(jsonObject);
        record.columnComplete();
        if (record.enabled() == true)
        {
            table.addRecord(record);
        }
    }
    
    /**
     * JSONファイルにテーブルデータを保存
     * 
     * @param clazz
     */
    public <T> void saveJSONFile(Class<T> clazz)
    {
        this.saveJSONFile(clazz, "");
    }
    
    /**
     * JSONファイルにテーブルデータを保存
     * 
     * @param clazz
     * @param suffix
     */
    public <T> void saveJSONFile(Class<T> clazz, String suffix)
    {
        // entity名を取得
        String entityName = clazz.getSimpleName();
        
        // ファイルパスを取得
        String filepath = this.callJSONFilepath(entityName);
        String filename = clazz.getSimpleName().replace("Entity", "") + ".json";
        String dirpath = filepath.replace(filename, "");
        filename = filename.replace(".json", suffix + ".json");

        CPLogger.debug("database save start : " + filepath);
        try
        {
            JSONArray jsonArray = this.callTable(entityName).toJSONArray();
            if (jsonArray.length() == 0)
            {
                throw new Exception("json empty");
            }
            CPFile.saveText(dirpath, filename, jsonArray.toString());
        }
        catch(Exception e)
        {
            CPLogger.debug(e);
        }
      
        CPLogger.debug("database save e n d : " + filepath);
    }
    
    /**
     * JSONファイルにソースデータを保存
     * 
     * @param clazz
     * @param suffix
     */
    public <T1> void saveJSONSource(Class<T1> clazz, String suffix)
    {
        // entity名を取得
        String entityName = clazz.getSimpleName();
        
        // ファイルパスを取得
        String filepath = this.callJSONFilepath(entityName);
        String filename = clazz.getSimpleName().replace("Entity", "") + ".json";
        String dirpath = filepath.replace(filename, "");
        filename = filename.replace(".json", suffix + ".json");

        CPLogger.debug("database save start : " + filepath);
        try
        {
            String jsonString = this.callTable(entityName).callJSONSource();
            if (jsonString == null)
            {
//                this.callTable(entityName).replaceJSONSource(this.callTable(entityName).toJSONArray().toString());
                this.callTable(entityName).replaceToJSONSource();
            }
            CPFile.saveText(dirpath, filename, this.callTable(entityName).callJSONSource());
        }
        catch(Exception e)
        {
            CPLogger.debug(e);
        }
      
        CPLogger.debug("database save e n d : " + filepath);
    }
    
    /**
     * JSONファイルにソースデータをバックアップ保存
     * 
     * @param fromClazz
     * @param toClazz
     * @param suffix
     */
    public <T1, T2> void backupJSONSource(Class<T1> fromClazz, Class<T2> toClazz, String suffix)
    {
        // entity名を取得
        String fromEntityName = fromClazz.getSimpleName();
        String toEntityName = toClazz.getSimpleName();
        
        // ファイルパスを取得
        String toFilepath = this.callJSONFilepath(toEntityName);
        String toFilename = toClazz.getSimpleName().replace("Entity", "") + ".json";
        String toDirpath = toFilepath.replace(toFilename, "");
        toFilename = toFilename.replace(".json", suffix + ".json");

        CPLogger.debug("database save start : " + toFilepath);
        try
        {
            CPFile.saveText(toDirpath, toFilename, this.callTable(fromEntityName).callJSONSource());
        }
        catch(Exception e)
        {
            CPLogger.debug(e);
        }
      
        CPLogger.debug("database save e n d : " + toFilepath);
    }
    
    /**
     * クラスからファイルパスを取得する
     * 
     * @param clazz
     */
    public String callJSONFilepath(Class<?> clazz)
    {
        return this.callJSONFilepath(clazz.getSimpleName());
    }
    
    /**
     * クラス名からファイルパスを取得する
     * 
     * @param className
     */
    public String callJSONFilepath(String className)
    {
        // entity名分割
        List<String> entityNameParts = CPStringUtils.splitClassName(className);
        
        // ディレクトリ名取得
        String dirPath = this.directory + this.tableMapping.get(entityNameParts.get(0)) + "/";
        
        // ファイル名取得
        StringBuilder stringBuilder = new StringBuilder();
        int length = entityNameParts.size();
        for (int i = 0; i < (length - 1); i++)
        {
            stringBuilder.append(entityNameParts.get(i));
        }
        String filename = stringBuilder.toString();
        
        // ファイルパス取得
        String filepath = dirPath + filename + ".json";
        
        return filepath;
    }
    
    /**
     * プライマリキー自動生成
     * 
     * @return
     */
    public static String generatePrimaryKey()
    {
        String result = RandomStringUtils.randomAlphanumeric(32);
        return result;
    }
}
