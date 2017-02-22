package live.citrus.pulse.fx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * データベースカラムのフィールドに登録することでmappingと同じように振る舞う
 * 
 * @author take64
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CPFX
{
    String value();
}
