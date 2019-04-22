package gupao.jdbcframework;

import gupao.jdbcframework.annotation.Table;
import gupao.jdbcframework.conversion.Field2String;
import gupao.jdbcframework.conversion.TypeConversion;
import gupao.jdbcframework.resulthandl.ResultSetMap;
import gupao.jdbcframework.resulthandl.ResultSetMapFactiory;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MiniJdbcTemplateImpl extends  AbstractMiniJdbcTemplate{

    private TypeConversion tc;

    public MiniJdbcTemplateImpl(DataSource dataSource) {
        super(dataSource);
        tc=new Field2String();
    }

    @Override
    public int insertByEntity(Object entity) throws SQLException {
        Connection conn = super.getConn();
        try {
            StringBuffer sb=new StringBuffer();
            List<String> list=new ArrayList<>();
            sb.append("insert into ");
            String tablesname="";
            Class<?> aClass = entity.getClass();
            if(aClass.isAnnotationPresent(Table.class)){
                Table table = aClass.getAnnotation(Table.class);
                tablesname=table.value();
            }else{
                //类名首字符小写便是 表的名称
                tablesname=getLowverCase(aClass.getSimpleName());
            }
            sb.append(tablesname);
            sb.append("(");
            Field[] fields = aClass.getFields();
            for (int i = 0; i < fields.length; i++) {
                Field field=fields[i];
                Object o = field.get(entity);
                //获取参数值
                list.add((String) tc.change(field.getType(), o));
                ResultSetMap resultSetMap = ResultSetMapFactiory.getResultSetMap(aClass);
                String dbColumByFile = resultSetMap.getDbColumByFile(field.getName());
                sb.append(dbColumByFile);
                if(i!=fields.length-1){
                    sb.append(",");
                }
            }
            sb.append(")");
            sb.append("values");
            sb.append(" （");
            for (int i = 0; i <list.size() ; i++) {
                String s = list.get(i);
                sb.append(s);
                if(i!=list.size()-1){
                    sb.append(",");
                }else{
                    sb.append(")");
                }
            }
            String sql = sb.toString();
            return  this.insertBySql(sql);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int updateByEntity(Object entity) throws Exception {
        StringBuffer sb=new StringBuffer();
        Class<?> aClass = entity.getClass();
        ResultSetMap resultSetMap = ResultSetMapFactiory.getResultSetMap(aClass);
        String tableName="";
        if(aClass.isAnnotationPresent(Table.class)){
            Table table = aClass.getAnnotation(Table.class);
            tableName = table.value();
        }else{
            String simpleName = aClass.getSimpleName();
            tableName = super.getLowverCase(simpleName);
        }
        sb.append("update ");
        sb.append(tableName);
        sb.append(" set");

        Field[] fields = aClass.getFields();
        for (int i = 0; i < fields.length; i++) {
            Field f=fields[i];
            String fieldName = f.getName();
            if("id".equals(fieldName))continue;
            String dbColumByFile = resultSetMap.getDbColumByFile(fieldName);
            Object o = f.get(entity);
            sb.append(dbColumByFile);
            sb.append("=");
            sb.append("‘");
            sb.append(tc.change(String.class,o));
            sb.append("’");
            if(i!=fields.length-1){
                sb.append(",");
            }
        }
        Field id = aClass.getField("id");
        Object idval = id.get(entity);
        sb.append(" where ");
        sb.append(" id= ‘");
        sb.append(tc.change(String.class,idval));
        sb.append("’");
        String sql=sb.toString();
        return this.deleteBySql(sql);
    }

    @Override
    public int deleteByEntity(Object entity) throws  Exception {
        ResultSetMap resultSetMap = ResultSetMapFactiory.getResultSetMap(entity.getClass());
        StringBuffer sb=new StringBuffer();
        sb.append(" delete from ");
        String tableName = resultSetMap.getTableName();
        sb.append(tableName);
        sb.append(" where");
        sb.append(" id");
        sb.append("=");
        sb.append("'");
        Field id = entity.getClass().getField("id");
        Object o = id.get(entity);
        sb.append(tc.change(String.class,o));
        sb.append("'");
        return  this.deleteBySql(sb.toString());
    }

    @Override
    public List selectByEntity(Object entity) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("select * from ");

        ResultSetMap resultSetMap = ResultSetMapFactiory.getResultSetMap(entity.getClass());
        stringBuffer.append(resultSetMap.getTableName());
        stringBuffer.append(" where");
        Class<?> aClass = entity.getClass();
        Field[] fields = aClass.getFields();
        for (int i=0;i<fields.length;i++) {
            Field f = fields[i];
            Object obj=f.get(entity);
            if(obj==null) continue;
            String dbColumByFile = resultSetMap.getDbColumByFile(f.getName());
            stringBuffer.append(dbColumByFile);
            stringBuffer.append("=");
            stringBuffer.append("'");
            stringBuffer.append(tc.change(f.getDeclaringClass(),obj));
            stringBuffer.append("'");
            if(i!=(fields.length-1)){
                stringBuffer.append("and ");
            }
        }
        return selectBySql(stringBuffer.toString(),null);
    }


    @Override
    public int insertBySql(String sql) throws SQLException {
        int i =0;
        Connection conn =null;
        Statement statement =null;
        try{
            conn = getConn();
            statement = conn.createStatement();
            i = statement.executeUpdate(sql);
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null){
                conn.close();
            }
            if(statement!=null){
                statement.close();
            }
        }
        return i;
    }

    @Override
    public int updateBySql(String sql) throws SQLException {
        int i=0;
        Connection conn =null;
        Statement statement =null;
        try{
            conn = getConn();
            statement = conn.createStatement();
             i = statement.executeUpdate(sql);
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null){
                conn.close();
            }
            if(statement!=null){
                statement.close();
            }
        }
        return i;
    }

    @Override
    public int deleteBySql(String sql) throws SQLException {
        int i=0;
        Connection conn =null;
        Statement statement =null;
        try{
            conn = getConn();
            statement = conn.createStatement();
            i = statement.executeUpdate(sql);
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null){
                conn.close();
            }
            if(statement!=null){
                statement.close();
            }
        }
        return i;
    }

    @Override
    public List<Object> selectBySql(String sql,ResultSetMap resultSetMap) throws SQLException {
        int i=0;
        List returns=null;
        Connection conn =null;
        Statement statement =null;
        try{
            conn = getConn();
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            returns = resultSetMap.MappRalation(resultSet);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (conn != null) {
                conn.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        return returns;
    }
}
