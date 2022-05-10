package com.ctgu.oa.dao.impl;

import com.ctgu.oa.dao.LeaveFormDao;
import com.ctgu.oa.entity.LeaveForm;
import com.ctgu.oa.utils.MybatisUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Boliang Weng
 */
public class LeaveFormDaoImpl implements LeaveFormDao {
    @Override
    public void insertLeaveForm(LeaveForm leaveForm) {
        MybatisUtils.executeUpdate(sqlSession -> {
            LeaveFormDao mapper = sqlSession.getMapper(LeaveFormDao.class);
            mapper.insertLeaveForm(leaveForm);
            return null;
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> selectLeaveFormList(String state, Long operatorId) {
        return (List<Map<String, Object>>) MybatisUtils.executeQuery(sqlSession -> {
            LeaveFormDao mapper = sqlSession.getMapper(LeaveFormDao.class);
            return mapper.selectLeaveFormList(state, operatorId);
        });
    }

    @Override
    public LeaveForm selectLeaveFormById(Long formId) {
        return (LeaveForm) MybatisUtils.executeQuery(sqlSession -> {
            LeaveFormDao mapper = sqlSession.getMapper(LeaveFormDao.class);
            return mapper.selectLeaveFormById(formId);
        });
    }

    @Override
    public void updateLeaveForm(LeaveForm leaveForm) {
        MybatisUtils.executeUpdate(sqlSession -> {
            LeaveFormDao mapper = sqlSession.getMapper(LeaveFormDao.class);
            mapper.updateLeaveForm(leaveForm);
            return null;
        });
    }

}
