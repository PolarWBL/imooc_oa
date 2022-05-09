package com.ctgu.oa.dao;

import com.ctgu.oa.entity.Employee;
import com.ctgu.oa.utils.MybatisUtils;

/**
 * @author Boliang Weng
 */
public class EmployeeDaoImpl implements EmployeeDao{

    @Override
    public Employee selectEmployeeById(Long employeeId) {
        return (Employee) MybatisUtils.executeQuery(sqlSession -> {
            EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
            return mapper.selectEmployeeById(employeeId);
        });
    }

    @Override
    public Employee selectLeaderByEmployee(Employee employee) {
        return (Employee) MybatisUtils.executeQuery(sqlSession -> {
            EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
            return mapper.selectLeaderByEmployee(employee);
        });
    }
}
