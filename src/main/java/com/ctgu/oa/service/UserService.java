package com.ctgu.oa.service;

import com.ctgu.oa.dao.NodeDao;
import com.ctgu.oa.dao.impl.NodeDaoImpl;
import com.ctgu.oa.dao.UserDao;
import com.ctgu.oa.dao.impl.UserDaoImpl;
import com.ctgu.oa.entity.Node;
import com.ctgu.oa.entity.User;
import com.ctgu.oa.service.exception.BusinessException;
import com.ctgu.oa.utils.Md5Utils;

import java.util.List;

/**
 * @author Boliang Weng
 */
public class UserService {
    private UserDao userDao = new UserDaoImpl();
    private NodeDao nodeDao = new NodeDaoImpl();

    public User checkLogin(String username, String password){
        User user = userDao.selectUserByUsername(username);
        String md5Digest = null;
        if (user == null) {
            throw new BusinessException("Login001", "用户不存在");
        }
        if (password != null){
            md5Digest = Md5Utils.getMd5Digest(password, user.getUserId().intValue());
        }
        if (!user.getPassword().equals(md5Digest)){
            throw new BusinessException("Login002", "密码错误");
        }
        return user;
    }

    public List<Node> getNodeByUserId(Long userId){
        return nodeDao.selectNodeByUserId(userId);
    }

    public List<User> getAllUser(){
        return userDao.selectAllUser();
    }
}
