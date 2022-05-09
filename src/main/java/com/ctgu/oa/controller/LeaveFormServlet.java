package com.ctgu.oa.controller;

import com.alibaba.fastjson.JSON;
import com.ctgu.oa.entity.LeaveForm;
import com.ctgu.oa.entity.User;
import com.ctgu.oa.service.LeaveFormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Boliang Weng
 */
public class LeaveFormServlet extends HttpServlet {
    private LeaveFormService leaveFormService = new LeaveFormService();
    Logger logger = LoggerFactory.getLogger(LeaveFormServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String uri = req.getRequestURI();
        String method = uri.substring(uri.lastIndexOf("/")+1);
        switch (method) {
            case "create":
                create(req, resp);
                break;
            case "list":
                list(req, resp);
                break;
            case "audit":
                audit(req, resp);
                break;
            default:
                break;
        }

    }

    private void audit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String formId = req.getParameter("formId");
        String result = req.getParameter("result");
        String reason = req.getParameter("reason");
        User user = (User) req.getSession().getAttribute("login_user");
        Map<String, Object> map = new HashMap<>();
        try {
            leaveFormService.auditLeaveForm(Long.parseLong(formId),user.getEmployeeId(),result,reason);
            map.put("code", "0");
            map.put("message", "success");
        } catch (NumberFormatException e) {
            map.put("code", e.getClass().getSimpleName());
            map.put("message", e.getMessage());
            e.printStackTrace();
        }
        String json = JSON.toJSONString(map);
        resp.getWriter().println(json);
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //定义simpledateformat
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH");
        //1. 获取前端数据
        User user = (User) req.getSession().getAttribute("login_user");
        String formType = req.getParameter("formType");
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");
        String reason = req.getParameter("reason");
        //去除前端传来数据中的空白符
        reason = reason.replaceAll("\\s*", "");
        //填写表单属性
        Map<String, Object> map = new HashMap<>();
        try {
            LeaveForm leaveForm = new LeaveForm();
            leaveForm.setEmployeeId(user.getEmployeeId());
            leaveForm.setFormType(Integer.parseInt(formType));
            leaveForm.setStartTime(dateFormat.parse(startTime));
            leaveForm.setEndTime(dateFormat.parse(endTime));
            leaveForm.setReason(reason);
            leaveForm.setCreateTime(new Date());
            //提交生成表单
            leaveFormService.createLeaveForm(leaveForm);
            map.put("code", "0");
            map.put("message", "success");
        } catch (ParseException e) {
            logger.error("请假申请失败", e);
            map.put("code", e.getClass().getSimpleName());
            map.put("message", e.getMessage());
        }
        //返回前台数据
        String json = JSON.toJSONString(map);
        resp.getWriter().println(json);
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("login_user");
        List<Map<String, Object>> formList = leaveFormService.getLeaveFormList("process", user.getEmployeeId());
        Map<String, Object> map = new HashMap<>();
        map.put("code", "0");
        map.put("message", "");
        map.put("count", formList.size());
        map.put("data", formList);
        String json = JSON.toJSONString(map);
        resp.getWriter().println(json);
    }
}
