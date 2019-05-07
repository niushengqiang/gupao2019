package com.gp.mibatis.demo;

public interface BlogMapper {
    /**
     * 根据主键查询文章
     * @param bid
     * @return
     */
     Blog selectBlogById(Integer bid);
}
