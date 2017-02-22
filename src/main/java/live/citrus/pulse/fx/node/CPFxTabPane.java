package live.citrus.pulse.fx.node;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * カスタマイズが容易なCPFxTabPane
 * 
 * @author take64
 *
 */
public class CPFxTabPane extends CPFxAnchorPane
{
    /** 内包するScrollPane **/
    public TabPane innerNode = new TabPane();

    /**
     * constructor
     */
    public CPFxTabPane()
    {
        super();
        
        // 初期設定
        super.add(this.innerNode);
    }
    
    /**
     * constructor
     * 
     * @param tabTitles
     */
    public CPFxTabPane(String[] tabTitles, boolean closable)
    {
        this();
        
        for(String tabTitle : tabTitles)
        {
            Tab tab = new Tab(tabTitle);
            tab.setClosable(closable);
            this.innerNode.getTabs().add(tab);
        }
    }

    /**
     * constructor
     * 
     * @param tabTitles
     */
    public CPFxTabPane(String[] tabTitles)
    {
        this(tabTitles, false);
    }
    
    /**
     * Tabの取得
     * 
     * @param index
     * @return
     */
    public Tab getTab(int index)
    {
        return this.innerNode.getTabs().get(index);
    }
}
