package live.citrus.pulse.fx.node;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * カスタマイズが容易なHBox
 * 
 * @author take64
 *
 */
public class CPFxHBox extends CPFxAnchorPane
{
    /** 内包するHBox **/
    public HBox innerNode = new HBox();
    

    /**
     * constructor
     */
    public CPFxHBox()
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
     * @param childs
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
