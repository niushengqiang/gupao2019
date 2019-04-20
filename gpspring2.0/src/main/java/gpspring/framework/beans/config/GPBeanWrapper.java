package gpspring.framework.beans.config;

/**
 * 类的描述
 */
public class GPBeanWrapper {
    private Object wrappedObject;
    private Class wrappedObjectClass;


    public GPBeanWrapper(Object wrappedObject) {
        this.wrappedObject = wrappedObject;
        this.wrappedObjectClass=wrappedObject.getClass();
    }

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
}
