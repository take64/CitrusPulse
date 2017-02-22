package live.citrus.pulse.fx.node;

import javafx.geometry.Insets;
import javafx.scene.control.ProgressBar;

/**
 * カスタマイズが容易なProgressBar
 * 
 * @author take64
 *
 */
public class CPFxProgressBar extends CPFxAnchorPane
{
    /** 内包するProgressBar **/
    public ProgressBar innerNode = new ProgressBar(0);
    

    /**
     * constructor
     */
    public CPFxProgressBar()
    {
        super();

        // 初期化
        CPFxAnchorPane.anchorAll(this);
        this.setPadding(new Insets(2));

        // 設置
        super.add(this.innerNode);
    }
    
    
    /**
     * 値の設定
     * 
     * @param progress
     */
    public void setProgress(double progress)
    {
        this.innerNode.setProgress(progress);
    }
    
    /**
     * Padding設定
     * 
     * @param insets
     */
    public void setProgressPadding(Insets insets)
    {
        this.innerNode.setPadding(insets);
    }
    
    /**
     * Height設定
     * 
     * @param height
     */
    public void setProgressHeight(double height)
    {
        this.innerNode.setMinHeight(height);
        this.innerNode.setPrefHeight(height);
        this.innerNode.setMaxHeight(height);
    }
}
