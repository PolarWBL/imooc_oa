package com.ctgu.oa.service;

import com.ctgu.oa.dao.EmployeeDao;
import com.ctgu.oa.dao.EmployeeDaoImpl;
import com.ctgu.oa.entity.Employee;

/**
 * @author Boliang Weng
 */
public class EmployeeService {
    private EmployeeDao employeeDao = new EmployeeDaoImpl();

    public Employee getEmployeeById(Long employeeId){
        return employeeDao.selectEmployeeById(employeeId);
    }

    public Employee getLeader(Employee employee){
        return employeeDao.selectLeaderByEmployee(employee);
    }
}
