package gpspring.framework.beans.support;

import gpspring.framework.annotation.GPAutowired;
import gpspring.framework.beans.config.GPBeanDefinition;
import gpspring.framework.context.support.Contant;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
/**
 * 进行数据加载的类
 *  1.扫描配置资源
 *  2.扫描配置的加载的类
 */
public class GPBeanDefinitionReader {

    private final Properties config=new Properties();

    private List<String> registyBeanClasses=new ArrayList<>();

    private String[] locations;


    public GPBeanDefinitionReader(String[] locations) {
        this.locations = locations;
        loadBeanDefintion();
    }
    //加载配置文件
    private int loadBeanDefintion(){
        int j=0;
        if(locations==null||locations.length==0)return 0;
        for (int i = 0; i < locations.length; i++) {
            try {
                this.loadOneLocationBeanDefintion(locations[i]);
                j++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.doScanner((String) config.get(Contant.SCAN_PACKAGE));
        return j;
    }

    public Properties getConfig(){
        return  this.config;
    }

    //扫描一个包下所有的类
    private void doScanner(String scanPackage) {
        //获取根文件进行遍历获取
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.","/"));
        File classPath = new File(url.getFile());
        for (File file : classPath.listFiles()) {
            if(file.isDirectory()){
                doScanner(scanPackage + "." + file.getName());
            }else{
                if(!file.getName().endsWith(".class")){ continue;}
                String className = (scanPackage + "." + file.getName().replace(".class",""));
                registyBeanClasses.add(className);
            }
        }
    }

    public List<GPBeanDefinition> loadBeanDefinitions() throws ClassNotFoundException {
        List<GPBeanDefinition> returns=new ArrayList<>();
        for (String className: registyBeanClasses ) {
            Class<?> aClass = Class.forName(className);
            if(aClass.isInterface())continue;
            GPBeanDefinition gpBeanDefinition = docreateBeanDefition(aClass);
            returns.add(gpBeanDefinition);

            //设置当前bean的依赖bean;
            List<String> dependsOn = gpBeanDefinition.getDependsOn();
            Field[] fields = aClass.getFields();
            for (Field f:fields ) {
                if(f.isAnnotationPresent(GPAutowired.class)){
                    Class<?> declaringClass = f.getDeclaringClass();
                    String simpleName = declaringClass.getSimpleName();
                    dependsOn.add(this.toFirstLowerCase(simpleName));
                }
            }
            Class<?>[] interfaces = aClass.getInterfaces();
            for (Class clazz:interfaces){
                returns.add(docreateBeanDefition(clazz));
            }
        }
        return returns;
    }


    private GPBeanDefinition docreateBeanDefition(Class clazz){
        GPBeanDefinition gpBeanDefinition = new GPBeanDefinition();
        gpBeanDefinition.setBeanClassName(clazz.getName());
        gpBeanDefinition.setFactoryBeanName(this.toFirstLowerCase(clazz.getSimpleName()));
        return gpBeanDefinition;
    }

    private String toFirstLowerCase(String string){
        char [] chars =  string.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }


















    private  void loadOneLocationBeanDefintion(String location) throws IOException {
        InputStream is = this.getClass().getClassLoader()
                .getResourceAsStream(location.replace("classpath:",""));
        config.load(is);
        is.close();
    }
}
