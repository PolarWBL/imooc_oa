package com.ctgu.oa.utils;

import com.ctgu.oa.entity.User;
import com.ctgu.oa.service.UserService;
import org.junit.Test;

import java.util.List;

public class Md5UtilsTest {
    private UserService userService = new UserService();
    @Test
    public void getMd5Hex(){
        List<User> users = userService.getAllUser();
        for (User user : users) {
            String password = user.getPassword();
            int salt = user.getUserId().intValue();
            String md5Digest = Md5Utils.getMd5Digest(password, salt);
            System.out.println(md5Digest);
        }
    }
}