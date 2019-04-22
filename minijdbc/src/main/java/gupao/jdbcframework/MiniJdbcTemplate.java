package gupao.jdbcframework;

import gupao.jdbcframework.resulthandl.ResultSetMap;

import java.sql.SQLException;
import java.util.List;

public interface MiniJdbcTemplate {
    int insertByEntity(Object entity) throws SQLException;
    int updateByEntity(Object entity) throws  Exception;
    int deleteByEntity(Object entity)throws  Exception;
    List selectByEntity(Object entity)throws  Exception;
    int insertBySql(String  sql)throws  Exception;
    int updateBySql(String  sql)throws  Exception;
    int deleteBySql(String  sql)throws  Exception;
    //这里可以自定义结果集的收集
    List<Object> selectBySql(String  sql,ResultSetMap resultSetMap)throws  Exception;
}
