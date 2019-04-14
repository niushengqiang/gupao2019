package gpspring.framework.beans.config;

/**
 * 类的描述
 */
public class GPBeanWrapper {
    private Object wrappedObject;
    private Class wrappedObjectClass;
    private boolean isSingleton;

    public Object getWrappedObject() {
        return wrappedObject;
    }

    public void setWrappedObject(Object wrappedObject) {
        this.wrappedObject = wrappedObject;
    }

    public Class getWrappedObjectClass() {
        return wrappedObjectClass;
    }

    public void setWrappedObjectClass(Class wrappedObjectClass) {
        this.wrappedObjectClass = wrappedObjectClass;
    }

    public boolean isSingleton() {
        return isSingleton;
    }

    public void setSingleton(boolean singleton) {
        isSingleton = singleton;
    }
}
