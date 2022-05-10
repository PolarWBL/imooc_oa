package com.ctgu.oa.service;

import com.ctgu.oa.dao.DepartmentDao;
import com.ctgu.oa.dao.impl.DepartmentDaoImpl;
import com.ctgu.oa.entity.Department;

/**
 * @author Boliang Weng
 */
public class DepartmentService {
    private DepartmentDao departmentDao = new DepartmentDaoImpl();

    public Department getDepartmentById(Long departmentId){
        return departmentDao.selectDepartmentById(departmentId);
    }
}
