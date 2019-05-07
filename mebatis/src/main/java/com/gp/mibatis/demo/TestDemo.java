package com.gp.mibatis.demo;

import com.gp.mibatis.myversion.Configuration;
import com.gp.mibatis.myversion.GpSqlSession;
import com.gp.mibatis.myversion.MiExecutor;

public class TestDemo {

    public static void main(String[] args) {
        GpSqlSession gpSqlSession = new GpSqlSession(new MiExecutor(), new Configuration());
        BlogMapper mapper = gpSqlSession.getMapper(BlogMapper.class);
        Blog blog = mapper.selectBlogById(1);
        System.out.println(blog);

    }
}
