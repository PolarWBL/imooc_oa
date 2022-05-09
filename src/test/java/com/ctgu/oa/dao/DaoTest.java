package com.ctgu.oa.dao;

import com.ctgu.oa.entity.LeaveForm;
import com.ctgu.oa.entity.ProcessFlow;
import org.junit.Test;

import java.util.List;

public class DaoTest {
    private ProcessFlowDao processFlowDao = new ProcessFlowDaoImpl();
    private LeaveFormDao leaveFormDao = new LeaveFormDaoImpl();

    @Test
    public void selectProcessFlowByFormId(){
        List<ProcessFlow> processFlows = processFlowDao.selectProcessFlowByFormId(18L);
        for (ProcessFlow processFlow : processFlows) {
            System.out.println(processFlow);
        }
    }
    @Test
    public void selectLeaveFormById(){
        LeaveForm leaveForm = leaveFormDao.selectLeaveFormById(18L);
        System.out.println(leaveForm);
    }
}
