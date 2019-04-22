package gupao.jdbcframework;

import com.mysql.jdbc.ConnectionPropertiesTransform;

import javax.sql.DataSource;
import java.sql.*;

/**
 * 进行基础流程的封装
 */
public abstract class AbstractMiniJdbcTemplate implements MiniJdbcTemplate{
    private DataSource dataSource;

    public AbstractMiniJdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private final static ThreadLocal<Connection> threadLocal=new ThreadLocal<>();

    protected Connection getConn(){
        try {
            Connection connection = dataSource.getConnection();
            threadLocal.set(connection);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }





    //基础的jdbc操作
    //进行sql语句的封装
    //结果集的收集

    protected void exec(String sql) throws SQLException {
        Connection conn=null;
        Statement stmt=null;
        ResultSet rs=null;
        try {
            conn = dataSource.getConnection();
            //3获取数据库操作对象
            stmt=conn.createStatement();
            rs=stmt.executeQuery(sql);
            //处理查询结果集
            while(rs.next())
            {
                String id=rs.getString("Email");
                String name=rs.getString("name");
                System.out.println(id+" "+ name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            //6关闭资源
            if(rs!=null)
            {
                try{rs.close();}catch (Exception ex)
                {ex.printStackTrace();}
            }
            //关闭资源
            if(stmt!=null)
            {
                try{stmt.close();}catch (Exception ex)
                {ex.printStackTrace();}
            }
            //关闭资源
            if(conn!=null)
            {
                try{conn.close();}catch (Exception ex)
                {ex.printStackTrace();}
            }
        }
    }





    public static   String getLowverCase(String str){
        char[] chars = str.toCharArray();
        chars[0]= (char) (chars[0]+32);
        return new String(chars);
    }


}
