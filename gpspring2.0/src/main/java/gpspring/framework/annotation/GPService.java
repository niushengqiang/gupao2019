package gpspring.framework.annotation;

import java.lang.annotation.*;

/**
 * 业务逻辑,注入接口
 * @author niushengqiang
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPService {
	String value() default "";
}
