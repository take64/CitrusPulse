package live.citrus.pulse.database.object;

import javax.security.auth.callback.Callback;

import org.json.JSONObject;

/**
 * チェック用コールバック
 * 
 * @author take64
 *
 */
public interface CPObjectDatabaseCallback extends Callback
{
    /**
     * jsonデータのチェック用コールバック
     * 
     * @param jsonObject
     * @return
     */
    public boolean validate(JSONObject jsonObject);
}
