package com.services.impl;


import com.dao.ClearLogDao;
import com.services.ClearLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author NavyWang
 * @email wanghj@aratek.com.cn
 * @date 2017/8/25  9:24
 */
@Service("clearLogService")
public class ClearLogServiceImpl implements ClearLogService {

    @Autowired
    private ClearLogDao clearLogDao;

//    private int getLimitDays() {
////        String time = SysConfigUtils.readSetting("SYS", "LOG_HOLD_TIME");
//        int days = 180;
//        if (time != null) {
//            days = Integer.parseInt(time);
//        }
//        return days;
//    }

//    @Override
//    public int clearSystemTaskLog() {
//        int days = getLimitDays();
//        return clearLogDao.clearEntity(ScheduleJobLogEntity.class, days, "createTime");
//    }
//
//    @Override
//    public int clearSystemLog() {
//        int days = getLimitDays();
//        return clearLogDao.clearEntity(SysLogEntity.class, days, "createDate");
//    }
//
//    @Override
//    public int clearRuntimeLog() {
//        int days = getLimitDays();
//        return clearLogDao.clearEntity(DevRuntimeLogEntity.class, days, "cjsj");
//    }
//
//    @Override
//    public int clearDevHbsLog() {
//        int days = getLimitDays();
//        return clearLogDao.clearEntity(DevHbsEntity.class, days, "cjsj");
//    }
//
//    @Override
//    public int coutClearSystemTaskLog() {
//        int days = getLimitDays();
//        return clearLogDao.coutClearEntity(ScheduleJobLogEntity.class, days, "createTime");
//    }
//
//    @Override
//    public int coutClearSystemLog() {
//        int days = getLimitDays();
//        return clearLogDao.coutClearEntity(SysLogEntity.class, days, "createDate");
//    }
//
//    @Override
//    public int coutClearRuntimeLog() {
//        int days = getLimitDays();
//        return clearLogDao.coutClearEntity(DevRuntimeLogEntity.class, days, "cjsj");
//    }
//
//    @Override
//    public int coutClearDevHbsLog() {
//        int days = getLimitDays();
//        return clearLogDao.coutClearEntity(DevHbsEntity.class, days, "cjsj");
//    }

    @Override
    public int[] countLog() {
        String[] entityClassName =  new String[]{"ScheduleJobLogEntity","SysLogEntity","DevRuntimeLogEntity","DevHbsEntity"};
        return clearLogDao.coutEntity(entityClassName);
    }
}
