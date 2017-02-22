package live.citrus.pulse.fx.node;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.text.Font;

/**
 * カスタマイズが容易なLabel
 * 
 * @author take64
 *
 */
public class CPFxLabel extends CPFxAnchorPane
{
    /** 内包するScrollPane **/
    public Label innerNode = new Label();

    
    /**
     * constructor
     */
    public CPFxLabel()
    {
        super();
        
        // 初期化
        CPFxAnchorPane.anchorAll(this);
        this.setPadding(new Insets(2));
        this.innerNode.setFont(Font.font(12));
//        this.setLabelPadding(new Insets(2));

        // 設置
        super.add(this.innerNode);
    }

    /**
     * constructor
     * 
     * @param title
     */
    public CPFxLabel(String title)
    {
        this();
        
        // 初期化
        this.innerNode.setText(title);
    }

    /**
     * 文字寄せ
     * 
     * @param pos
     */
    public void setLabelAlignment(Pos pos)
    {
        this.innerNode.setAlignment(pos);
    }
    
    /**
     * 文字設定
     * 
     * @param text
     */
    public void setLabelText(String text)
    {
        this.innerNode.setText(text);
    }
    
    /**
     * フォントサイズ
     * 
     * @param fontSize
     */
    public void setLabelFontSize(double fontSize)
    {
        this.innerNode.setFont(Font.font(fontSize));
    }
    
    /**
     * Padding設定
     * 
     * @param insets
     */
    public void setLabelPadding(Insets insets)
    {
        this.innerNode.setPadding(insets);
    }

    /**
     * ボーダー設定
     * 
     * @param color
     */
    public void setLabelBorder(Border border)
    {
        this.innerNode.setBorder(border);
    }
}
