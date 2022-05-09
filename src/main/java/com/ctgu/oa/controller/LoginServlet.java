package com.ctgu.oa.controller;

import com.alibaba.fastjson.JSON;
import com.ctgu.oa.entity.User;
import com.ctgu.oa.service.UserService;
import com.ctgu.oa.service.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Boliang Weng
 */
public class LoginServlet extends HttpServlet {
    Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    private UserService userService = new UserService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Map<String, Object> map = new HashMap<>();
        HttpSession session = req.getSession();
        try {
            User user = userService.checkLogin(username, password);
            session.setAttribute("login_user", user);
            map.put("code", "0");
            map.put("message","success");
            map.put("redirect_url", "/index");
        }catch (BusinessException e){
            logger.error(e.getMsg(),e);
            map.put("code", e.getCode());
            map.put("message",e.getMsg());
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
            map.put("code", e.getClass().getSimpleName());
            map.put("message",e.getMessage());
        }
        String jsonString = JSON.toJSONString(map);
        resp.getWriter().println(jsonString);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
