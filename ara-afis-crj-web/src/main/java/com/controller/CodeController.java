package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.exception.CommonUtilException;
import com.exception.ServiceException;
import com.model.Code;
import com.vo.OperateVO;
import com.services.CodeService;
import com.util.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 文件名：CodeController
 * 作者：tree
 * 时间：2016/4/21
 * 描述：数据字典 相关业务逻辑类
 * 版权：亚略特
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/code")
public class CodeController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(CodeController.class);

    @Autowired
    private CodeService codeService;

    /**
     * @author 作者：tree
     * @Date 时间：2016/7/15 18:04
     * 功能说明：公共查询模块的对应方法
     * 处理流程：
     * @param
     * @return
     */
    @RequestMapping(value = "/toGetList")
    public String toGetList(Model model){
        logger.debug("查询数据字典列表");
        //查询所有基本数据字典类型
        model.addAttribute("codeListJson", JSON.toJSONString(codeService.getObjList(false), SerializerFeature.WriteNullStringAsEmpty));
        //跳转前台
        return "/system/code/queryCode";
    }

    /**
     * @author 作者：tree
     * @Date 时间：2016/7/15 18:06
     * 功能说明：初始化添加或修改页面的对应方法
     * 处理流程：
     * @param
     * @return
     */
    @RequestMapping(value = "/toAddOption")
    public String toAddOption(Model model, String anyId){
        List<Code> typelist = codeService.getAllBaseCode();
        //判定是否指定了数据字典
        if(StringUtils.isNotEmpty(anyId)){
            //根据数据字典对象标示ID获取数据字典信息
            Code code = codeService.getObj(anyId);
            model.addAttribute("code", code);
        }
        model.addAttribute("typelist", typelist);
        //跳转到添加数据字典界面
        return "/system/code/addCode";
    }

    /**
     * @author 作者：tree
     * @Date 时间：2016/7/15 18:06
     * 功能说明：公共添加/修改模块的对应方法
     * 处理流程：
     * @param
     * @return
     */
    @RequestMapping(value = "/addOption", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addOption(Code code, String anyOption, String anyId, HttpSession session){
        initController();
        //打包系统日志
        packageLogInfo("CODE", OperateVO.ADD_OPERATE);
        //初始化参数
        String codeId = "",typeMean = "",resultMsg = "";
        try{
            //判定数据完整性
            if(null != code){
                codeId = code.getCode();
                //页面复选框值转义，当传值为‘on’时，设置数据字典状态为额可用，否则为停用
                if("on".equals(code.getStatu())){
                    code.setStatu("E");
                }else{
                    code.setStatu("D");
                }
                //判别要执行的是添加还是修改操作
                if(anyOption.contains("add")){
                    //设置数据字典的类型描述
                    code.setTypeMean(codeService.getBaseCodeType(code.getType()).getCodeMean());
                    //保存数据字典对象
                    codeService.saveObj(code, getSessionManager(session));
                    resultMsg = "添加成功";
                }else{
                    logInfo.setOperate(OperateVO.MODEIFY_OPERATE);
                    code.setTypeMean(codeService.getBaseCodeType(code.getType()).getCodeMean());
                    //修改数据字典
                    codeService.updateObj(code, getSessionManager(session),anyId);
                    resultMsg = "修改成功";
                }
                typeMean = code.getTypeMean();
                //构造前台数据模型
                anyStatus = 1;
                tr = PageUtil.drawTable(Code.convert(code));
            }
        } catch (CommonUtilException e) {
            resultMsg = "添加失败,失败原因: " + e.getMessage();
        }catch (ServiceException e){
            msg = "添加失败,失败原因: " + e.getMessage();
        }
        msg = "数据字典 "+codeId+" (所属数据字典类型为: "+typeMean + " ） " + resultMsg ;
        logInfo.setContent(msg);
        logInfo.setCreateBy(getSessionManager(session));
        responseMap.put("anyStatus", anyStatus);
        responseMap.put("msg", msg);
        responseMap.put("tr", tr);
        return responseMap;
    }

    /**
     * @author 作者：tree
     * @Date 时间：2016/7/15 18:06
     * 功能说明：公共删除模块的对应方法
     * 处理流程：
     * @param
     * @return
     */
    @RequestMapping(value = "/delOption", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> delOption(String anyId, HttpSession session) throws ServiceException {
        initController();
        //打包系统日志
        packageLogInfo("CODE", OperateVO.DELETE_OPERATE);
        //初始化参数
        String codeId = "",codeMean = "",resultMsg = "";
        //判定是否指定了引擎
        if(StringUtils.isNotEmpty(anyId)) {
            //通过数据字典对象主键获取数据字典对象
            Code code = codeService.getObj(anyId);
            if (null != code) {
                codeId = code.getCode();
                codeMean = code.getCodeMean();
                //删除指定的数据字典对象
                codeService.delObj(anyId);
                anyStatus = 1;
                resultMsg = "删除成功";
            }
        }
        msg = "数据字典 "+codeId+" (所属数据字典类型为: "+codeMean + " ） " + resultMsg;
        logInfo.setContent(msg);
        logInfo.setCreateBy(getSessionManager(session));
        responseMap.put("anyStatus", anyStatus);
        responseMap.put("msg", msg);
        return responseMap;
    }
}
