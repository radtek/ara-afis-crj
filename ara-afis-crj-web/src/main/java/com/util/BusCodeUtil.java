package com.util;

import com.model.Page;

import java.util.List;
import java.util.Map;

/**
 * 文件名：
 * 作者：tree
 * 时间：2017/5/16
 * 描述：
 * 版权：亚略特
 */
public class BusCodeUtil {

    /**
     * @author 作者：tree
     * @Date 时间：2016/10/25 18:21
     * 功能说明：根据有效页面，对权限数据进行再次筛选
     * 处理流程：
     * @param
     * @return
     */
    public static String filterString(List<Page> pageList, String purview){
        String result = "";
        String[] arrayTemp = purview.split(",");

        for(Page pageTemp : pageList){
            for(String str : arrayTemp){
                if(str.equals(pageTemp.getCode())){
                    result += "," + str;
                    break;
                }
            }
        }
        if(result.length()>0){
            result = result.substring(1, result.length());
        }
        return result;
    }

    /**
     * 构造业务类型分布趋势图数据
     * @param map 原始图表数据
     * @param busCodes 指定的业务码列表
     * @return 转换后的图表数据
     */
    public static String changeBusCodeData(Map<Integer,Long> map,String[] busCodes){
        String result = "";
        for(String busCode : busCodes){
            long value = 0;
            if (map.containsKey(Integer.parseInt(busCode))) {
                value = map.get(Integer.parseInt(busCode));
            }
            result += ",{value:"+value+",name:\'"+changeActionType(busCode)+"\'}";
        }
        if(result.length()>0){
            result = result.substring(1, result.length());
        }
        return result;
    }

    /**
     * 功能说明：构造业务类型分布趋势图数据
     * @param actionCode 类型数组
     * @return
     */
    public static String changeActionType(String actionCode){
        String actionName;
        switch(actionCode){
            case "102201":
                actionName = "参数设置";
                break;
            case "102202":
                actionName = "心跳监听";
                break;
            case "102203":
                actionName = "运行状态获取";
                break;
            case "102204":
                actionName = "任务状态获取";
                break;
            case "102205":
                actionName = "监控信息获取";
                break;
            case "102206":
                actionName = "数据增量同步";
                break;
            case "201301":
                actionName = "1对1比对";
                break;
            case "201302":
                actionName = "1对1认证";
                break;
            case "201303":
                actionName = "图片数据比对";
                break;
            case "201311":
                actionName = "1对多比对";
                break;
            case "201321":
                actionName = "X对多比对";
                break;
            case "201322":
                actionName = "X指查重";
                break;
            case "201201":
                actionName = "图片质量评价";
                break;
            case "201202":
                actionName = "图片建模";
                break;
            case "201203":
                actionName = "图片格式转换";
                break;
            case "201204":
                actionName = "图片批量建模";
                break;
            case "201111":
                actionName = "模板库导出";
                break;
            case "201221":
                actionName = "模板管理";
                break;
            case "201401":
                actionName = "库别管理";
                break;
            case "201402":
                actionName = "库别查询";
                break;
            default:
                actionName = "未知指纹业务";
                break;
        }
        return actionName;
    }
}
