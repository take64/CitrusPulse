package live.citrus.pulse.fx.node;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import live.citrus.pulse.fx.CPFxPoint;
import live.citrus.pulse.fx.CPFxRect;
import live.citrus.pulse.fx.CPFxSize;
import live.citrus.pulse.fx.CPFxUtils;
import live.citrus.pulse.log.CPLogger;

/**
 * カスタマイズが容易なAnchorPane
 * 
 * @author take64
 *
 */
public class CPFxAnchorPane extends AnchorPane
{
    /** 内包するPane **/
    protected Node innerNode;
    
    
    
    /**
     * constructor
     */
    public CPFxAnchorPane()
    {
        super();
        
        // 初期化
//        this.setPadding(new Insets(2));
    }

    /**
     * Nodeの追加
     * 
     * @param node
     */
    public void add(Node node)
    {
        this.getChildren().add(node);
    }
    
    /**
     * 背景色の設定
     * 
     * @param color
     */
    public void setBackgroundColor(Color color)
    {
        this.setBackground(CPFxUtils.callBackgroundColor(color));
    }
    
    /**
     * 線種がSOLIDなボーダーの取得
     * 
     * @param color
     */
    public void setSolidBorder(Color color)
    {
        this.setBorder(CPFxUtils.callSolidBorder(color));
    }

    /**
     * 座標の設定
     * 
     * @param point
     */
    public void setPoint(CPFxPoint point)
    {
        // x座標
        if(point.x != Double.NaN)
        {
            this.setLayoutX(point.x);
        }
        // y座標
        if(point.y != Double.NaN)
        {
            this.setLayoutY(point.y);
        }
    }
    
    /**
     * サイズの設定
     * 
     * @param size
     */
    public void setSize(CPFxSize size)
    {
        // 幅
        if(size.width != Double.NaN)
        {
            this.setPrefWidth(size.width);
        }
        // 高さ
        if(size.height != Double.NaN)
        {
            this.setPrefHeight(size.height);
        }
    }
    
    /**
     * 座標とサイズの設定
     * 
     * @param rect
     */
    public void setRect(CPFxRect rect)
    {
        // 座標
        this.setPoint(rect.point);
        
        // サイズ
        this.setSize(rect.size);
    }
    
//    /**
//     * Paddingのの設定
//     * 
//     * @param insets
//     */
//    public void setPanePadding(Insets insets)
//    {
////        this.innerNode.
//        this.setPadding(insets);
//    }
    

    
    /**
     * アンカーを張る
     * 
     * @param pane
     */
    public static void anchorAll(CPFxAnchorPane pane)
    {
        CPFxAnchorPane.anchorVertical(pane);
        CPFxAnchorPane.anchorHorizontal(pane);
    }

    /**
     * アンカーを張る(縦方向)
     * 
     * @param pane
     */
    public static void anchorVertical(CPFxAnchorPane pane)
    {
        // CPFxGridPane
        if(pane instanceof CPFxGridPane)
        {
            CPFxAnchorPane.innerAnchorVertical(((CPFxGridPane)pane).innerNode);
        }
        // CPFxScrollPane
        else if(pane instanceof CPFxScrollPane)
        {
            CPFxAnchorPane.innerAnchorVertical(((CPFxScrollPane)pane).innerNode);
        }
        // CPFxTitledPane
        else if(pane instanceof CPFxTitledPane)
        {
            CPFxAnchorPane.innerAnchorVertical(((CPFxTitledPane)pane).innerNode);
        }
        // CPFxHBox
        else if(pane instanceof CPFxHBox)
        {
            CPFxAnchorPane.innerAnchorVertical(((CPFxHBox)pane).innerNode);
        }
        // CPFxVBox
        else if(pane instanceof CPFxVBox)
        {
            CPFxAnchorPane.innerAnchorVertical(((CPFxVBox)pane).innerNode);
        }
        // CPFxLabel
        else if(pane instanceof CPFxLabel)
        {
            CPFxAnchorPane.innerAnchorVertical(((CPFxLabel)pane).innerNode);
        }
        // CPFxProgressBar
        else if(pane instanceof CPFxProgressBar)
        {
            CPFxAnchorPane.innerAnchorVertical(((CPFxProgressBar)pane).innerNode);
        }
        // CPFxButton
        else if(pane instanceof CPFxButton)
        {
            CPFxAnchorPane.innerAnchorVertical(((CPFxButton)pane).innerNode);
        }
        // CPFxDatePicker
        else if(pane instanceof CPFxDatePicker)
        {
            CPFxAnchorPane.innerAnchorVertical(((CPFxDatePicker)pane).innerNode);
        }
        // CPFxChoiceBox
        else if(pane instanceof CPFxChoiceBox)
        {
            CPFxAnchorPane.innerAnchorVertical(((CPFxChoiceBox)pane).innerNode);
        }
        // CPFxCheckBox
        else if(pane instanceof CPFxCheckBox)
        {
            CPFxAnchorPane.innerAnchorVertical(((CPFxCheckBox)pane).innerNode);
        }
        // CPFxTableView
        else if(pane instanceof CPFxTableView)
        {
            CPFxAnchorPane.innerAnchorVertical(((CPFxTableView)pane).innerNode);
        }
        // CPFxVBoxPackedScrollPane
        else if(pane instanceof CPFxVBoxPackedScrollPane)
        {
            CPFxAnchorPane.innerAnchorVertical(((CPFxVBoxPackedScrollPane)pane).packedNode);
        }
        // CPFxLabelPackedGridPane
        else if(pane instanceof CPFxLabelPackedGridPane)
        {
//            CPFxAnchorPane.innerAnchorVertical(((CPFxLabelPackedGridPane)pane).packedNodes);
        }
        // CPFxLabelPackedHBox
        else if(pane instanceof CPFxLabelPackedHBox)
        {
            CPFxAnchorPane.innerAnchorVertical(((CPFxLabelPackedHBox)pane).packedNodes);
        }
        // CPFxHBoxPackedVBox
        else if(pane instanceof CPFxHBoxPackedVBox)
        {
            CPFxAnchorPane.innerAnchorVertical(((CPFxHBoxPackedVBox)pane).packedNodes);
        }
        
        else
        {
            CPLogger.debug("anchorV not hit : " + pane.getClass().getName());
        }
    }

    /**
     * アンカーを張る(横方向)
     * 
     * @param pane
     */
    public static void anchorHorizontal(CPFxAnchorPane pane)
    {
        // CPFxGridPane
        if(pane instanceof CPFxGridPane)
        {
            CPFxAnchorPane.innerAnchorHorizontal(((CPFxGridPane)pane).innerNode);
        }
        // CPFxScrollPane
        else if(pane instanceof CPFxScrollPane)
        {
            CPFxAnchorPane.innerAnchorHorizontal(((CPFxScrollPane)pane).innerNode);
        }
        // CPFxTitledPane
        else if(pane instanceof CPFxTitledPane)
        {
            CPFxAnchorPane.innerAnchorHorizontal(((CPFxTitledPane)pane).innerNode);
        }
        // CPFxHBox
        else if(pane instanceof CPFxHBox)
        {
            CPFxAnchorPane.innerAnchorHorizontal(((CPFxHBox)pane).innerNode);
        }
        // CPFxVBox
        else if(pane instanceof CPFxVBox)
        {
            CPFxAnchorPane.innerAnchorHorizontal(((CPFxVBox)pane).innerNode);
        }
        // CPFxLabel
        else if(pane instanceof CPFxLabel)
        {
            CPFxAnchorPane.innerAnchorHorizontal(((CPFxLabel)pane).innerNode);
        }
        // CPFxProgressBar
        else if(pane instanceof CPFxProgressBar)
        {
            CPFxAnchorPane.innerAnchorHorizontal(((CPFxProgressBar)pane).innerNode);
        }
        // CPFxButton
        else if(pane instanceof CPFxButton)
        {
            CPFxAnchorPane.innerAnchorHorizontal(((CPFxButton)pane).innerNode);
        }
        // CPFxDatePicker
        else if(pane instanceof CPFxDatePicker)
        {
            CPFxAnchorPane.innerAnchorHorizontal(((CPFxDatePicker)pane).innerNode);
        }
        // CPFxChoiceBox
        else if(pane instanceof CPFxChoiceBox)
        {
            CPFxAnchorPane.innerAnchorHorizontal(((CPFxChoiceBox)pane).innerNode);
        }
        // CPFxCheckBox
        else if(pane instanceof CPFxCheckBox)
        {
            CPFxAnchorPane.innerAnchorHorizontal(((CPFxCheckBox)pane).innerNode);
        }
        // CPFxTableView
        else if(pane instanceof CPFxTableView)
        {
            CPFxAnchorPane.innerAnchorHorizontal(((CPFxTableView)pane).innerNode);
        }
        // CPFxVBoxPackedScrollPane
        else if(pane instanceof CPFxVBoxPackedScrollPane)
        {
            CPFxAnchorPane.innerAnchorHorizontal(((CPFxVBoxPackedScrollPane)pane).packedNode);
        }
        // CPFxLabelPackedGridPane
        else if(pane instanceof CPFxLabelPackedGridPane)
        {
//            CPFxAnchorPane.innerAnchorHorizontal(((CPFxLabelPackedGridPane)pane).packedNodes);
        }
        // CPFxLabelPackedHBox
        else if(pane instanceof CPFxLabelPackedHBox)
        {
            CPFxAnchorPane.innerAnchorHorizontal(((CPFxLabelPackedHBox)pane).packedNodes);
        }
        // CPFxHBoxPackedVBox
        else if(pane instanceof CPFxHBoxPackedVBox)
        {
            CPFxAnchorPane.innerAnchorHorizontal(((CPFxHBoxPackedVBox)pane).packedNodes);
        }

        else
        {
            CPLogger.debug("anchorH not hit : " + pane.getClass().getName());
        }
    }

//    /**
//     * アンカーを張る(内部)
//     * 
//     * @param child
//     */
//    private static void anchorAll(Node child)
//    {
//        CPFxAnchorPane.anchorVertical(child);
//        CPFxAnchorPane.anchorHorizontal(child);
//    }


    /**
     * アンカーを張る(縦方向)(複数)(内部)
     * 
     * @param childs
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static void innerAnchorVertical(List childs)
    {
        for(Node node : (List<Node>)childs)
        {
            CPFxAnchorPane.innerAnchorVertical(node);
        }
    }

    /**
     * アンカーを張る(横方向)(複数)(内部)
     * 
     * @param childs
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static void innerAnchorHorizontal(List childs)
    {
        for(Node node : (List<Node>)childs)
        {
            CPFxAnchorPane.innerAnchorHorizontal(node);
        }
    }
    
    /**
     * アンカーを張る(縦方向)(内部)
     * 
     * @param child
     */
    private static void innerAnchorVertical(Node child)
    {
        AnchorPane.setTopAnchor(child, 0d);
        AnchorPane.setBottomAnchor(child, 0d);
    }

    /**
     * アンカーを張る(横方向)(内部)
     * 
     * @param child
     */
    private static void innerAnchorHorizontal(Node child)
    {
        AnchorPane.setRightAnchor(child, 0d);
        AnchorPane.setLeftAnchor(child, 0d);
    }
    
    public String toString()
    {
        return String.format("%s [width=%.3f, height=%.3f]", 
                this.getClass().getSimpleName(),
                this.getWidth(),
                this.getHeight()
                );
    }
}
