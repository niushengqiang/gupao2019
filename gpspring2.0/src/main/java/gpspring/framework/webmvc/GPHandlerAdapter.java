package gpspring.framework.webmvc;


import gpspring.framework.annotation.GPRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 参数处理装置
 */
public class GPHandlerAdapter {

    public boolean supports(Object handler){ return (handler instanceof GpHanlderMapping);}


    /**
     * 封装参数
     * 调用方法
     * 返回结果
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    GPModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        GpHanlderMapping handlerMapping = (GpHanlderMapping) handler;
        //进行参数转换

        //获取参数名称和下标
        Map<String, Integer> paramIndexMapping = new HashMap<String, Integer>();
        Annotation[][] pa = handlerMapping.getMethod().getParameterAnnotations();
        for (int i = 0; i < pa.length; i++) {
            for (Annotation a : pa[i]) {//这里默认一个方法只有一个注解
                if (a instanceof GPRequestParam) {
                    String paramName = ((GPRequestParam) a).value();
                    if (!"".equals(paramName.trim())) {
                        paramIndexMapping.put(paramName, i);
                    }
                }
            }
        }
            //提取方法中的request和response参数
            Class<?>[] paramsTypes = handlerMapping.getMethod().getParameterTypes();
            for (int j = 0; j < paramsTypes.length; j++) {
                Class<?> paramsType = paramsTypes[j];
                if (paramsType == HttpServletRequest.class ||
                        paramsType == HttpServletResponse.class) {
                    paramIndexMapping.put(paramsType.getName(), j);
                }
            }
            //获得方法的形参列表
            //从request中获取参数
            Map<String, String[]> params = request.getParameterMap();
            //实参列表
            Object[] paramValues = new Object[paramsTypes.length];
            for (Map.Entry<String, String[]> entry : params.entrySet()) {
                String value = Arrays.toString(entry.getValue())
                        .replaceAll("\\[|\\]", "")
                        .replaceAll("\\s", ",");
                //不需要的参数直接舍弃掉即可
                if (!paramIndexMapping.containsKey(entry.getKey())) {
                    continue;
                }

                int index = paramIndexMapping.get(entry.getKey());
                paramValues[index] = caseStringValue(value, paramsTypes[index]);
            }

            //单独处理request 和response参数
            if (paramIndexMapping.containsKey(HttpServletRequest.class.getName())) {
                int reqIndex = paramIndexMapping.get(HttpServletRequest.class.getName());
                paramValues[reqIndex] = request;
            }
            if (paramIndexMapping.containsKey(HttpServletResponse.class.getName())) {
                int respIndex = paramIndexMapping.get(HttpServletResponse.class.getName());
                paramValues[respIndex] = response;
            }
            //利用封装的参数访问方法
            Method method = handlerMapping.getMethod();
            Object result = method.invoke(handlerMapping.getController(), paramValues);
            //收集结果封装结果mav
            if (result == null || result instanceof Void) {
                return null;
            }
            if (method.getReturnType() == GPModelAndView.class) {
                return (GPModelAndView) request;
            }
            return null;
    }




    //类型转换可以拓展为策略模式
    private Object caseStringValue(String value, Class<?> paramsType) {
        if(String.class == paramsType){
            return value;
        }
        //如果是int
        if(Integer.class == paramsType){
            return Integer.valueOf(value);
        }
        else if(Double.class == paramsType){
            return Double.valueOf(value);
        }else {
            if(value != null){
                return value;
            }
            return null;
        }
    }


}
