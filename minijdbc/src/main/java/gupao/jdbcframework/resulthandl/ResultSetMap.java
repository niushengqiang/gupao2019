package gupao.jdbcframework.resulthandl;

import java.sql.ResultSet;
import java.util.List;


/**
 * 用于用户自己拓展的转换实现
 * @param <T>
 */
public interface ResultSetMap<T> {
    List<T> MappRalation(ResultSet rs);
    //在进行参数转换的时候需要用到
    String getDbColumByFile(String str);
    String getFileByDbColum(String str);
    String getTableName();
}
