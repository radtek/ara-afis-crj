package com.controller;

import com.services.BusLogService;
import com.time.TimeUtil;
import com.util.BusCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author NavyWang
 * @email wanghj@aratek.com.cn
 * @date 2017/8/23  11:01
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/busLog")
public class BusLogController extends BaseController {

    @Autowired
    private BusLogService busLogService;

    /**
     * ip请求次数统计
     * @return
     */
    @RequestMapping("/ipCount")
    public String ipCount(){
        return "/analysis/requestAls";
    }

    @ResponseBody
    @RequestMapping("/ipCountByDate")
    public Map ipCountByDate(String dateTimeRange){
        HashMap hashMap = new HashMap();
        if(!Optional.ofNullable(dateTimeRange).isPresent()) {
            dateTimeRange = TimeUtil.getTodayDateArea();
        }
        String[] dateTimeRangeSpilt = dateTimeRange.split(" - ");
        List<Object[]> ipArrList = busLogService.countIP(dateTimeRangeSpilt[0],dateTimeRangeSpilt[1]);
        List<Map> IpJsonList = new ArrayList<>();
        List countList = new ArrayList();
        List ipList = new ArrayList();
        ipArrList.forEach(x ->{
            ipList.add(x[0]);
            countList.add(x[1]);
            HashMap tempMap = new HashMap();
            tempMap.put("name", x[0]);
            tempMap.put("value", x[1]);
            IpJsonList.add(tempMap);
        });
        hashMap.put("xList", ipList);
        hashMap.put("yList", countList);
        hashMap.put("pieList", IpJsonList);
        return hashMap;
    }

    /**
     * 某个ip请求类型分析
     * @param ip
     * @return
     */
    @ResponseBody
    @RequestMapping("countActionTypeByIPAndDate")
    public Map countActionTypeByIPAndDate(String ip,String dateTimeRange){
        Map hashMap = new HashMap();
        List<Map> resultList = new ArrayList<>();
        List countList = new ArrayList();
        List actionTypeList = new ArrayList();
        if(!Optional.ofNullable(dateTimeRange).isPresent()) {
            hashMap.put("msg", "时间段格式不正确");
            return hashMap;
        }
        String[] dateTimeRangeSpilt = dateTimeRange.split(" - ");
        List<Object[]> countActionType = busLogService.countActionTypeByIP(ip,dateTimeRangeSpilt[0],dateTimeRangeSpilt[1]);
        countActionType.forEach(x -> {
            actionTypeList.add(BusCodeUtil.changeActionType(String.valueOf(x[0])));
            countList.add(x[1]);
            HashMap tempMap = new HashMap();
            tempMap.put("name", (BusCodeUtil.changeActionType(String.valueOf(x[0]))));
            tempMap.put("value", x[1]);
            resultList.add(tempMap);
        });
        hashMap.put("xList", actionTypeList);
        hashMap.put("yList", countList);
        hashMap.put("pieList", resultList);
        return hashMap;
    }
}
