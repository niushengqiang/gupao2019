package gpspring.framework.context.support;


import gpspring.framework.annotation.GPAutowired;
import gpspring.framework.beans.config.GPBeanDefinition;
import gpspring.framework.beans.config.GPBeanWrapper;
import gpspring.framework.beans.support.DefaultListableBeanFactory;
import gpspring.framework.beans.support.GPBeanDefinitionReader;
import gpspring.framework.core.BeanFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext extends DefaultListableBeanFactory implements BeanFactory{

    private  String[] locations;

    //单例对象存储的
    private Map<String,Object> singletonObjects = new ConcurrentHashMap<String, Object>();

    //通用的IOC容器
    private Map<String,GPBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<String, GPBeanWrapper>();


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
         GPBeanDefinitionReader reader=new GPBeanDefinitionReader(locations);
        //2、加载配置文件，扫描相关的类，把它们封装成BeanDefinition
        List<GPBeanDefinition> gpBeanDefinitions = reader.loadBeanDefinitions();
        //3、注册，把配置信息放到容器里面(伪IOC容器)
        this.doRegistoryBeanDefition(gpBeanDefinitions);
        //4、把不是延时加载的类，有提前初始化
        this.doAutowrited();
    }

    private void doRegistoryBeanDefition(List<GPBeanDefinition> gpBeanDefinitions) throws Exception {
        for (GPBeanDefinition gpBeanDefinition: gpBeanDefinitions ) {
            if(super.beanDefinitionMap.containsKey(gpBeanDefinition.getFactoryBeanName())){
                throw new Exception("The “" + gpBeanDefinition.getFactoryBeanName() + "” is exists!!");
            }
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
        //初始化对象
        GPBeanWrapper instanceWrapper=null;
        //初始化beanWrapper
        instanceWrapper = this.createBeanInstance(beanName, gpBeanDefinition);
        //进行类型的注入
        this.populateBean(beanName, instanceWrapper);
        //根据beanWrapper 获取可用的bean对象
        Object obj=this.initializeBean(beanName,instanceWrapper);
        //对象进行属性赋值
        return obj;
    }


    //根据bena Wrapper 返回实例化的bean
    private Object initializeBean(String beanName, GPBeanWrapper instanceWrapper) {
        return null;
    }

    //进行属性值的注入
    private void populateBean(String beanName, GPBeanWrapper instanceWrapper) {
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

            }
        }
    }

    //初始化beanwrapper
    private GPBeanWrapper createBeanInstance(String beanName, GPBeanDefinition gpBeanDefinition) throws Exception {
        GPBeanWrapper beanWrapper = new GPBeanWrapper();
        beanWrapper.setSingleton(gpBeanDefinition.isSingleton());

        String beanClassName = gpBeanDefinition.getBeanClassName();
        Class<?> aClass = Class.forName(beanClassName);
        beanWrapper.setWrappedObjectClass(aClass);

        Object o = aClass.newInstance();
        beanWrapper.setWrappedObject(o);

        return beanWrapper;
    }

}
