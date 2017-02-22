package live.citrus.pulse.fx;

import javafx.scene.effect.ColorAdjust;

public enum CPFxColorEffect
{
    RED(-1.0),
    ORANGE(-0.9),
    YELLOW(-0.8),
    GREEN(-0.3),
    
    CLEAR(-0.3),
    
    ;
    
    
    private final double hue;
    private CPFxColorEffect(final double hue)
    {
        this.hue = hue;
    }
    
    public ColorAdjust getEffect()
    {
        return new ColorAdjust(this.hue, 0, 0, 0);
    }
}
