package gpspring.framework.webmvc;

import gpspring.framework.annotation.GPController;
import gpspring.framework.annotation.GPRequestMapping;
import gpspring.framework.context.support.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.RequestWrapper;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GPDispatcherServlet extends HttpServlet{

    private final String CONTEXT_CONFIG_LOCATION="contextConfigLocation";

    private List<GpHanlderMapping> handlerMpapings=new ArrayList<GpHanlderMapping>();
    private Map<GpHanlderMapping,GPHandlerAdapter> handlerAdapters = new HashMap<GpHanlderMapping,GPHandlerAdapter>();
    private List<GPViewResolver> viewResolvers = new ArrayList<GPViewResolver>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.dispatch(req,resp);
        } catch (Exception e) {
            resp.getWriter().write("500 Exception,Details:\r\n"
                    + Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]", "").replaceAll(",\\s", "\r\n"));
            e.printStackTrace();
        }
    }


    /**
     * 委派模式进行请求的调度
     * @param req
     * @param resp
     */
    private void dispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //关联hanlder
        GpHanlderMapping handler = getHandler(req);
        if(handler == null){
            processDispatchResult(req,resp,new GPModelAndView("404"));
            return;
        }
        //2、准备调用前的参数
        GPHandlerAdapter ha = getHandlerAdapter(handler);

        //3、真正的调用方法,返回ModelAndView存储了要穿页面上值，和页面模板的名称
        GPModelAndView mv = ha.handle(req,resp,handler);
        //这一步才是真正的输出
        processDispatchResult(req, resp, mv);

    }

    private GPHandlerAdapter getHandlerAdapter(GpHanlderMapping handler) {
        GPHandlerAdapter gpHandlerAdapter = this.handlerAdapters.get(handler);
        if(null==gpHandlerAdapter){return null;}
        if(gpHandlerAdapter.supports(handler)){
            return gpHandlerAdapter;
        }
        return null;
    }

    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, GPModelAndView gpModelAndView) throws Exception {
        if(null == gpModelAndView){return;}
        //如果ModelAndView不为null，怎么办？
        if(this.viewResolvers.isEmpty()){return;}
        for (GPViewResolver viewResolver : this.viewResolvers) {
            GPview view = viewResolver.resolveViewName(gpModelAndView.getViewName(),null);
            view.render(gpModelAndView.getModel(),req,resp);
            return;
        }
    }

    /**
     * 关联请求与hanlderMapping的区别
     * @param req
     * @return
     */
    private GpHanlderMapping getHandler(HttpServletRequest req) {
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        for (GpHanlderMapping hm:handlerMpapings){
            Pattern pattern = hm.getPattern();
            Matcher matcher = pattern.matcher(url);
            if(!matcher.matches()){ continue; }
            return hm;
        }
        return null;
    }

    @Override//进行初始化
    public void init(ServletConfig config) throws ServletException {
        //读取配置文件
        //1、初始化ApplicationContext
        ApplicationContext context = new ApplicationContext(config.getInitParameter(CONTEXT_CONFIG_LOCATION));
        //加载spring九大组件
        initStrategies(context);
    }

    /**
     * @param context
     * 初始spring九大组件
     */
    private void initStrategies(ApplicationContext context) {
        //多文件上传组件
        initMultipartResolver(context);
        //初始化本地环境组件
        initLocaleResolver(context);
        //初始化模板化组件
        initThemeResolver(context);
        //handlerMapping
        initHandlerMappings(context);
        //初始化参数适配器
        initHandlerAdapters(context);
        //初始化异常拦截器
        initHandlerExceptionResolvers(context);
        //初始化异常预处理器
        initRequestToViewNameTranslator(context);
        //初始化视图转换器
        initViewResolvers(context);
        initFlashMapManager(context);
    }

    private void initFlashMapManager(ApplicationContext context) {
        System.out.println("initFlashMapManager");
    }

    //必要
    private void initViewResolvers(ApplicationContext context) {
        System.out.println("视图转换器");
        //拿到模板的存放目录
        String templateRoot = context.getConfig().getProperty("templateRoot");
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();

        File templateRootDir = new File(templateRootPath);
        String[] templates = templateRootDir.list();
        for (int i = 0; i < templates.length; i ++) {
            this.viewResolvers.add(new GPViewResolver(templateRoot));
        }
    }

    private void initRequestToViewNameTranslator(ApplicationContext context) {
        System.out.println("异常预处理器");
    }

    private void initHandlerExceptionResolvers(ApplicationContext context) {
        System.out.println("异常拦截器");
    }

    private void initHandlerAdapters(ApplicationContext context) {
        System.out.println("初始化参数适配器");
        for (GpHanlderMapping hm:this.handlerMpapings) {
            this.handlerAdapters.put(hm,new GPHandlerAdapter());
        }
    }

    /**
     * 处理器映射器
     * 最终是封装了一个 名为handlerMpapings 的集合
     * @param context
     */
    private void initHandlerMappings(ApplicationContext context) {
        System.out.println("处理器映射器");
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        try{
            for (String beanName:beanDefinitionNames){
                Object contorller = context.getBean(beanName);
                Class<?> aClass = contorller.getClass();
                if(!aClass.isAnnotationPresent(GPController.class)){
                    //非controller则进行注释
                    continue;
                }
                String baseUrl="";
                if(!aClass.isAnnotationPresent(GPRequestMapping.class)){
                    GPRequestMapping annotation = aClass.getAnnotation(GPRequestMapping.class);
                    baseUrl= annotation.value();
                }
                Method[] methods = aClass.getMethods();
                for (Method m:methods){
                    if(!m.isAnnotationPresent(GPRequestMapping.class))continue;
                    GPRequestMapping requestMapping = m.getAnnotation(GPRequestMapping.class);
                    String regex = ("/" + baseUrl + "/" + requestMapping.value().
                            replaceAll("\\*",".*"))
                            .replaceAll("/+", "/");
                    Pattern pattern = Pattern.compile(regex);
                    GpHanlderMapping gpHanlderMapping = new GpHanlderMapping(contorller, m, pattern);
                    handlerMpapings.add(gpHanlderMapping);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initThemeResolver(ApplicationContext context) {
        System.out.println("初始化模板化组件");
    }

    private void initLocaleResolver(ApplicationContext context) {
        System.out.println("初始化本地环境组件");
    }

    private void initMultipartResolver(ApplicationContext context) {
        System.out.println("多文件上传组件");
    }

}
