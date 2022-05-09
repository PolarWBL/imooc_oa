package com.ctgu.oa.controller;

import com.alibaba.fastjson.JSON;
import com.ctgu.oa.entity.Notice;
import com.ctgu.oa.entity.User;
import com.ctgu.oa.service.NoticeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoticeServlet extends HttpServlet {
    private NoticeService noticeService = new NoticeService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        User user = (User) req.getSession().getAttribute("login_user");
        List<Notice> noticeList = noticeService.getNoticeList(user.getEmployeeId());
        Map<String, Object> map = new HashMap<>();
        map.put("code", "0");
        map.put("message", "");
        map.put("count", noticeList.size());
        map.put("data", noticeList);
        String json = JSON.toJSONString(map);
        resp.getWriter().println(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
