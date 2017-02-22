package live.citrus.pulse.fx.node;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import live.citrus.pulse.fx.CPFxUtils;

/**
 * CPFxHBoxを内包したCPFxVBox
 * 
 * @author take64
 *
 */
public class CPFxHBoxPackedVBox extends CPFxVBox
{
    /** 内包するCPFxHBox **/
    public List<CPFxHBox> packedNodes = new ArrayList<CPFxHBox>();

    /**
     * constructor
     */
    public CPFxHBoxPackedVBox()
    {
        super();

        // 初期化
        CPFxAnchorPane.anchorAll(this);

        // 設置
        super.add(this.packedNodes);
    }
    
    /**
     * 
     * ボーダー設定を伝搬する
     * @param color
     */
    public void setChainBorderColor(Color color)
    {
        Border border = CPFxUtils.callSolidBorder(color);
        
        this.innerNode.setBorder(border);
        
        for(CPFxHBox node : this.packedNodes)
        {
            node.setBorder(border);
        }
    }
}
