package live.citrus.pulse.database.object;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import live.citrus.pulse.log.CPLogger;

public class CPObjectDatabaseTable
{
    /** 管理マネージャー **/
    public CPObjectDatabaseManager manager;
    
    /** テーブル名 **/
    public String tableName;
    
    /** 保持レコード **/
    private Map<Object, CPObjectDatabaseRecord> records;
    
    /** 元JSONデータ **/
    private String jsonSourceString;

    /** 処理中 **/
    private boolean processing = false;
    

    /**
     * constructor
     */
    public CPObjectDatabaseTable(String tableName)
    {
        this.tableName = tableName;
        this.records = Collections.synchronizedMap(new HashMap<Object, CPObjectDatabaseRecord>());
        
//        this.processing = true;
    };
    
    
    /**
     * add record
     * 
     * @param record
     */
    public void addRecord(CPObjectDatabaseRecord record)
    {
        record.columnComplete();
        // プライマリキーが無い場合は自動生成
        if(record.primaryKey == null)
        {
            record.primaryKey = CPObjectDatabaseManager.generatePrimaryKey();
            record.setTable(this);
        }
        // ロックして登録
        synchronized(this.records)
        {
            this.records.put(record.primaryKey, record);
        }
    }

    /**
     * add record(s)
     * 
     * @param records
     */
    public void addRecords(List<? extends CPObjectDatabaseRecord> records)
    {
        for(CPObjectDatabaseRecord one : records)
        {
            this.addRecord(one);
        }
    }

    /**
     * remove record
     */
    public void removeRecord(Object primaryKey)
    {
        // ロックして削除
        synchronized(this.records)
        {
            this.records.remove(primaryKey);
        }
    }
    
    /**
     * 処理開始
     */
    public void startProcess()
    {
        this.processing = true;
    }
    
    /**
     * 処理終了
     */
    public void endProcess()
    {
        this.processing = false;
    }
    
    /**
     * 処理中？
     */
    public boolean callProcess()
    {
        return this.processing;
    }
    
    /**
     * call record
     * 
     * @param primaryKey
     * @return
     */
    public CPObjectDatabaseRecord callRecord(Object primaryKey)
    {
//        CPLoggerTimer.start();
        CPObjectDatabaseRecord record = this.callRecords().get(primaryKey);
//        CPLoggerTimer.end();
        return record;
    }
    
    /**
     * call record(s)
     * 
     * @return
     */
    public Map<Object, CPObjectDatabaseRecord> callRecords()
    {
        boolean available = (this.callProcess() == false);
        while(available == false)
        {
            CPLogger.debug(this.tableName + "のデータ処理を待機しています。");
            
            try { Thread.sleep(500); } catch(Exception e) { CPLogger.debug(e); }
            
            available = (this.callProcess() == false);
        }
        
        return this.records;
    }

    /**
     * search record(s)
     * 
     * @param key
     * @param value
     * @return
     */
    public List<CPObjectDatabaseRecord> searchRecords(String key, Object value)
    {
        List<CPObjectDatabaseRecord> results = new ArrayList<CPObjectDatabaseRecord>(4);
        Collection<CPObjectDatabaseRecord> entities = this.callRecords().values();
        try
        {
            for(CPObjectDatabaseRecord one : entities)
            {
                Field field = one.getClass().getField(key);
                Object fieldValue = field.get(one);
                if(value.equals(fieldValue) == true)
                {
                    results.add(one);
                }
            }
        }
        catch(Exception e)
        {
            CPLogger.debug(e);
        }
        return results;
    }

    /**
     * search record(s)
     * 
     * @param conditions
     * @return
     */
    public List<CPObjectDatabaseRecord> searchRecords(Map<String, Object> conditions)
    {
        List<CPObjectDatabaseRecord> results = new ArrayList<CPObjectDatabaseRecord>(4);
        Collection<CPObjectDatabaseRecord> entities = this.callRecords().values();
        try
        {
            for(CPObjectDatabaseRecord one : entities)
            {
                boolean result = false;
                for(Entry<String, Object> entry : conditions.entrySet())
                {
                    Field field = one.getClass().getField(entry.getKey());
                    Object fieldValue = field.get(one);
                    if(fieldValue.equals(entry.getValue()) == true)
                    {
                        result = true;
                    }
                    else
                    {
                        result = false;
                        break;
                    }
                }
                if(result == true)
                {
                    results.add(one);
                }
            }
        }
        catch(Exception e)
        {
            CPLogger.debug(e);
        }
        return results;
    }

    /**
     * search record
     * 
     * @param key
     * @param value
     * @return
     */
    public CPObjectDatabaseRecord searchRecord(String key, Object value)
    {
        CPObjectDatabaseRecord result = null;
        List<CPObjectDatabaseRecord> entities = this.searchRecords(key, value);
        if(entities.isEmpty() == false)
        {
            result = entities.get(0);
        }
        return result;
    }

    /**
     * search record
     * 
     * @param conditions
     * @return
     */
    public CPObjectDatabaseRecord searchRecord(Map<String, Object> conditions)
    {
        CPObjectDatabaseRecord result = null;
        List<CPObjectDatabaseRecord> entities = this.searchRecords(conditions);
        if(entities.isEmpty() == false)
        {
            result = entities.get(0);
        }
        return result;
    }
    
    /**
     * 保持してあるJSONデータを取得
     * 
     * @return
     */
    public String callJSONSource()
    {
        return this.jsonSourceString;
    }
    
    /**
     * JSONデータを置き換える
     * 
     * @return
     */
    public void replaceJSONSource(String jsonString)
    {
        this.jsonSourceString = jsonString;
    }
    
    /**
     * 保持してあるJSONデータに引数JSONデータを追加
     * 
     * @param jsonObject
     */
    public void appendJSONRecord(JSONObject jsonObject)
    {
        JSONArray jsonArray = new JSONArray(this.jsonSourceString);
        jsonArray.put(jsonObject);
        this.jsonSourceString = jsonArray.toString();
    }
    
    /**
     * 保持してあるJSONデータからキーに合致したデータを削除
     * 
     * @param key
     * @param value
     */
    public void removeJSONRecord(String key, Object value)
    {
        
        String jsonString = this.jsonSourceString;

        // JSONObject
        if(jsonString.startsWith("{") == true)
        {
            // ここに入ることは想定していない
            JSONObject jsonObject = new JSONObject(jsonString);
            if(jsonObject.get(key).equals(value) == true)
            {
                this.jsonSourceString = "";
            }
        }
        // JSONArray
        else if(jsonString.startsWith("[") == true)
        {
            JSONArray jsonArray = new JSONArray(jsonString);

            int length = jsonArray.length();
            for(int i = (length - 1); i >= 0; i--)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.get(key).equals(value) == true)
                {
                    jsonArray.remove(i);
                }
            }
            this.jsonSourceString = jsonArray.toString();
        }
    }
    
    /**
     * 保持してあるJSONデータからキーに合致したレコードの特定キーの値を更新する
     * 
     * @param searchKey
     * @param searchValue
     * @param replaceKey
     * @param replaceValue
     */
    public void updateJSONRecord(String searchKey, Object searchValue, String replaceKey, Object replaceValue)
    {
        
        String jsonString = this.jsonSourceString;

        // JSONObject
        if(jsonString.startsWith("{") == true)
        {
            // ここに入ることは想定していない
            JSONObject jsonObject = new JSONObject(jsonString);
            if(jsonObject.get(searchKey).equals(searchValue) == true)
            {
                jsonObject.put(replaceKey, replaceValue);
            }
        }
        // JSONArray
        else if(jsonString.startsWith("[") == true)
        {
            JSONArray jsonArray = new JSONArray(jsonString);

            int length = jsonArray.length();
            for(int i = (length - 1); i >= 0; i--)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.get(searchKey).equals(searchValue) == true)
                {
                    jsonObject.put(replaceKey, replaceValue);
                }
                jsonArray.put(i, jsonObject);
            }
            this.jsonSourceString = jsonArray.toString();
        }
    }

    /**
     * 保持してあるJSONデータからキーに合致したレコードを削除して新たなJSONObjectを挿入する
     * 
     * @param searchKey
     * @param searchValue
     * @param replaceJSONObject
     */
    public void replaceJSONRecord(String searchKey, Object searchValue, JSONObject replaceJSONObject)
    {
        
        String jsonString = this.jsonSourceString;

        // JSONObject
        if(jsonString.startsWith("{") == true)
        {
            // ここに入ることは想定していない
            JSONObject jsonObject = new JSONObject(jsonString);
            if(jsonObject.get(searchKey).equals(searchValue) == true)
            {
                this.jsonSourceString = replaceJSONObject.toString();
            }
        }
        // JSONArray
        else if(jsonString.startsWith("[") == true)
        {
            JSONArray jsonArray = new JSONArray(jsonString);

            int length = jsonArray.length();
            for(int i = (length - 1); i >= 0; i--)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.get(searchKey).equals(searchValue) == true)
                {
                    jsonArray.remove(i);
                }
            }
            jsonArray.put(replaceJSONObject);
            this.jsonSourceString = jsonArray.toString();
        }
    }

    /**
     * データベースにある内容でjsonSourceStringを置き換える
     */
    public void replaceToJSONSource()
    {
        this.replaceJSONSource(this.toJSONArray().toString());
    }
    
    /**
     * to JSONArray
     * 
     * @return
     */
    public JSONArray toJSONArray()
    {
        JSONArray jsonArray = new JSONArray();
        for(CPObjectDatabaseRecord record : this.callRecords().values())
        {
            jsonArray.put(record.toMap());
        }
        return jsonArray;
    }
    
    public String toString()
    {
        return this.tableName + " processing : " + Boolean.valueOf(this.processing).toString();
    }
}
