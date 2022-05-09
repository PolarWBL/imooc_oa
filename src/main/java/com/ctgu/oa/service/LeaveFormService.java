package com.ctgu.oa.service;

import com.ctgu.oa.dao.EmployeeDao;
import com.ctgu.oa.dao.LeaveFormDao;
import com.ctgu.oa.dao.LeaveFormDaoImpl;
import com.ctgu.oa.dao.ProcessFlowDao;
import com.ctgu.oa.entity.Employee;
import com.ctgu.oa.entity.LeaveForm;
import com.ctgu.oa.entity.ProcessFlow;
import com.ctgu.oa.utils.MybatisUtils;
import java.util.List;
import java.util.Map;

/**
 * @author Boliang Weng
 */
public class LeaveFormService {
    private LeaveFormDao leaveFormDao = new LeaveFormDaoImpl();
    private ProcessFlowService processFlowService = new ProcessFlowService();
    private NoticeService noticeService = new NoticeService();

    public LeaveForm createLeaveForm(LeaveForm leaveForm){
        return (LeaveForm) MybatisUtils.executeUpdate(sqlSession -> {
            ProcessFlowDao processFlowDao = sqlSession.getMapper(ProcessFlowDao.class);
            LeaveFormDao leaveFormDao = sqlSession.getMapper(LeaveFormDao.class);
            EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
            Employee employee = employeeDao.selectEmployeeById(leaveForm.getEmployeeId());

            //设置表单数据并持久化
            if (employee.getLevel() == 8) {
                leaveForm.setState("approved");
            }else {
                leaveForm.setState("processing");
            }
            leaveFormDao.insertLeaveForm(leaveForm);

            List<ProcessFlow> flows = processFlowService.getAllFlows(leaveForm, employee);
            for (ProcessFlow flow : flows) {
                processFlowDao.insertProcessFlow(flow);
            }
            return leaveForm;
        });
    }

    public List<Map<String, Object>> getLeaveFormList(String state, Long operatorId){
        return leaveFormDao.selectLeaveFormList(state, operatorId);
    }

    public void auditLeaveForm(Long formId, Long operatorId, String result, String reason){
        MybatisUtils.executeUpdate(sqlSession -> {
            ProcessFlowDao processFlowDao = sqlSession.getMapper(ProcessFlowDao.class);
            LeaveFormDao leaveFormDao = sqlSession.getMapper(LeaveFormDao.class);

            ProcessFlow processFlow = processFlowService.updateToComplete(formId, operatorId, result, reason);
            processFlowDao.updateProcessFlow(processFlow);

            LeaveForm leaveForm = leaveFormDao.selectLeaveFormById(formId);

            //通知员工当前审批完成
            noticeService.applyComplete(leaveForm, operatorId, result, reason);
            //通知经办人当前审批结果
            noticeService.approvalComplete(leaveForm, operatorId, result, reason);

            if (processFlow.getIsLast() == 1){
                leaveForm.setState(result);
                leaveFormDao.updateLeaveForm(leaveForm);
            }else {
                List<ProcessFlow> flowList = processFlowService.updateReadyFlow(formId, result);
                for (ProcessFlow flow : flowList) {
                    processFlowDao.updateProcessFlow(flow);
                }
                if ("refused".equals(result)){
                    leaveForm.setState(result);
                    leaveFormDao.updateLeaveForm(leaveForm);
                }else {
                    //通知总经理审批
                    noticeService.approvalPending(leaveForm, operatorId);
                }
            }
            return null;
        });
    }
}
