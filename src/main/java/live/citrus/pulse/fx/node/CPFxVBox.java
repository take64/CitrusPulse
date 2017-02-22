package live.citrus.pulse.fx.node;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * カスタマイズが容易なVBox
 * 
 * @author take64
 *
 */
public class CPFxVBox extends CPFxAnchorPane
{
    /** 内包するVBox **/
    public VBox innerNode = new VBox();
    

    /**
     * constructor
     */
    public CPFxVBox()
    {
        super();
        
        // 初期化
        CPFxAnchorPane.anchorAll(this);

        // 設置
        super.add(this.innerNode);
    }


    /**
     * add node
     * 
     * @param child
     */
    public void add(Node child)
    {
        this.innerNode.getChildren().add(child);
    }
    
    /**
     * add nodes
     * 
     * @param child
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void add(List childs)
    {
        for(Node node : (List<Node>)childs)
        {
            this.add(node);
        }
    }
}
