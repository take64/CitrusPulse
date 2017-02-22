package live.citrus.pulse.fx.node;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import live.citrus.pulse.fx.CPFxUtils;

/**
 * CPFxLabelを内包したCPFxHBox
 * 
 * @author take64
 *
 */
public class CPFxLabelPackedHBox extends CPFxHBox
{
    /** 内包するCPFxLabel **/
    public List<CPFxLabel> packedNodes = new ArrayList<CPFxLabel>();


    /**
     * constructor
     */
    public CPFxLabelPackedHBox()
    {
        super();

        // 初期化
        CPFxAnchorPane.anchorAll(this);

        // 設置
        super.add(this.packedNodes);
    }
    
    /**
     * constructor
     * 
     * @param titles
     */
    public CPFxLabelPackedHBox(String[] titles)
    {
        this();
        
        for(String title : titles)
        {
            this.addLabel(title);
        }
    }

    /**
     * 文字寄せ設定
     * 
     * @param pos
     */
    public void setAlignment(Pos pos)
    {
        this.innerNode.setAlignment(pos);
    }
    
    /**
     * Labelの追加
     * 
     * @param title
     */
    public void addLabel(String title)
    {
        this.addLabel(new CPFxLabel(title));
    }
    
    /**
     * Labelの追加
     * 
     * @param label
     */
    public void addLabel(CPFxLabel label)
    {
        this.packedNodes.add(label);
        this.add(label);
    }
    
    /**
     * Labelの取得
     * 
     * @param index
     * @return
     */
    public CPFxLabel callLabel(int index)
    {
        return this.packedNodes.get(index);
    }

    /**
     * 文字寄せ設定
     * 
     * @param pos
     */
    public void setLabelAlignment(Pos pos)
    {
        for(CPFxLabel label : this.packedNodes)
        {
            label.setLabelAlignment(pos);
        }
    }

    /**
     * Padding設定
     * 
     * @param insets
     */
    public void setLabelPadding(Insets insets)
    {
        for(CPFxLabel label : this.packedNodes)
        {
            label.setLabelPadding(insets);
        }
    }
    
    /**
     * ボーダー設定
     * 
     * @param color
     */
    public void setChainBorderColor(Color color)
    {
        Border border = CPFxUtils.callSolidBorder(color);
        
        this.setBorder(border);
        
        for(CPFxLabel label : this.packedNodes)
        {
            label.setLabelBorder(border);
        }
    }
}
