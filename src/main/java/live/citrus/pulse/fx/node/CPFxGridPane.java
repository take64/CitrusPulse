package live.citrus.pulse.fx.node;

import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import live.citrus.pulse.fx.CPFxUtils;

public class CPFxGridPane extends CPFxAnchorPane
{
    /** 内包するGridPane **/
    public GridPane innerNode = new GridPane();

    
    /**
     * constructor
     */
    public CPFxGridPane()
    {
        super();
        
        // 初期化
        CPFxAnchorPane.anchorAll(this);

        // 設置
        super.add(this.innerNode);
    }
    
    /**
     * constructor
     * 
     * @param columnCount
     * @param rowCount
     */
    public CPFxGridPane(int columnCount, int rowCount)
    {
        this();
        
        // 初期化
        for(int i = 0; i < columnCount; i++)
        {
            this.innerNode.getColumnConstraints().add(new ColumnConstraints());
        }
        for(int i = 0; i < rowCount; i++)
        {
            this.innerNode.getRowConstraints().add(new RowConstraints());
        }
    }

    /**
     * add node
     * 
     * @param child
     * @param columnIndex
     * @param rowIndex
     */
    public void add(Node child, int columnIndex, int rowIndex)
    {
        this.add(child, columnIndex, rowIndex, 1, 1);
//        this.innerNode.add(child, columnIndex, rowIndex);
//        
//        for(int i = this.innerNode.getRowConstraints().size(); i <= rowIndex; i++)
//        {
//            this.innerNode.getRowConstraints().add(new RowConstraints());
//        }
//        for(int i = this.innerNode.getColumnConstraints().size(); i <= columnIndex; i++)
//        {
//            this.innerNode.getColumnConstraints().add(new ColumnConstraints());
//        }
    }

    /**
     * add node
     * 
     * @param child
     * @param columnIndex
     * @param rowIndex
     * @param columnSpan
     * @param rowSpan
     */
    public void add(Node child, int columnIndex, int rowIndex, int columnSpan, int rowSpan)
    {
        this.innerNode.add(child, columnIndex, rowIndex, columnSpan, rowSpan);
        
        for(int i = this.innerNode.getRowConstraints().size(); i <= rowIndex; i++)
        {
            this.innerNode.getRowConstraints().add(new RowConstraints());
        }
        for(int i = this.innerNode.getColumnConstraints().size(); i <= columnIndex; i++)
        {
            this.innerNode.getColumnConstraints().add(new ColumnConstraints());
        }
    }

    /**
     * カラム幅の設定
     * 
     * @param columnIndex
     * @param width
     */
    public void setColumnWidth(int columnIndex, double width)
    {
        this.setColumnWidth(columnIndex, USE_PREF_SIZE, width, USE_COMPUTED_SIZE);
    }
    
    /**
     * カラム幅の設定
     * 
     * @param columnIndex
     * @param minWidth
     * @param prefWidth
     * @param maxWidth
     */
    public void setColumnWidth(int columnIndex, double minWidth, double prefWidth, double maxWidth)
    {
        ColumnConstraints columnConstraints = this.innerNode.getColumnConstraints().get(columnIndex); 
        columnConstraints.setMinWidth(minWidth);
        columnConstraints.setPrefWidth(prefWidth);
        columnConstraints.setMaxWidth(maxWidth);
    }

    /**
     * カラム高さの設定
     * 
     * @param rowIndex
     * @param height
     */
    public void setRowHeight(int rowIndex, double height)
    {
        this.setRowHeight(rowIndex, USE_PREF_SIZE, height, USE_COMPUTED_SIZE);
    }
    
    /**
     * カラム高さの設定
     * 
     * @param rowIndex
     * @param minHeight
     * @param prefHeight
     * @param maxHeight
     */
    public void setRowHeight(int rowIndex, double minHeight, double prefHeight, double maxHeight)
    {
        RowConstraints rowConstraints = this.innerNode.getRowConstraints().get(rowIndex); 
        rowConstraints.setMinHeight(minHeight);
        rowConstraints.setPrefHeight(prefHeight);
        rowConstraints.setMaxHeight(maxHeight);
    }
    
    /**
     * ボーダー設定を伝搬する
     * 
     * @param color
     */
    public void setChainBorderColor(Color color)
    {
        Border border = CPFxUtils.callSolidBorder(color);
        this.setChainBorder(border);
    }
    
    /**
     * ボーダー設定を伝搬する
     * 
     * @param border
     */
    public void setChainBorder(Border border)
    {
        for(Node pane : this.innerNode.getChildren())
        {
            if(pane instanceof CPFxAnchorPane)
            {
                ((CPFxAnchorPane) pane).setBorder(border);
            }
        }
    }
    
    /**
     * 背景設定を伝搬する
     * 
     * @param color
     */
    public void setChainBackgroundColor(Color color)
    {
        Background background = CPFxUtils.callBackgroundColor(color);
        this.setChainBackground(background);
    }
    
    /**
     * 背景設定を伝搬する
     * 
     * @param border
     */
    public void setChainBackground(Background background)
    {
        for(Node pane : this.innerNode.getChildren())
        {
            if(pane instanceof CPFxAnchorPane)
            {
                ((CPFxAnchorPane) pane).setBackground(background);
            }
        }
    }
    
    
}
