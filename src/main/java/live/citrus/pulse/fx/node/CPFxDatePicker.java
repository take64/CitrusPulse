package live.citrus.pulse.fx.node;

import java.time.LocalDate;

import javafx.scene.control.DatePicker;

/**
 * カスタマイズが容易なDatePicker
 * 
 * @author take64
 *
 */
public class CPFxDatePicker extends CPFxAnchorPane
{
    /** 内包するDatePicker **/
    public DatePicker innerNode;

    
    /**
     * constructor
     */
    public CPFxDatePicker()
    {
        super();
        
        // 初期化
        this.innerNode = new DatePicker(LocalDate.now());
        CPFxAnchorPane.anchorAll(this);
//        this.setPadding(new Insets(2));

        // 設置
//        super.add(this.innerNode);
        this.getChildren().add(this.innerNode);
    }

    /**
     * 横幅の設定
     * 
     * @param width
     */
    public void setWidth(double width)
    {
        this.setWidth(USE_PREF_SIZE, width, USE_COMPUTED_SIZE);
    }

    /**
     * 横幅の設定
     * 
     * @param minWidth
     * @param prefWidth
     * @param maxWidth
     */
    public void setWidth(double minWidth, double prefWidth, double maxWidth)
    {
        this.innerNode.setMinWidth(minWidth);
        this.innerNode.setPrefWidth(prefWidth);
        this.innerNode.setMaxWidth(maxWidth);
    }
}
