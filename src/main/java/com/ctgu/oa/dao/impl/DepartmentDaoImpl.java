package com.ctgu.oa.dao.impl;

import com.ctgu.oa.dao.DepartmentDao;
import com.ctgu.oa.entity.Department;
import com.ctgu.oa.utils.MybatisUtils;

/**
 * @author Boliang Weng
 */
public class DepartmentDaoImpl implements DepartmentDao {
    @Override
    public Department selectDepartmentById(Long departmentId) {
        return (Department) MybatisUtils.executeQuery(sqlSession -> {
            DepartmentDao mapper = sqlSession.getMapper(DepartmentDao.class);
            return mapper.selectDepartmentById(departmentId);
        });
    }
}
