package gpspring.framework.webmvc;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * 这里是进行了自己的url上的封装
 */
public class GpHanlderMapping  {
    private Object controller;//controller 实例对象
    private Method method;    //需要执行的方法
    private Pattern pattern; // 匹配的规则

    public GpHanlderMapping(Object controller, Method method, Pattern pattern) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
    }

    public Object getController() {
        return controller;
    }

    public Method getMethod() {
        return method;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
