package live.citrus.pulse.fx.node;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

/**
 * カスタマイズが容易なScrollPane
 * 
 * @author take64
 *
 */
public class CPFxScrollPane extends CPFxAnchorPane
{
    /** 内包するScrollPane **/
    public ScrollPane innerNode = new ScrollPane();
    

    /**
     * constructor
     */
    public CPFxScrollPane()
    {
        super();

        // 初期化
        CPFxAnchorPane.anchorAll(this);
//        this.setPadding(new Insets(2));

        // 設置
        super.add(this.innerNode);
    }
    
    /**
     * constructor
     * 
     * @param fit
     */
    public CPFxScrollPane(boolean fit)
    {
        this();

        // 初期化
        this.fitPane(fit);
    }
    

    /**
     * add node
     * 
     * @param child
     */
    public void add(Node child)
    {
        this.innerNode.setContent(child);
    }
    
    /**
     * fit pane
     * 
     * @param fit
     */
    public void fitPane(boolean fit)
    {
        this.innerNode.setFitToWidth(fit);
        this.innerNode.setFitToHeight(fit);
    }
}
