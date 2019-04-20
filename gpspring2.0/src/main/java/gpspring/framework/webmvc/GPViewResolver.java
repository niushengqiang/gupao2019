package gpspring.framework.webmvc;


import java.io.File;
import java.util.Locale;

//页面关联
public class GPViewResolver {
    private final String DEFAULT_TEMPLATE_SUFFX = ".html";
    private File templateRootDir;

    public GPViewResolver(String templateRoot) {
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        templateRootDir = new File(templateRootPath);
    }

    //根据页面的名称将 文件名称与实际的文件进行关联
    public GPview resolveViewName(String viewName, Locale locale) throws Exception{
        if(null == viewName || "".equals(viewName.trim())){return null;}
        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFX) ? viewName : (viewName + DEFAULT_TEMPLATE_SUFFX);
        File templateFile = new File((templateRootDir.getPath() + "/" + viewName).replaceAll("/+","/"));
        return new GPview(templateFile);
    }
}
