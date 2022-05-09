package com.ctgu.oa.dao;

import com.ctgu.oa.entity.Employee;

/**
 * @author Boliang Weng
 */
public interface EmployeeDao {
    /**
     * 通过员工id查询员工信息
     * @param employeeId 员工id
     * @return 返回当前员工实体类
     */
    public Employee selectEmployeeById(Long employeeId);

    /**
     * 通过员工查询上级领导
     * @param employee 员工信息
     * @return 返回领导信息
     */
    public Employee selectLeaderByEmployee(Employee employee);
}
