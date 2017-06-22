package live.citrus.pulse.fx.dialog;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import live.citrus.pulse.fx.CPFxAlertCallback;

/**
 * ダイアログ系クラス
 * 
 * @author take64
 *
 */
public class CPFxDialog
{

    /**
     * YES/NOアラートダイアログを生成し、表示する
     * 
     * @param title
     * @param content
     * @param yesCallback
     * @param noCallback
     */
    public static void showYesNoAlert(String title, String content, CPFxAlertCallback yesCallback, CPFxAlertCallback noCallback)
    {
        Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
        alert.setTitle(title);
        alert.getDialogPane().setHeaderText(title);
        alert.getDialogPane().setContentText(content);
        Optional<ButtonType> button = alert.showAndWait();
        
        if(button.get().equals(ButtonType.NO) == true)
        {
            if(noCallback != null)
            {
                noCallback.callback();
            }
        }
        else
        {
            if(yesCallback != null)
            {
                yesCallback.callback();
            }
        }
    }

    /**
     * OKアラートダイアログを生成し、表示する
     * 
     * @param title
     * @param content
     * @param yesCallback
     * @param noCallback
     */
    public static void showOKAlert(String title, String content, CPFxAlertCallback okCallback)
    {
        Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.OK);
        alert.setTitle(title);
        alert.getDialogPane().setHeaderText(title);
        alert.getDialogPane().setContentText(content);
        Optional<ButtonType> button = alert.showAndWait();
        
        if(button.get().equals(ButtonType.OK) == true)
        {
            if(okCallback != null)
            {
                okCallback.callback();
            }
        }
    }

    /**
     * OKエラーダイアログを生成し、表示する
     * 
     * @param title
     * @param content
     * @param yesCallback
     */
    public static void showOKError(String title, String content, CPFxAlertCallback okCallback)
    {
        Alert alert = new Alert(AlertType.ERROR, "", ButtonType.OK);
        alert.setTitle(title);
        alert.getDialogPane().setHeaderText(title);
        alert.getDialogPane().setContentText(content);
        Optional<ButtonType> button = alert.showAndWait();
        
        if(button.get().equals(ButtonType.OK) == true)
        {
            if(okCallback != null)
            {
                okCallback.callback();
            }
        }
    }
}
