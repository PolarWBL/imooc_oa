package com.ctgu.oa.dao;

import com.ctgu.oa.entity.Department;

/**
 * @author Boliang Weng
 */
public interface DepartmentDao {
    /**
     * 通过部门id查询部门信息
     * @param departmentId 部门id
     * @return 返回部门信息
     */
    public Department selectDepartmentById(Long departmentId);
}
