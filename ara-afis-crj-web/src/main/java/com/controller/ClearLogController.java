package com.controller;


import com.services.ClearLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yvan Jiang
 * @version 0.0.1
 * @Title ClearLogController
 * @note 清理日志记录
 * @note Copyright 2016 by Aratek . All rights reserved
 * @time 2017/6/14
 **/
@Controller
@RequestMapping("clearlog")
public class ClearLogController {

    @Autowired
    private ClearLogService clearLogService;

    @RequestMapping("toClearlogPage")
    private String toClearlogPage(){
        return "log/logClear";
    }





    /**
     * 获取首页各个日志数量
     */
    @ResponseBody
    @RequestMapping("/cout")
    public Map coutLog() {
        Map<String,Object> map = new HashMap<>();
        map.put("count_log", clearLogService.countLog());

//        int[] clear_log = new int[]{clearLogService.coutClearSystemTaskLog(),
//                clearLogService.coutClearSystemLog(),
//                clearLogService.coutClearRuntimeLog(),
//                clearLogService.coutClearDevHbsLog()};
//        map.put("clear_log", clear_log);
//        return R.ok().put("logs",map);
        return null;
    }
//
//    /**
//     * 清理设备心跳日志
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping("/hbs")
//    @RequiresPermissions("devhbs:delete")
//    public R clearDevHbsLog(){
//        long count = clearLogService.clearDevHbsLog();
//        return R.ok("已清理"+count+"条数据");
//    }
//
//    /**
//     * 清理设备运行时日志
//     */
//    @ResponseBody
//    @RequestMapping("/runtimelog")
//    @RequiresPermissions("devruntimelog:delete")
//    public R clearRuntimeLog(){
//        long count = clearLogService.clearRuntimeLog();
//        return R.ok("已清理"+count+"条数据");
//    }
//
//    /**
//     * 清理系统日志
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping("/syslog")
//    @RequiresPermissions("sys:log:list")
//    public R clearSystemLog(){
//        long count = clearLogService.clearSystemLog();
//        return R.ok("已清理"+count+"条数据");
//    }
//
//    /**
//     * 清理定时任务日志
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping("/schedulelog")
//    @RequiresPermissions("sys:schedule:delete")
//    public R clearSystemTaskLog(){
//        long count = clearLogService.clearSystemTaskLog();
//        return R.ok("已清理"+count+"条数据");
//    }
}
