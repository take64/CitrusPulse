package live.citrus.pulse.fx.node;

import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;

/**
 * カスタマイズが容易なChoiceBox
 * 
 * @author take64
 *
 */
public class CPFxChoiceBox extends CPFxAnchorPane
{
    /** 内包するChoiceBox **/
    public ChoiceBox<Object> innerNode = new ChoiceBox<Object>();

    

    /**
     * constructor
     */
    public CPFxChoiceBox()
    {
        super();
        
        // 初期化
        CPFxAnchorPane.anchorAll(this);
        this.setPadding(new Insets(2));

        // 設置
        super.add(this.innerNode);
    }
    
    /**
     * 数値を範囲で一括登録する
     * 
     * @param start
     * @param end
     */
    public void addNumbers(int start, int end)
    {
        for(int i = start; i <= end; i++)
        {
            this.innerNode.getItems().add(Integer.valueOf(i));
        }
    }
}
