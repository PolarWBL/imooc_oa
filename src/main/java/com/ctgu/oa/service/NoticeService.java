package com.ctgu.oa.service;

import com.ctgu.oa.dao.EmployeeDao;
import com.ctgu.oa.dao.impl.EmployeeDaoImpl;
import com.ctgu.oa.dao.NoticeDao;
import com.ctgu.oa.dao.impl.NoticeDaoImpl;
import com.ctgu.oa.entity.Employee;
import com.ctgu.oa.entity.LeaveForm;
import com.ctgu.oa.entity.Notice;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Boliang Weng
 */
public class NoticeService {
    private NoticeDao noticeDao = new NoticeDaoImpl();
    private EmployeeDao employeeDao = new EmployeeDaoImpl();
    private String content = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH时");


    public List<Notice> getNoticeList(Long receiverId){
        return noticeDao.selectNoticeById(receiverId);
    }

    public void applyPending(LeaveForm leaveForm, Employee employee){
        content = String.format("您的请假申请[%s-%s]已提交, 请等待审批",
                sdf.format(leaveForm.getStartTime()),
                sdf.format(leaveForm.getEndTime())
        );
        noticeDao.insertNotice(new Notice(employee.getEmployeeId(),content));
    }

    public void applyComplete(LeaveForm leaveForm, Long operatorId, String result, String reason){
        Employee leader = employeeDao.selectEmployeeById(operatorId);
        Employee employee = employeeDao.selectEmployeeById(leaveForm.getEmployeeId());
        String resultInfo = null;
        if ("approved".equals(result)){
            resultInfo = "同意";
        }else {
            resultInfo = "拒绝";
        }
        content = String.format("您的请假申请[%s-%s]已被%s%s, 原因:%s",
                sdf.format(leaveForm.getStartTime()), sdf.format(leaveForm.getEndTime()),
                leader.getTitle(), resultInfo, reason
        );
        noticeDao.insertNotice(new Notice(employee.getEmployeeId(),content));
    }

    public void approvalPending(LeaveForm leaveForm, Employee employee, Employee leader){
        content = String.format("%s-%s提起请假申请[%s-%s], 请尽快审批",
                employee.getTitle(), employee.getName(),
                sdf.format(leaveForm.getStartTime()), sdf.format(leaveForm.getEndTime())
        );
        noticeDao.insertNotice(new Notice(leader.getEmployeeId(),content));
    }

    public void approvalPending(LeaveForm leaveForm, Long operatorId){
        Employee employee = employeeDao.selectEmployeeById(leaveForm.getEmployeeId());
        Employee operator = employeeDao.selectEmployeeById(operatorId);
        Employee leader = employeeDao.selectLeaderByEmployee(operator);

        content = String.format("%s-%s提起请假申请[%s-%s], 请尽快审批",
                employee.getTitle(), employee.getName(),
                sdf.format(leaveForm.getStartTime()), sdf.format(leaveForm.getEndTime())
        );
        noticeDao.insertNotice(new Notice(leader.getEmployeeId(),content));
    }

    public void approvalComplete(LeaveForm leaveForm, Long operatorId, String result, String reason){
        Employee employee = employeeDao.selectEmployeeById(leaveForm.getEmployeeId());
        String resultInfo = null;
        if ("approved".equals(result)){
            resultInfo = "同意";
        }else {
            resultInfo = "拒绝";
        }
        content = String.format("%s-%s的请假申请[%s-%s]已被您%s, 原因:%s",
                employee.getTitle(), employee.getName(),
                sdf.format(leaveForm.getStartTime()), sdf.format(leaveForm.getEndTime()),
                resultInfo, reason
        );
        noticeDao.insertNotice(new Notice(operatorId,content));
    }

    public void specialApply(LeaveForm leaveForm, Employee employee){
        content = String.format("由于您的请假申请[%s-%s]时间过长, 请等待部门经理和总经理审批",
                sdf.format(leaveForm.getStartTime()),
                sdf.format(leaveForm.getEndTime())
        );
        noticeDao.insertNotice(new Notice(employee.getEmployeeId(),content));
    }

    public void autoPassed(LeaveForm leaveForm, Employee employee){
        content = String.format("您的请假申请[%s-%s]已通过",
                sdf.format(leaveForm.getStartTime()), sdf.format(leaveForm.getEndTime())
        );
        noticeDao.insertNotice(new Notice(employee.getEmployeeId(),content));
    }



}
