package com.ctgu.oa.service;

import com.ctgu.oa.dao.LeaveFormDaoImpl;
import com.ctgu.oa.dao.NoticeDaoImpl;
import com.ctgu.oa.dao.ProcessFlowDaoImpl;
import com.ctgu.oa.entity.*;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceTest {
    private UserService userService = new UserService();
    private EmployeeService employeeService = new EmployeeService();
    private DepartmentService departmentService = new DepartmentService();

    @Test
    public void checkLogin1() {
        User user = userService.checkLogin("123", "123");
    }
    @Test
    public void checkLogin2() {
        userService.checkLogin("manager8","123");
    }
    @Test
    public void checkLogin3() {
        User manager8 = userService.checkLogin("manager8", "123456");
        System.out.println(manager8);
    }
    @Test
    public void getNodeByUserId(){
        List<Node> nodeList = userService.getNodeByUserId(2l);
        for (Node node : nodeList) {
            System.out.println(node);
        }
    }
    @Test
    public void getEmployee(){
        Employee employee = employeeService.getEmployeeById(1L);
        System.out.println(employee);
    }
    @Test
    public void getDepartment(){
        Department department = departmentService.getDepartmentById(1L);
        System.out.println(department);
    }
    @Test
    public void insertLeaveFormTest(){
        LeaveFormDaoImpl leaveFormDao = new LeaveFormDaoImpl();
        LeaveForm leaveForm = new LeaveForm();
        leaveForm.setCreateTime(new Date());
        leaveForm.setEmployeeId(4L);
        leaveForm.setFormType(1);//事假
        leaveForm.setReason("回家");
        leaveForm.setState("processing");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = null;
        Date endTime = null;
        try {
            startTime = simpleDateFormat.parse("2022-04-04 08:00:00");
            endTime = simpleDateFormat.parse("2022-04-07 08:00:00");
            leaveForm.setStartTime(startTime);
            leaveForm.setEndTime(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        leaveFormDao.insertLeaveForm(leaveForm);
    }
    @Test
    public void insertProcessFlowTest(){
        ProcessFlowDaoImpl processFlowDao = new ProcessFlowDaoImpl();
        ProcessFlow processFlow = new ProcessFlow();
        processFlow.setFormId(3L);
        processFlow.setOperatorId(2L);
        processFlow.setAction("audit");
        processFlow.setResult("approved");
        processFlow.setReason("同意");
        processFlow.setCreateTime(new Date());
        processFlow.setAuditTime(new Date());
        processFlow.setOrderNo(1);
        processFlow.setState("ready");
        processFlow.setIsLast(1);
        processFlowDao.insertProcessFlow(processFlow);
    }
    @Test
    public void insertNoticeTest(){
        NoticeDaoImpl noticeDao = new NoticeDaoImpl();

        Notice notice = new Notice();
        notice.setReceiverId(3L);
        notice.setCreateTime(new Date());
        notice.setContent("测试内容");

        noticeDao.insertNotice(notice);
    }

    @Test
    public void getLeaderTest(){
        List<Employee> employees = new ArrayList<>();
        Employee employee = null;
        employee = employeeService.getEmployeeById(1L);
        employee = employeeService.getLeader(employee);
        employees.add(employee);
        employee = employeeService.getEmployeeById(2L);
        employee = employeeService.getLeader(employee);
        employees.add(employee);
        employee = employeeService.getEmployeeById(3L);
        employee = employeeService.getLeader(employee);
        employees.add(employee);
        employee = employeeService.getEmployeeById(6L);
        employee = employeeService.getLeader(employee);
        employees.add(employee);
        employee = employeeService.getEmployeeById(7L);
        employee = employeeService.getLeader(employee);
        employees.add(employee);

        for (Employee employee1 : employees) {
            System.out.println(employee1);
        }
    }



}
