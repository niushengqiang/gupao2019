package gupao.jdbcframework.resulthandl;

import gupao.jdbcframework.annotation.Column;
import gupao.jdbcframework.annotation.Table;
import gupao.jdbcframework.commutil.Util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 用于用户自己拓展的转换实现
 * 可以考虑拓展为单例 因为一个class 只会有一个转换形式
 * @param <T>
 */
public class DefaultResultSetMap<T> implements ResultSetMap{
    private  T t;
    private Class aClass;
    private String tableName;
    //字段映射K为数据库中字段名称 V是实体类中字段名称
    private Map<String,String> dbColumRelationField=new HashMap<>();

    //进行
    private Map<String,String> fieldRelationdbColum=new HashMap<>();


    @Override
    public String getDbColumByFile(String s){
        return fieldRelationdbColum.get(s);
    }
    @Override
    public  String getFileByDbColum(String str){
        return dbColumRelationField.get(str);
    }

    @Override
    public String getTableName() {
        return this.tableName;
    }
    public DefaultResultSetMap(T t) {
        this.t = t;
        aClass=t.getClass();
        Field[] fields = aClass.getFields();
        for (Field f:fields) {
            String fieldName = f.getName();
            if(f.isAnnotationPresent(Column.class)){
                Column annotation = f.getAnnotation(Column.class);
                String value = annotation.value();
                if(!"".equals(value)){
                    dbColumRelationField.put(value,fieldName);
                    fieldRelationdbColum.put(fieldName,value);
                }else{
                    dbColumRelationField.put(fieldName,fieldName);
                    fieldRelationdbColum.put(fieldName,fieldName);
                }
            }else{
                dbColumRelationField.put(fieldName,fieldName);
                fieldRelationdbColum.put(fieldName,fieldName);
            }
        }
        if(aClass.isAnnotationPresent(Table.class)){
            Table annotation = (Table) aClass.getAnnotation(Table.class);
            tableName = annotation.value();
        }else{
            String simpleName = aClass.getSimpleName();
            tableName=Util.getLowverCase(simpleName);
        }
    }


    public List<T> MappRalation(ResultSet rs){
        try {
            List<T> theReturn=new ArrayList<T>();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while(rs.next()){
                T o =(T) aClass.newInstance();
                for(int j=1 ;j<=columnCount;j++){
                    String catalogName = metaData.getCatalogName(j);
                    this.fieldSetAndTypeReturn(o,catalogName,rs);
                }
                theReturn.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return theReturn;
    }


    //进行类型转换并赋值
    private void fieldSetAndTypeReturn(Object target,String columName,ResultSet rs){
        try {
            String s = dbColumRelationField.get(columName);
            Field field = aClass.getField(s);
            field.setAccessible(true);
            Class<?> type = field.getType();
            //这里列举几个简单的类型转换.策略模式更好
            if(String.class==type){
                field.set(target,rs.getString(columName));
            }else if(java.sql.Date.class==type){
                //sql data 是util data的子类可以直接转换
                field.set(target,rs.getDate(columName));
             }else if(Integer.class==type){
                field.set(target,rs.getInt(columName));
            }else if(Long.class==type){
                field.set(target,rs.getLong(columName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}