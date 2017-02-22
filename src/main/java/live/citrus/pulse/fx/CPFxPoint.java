package live.citrus.pulse.fx;

/**
 * 座標構造体
 * 
 * @author take64
 *
 */
public class CPFxPoint
{
    // x
    public double x = Double.NaN;
    
    // y
    public double y = Double.NaN;
    
    
    /**
     * constructor
     * 
     * @param x
     * @param y
     */
    public CPFxPoint(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    /**
     * 座標の生成
     * 
     * @param x
     * @param y
     */
    public static CPFxPoint point(double x, double y)
    {
        return new CPFxPoint(x, y);
    }
}
