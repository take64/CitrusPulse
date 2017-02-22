package live.citrus.pulse.fx.node;

import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Font;

/**
 * カスタマイズが容易なTitledPane
 * 
 * @author take64
 *
 */
public class CPFxTitledPane extends CPFxAnchorPane
{
    /** 内包するScrollPane **/
    public TitledPane innerNode = new TitledPane();

    
    /**
     * constructor
     */
    public CPFxTitledPane()
    {
        super();

        // 初期化
        CPFxAnchorPane.anchorAll(this);

        // 設置
        super.add(this.innerNode);
    }
    /**
     * constructor
     * 
     * @param title
     * @param collapsible
     */
    public CPFxTitledPane(String title, boolean collapsible)
    {
        this();
        
        // タイトル
        this.setTitle(title);
        this.innerNode.setFont(Font.font(12));
        
        // 展開ボタン
        this.setCollapsible(collapsible);
    }
    
    
    /**
     * Nodeの追加
     * 
     * @param node
     */
    public void add(Node node)
    {
        this.innerNode.setContent(node);
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
    
    /**
     * 展開フラグの設定
     * 
     * @param collapsible
     */
    public void setCollapsible(boolean collapsible)
    {
        this.innerNode.setCollapsible(collapsible);
    }
}
