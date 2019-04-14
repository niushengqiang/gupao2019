package com.nsq.minispring.version_v1;

import com.nsq.minispring.annotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.util.*;

public class NsqlDispatcherServlet extends HttpServlet{
    //保存application.properties配置文件中的内容
    private Properties contextConfig = new Properties();
    //当前扫描的文件所在地
    private List<String> classNames=new ArrayList<>();
    //本地ioc容器将扫描到的类加载到内存当中
    private Map<String,Object> ioc=new HashMap<>();

    //处理请求映射结果集
    private Map<String,Object> handleMapping=new HashMap<>();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.doDispatcher(req,resp);
        } catch (Exception e) {
            resp.getWriter().write("500");
            e.printStackTrace();
        }
    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //这里按道理应该采用委派模式将请求的结果进行转移
        String requestURI = req.getRequestURI();
        String contextPath = req.getContextPath();
        //这里是为了配出//的干扰
        String  url = requestURI.replaceAll(contextPath,"").replaceAll("/+","/");
        if(!this.handleMapping.containsKey(url)){
            try {
                resp.getWriter().write("404 Not Found Resource");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Method method = (Method) this.handleMapping.get(url);

        //从reqest中拿到url传过来的参数
        Map<String,String[]> params = req.getParameterMap();

        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] methoArgs = new Object[parameterTypes.length];
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            //不能用instanceof，parameterType它不是实参，而是形参
            if(parameterType == HttpServletRequest.class){
                methoArgs[i]=req;
            }else if(parameterType == HttpServletResponse.class){
                methoArgs[i]=resp;
            }else{
                Annotation[][] annotations = method.getParameterAnnotations();
                x: for (int j=i;j<annotations.length;j++){
                    for (Annotation a : annotations[j]){
                        //解析的只是MyRequestParameter
                        if (a instanceof NsqlRequestParam){
                            //拿到参数名称，去http://localhost:8080/demo/query?name=zhangsan匹配
                            String paramName = ((NsqlRequestParam) a).value();
                            //从req中拿到的参数列表中去找对应的key
                            if (params.containsKey(paramName)){
                                //拿到key对应的值，这个值有一对多的关系，一个key对应一个数组
                                //对方接收的是String类型，把数据统一处理为为String
                                String value = Arrays.toString(params.get(paramName))
                                        .replaceAll("\\[|\\]","")
                                        .replaceAll("\\s","");

                                //类型强制转换
                                methoArgs[i] = convert(parameterType,value);
                                break x;
                            }
                        }
                    }
                }
            }
        }
        String beanName  = toLowerFirstCase(method.getDeclaringClass().getSimpleName());
        method.invoke(ioc.get(beanName),methoArgs);
    }


    private Object convert(Class<?> type,String value){
        if (Integer.class == type){
            return Integer.valueOf(value);
        }
        //如果还有其他类型，在这里继续加if(后面优化可以用策略模式)
        return value;
    }

    @Override//初始化阶段
    public void init(ServletConfig config) throws ServletException {
            //加载配置文件
             doloadConig(config.getInitParameter("contextConfigLocation"));
            //扫描相关的类
            doScanner((String) contextConfig.get("scanpackage"));
            //初始化相关的类,并且将它们放入到ioc容器当中  ioc容器是个map  k 为类名首字母小写, v 为ob加载的对象赋值为对象
            doInstance();
            //完成依赖注入
            doDI();
            //初始化HandlingMapping' springmvc部门将url映射出去
            initHandlerMapping();
            System.out.println("NSQ Spring framework is init.");
    }

    private void initHandlerMapping() {
        //遍历IOC
        for (Map.Entry<String,Object> entry:ioc.entrySet()) {
            Class<?> aClass = entry.getValue().getClass();
            if(aClass.isAnnotationPresent(NsqController.class)){
                String baseUrl="";
                RequestMapping clazzRequestMapping = aClass.getAnnotation(RequestMapping.class);
                baseUrl+=clazzRequestMapping.value();
                Method[] methods = aClass.getMethods();
                for (Method method:methods ) {
                    if(method.isAnnotationPresent(RequestMapping.class)){
                        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                        String url = ("/" + baseUrl + "/" + requestMapping.value())
                                .replaceAll("/+","/");
                        handleMapping.put(url,method);
                        System.out.println("Url:"+url+"; method"+method);
                    }
                }
            }
        }
    }

    //进行类的依赖注入
    private void doDI() {
         for (Map.Entry<String,Object> entry :ioc.entrySet()){
            Field[] declaredFields = entry.getValue().getClass().getDeclaredFields();
            for (Field f:declaredFields) {
                if(!f.isAnnotationPresent(NsqAutowired.class)){
                    continue;
                }else{
                    //基于注解来进行适配,若是注解有值则根据注解获取
                    NsqAutowired annotation = f.getAnnotation(NsqAutowired.class);
                    String beanName = annotation.value();
                    if(this.isEmptly(beanName)){
                        beanName=this.toLowerFirstCase(f.getType().getSimpleName());
                    }

                    f.setAccessible(true);
                    try {
                        f.set(entry.getValue(),ioc.get(beanName));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void doInstance() {
        if(classNames.isEmpty()){return;}
        try{
            for (String className: classNames) {
                Class<?> aClass = Class.forName(className);
                if(aClass.isAnnotationPresent(NsqController.class)){
                    //若是controller则直接加载到容器当中去
                    Object o = aClass.newInstance();
                    String name = aClass.getSimpleName();
                    String beanClassName = this.toLowerFirstCase(name);
                    ioc.put(beanClassName,o);
                }else if(aClass.isAnnotationPresent(NsqService.class)){
                    //若是service 则需要考虑其接口的问题
                    NsqService service = aClass.getAnnotation(NsqService.class);
                    String value = service.value();
                    Object instance = aClass.newInstance();
                    if(this.isEmptly(value)){
                           ioc.put(this.toLowerFirstCase(aClass.getSimpleName()),instance);
                    }
                    for (Class inter:aClass.getInterfaces()) {
                        //这里考虑依赖注入将接口的实现bean和本身一样,为了后续DI做准备
                        String s = this.toLowerFirstCase(inter.getSimpleName());
                        if(ioc.containsKey(s)){
                            throw new Exception("bean key"+s+"is exits");
                        }else {
                            ioc.put(this.toLowerFirstCase(inter.getSimpleName()),instance);
                        }
                    }
                }else{
                    //没加注解则忽略
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isEmptly(String s){
        if(s==null||s.trim().length()==0){
            return true;
        }else{
            return false;
        }
    }


    private String toLowerFirstCase(String className){
        if(className==null||className.trim().length()==0)return "";
        char[] chars = className.toCharArray();
        chars[0] += 32;
        return  String.valueOf(chars);
    }



    //进行类的扫描 将扫描的类加载到内存当中去
    private void doScanner(String scanPackage) {
        //扫描当前有哪些类将其加载到内存当中
        //将  报名的.  换成/
        URL url = this.getClass().
                getClassLoader().
                getResource("/" + scanPackage.replaceAll("\\.","/"));
        File classPath = new File(url.getFile());
        for (File file: classPath.listFiles()) {
            if(file.isDirectory()){
                //是文件夹则进行递归找到其子文件
                doScanner(scanPackage + "." + file.getName());
            }else{
                //非文件夹则需要将文件名称加载到内存当中去
                if(!file.getName().endsWith(".class")){ continue;}//出去非class文件
                String className = (scanPackage + "." + file.getName().replace(".class",""));
                classNames.add(className);
            }
        }
    }

    private void doloadConig(String contextConfigLocation) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            contextConfig.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    //ignore
                }
            }
        }
    }



}
