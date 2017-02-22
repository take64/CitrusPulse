package live.citrus.pulse.fx.node;

import javafx.scene.Node;

/**
 * CPFxVBoxを内包したCPFxScrollPane
 * 
 * @author take64
 *
 */
public class CPFxVBoxPackedScrollPane extends CPFxScrollPane
{
    /** 内包するCPFxVBox **/
    public CPFxVBox packedNode = new CPFxVBox();

    
    /**
     * constructor
     */
    public CPFxVBoxPackedScrollPane()
    {
        super(true);

        // 初期化
        CPFxAnchorPane.anchorAll(this);

        // 設置
        super.add(this.packedNode);
    }
    
    /**
     * constructor
     * 
     * @pram fit
     */
    public CPFxVBoxPackedScrollPane(boolean fit)
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
        this.packedNode.add(child);
    }
}
