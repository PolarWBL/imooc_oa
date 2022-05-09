package com.ctgu.oa.service;

import com.ctgu.oa.entity.LeaveForm;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class LeaveFormServiceTest {
    private LeaveFormService leaveFormService = new LeaveFormService();

    /**
     * 市场部员工请假单(72小时以上)测试用例
     */
    @Test
    public void createLeaveForm1() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHH");
        LeaveForm leaveForm = new LeaveForm();
        leaveForm.setEmployeeId(8L);
        leaveForm.setFormType(1);
        leaveForm.setStartTime(dateFormat.parse("2022040508"));
        leaveForm.setEndTime(dateFormat.parse("2022041008"));
        leaveForm.setReason("市场部底层72小时以上");
        leaveForm.setCreateTime(new Date());

        LeaveForm form = leaveFormService.createLeaveForm(leaveForm);
        System.out.println(form);
    }

    /**
     * 市场部员工请假单(72小时以下)测试用例
     */
    @Test
    public void createLeaveForm2() throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHH");
        LeaveForm leaveForm = new LeaveForm();
        leaveForm.setEmployeeId(9L);
        leaveForm.setFormType(1);
        leaveForm.setStartTime(dateFormat.parse("2022040508"));
        leaveForm.setEndTime(dateFormat.parse("2022040608"));
        leaveForm.setReason("市场部底层72小时以下");
        leaveForm.setCreateTime(new Date());

        LeaveForm form = leaveFormService.createLeaveForm(leaveForm);
        System.out.println(form);
    }

    /**
     * 市场部经理请假单测试用例
     */
    @Test
    public void createLeaveForm3() throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHH");
        LeaveForm leaveForm = new LeaveForm();
        leaveForm.setEmployeeId(6L);
        leaveForm.setFormType(2);
        leaveForm.setStartTime(dateFormat.parse("2022040608"));
        leaveForm.setEndTime(dateFormat.parse("2022050608"));
        leaveForm.setReason("市场部经理请假");
        leaveForm.setCreateTime(new Date());

        LeaveForm form = leaveFormService.createLeaveForm(leaveForm);
        System.out.println(form);
    }

    /**
     * 总经理请假单测试用例
     */
    @Test
    public void createLeaveForm4() throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHH");
        LeaveForm leaveForm = new LeaveForm();
        leaveForm.setEmployeeId(1L);
        leaveForm.setFormType(3);
        leaveForm.setStartTime(dateFormat.parse("2022040608"));
        leaveForm.setEndTime(dateFormat.parse("2022050608"));
        leaveForm.setReason("总经理请假");
        leaveForm.setCreateTime(new Date());

        LeaveForm form = leaveFormService.createLeaveForm(leaveForm);
        System.out.println(form);
    }

    @Test
    public void selectLeafFormListTest(){
        List<Map<String, Object>> process = leaveFormService.getLeaveFormList("process", 2L);
        for (Map<String, Object> map : process) {
            System.out.println(map);
        }
    }


}