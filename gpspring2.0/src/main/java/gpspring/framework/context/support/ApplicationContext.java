package gpspring.framework.context.support;


import gpspring.framework.annotation.GPAutowired;
import gpspring.framework.aop.GPAopProxy;
import gpspring.framework.aop.GPCGlibAopProxy;
import gpspring.framework.aop.GPJDKAopProxy;
import gpspring.framework.aop.config.GPAopConfig;
import gpspring.framework.aop.support.GPAdvisedSupport;
import gpspring.framework.beans.config.GPBeanDefinition;
import gpspring.framework.beans.config.GPBeanWrapper;
import gpspring.framework.beans.support.DefaultListableBeanFactory;
import gpspring.framework.beans.support.GPBeanDefinitionReader;
import gpspring.framework.core.BeanFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext extends DefaultListableBeanFactory implements BeanFactory{

    private  String[] locations;

    //单例对象存储的
    private Map<String,Object> factoryBeanObjectCache = new ConcurrentHashMap<String, Object>();

    //通用的IOC容器
    private Map<String,GPBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<String, GPBeanWrapper>();


    private final Map<String, Set<String>> dependentBeanMap = new ConcurrentHashMap<>(64);


    private   GPBeanDefinitionReader reader;

    public ApplicationContext(String ...locations) {
        this.locations=locations;
        try {
            this.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void refresh() throws Exception {
        //1、定位，定位配置文件
        reader =new GPBeanDefinitionReader(locations);
        //2、加载配置文件，扫描相关的类，把它们封装成BeanDefinition
        List<GPBeanDefinition> gpBeanDefinitions = reader.loadBeanDefinitions();
        //3、注册，把配置信息放到容器里面(伪IOC容器)
        this.doRegistoryBeanDefition(gpBeanDefinitions);
        //4、把不是延时加载的类，有提前初始化
        this.doAutowrited();
    }

    private void doRegistoryBeanDefition(List<GPBeanDefinition> gpBeanDefinitions) throws Exception {
        for (GPBeanDefinition gpBeanDefinition: gpBeanDefinitions ) {
            //单独加的功能
            dependentBeanMap.put(gpBeanDefinition.getFactoryBeanName(),gpBeanDefinition.getDependsOn());
            super.beanDefinitionMap.put(gpBeanDefinition.getFactoryBeanName(),gpBeanDefinition);
        }
    }

    private void doAutowrited() {
        for (Map.Entry<String, GPBeanDefinition> beanDefinitionEntry : super.beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            if(!beanDefinitionEntry.getValue().isLazyInit()) {
                getBean(beanName);
            }
        }
    }


    @Override//这里是进行初始化获得bean的过程
    public <T> T getBean(String name){
        GPBeanDefinition gpBeanDefinition = beanDefinitionMap.get(name);
        Object instance=null;
        //创建对应的bean
        try {
            instance=this.doCreateBean(name,gpBeanDefinition);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T)instance;
    }

    private Object doCreateBean(String beanName,GPBeanDefinition gpBeanDefinition) throws Exception {
        //处理相互依赖的问题
        Set<String> dependsOn = gpBeanDefinition.getDependsOn();
        //如果当前Bean有依赖Bean
        if (dependsOn != null) {
            for (String dep : dependsOn) {
                isDependent(beanName,dep);
                this.getBean(dep);
            }
        }
        //初始化对象
        GPBeanWrapper instanceWrapper=null;
        //根据beanWrapper 获取可用的bean对象
        Object instance=this.initializeBean(beanName,gpBeanDefinition);
        instanceWrapper=new GPBeanWrapper(instance);
        factoryBeanInstanceCache.put(beanName,instanceWrapper);
        //进行类型的注入
        this.populateBean(beanName,gpBeanDefinition, instanceWrapper);
        //返回对象
        return instanceWrapper.getWrappedObject();
    }

    //这里简单操作不做复杂依赖判断
    private void isDependent(String beanName, String dep) throws Exception {
        Set<String> deps = dependentBeanMap.get(dep);
        if(deps==null||deps.size()==0){
            return ;
        }
        //这里证明是相互依赖
        if(deps.contains(beanName)){
            throw new  Exception("出现相互依赖的情况在" +dep+"和"+beanName+"之间");
        }
        for (String de:deps) {
            this.isDependent(beanName,de);
        }
    }


    //根据bena Wrapper 返回实例化的bean
    private Object initializeBean(String beanName, GPBeanDefinition beanDefinition) {
        String className = beanDefinition.getBeanClassName();
        Object instance = null;
        try{
            if(this.factoryBeanObjectCache.containsKey(className)){
                instance = this.factoryBeanObjectCache.get(className);
            }else {
                //生成对象
                //并放入至缓存当中去
                Class<?> clazz = Class.forName(className);
                instance = clazz.newInstance();

                /**
                 * 这里进行Apo的解析
                 */
                GPAdvisedSupport config = instantionAopConfig(beanDefinition);
                config.setTargetClass(clazz);
                config.setTarget(instance);

                //符合PointCut的规则的话，闯将代理对象
                if(config.pointCutMatch()) {
                    instance = createProxy(config).getProxy();
                }

                this.factoryBeanObjectCache.put(className,instance);
                this.factoryBeanObjectCache.put(beanDefinition.getFactoryBeanName(),instance);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private GPAopProxy createProxy(GPAdvisedSupport config) {
        Class targetClass = config.getTargetClass();
        if(targetClass.getInterfaces().length > 0){
            return new GPJDKAopProxy(config);
        }
        return new GPCGlibAopProxy(config);
    }

    /**
     * 加载Aop的初始化配置文件
     * @param beanDefinition
     */
    private GPAdvisedSupport instantionAopConfig(GPBeanDefinition beanDefinition) {
        //这里默只是实现一种包装
        GPAopConfig config = new GPAopConfig();
        config.setPointCut(this.reader.getConfig().getProperty("pointCut"));
        config.setAspectClass(this.reader.getConfig().getProperty("aspectClass"));
        config.setAspectBefore(this.reader.getConfig().getProperty("aspectBefore"));
        config.setAspectAfter(this.reader.getConfig().getProperty("aspectAfter"));
        config.setAspectAfterThrow(this.reader.getConfig().getProperty("aspectAfterThrow"));
        config.setAspectAfterThrowingName(this.reader.getConfig().getProperty("aspectAfterThrowingName"));
        return new GPAdvisedSupport(config);
    }

    //进行属性值的注入
    private void populateBean(String beanName, GPBeanDefinition beanDefinition,GPBeanWrapper instanceWrapper) throws Exception {
        //将现有的beanWrapper进行属性的注入
        Object wrappedObject = instanceWrapper.getWrappedObject();
        //根据class获取属性
        Class wrappedObjectClass = instanceWrapper.getWrappedObjectClass();
        Field[] classFields = wrappedObjectClass.getFields();
        //对属性进行遍历
        for (Field field:classFields) {
            //不需要自动注入的进行跳过
            if(!field.isAnnotationPresent(GPAutowired.class))continue;
            String autoWiredBeanName=null;

            GPAutowired annotation = field.getAnnotation(GPAutowired.class);
            String value = annotation.value();
            if(null!=value&&"".equals(value.trim())){
                autoWiredBeanName=this.toFirstLowerCase(field.getClass().getSimpleName());
            }else{
                autoWiredBeanName=value;
            }
            Object o = factoryBeanObjectCache.get(autoWiredBeanName);
            field.setAccessible(true);
            field.set(wrappedObject,o);
        }
    }
    private String toFirstLowerCase(String string){
        char [] chars =  string.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    public String[] getBeanDefinitionNames() {
        return super.beanDefinitionMap.
                keySet().
                toArray(new String[this.beanDefinitionMap.size()]);
    }
    public Properties getConfig(){
        return  this.reader.getConfig();
    }
}
