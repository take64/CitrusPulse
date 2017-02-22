package live.citrus.pulse.fx;

import javax.security.auth.callback.Callback;

/**
 * アラート用のコールバック
 * 
 * @author take64
 *
 */
public interface CPFxAlertCallback extends Callback
{
    public void callback();
}
