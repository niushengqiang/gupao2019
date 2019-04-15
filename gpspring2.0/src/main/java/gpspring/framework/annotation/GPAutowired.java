package gpspring.framework.annotation;

import java.lang.annotation.*;


/**
 * @author niushengqiang
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPAutowired {
	String value() default "";
}
