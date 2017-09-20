package com.controller;

import com.alibaba.fastjson.JSON;
import com.excel.ExcelUtil;
import com.exception.CommonUtilException;
import com.exception.ServiceException;
import com.ip.IpUtil;
import com.model.SystemOrder;
import com.model.SystemOrderResult;
import com.vo.OperateVO;
import com.param.ConfigParam;
import com.services.SystemOrderResultService;
import com.services.SystemOrderService;
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
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 文件名：SystemOrderController
 * 作者：tree
 * 时间：2016/4/21
 * 描述：系统命令 相关业务逻辑类
 * 版权：亚略特
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/systemOrder")
public class SystemOrderController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(SystemOrderController.class);

    @Autowired
    private SystemOrderService systemOrderService;
    @Autowired
    private SystemOrderResultService systemOrderResultService;

    /**
     * @param
     * @return
     * @author 作者：tree
     * @Date 时间：2016/7/15 18:04
     * 功能说明：公共查询模块的对应方法
     * 处理流程：
     */
    @RequestMapping(value = "/toGetList")
    public String toGetList() {
        logger.debug("查询系统命令列表");
        //跳转前台
        return "/engine/querySystemOrder";
    }

    /**
     * @param
     * @return
     * @author 作者：tree
     * @Date 时间：2016/7/15 18:04
     * 功能说明：公共查询模块的对应方法，获取所有业务日志
     * 处理流程：
     */
    @RequestMapping(value = "/querySystemOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> querySystemOrder(String aoData, String filterItem) {
        initController();
        Map<String, Object> paramMap = resolveControllerParam(aoData, page);
        List<SystemOrder> listTemp = null;
        if (StringUtils.isNotBlank((String) paramMap.get("searchValue"))) {
            listTemp = systemOrderService.getObjListPage(page, ConfigParam.QUERY_TYPE_ALL, CommonStringUtil.parseShowString(paramMap.get("searchValue")),filterItem);
        } else {
            listTemp = systemOrderService.getObjListPage(page, ConfigParam.QUERY_TYPE_SOME);
        }
        listTemp.forEach(systemOrder -> {
            List<SystemOrderResult> list = systemOrderResultService.getObjList(systemOrder.getId());
            StringBuffer resultTemp = new StringBuffer("完成");
            StringBuffer contentTemp = new StringBuffer();
            StringBuffer spendTimeTemp = new StringBuffer();
            if (list.size() == 0) {
                resultTemp.setLength(0);
                resultTemp.append("执行中");
                spendTimeTemp.append(",");
            } else {
                list.forEach(systemOrderResult -> {
                    if (StringUtils.isEmpty(systemOrderResult.getResult()) || ConfigParam.PROGRAM_FLAG.FAIL.equals(systemOrderResult.getResult())) {
                        resultTemp.setLength(0);
                        resultTemp.append("失败");
                    }
                    if(StringUtils.isNotEmpty(systemOrderResult.getContent())){
                        contentTemp.append("," + systemOrderResult.getContent());
                    }
                    spendTimeTemp.append("," + String.valueOf(systemOrderResult.getSpendTime()));
                });
            }
            systemOrder.setResult(resultTemp.toString());
            if(contentTemp.toString().length() > 0){
                systemOrder.setContent(contentTemp.substring(1));
            }
            systemOrder.setSpendTime(spendTimeTemp.substring(1) + " 秒");
            try {
                SystemOrder.convert(systemOrder);
            } catch (CommonUtilException e) {
                e.printStackTrace();
            }
        });
        responseMap.put("anyStatus", 1);
        responseMap.put("data", listTemp);
        responseMap.put("recordsFiltered", page.getTotalResult());
        responseMap.put("recordsTotal", page.getTotalResult());
        responseMap.put("draw", (int) (paramMap.get("draw")));
        return responseMap;
    }

    /**
     * @param
     * @return
     * @author 作者：tree
     * @Date 时间：2016/7/15 18:06
     * 功能说明：公共添加/修改模块的对应方法
     * 处理流程：
     */
    @RequestMapping(value = "/addOption", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addOption(SystemOrder systemOrder, HttpServletRequest request, HttpSession session) throws ServiceException {
        initController();
        //打包系统日志
        packageLogInfo("SYSTEMORDER", OperateVO.MODEIFY_OPERATE);
        //判定数据完整性
        if (null != systemOrder) {
            //构造系统命令数据
            try {
                systemOrder.setRequestIp(IpUtil.getIpAddr(request));
            } catch (CommonUtilException e) {
                e.printStackTrace();
            }
            //保存系统命令
            systemOrderService.saveObj(systemOrder, getSessionManager(session));
            //构造前台数据模型
            anyStatus = 1;
            msg = "系统命令已在后台启动执行";
        } else {
            msg = "系统命令启动失败，命令对象丢失";
        }
        logInfo.setContent("执行系统命令: "+msg);
        logInfo.setCreateBy(getSessionManager(session));
        responseMap.put("anyStatus", anyStatus);
        responseMap.put("msg", msg);
        return responseMap;
    }

    /**
     * @param
     * @return
     * @author 作者：tree
     * @Date 时间：2016/7/15 18:06
     * 功能说明：公共删除模块的对应方法
     * 处理流程：
     */
    @RequestMapping(value = "/delOption", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delOption(long anyId, HttpSession session) throws ServiceException {
        initController();
        //打包系统日志
        packageLogInfo("SYSTEMORDER", OperateVO.DELETE_OPERATE);
        String resultMsg = "";
        //初始化参数
        //判定是否指定了引擎
        if (anyId != 0) {
            //通过数据字典对象主键获取数据字典对象
            SystemOrder systemOrder = systemOrderService.getObj(anyId);
            if (null != systemOrder) {
                //删除指定的系统命令对象
                systemOrderService.delObj(anyId);
                anyStatus = 1;
                resultMsg = "删除成功";
            }
        }
        msg = "此条系统命令 " + resultMsg;
        logInfo.setContent(msg);
        logInfo.setCreateBy(getSessionManager(session));
        responseMap.put("anyStatus", anyStatus);
        responseMap.put("msg", msg);
        return responseMap;
    }

    /**
     * @author 作者：tree
     * @Date 时间：2016/7/15 18:04
     * 功能说明：公共查询模块的对应方法，获取所有系统命令
     * 处理流程：
     * @param
     * @return
     */
    @RequestMapping(value = "/exportOption")
    public void exportOption(String objType, String filterItem, String fileType, String searchText, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.reset();
        String[] showName = new String[]{"序号", "系统命令码","系统命令说明", "执行结果", "扩展信息", "执行耗时", "命令来源", "命令等级", "请求IP地址", "变更流水号", "活动状态", "创建时间", "创建者"};
        String[] fieldName = new String[]{"id", "orderCode", "orderCodeDes","result", "content", "spendTime", "orderSource", "orderLevel", "requestIp", "serialNum", "activeStatu", "createOn", "createBy"};
        List<SystemOrder> listTemp = null;
        if (StringUtils.isNotBlank(searchText)) {
            listTemp = systemOrderService.getObjListPage(page, ConfigParam.QUERY_TYPE_ALL, CommonStringUtil.parseShowString(searchText), filterItem);
        } else {
            listTemp = systemOrderService.getObjListPage(page, ConfigParam.QUERY_TYPE_SOME);
        }
        //对若数据为空则进行处理
        listTemp = Optional.ofNullable(listTemp).orElse(new ArrayList<>());
        listTemp.forEach(systemOrder -> {
            List<SystemOrderResult> list = systemOrderResultService.getObjList(systemOrder.getId());
            StringBuffer resultTemp = new StringBuffer("完成");
            StringBuffer contentTemp = new StringBuffer();
            StringBuffer spendTimeTemp = new StringBuffer();
            if (list.size() == 0) {
                resultTemp.setLength(0);
                resultTemp.append("执行中");
                spendTimeTemp.append(",");
            } else {
                list.forEach(systemOrderResult -> {
                    if (StringUtils.isEmpty(systemOrderResult.getResult()) || ConfigParam.PROGRAM_FLAG.FAIL.equals(systemOrderResult.getResult())) {
                        resultTemp.setLength(0);
                        resultTemp.append("失败");
                    }
                    if (StringUtils.isNotEmpty(systemOrderResult.getContent())) {
                        contentTemp.append("," + systemOrderResult.getContent());
                    }
                    spendTimeTemp.append("," + String.valueOf(systemOrderResult.getSpendTime()));
                });
            }
            systemOrder.setResult(resultTemp.toString());
            if (contentTemp.toString().length() > 0) {
                systemOrder.setContent(contentTemp.substring(1));
            }
            systemOrder.setSpendTime(spendTimeTemp.substring(1) + " 秒");
            try {
                SystemOrder.convert(systemOrder);
            } catch (CommonUtilException e) {
                e.printStackTrace();
            }
        });
        //根据导出文件类型生成导出文件
        switch (fileType) {
            case "Excel":
                // 指定下载的文件名
                String fileTitle = "系统命令";
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
                break;
            default:
                break;
        }
        logger.debug(fileType + "文件生成成功...");
    }
}
