package com.ctgu.oa.dao;

import com.ctgu.oa.entity.ProcessFlow;
import com.ctgu.oa.utils.MybatisUtils;

import java.util.List;

/**
 * @author Boliang Weng
 */
public class ProcessFlowDaoImpl implements ProcessFlowDao{
    @Override
    public void insertProcessFlow(ProcessFlow processFlow) {
        MybatisUtils.executeUpdate(sqlSession -> {
            ProcessFlowDao mapper = sqlSession.getMapper(ProcessFlowDao.class);
            mapper.insertProcessFlow(processFlow);
            return null;
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ProcessFlow> selectProcessFlowByFormId(Long formId) {
        return (List<ProcessFlow>) MybatisUtils.executeQuery(sqlSession -> {
            ProcessFlowDao mapper = sqlSession.getMapper(ProcessFlowDao.class);
            return mapper.selectProcessFlowByFormId(formId);
        });
    }

    @Override
    public void updateProcessFlow(ProcessFlow processFlow) {
        MybatisUtils.executeUpdate(sqlSession -> {
            ProcessFlowDao mapper = sqlSession.getMapper(ProcessFlowDao.class);
            mapper.updateProcessFlow(processFlow);
            return null;
        });
    }
}
