package live.citrus.pulse.fx.node;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;

/**
 * カスタマイズが容易なCheckBox
 * 
 * @author take64
 *
 */
public class CPFxCheckBox extends CPFxAnchorPane
{
    /** 内包するCheckBox **/
    public CheckBox innerNode = new CheckBox();


    
    /**
     * constructor
     */
    public CPFxCheckBox()
    {
        super();
        
        // 初期化
        CPFxAnchorPane.anchorAll(this);
        this.setPadding(new Insets(2));

        // 設置
        super.add(this.innerNode);
    }

    /**
     * constructor
     */
    public CPFxCheckBox(String title)
    {
        this();
        
        // 初期化
        this.setTitle(title);
    }
    
    
    /**
     * タイトルの設定
     * 
     * @param title
     */
    public void setTitle(String title)
    {
        this.innerNode.setText(title);
    }

}
