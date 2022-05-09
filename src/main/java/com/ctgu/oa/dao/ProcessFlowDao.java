package com.ctgu.oa.dao;

import com.ctgu.oa.entity.ProcessFlow;

import java.util.List;

/**
 * @author Boliang Weng
 */
public interface ProcessFlowDao {
    /**
     * 插入任务流程
     * @param processFlow 任务流程
     */
    public void insertProcessFlow(ProcessFlow processFlow);

    /**
     * 通过请假单查询对应的所有流程
     * @param formId 请假单id
     * @return 返回所有流程表
     */
    public List<ProcessFlow> selectProcessFlowByFormId(Long formId);

    /**
     * 更新流程信息
     * @param processFlow 要更新的流程
     */
    public void updateProcessFlow(ProcessFlow processFlow);
}
