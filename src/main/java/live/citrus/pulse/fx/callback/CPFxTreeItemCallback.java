package live.citrus.pulse.fx.callback;

import javax.security.auth.callback.Callback;

import javafx.scene.control.TreeItem;

/**
 * TreeViewのTreeItemのコールバック
 * 
 * @author take64
 *
 */
public interface CPFxTreeItemCallback extends Callback
{
    public void callback(TreeItem<?> treeItem);
}
