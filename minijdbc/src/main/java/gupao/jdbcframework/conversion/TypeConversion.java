package gupao.jdbcframework.conversion;

/**
 * 类型转换
 */
public interface TypeConversion<E> {

    //将val由T转换为E
    E change(Class original,Object val);

}
