package gpspring.framework.annotation;

import java.lang.annotation.*;

/**
 * 请求url
 * @author niushengqiang
 *
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPRequestMapping {
	String value() default "";
}
