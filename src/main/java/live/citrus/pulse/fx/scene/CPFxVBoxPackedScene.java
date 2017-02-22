package live.citrus.pulse.fx.scene;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import live.citrus.pulse.fx.node.CPFxVBox;

public class CPFxVBoxPackedScene extends Scene
{
//    /** 内包するAnchorPane **/
//    protected CPFxAnchorPane anchorPane;
    
    /** 内包するVBox **/
    protected CPFxVBox innerNode;
    

    /**
     * constructor
     * 
     * @param root
     * @param width
     * @param height
     */
    public CPFxVBoxPackedScene(Parent root, double width, double height)
    {
        super(root, width, height);
        this.innerNode = (CPFxVBox)root;
    }
    
    /**
     * constructor
     * 
     * @param width
     * @param height
     */
    public CPFxVBoxPackedScene(double width, double height)
    {
        this(new CPFxVBox(), width, height);
    }
    
    /**
     * Nodeを追加
     * 
     * @param node
     */
    public void add(Node node)
    {
        this.innerNode.add(node);
    }
    
//    /**
//     * 内包するAnchorPaneを生成して取得
//     * 
//     * @return
//     */
//    public CPFxAnchorPane callAnchorPane()
//    {
//        if(this.anchorPane == null)
//        {
//            this.anchorPane = new CPFxAnchorPane();
//        }
//        return this.anchorPane;
//    }
//    
//    /**
//     * 内包するVBoxを生成して取得
//     * 
//     * @return
//     */
//    public CPFxVBox callVBox()
//    {
//        if(this.vbox == null)
//        {
//            this.vbox = new CPFxVBox();
//            this.vbox.anchorAll();
//            this.callAnchorPane().getChildren().add(this.vbox);
//        }
//        return this.vbox;
//    }
}
