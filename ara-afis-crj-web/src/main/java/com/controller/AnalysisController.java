package com.controller;

import com.alibaba.fastjson.JSON;
import com.excel.ExcelUtil;
import com.model.FpTemplate;
import com.vo.FpImageSource;
import com.param.ConfigParam;
import com.services.FpTemplateService;
import com.services.FpImageSourceService;
import com.time.TimeUtil;
import com.util.DaoMethodUtil;
import org.slf4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
 * 文件名：AnalysisController
 * 作者：tree
 * 时间：2017/6/15
 * 描述：统计分析 相关业务逻辑类
 * 版权：亚略特
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/analysis")
public class AnalysisController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(AnalysisController.class);

    @Autowired
    private FpImageSourceService fpImageSourceService;
    @Autowired
    private FpTemplateService fpTemplateService;


    /**
     * @author 作者：tree
     * @Date 时间：2016/7/15 18:04
     * 功能说明：获取差异性比较数据
     * 处理流程：
     * @param
     * @return
     */
    @RequestMapping(value = "/toCompareSourceFPData")
    public String toCompareSourceFPData(Model model){
        logger.debug("进入边检源库的数据与指纹库的指纹数据差异性比较页面");
        model.addAttribute("dataListJson", JSON.toJSONString(ConfigParam.SYSTEM_STORE_DATA.get("differData")));
        model.addAttribute("diffColTime", ConfigParam.SYSTEM_STORE_DATA.get("diffColTime"));
        return "/analysis/compareSource";
    }

    /**
     * @param
     * @return
     * @author 作者：tree
     * @Date 时间：2017/6/15 18:04
     * 功能说明：将边检源库的数据与指纹库的指纹数据进行差异性比较
     * 处理流程：
     */
    @RequestMapping(value = "/compareSourceFPData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> compareSourceFPData() {
        initController();
        logger.debug("将边检源库的数据与指纹库的指纹数据进行差异性比较");
        logger.info("---------主动进行源库数据差异性比较开始 "+ TimeUtil.getFormatDate()+"-----------------------------");
        List<FpImageSource> imageList = fpImageSourceService.getFpImageSourcePage();
        logger.debug("-----打印数据"+imageList.get(1).getPersonId() + "," + imageList.get(1).getLibrary());
        List<FpTemplate> templateList = fpTemplateService.getObjListPage(null,1);
        ConfigParam.SYSTEM_STORE_DATA.put("differData", DaoMethodUtil.compareDifferList(imageList, templateList));
        ConfigParam.SYSTEM_STORE_DATA.put("diffColTime", TimeUtil.getFormatDate());
        responseMap.put("dataListJson", JSON.toJSONString(ConfigParam.SYSTEM_STORE_DATA.get("differData")));
        logger.info("---------主动进行源库数据差异性比较结束 "+ TimeUtil.getFormatDate()+"-----------------------------");
        responseMap.put("anyStatus", 1);
        responseMap.put("msg", "统计差异性数据成功");
        responseMap.put("diffColTime", ConfigParam.SYSTEM_STORE_DATA.get("diffColTime"));
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

        String fileTitle = "指纹库和梅沙库差异性数据统计";
        String[] showName = new String[]{"库别","档号","序号",  "比对库", "梅沙库"};
        String[] fieldName = new String[]{"library", "personId", "index", "desHaveFlag", "srcHaveFlag"};
        List<FpTemplate> listTemp = (List<FpTemplate>) ConfigParam.SYSTEM_STORE_DATA.get("differData");
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
