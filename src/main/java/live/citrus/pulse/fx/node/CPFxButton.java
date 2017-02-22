package live.citrus.pulse.fx.node;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

/**
 * カスタマイズが容易なButton
 * 
 * @author take64
 *
 */
public class CPFxButton extends CPFxAnchorPane
{
    /** 内包するProgressBar **/
    public Button innerNode = new Button();

    
    /**
     * constructor
     */
    public CPFxButton()
    {
        super();

        // 初期化
        CPFxAnchorPane.anchorAll(this);
        this.setPadding(new Insets(2));
        this.innerNode.setPadding(new Insets(2));
        this.innerNode.setFont(Font.font(12));

        // 設置
        super.add(this.innerNode);
    }
    
    /**
     * constructor
     * 
     * @param title
     */
    public CPFxButton(String title)
    {
        this();

        // 初期化
        this.innerNode.setText(title);
    }
    
    /**
     * 横幅の設定
     * 
     * @param width
     */
    public void setWidth(double width)
    {
        this.innerNode.setMinWidth(USE_PREF_SIZE);
        this.innerNode.setPrefWidth(width);
        this.innerNode.setMaxWidth(USE_COMPUTED_SIZE);
    }

    /**
     * 高さの設定
     * 
     * @param height
     */
    public void setHeight(double height)
    {
        this.innerNode.setMinHeight(USE_PREF_SIZE);
        this.innerNode.setPrefHeight(height);
        this.innerNode.setMaxHeight(USE_COMPUTED_SIZE);
    }
}
