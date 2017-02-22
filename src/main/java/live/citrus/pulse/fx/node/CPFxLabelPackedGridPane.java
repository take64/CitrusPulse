package live.citrus.pulse.fx.node;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import live.citrus.pulse.fx.CPFxUtils;

/**
 * CPFxLabelを内包したCPFxGridPane
 * 
 * @author take64
 *
 */
public class CPFxLabelPackedGridPane extends CPFxGridPane
{
    /** ラベルリスト **/
    public List<List<CPFxLabel>> packedNodes = new ArrayList<List<CPFxLabel>>();
    

    /**
     * constructor
     */
    public CPFxLabelPackedGridPane()
    {
        super();

        // 初期化
        CPFxAnchorPane.anchorAll(this);
    }
    
    /**
     * constructor
     * 
     * @param titles
     */
    public CPFxLabelPackedGridPane(String[][] titleses)
    {
        this();
        
        // カラム数
        int columns = 0;
        
        for(int row = 0; row < titleses.length; row++)
        {
            String[] titles = titleses[row];
            for(int column = 0; column < titles.length; column++)
            {
                this.addLabel(titles[column], column, row);
                
                // カラム数
                columns = Integer.max(columns, (column + 1));
            }
            
//            // 初期設定(Row)
//            RowConstraints rowConstraints = new RowConstraints();
//            rowConstraints.setValignment(VPos.BOTTOM);
//            this.innerNode.getRowConstraints().add(rowConstraints);
        }

//        // 初期設定(Column)
//        for(int i = 0; i < columns; i++)
//        {
//            ColumnConstraints columnConstraints = new ColumnConstraints();
//            this.innerNode.getColumnConstraints().add(columnConstraints);
//        }
    }
    
    /**
     * CPFxLabelの追加
     * 
     * @param title
     * @param columnIndex
     * @param rowIndex
     */
    public void addLabel(String title, int columnIndex, int rowIndex)
    {
        CPFxLabel label = new CPFxLabel(title);
        this.add(label, columnIndex, rowIndex);
        
        List<CPFxLabel> rows = null;
        if(this.packedNodes.size() <= rowIndex)
        {
            rows = new ArrayList<CPFxLabel>();
            this.packedNodes.add(rows);
        }
        else
        {
            rows = this.packedNodes.get(rowIndex);
        }
        
        if(rows.size() <= columnIndex)
        {
            rows.add(label);
        }
    }
    
    /**
     * CPFxLabelの取得
     * 
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    public CPFxLabel callLabel(int rowIndex, int columnIndex)
    {
        return this.packedNodes.get(rowIndex).get(columnIndex);
    }

    /**
     * 内包したラベルの寄せ(絡む一括設定)
     * 
     * @param pos
     * @param column
     */
    public void setLabelAlignmentColumn(Pos pos, int column)
    {
        for(List<CPFxLabel> labels : this.packedNodes)
        {
            // カラムが存在しない場合はスルー
            if(labels.size() <= column)
            {
                continue;
            }
            // 文字寄せ
            labels.get(column).setLabelAlignment(pos);
        }
    }

    /**
     * 全てのラベルにPaddingする
     * 
     * @param insets
     */
    public void setLabelPadding(Insets insets)
    {
        for(List<CPFxLabel> labels : this.packedNodes)
        {
            for(CPFxLabel label : labels)
            {
                label.setPadding(insets);
            }
        }
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
        
        for(List<CPFxLabel> labels : this.packedNodes)
        {
            for(CPFxLabel label : labels)
            {
                label.setLabelBorder(border);
            }
        }
    }
}
