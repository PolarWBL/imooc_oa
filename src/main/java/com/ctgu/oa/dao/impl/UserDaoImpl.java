package com.ctgu.oa.dao.impl;

import com.ctgu.oa.dao.UserDao;
import com.ctgu.oa.entity.User;
import com.ctgu.oa.utils.MybatisUtils;

import java.util.List;

/**
 * @author Boliang Weng
 */
public class UserDaoImpl implements UserDao {
    @Override
    public User selectUserByUsername(String username) {
        return (User) MybatisUtils.executeQuery(sqlSession -> {
            UserDao mapper = sqlSession.getMapper(UserDao.class);
            return mapper.selectUserByUsername(username);
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> selectAllUser() {
        return (List<User>) MybatisUtils.executeQuery(sqlSession -> {
            UserDao mapper = sqlSession.getMapper(UserDao.class);
            return mapper.selectAllUser();
        });
    }
}
