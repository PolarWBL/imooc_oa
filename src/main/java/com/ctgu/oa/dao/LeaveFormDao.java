package com.ctgu.oa.dao;

import com.ctgu.oa.entity.LeaveForm;

import java.util.List;
import java.util.Map;

/**
 * @author Boliang Weng
 */
public interface LeaveFormDao {
    /**
     * 插入请假单
     * @param leaveForm 请假单本单
     */
    public void insertLeaveForm(LeaveForm leaveForm);

    /**
     * 查询名下需要审批的请假单
     * @param state "process"状态的请假单
     * @param operatorId 自己的员工id
     * @return 返回查询结果
     */
    public List<Map<String, Object>> selectLeaveFormList(String state, Long operatorId);

    /**
     * 通过formId查询对应的请假单
     * @param formId 请假单id
     * @return 返回请假单
     */
    public LeaveForm selectLeaveFormById(Long formId);

    /**
     * 更新请假单
     * @param leaveForm 要更新的请假单信息
     */
    public void updateLeaveForm(LeaveForm leaveForm);
}
