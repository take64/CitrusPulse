package live.citrus.pulse.fx;

/**
 * サイズ構造体
 * 
 * @author take64
 *
 */
public class CPFxSize
{
    // width
    public double width = Double.NaN;
    
    // height
    public double height = Double.NaN;
    
    
    /**
     * constructor
     * 
     * @param width
     * @param y
     */
    public CPFxSize(double width, double height)
    {
        this.width = width;
        this.height = height;
    }
    
    /**
     * サイズの生成
     * 
     * @param x
     * @param y
     */
    public static CPFxSize size(double width, double height)
    {
        return new CPFxSize(width, height);
    }
}
