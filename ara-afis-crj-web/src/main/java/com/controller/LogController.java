package com.controller;

import com.alibaba.fastjson.JSON;
import com.excel.ExcelUtil;
import com.param.ConfigParam;
import com.services.BusLogService;
import com.services.DataSynLogService;
import com.services.SysLogService;
import com.time.TimeUtil;
import com.util.CommonStringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 文件名：CodeController
 * 作者：tree
 * 时间：2016/4/21
 * 描述：数据字典 相关业务逻辑类
 * 版权：亚略特
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/logInfo")
public class LogController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(LogController.class);

    //注入服务层
    @Autowired
    private SysLogService sysLogService;
    @Autowired
    private BusLogService busLogService;
    @Autowired
    private DataSynLogService dataSynLogService;

    /**
     * @author 作者：tree
     * @Date 时间：2016/7/15 18:04
     * 功能说明：公共查询模块的对应方法,获取所有系统日志
     * 处理流程：
     * @param
     * @return
     */
    @RequestMapping(value = "/toQuerySysLog")
    public String toQuerySysLog(){
        logger.debug("查询系统日志表");
        //跳转前台
        return "/log/querySysLog";
    }

    /**
     * @author 作者：tree
     * @Date 时间：2016/7/15 18:04
     * 功能说明：公共查询模块的对应方法，获取所有业务日志
     * 处理流程：
     * @param
     * @return
     */
    @RequestMapping(value = "/querySysLog", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> querySysLog(String aoData, String filterItem) {
        initController();
        Map<String,Object> paramMap = resolveControllerParam(aoData,page);
        if(StringUtils.isNotBlank((String)paramMap.get("searchValue"))){
            responseMap.put("data", sysLogService.getObjListPage(page, ConfigParam.QUERY_TYPE_ALL, CommonStringUtil.parseShowString(paramMap.get("searchValue")),filterItem));
        }else{
            responseMap.put("data", sysLogService.getObjListPage(page, ConfigParam.QUERY_TYPE_SOME));
        }
        responseMap.put("anyStatus", 1);
        responseMap.put("recordsFiltered", page.getTotalResult());
        responseMap.put("recordsTotal", page.getTotalResult());
        responseMap.put("draw", (int)(paramMap.get("draw")));
        return responseMap;
    }

    /**
     * @author 作者：tree
     * @Date 时间：2016/7/15 18:04
     * 功能说明：公共查询模块的对应方法,获取所有系统日志
     * 处理流程：
     * @param
     * @return
     */
    @RequestMapping(value = "/toQueryBusLog")
    public String toQueryBusLog(){
        logger.debug("查询业务日志表");
        //跳转前台
        return "/log/queryBusLog";
    }

    /**
     * @author 作者：tree
     * @Date 时间：2016/7/15 18:04
     * 功能说明：公共查询模块的对应方法，获取所有业务日志
     * 处理流程：
     * @param
     * @return
     */
    @RequestMapping(value = "/queryBusLog", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> queryBusLog(String aoData, String filterItem) {
        initController();
        Map<String,Object> paramMap = resolveControllerParam(aoData,page);
        if(StringUtils.isNotBlank((String)paramMap.get("searchValue"))){
            responseMap.put("data", busLogService.getObjListPage(page, ConfigParam.QUERY_TYPE_ALL, CommonStringUtil.parseShowString(paramMap.get("searchValue")),filterItem));
        }else{
            responseMap.put("data", busLogService.getObjListPage(page, ConfigParam.QUERY_TYPE_SOME));
        }
        responseMap.put("anyStatus", 1);
        responseMap.put("recordsFiltered", page.getTotalResult());
        responseMap.put("recordsTotal", page.getTotalResult());
        responseMap.put("draw", (int)(paramMap.get("draw")));
        return responseMap;
    }

    /**
     * @author 作者：tree
     * @Date 时间：2016/7/15 18:04
     * 功能说明：公共查询模块的对应方法,获取所有数据同步日志
     * 处理流程：
     * @param
     * @return
     */
    @RequestMapping(value = "/toQueryDataSynLog")
    public String toQueryDataSynLog(){
        logger.debug("查询数据同步日志");
        //跳转前台
        return "/log/queryDataSynLog";
    }

    /**
     * @author 作者：tree
     * @Date 时间：2016/7/15 18:04
     * 功能说明：公共查询模块的对应方法，获取所有业务日志
     * 处理流程：
     * @param
     * @return
     */
    @RequestMapping(value = "/queryDataSynLog", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> queryDataSynLog(String aoData, String filterItem) {
        initController();
        Map<String,Object> paramMap = resolveControllerParam(aoData,page);
        if(StringUtils.isNotBlank((String)paramMap.get("searchValue"))){
            responseMap.put("data", dataSynLogService.getObjListPage(page, ConfigParam.QUERY_TYPE_ALL, CommonStringUtil.parseShowString(paramMap.get("searchValue")),filterItem));
        }else{
            responseMap.put("data", dataSynLogService.getObjListPage(page, ConfigParam.QUERY_TYPE_SOME));
        }
        responseMap.put("anyStatus", 1);
        responseMap.put("recordsFiltered", page.getTotalResult());
        responseMap.put("recordsTotal", page.getTotalResult());
        responseMap.put("draw", (int)(paramMap.get("draw")));
        return responseMap;
    }

    /**
     * @author 作者：tree
     * @Date 时间：2016/7/15 18:04
     * 功能说明：公共查询模块的对应方法，获取所有业务日志
     * 处理流程：
     * @param
     * @return
     */
    @RequestMapping(value = "/exportOption")
    public void exportOption(String objType, String filterItem, String fileType, String searchText, HttpServletRequest request, HttpServletResponse response) {
        //重置response,并进行设置
        response.reset();
        // 指定下载的文件名
        response.setHeader("Content-Disposition", "attachment;filename=" + "ARA_AFIS_" + TimeUtil.getOnlyNumberDate() + ".xls");
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        String[] showName = null;
        String[] fieldName = null;
        String fileTitle = "";
        List<?> listTemp = null;
        switch (objType){
            case "DataSynLog":
                fileTitle = "数据同步日志";
                showName = new String[]{"序号", "库别", "档号", "版本号", "模板序号", "同步流水号", "同步类型",
                        "数据同步结果标志", "同步结果返回码", "同步结果内容", "重试次数", "同步时间", "更新时间"};
                fieldName = new String[]{"id", "sourceLibrary", "personId", "versionCode", "templateNo", "serialNum", "operateType", "resultFlag", "resultCode", "content", "retryNum", "createOn", "modeifyOn"};
                if (StringUtils.isNotBlank(searchText)) {
                    listTemp = dataSynLogService.getObjListPage(page, ConfigParam.QUERY_TYPE_ALL, CommonStringUtil.parseShowString(searchText), filterItem);
                } else {
                    listTemp = dataSynLogService.getObjListPage(page, ConfigParam.QUERY_TYPE_SOME);
                }
                break;
            case "BusLog":
                fileTitle = "业务日志";
                showName = new String[]{"业务码", "业务说明","处理耗时(毫秒)", "业务结果", "业务返回码", "业务返回信息", "重发次数", "请求方IP", "日志记录时间", "请求数据", "响应数据"};
                fieldName = new String[]{"actionType", "actionTypeDes","costTime", "resultFlag", "resultCode", "resultMSG", "retryNum", "clientIp", "createOn", "requestData", "responseData"};
                if (StringUtils.isNotBlank(searchText)) {
                    listTemp = busLogService.getObjListPage(page, ConfigParam.QUERY_TYPE_ALL, CommonStringUtil.parseShowString(searchText), filterItem);
                } else {
                    listTemp = busLogService.getObjListPage(page, ConfigParam.QUERY_TYPE_SOME);
                }
                break;
            case "SysLog":
                fileTitle = "系统日志";
                showName = new String[]{"日志类型", "操作类型", "操作内容", "操作者", "操作时间"};
                fieldName = new String[]{"type", "operate", "content", "createBy", "createOn"};
                if (StringUtils.isNotBlank(searchText)) {
                    listTemp = sysLogService.getObjListPage(page, ConfigParam.QUERY_TYPE_ALL, CommonStringUtil.parseShowString(searchText), filterItem);
                } else {
                    listTemp = sysLogService.getObjListPage(page, ConfigParam.QUERY_TYPE_SOME);
                }
                break;
            default:
                break;
        }
        //对若数据为空则进行处理
        listTemp = Optional.ofNullable(listTemp).orElse(new ArrayList<>());
        //根据导出文件类型生成导出文件
        switch (fileType) {
            case "Excel":
                // 指定下载的文件名
                response.setHeader("Content-Disposition", "attachment;filename=" + "ARA_AFIS_" + TimeUtil.getOnlyNumberDate() + ".xls");
                response.setContentType("application/msexcel");
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet(fileTitle);
                ExcelUtil.addTitle(sheet, showName, fileTitle, ExcelUtil.createFont(workbook, 1), ExcelUtil.createFont(workbook, 2));
                ExcelUtil.addContextByList(sheet, listTemp, fieldName, ExcelUtil.createFont(workbook, 2), false);
                try (BufferedOutputStream bufferedOutPut = new BufferedOutputStream(response.getOutputStream())) {
                    bufferedOutPut.flush();
                    workbook.write(bufferedOutPut);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                anyStatus = 1;
                break;
            case "Log":
                // 指定下载的文件名
                response.setHeader("Content-Disposition", "attachment;filename=" + "ARA_AFIS_" + TimeUtil.getOnlyNumberDate() + ".log");
                response.setContentType("application/octet-stream");
                try (BufferedOutputStream bufferedOutPut = new BufferedOutputStream(response.getOutputStream())) {
                    bufferedOutPut.flush();
                    bufferedOutPut.write(JSON.toJSONString(listTemp).getBytes("UTF-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                anyStatus = 1;
                break;
            default:
                break;
        }
        logger.debug(fileType + "文件生成成功...");
    }
}
