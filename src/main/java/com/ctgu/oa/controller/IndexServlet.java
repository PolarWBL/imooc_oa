package com.ctgu.oa.controller;

import com.ctgu.oa.entity.Department;
import com.ctgu.oa.entity.Employee;
import com.ctgu.oa.entity.Node;
import com.ctgu.oa.entity.User;
import com.ctgu.oa.service.DepartmentService;
import com.ctgu.oa.service.EmployeeService;
import com.ctgu.oa.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author Boliang Weng
 */
public class IndexServlet extends HttpServlet {
    private UserService userService = new UserService();
    private EmployeeService employeeService = new EmployeeService();
    private DepartmentService departmentService = new DepartmentService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("login_user");

        List<Node> nodeList = userService.getNodeByUserId(user.getUserId());
        Employee employee = employeeService.getEmployeeById(user.getEmployeeId());
        Department department = departmentService.getDepartmentById(employee.getDepartmentId());

        req.setAttribute("node_list", nodeList);
        session.setAttribute("current_employee", employee);
        session.setAttribute("current_department", department);

        req.getRequestDispatcher("/index.ftl").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
