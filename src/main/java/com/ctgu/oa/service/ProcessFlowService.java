package com.ctgu.oa.service;

import com.ctgu.oa.dao.ProcessFlowDao;
import com.ctgu.oa.dao.impl.ProcessFlowDaoImpl;
import com.ctgu.oa.entity.Employee;
import com.ctgu.oa.entity.LeaveForm;
import com.ctgu.oa.entity.ProcessFlow;
import com.ctgu.oa.utils.SystemConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Boliang Weng
 */
public class ProcessFlowService {
    private EmployeeService employeeService = new EmployeeService();
    private ProcessFlowDao processFlowDao = new ProcessFlowDaoImpl();
    public NoticeService noticeService = new NoticeService();

    /**
     * 将processFlow的ready状态修改为其他状态
     * @param formId 请假单id
     * @param result 处理原因
     * @return 返回该任务流程
     */
    public List<ProcessFlow> updateReadyFlow(Long formId, String result){
        List<ProcessFlow> flowList = new ArrayList<>();
        List<ProcessFlow> allFlows = processFlowDao.selectProcessFlowByFormId(formId);
        List<ProcessFlow> readyFlows = allFlows.stream().filter(processFlow -> "ready".equals(processFlow.getState())).collect(Collectors.toList());
        if ("approved".equals(result)){
            ProcessFlow processFlow = readyFlows.get(0);
            processFlow.setState("process");
            flowList.add(processFlow);
        }else{// if ("refused".equals(result))
            for (ProcessFlow readyFlow : readyFlows) {
                readyFlow.setState("cancel");
                flowList.add(readyFlow);
            }
        }
        return flowList;
    }

    /**
     * 将processFlow的状态修改为完成
     * @param formId 请假单id
     * @param operatorId 经办人id
     * @param result 处理结果
     * @param reason 处理原因
     * @return 返回该任务流程
     */
    public ProcessFlow updateToComplete(Long formId, Long operatorId, String result, String reason){
        List<ProcessFlow> allFlows = processFlowDao.selectProcessFlowByFormId(formId);
        List<ProcessFlow> processFlows = allFlows.stream().filter(processFlow -> processFlow.getOperatorId().equals(operatorId) && "process".equals(processFlow.getState())).collect(Collectors.toList());
        ProcessFlow processFlow = processFlows.get(0);
        processFlow.setState("complete");
        processFlow.setResult(result);
        processFlow.setReason(reason);
        processFlow.setAuditTime(new Date());
        return processFlow;
    }

    /**
     * 获取所有请假的流程表
     * @param leaveForm 请假申请表
     * @param employee 请假申请人
     * @return 返回流程列表
     */
    public List<ProcessFlow> getAllFlows(LeaveForm leaveForm, Employee employee){
        List<ProcessFlow> flows = new ArrayList<>();
        ProcessFlow flow1 = getProcessFlow1(leaveForm, employee);
        flows.add(flow1);
        List<ProcessFlow> flow2 = getProcessFlow2(leaveForm, employee);
        flows.addAll(flow2);
        return flows;
    }

    /**
     * 获取请假单的第一个请假流程, 创建请假申请流程
     * @param leaveForm 要创建流程的请假单
     * @param employee 申请请假的员工
     * @return 返回第一个请假单
     */
    private ProcessFlow getProcessFlow1(LeaveForm leaveForm, Employee employee) {
        ProcessFlow flow1 = new ProcessFlow();
        flow1.setFormId(leaveForm.getFormId());
        flow1.setOperatorId(employee.getEmployeeId());
        flow1.setAction("apply");
        flow1.setResult("approved");
        flow1.setReason(leaveForm.getReason());
        flow1.setCreateTime(new Date());
        flow1.setOrderNo(1);
        flow1.setState("complete");
        flow1.setAuditTime(new Date());
        flow1.setIsLast(0);
        return flow1;
    }

    /**
     * 获取请假单的多级请假流程
     * @param leaveForm 要创建流程的请假单
     * @param employee 申请请假的员工
     * @return 返回流程列表
     */
    private List<ProcessFlow> getProcessFlow2(LeaveForm leaveForm, Employee employee) {
        List<ProcessFlow> flows = new ArrayList<>();
        Employee leader = employeeService.getLeader(employee);

        ProcessFlow flow2 = new ProcessFlow();
        flow2.setFormId(leaveForm.getFormId());
        flow2.setOperatorId(leader.getEmployeeId());
        flow2.setAction("audit");
        flow2.setCreateTime(new Date());
        flow2.setOrderNo(2);
        flow2.setState("process");

        if (employee.getLevel() < 7) {
            if (isRegular(leaveForm)) {
                flow2.setIsLast(1);
                flows.add(flow2);
            } else {
                flow2.setIsLast(0);
                flows.add(flow2);
                ProcessFlow flow3 = getProcessFlow3(leaveForm, leader);
                flows.add(flow3);
                noticeService.specialApply(leaveForm, employee);
            }
            //创建待审批流程已提交通知
            noticeService.applyPending(leaveForm, employee);
            noticeService.approvalPending(leaveForm,employee,leader);
            return flows;
        } else if (employee.getLevel() == 7) {
            flow2.setIsLast(1);
            flows.add(flow2);
            //创建待审批流程已提交通知
            noticeService.applyPending(leaveForm, employee);
            noticeService.approvalPending(leaveForm,employee,leader);
            return flows;
        } else {
            flow2.setState("complete");
            flow2.setResult("approved");
            flow2.setReason("通过");
            flow2.setAuditTime(new Date());
            flow2.setIsLast(1);
            flows.add(flow2);
            //自动通过审批
            noticeService.autoPassed(leaveForm,employee);
            return flows;
        }
    }

    /**
     * 获取请假单的第三级请假流程
     * @param leaveForm 要创建流程的请假单
     * @param employee 申请请假员工的上司
     * @return 返回流程单
     */
    private ProcessFlow getProcessFlow3(LeaveForm leaveForm, Employee employee) {
        Employee leader = employeeService.getLeader(employee);
        ProcessFlow flow3 = new ProcessFlow();
        flow3.setFormId(leaveForm.getFormId());
        flow3.setOperatorId(leader.getEmployeeId());
        flow3.setAction("audit");
        flow3.setCreateTime(new Date());
        flow3.setOrderNo(3);
        flow3.setState("ready");
        flow3.setIsLast(1);
        return flow3;
    }

    /**
     * 判断是否需要再向上级申请(请假时间是否大于规定时间)
     * @param leaveForm 申请单
     * @return true表示申请时间在正常范围内
     */
    private Boolean isRegular(LeaveForm leaveForm) {
        long endTime = leaveForm.getEndTime().getTime();
        long startTime = leaveForm.getStartTime().getTime();
        float diff = (endTime - startTime) / (1000 * 60 * 60 * 1f);
        return diff < SystemConstants.REGULAR_LEAVE_TIME;
    }

}