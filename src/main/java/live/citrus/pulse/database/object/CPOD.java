package live.citrus.pulse.database.object;

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
public @interface CPOD
{
    String value();
}
