package gpspring.framework.annotation;

import java.lang.annotation.*;

/**
 * 请求参数映射
 * @author niushengqiang
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPRequestParam {
	
	String value() default "";
	
	boolean required() default true;

}
