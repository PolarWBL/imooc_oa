package com.ctgu.oa.dao;

import com.ctgu.oa.entity.User;

import java.util.List;

/**
 * 用户操作类
 * @author Boliang Weng
 */
public interface UserDao {
    /**
     * 通过username查询用户
     * @param username 用户名
     * @return 返回用户实体类
     */
    public User selectUserByUsername(String username);

    /**
     * 获取所有用户
     * @return 返回用户list集合
     */
    public List<User> selectAllUser();
}
