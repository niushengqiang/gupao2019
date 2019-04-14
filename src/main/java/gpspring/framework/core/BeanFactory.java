package gpspring.framework.core;

public interface BeanFactory {
    <T> T getBean(String name);
}
