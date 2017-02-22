package live.citrus.pulse.fx.node;

import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * カスタマイズが容易なTableView
 * 
 * @author take64
 *
 */
public class CPFxTableView extends CPFxAnchorPane
{
    /** 内包するDatePicker **/
    public TableView<Object> innerNode = new TableView<Object>();

    
    /**
     * constructor
     */
    public CPFxTableView()
    {
        super();
        
        // 初期化
        CPFxAnchorPane.anchorAll(this);
        this.setPadding(new Insets(2));

        // 設置
        super.add(this.innerNode);
    }
    
    
    /**
     * テーブルにカラムを追加
     * 
     * @param column
     */
    public void addColumn(TableColumn<Object, ?> column)
    {
        this.innerNode.getColumns().add(column);
    }

    /**
     * 高さの設定
     * 
     * @param height
     */
    public void setHeight(double height)
    {
        this.innerNode.setMinHeight(USE_PREF_SIZE);
        this.innerNode.setPrefHeight(height);
        this.innerNode.setMaxHeight(USE_COMPUTED_SIZE);
    }
}
