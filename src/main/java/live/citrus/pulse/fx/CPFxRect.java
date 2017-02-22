package live.citrus.pulse.fx;

/**
 * 座標・サイズ構造体
 * 
 * @author take64
 *
 */
public class CPFxRect
{
    /** 座標 **/
    public CPFxPoint point = CPFxPoint.point(Double.NaN, Double.NaN);
    
    /** サイズ **/
    public CPFxSize size = CPFxSize.size(Double.NaN, Double.NaN);
    
    
    /**
     * constructor
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public CPFxRect(double x, double y, double width, double height)
    {
        this.point = CPFxPoint.point(x, y);
        this.size = CPFxSize.size(width, height);
    }
    
    /**
     * 座標・サイズ構造体の生成
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public static CPFxRect rect(double x, double y, double width, double height)
    {
        return new CPFxRect(x, y, width, height);
    }
}
