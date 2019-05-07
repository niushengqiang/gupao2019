package com.gp.mibatis.myversion;


public class GpSqlSession {

    private MiExecutor miExecutor;//真正的代码执行器

    private Configuration configuration;

    public GpSqlSession(MiExecutor miExecutor, Configuration configuration) {
        this.miExecutor = miExecutor;
        this.configuration = configuration;
    }

    public <T> T selectOne(String statementId, Object paramater){
        // 根据statementId拿到SQL
        String sql = Configuration.sqlMappings.getString(statementId);
        if(null != sql && !"".equals(sql)){
            return miExecutor.query(sql, paramater );
        }
        return null;
    }

    public <T> T getMapper(Class clazz){
        return configuration.getMapper(clazz, this);
    }
}
