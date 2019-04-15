package gpspring.framework.annotation;

import java.lang.annotation.*;

/**
 * 页面交互
 * @author niushengqiang
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPController {
	String value() default "";
}
