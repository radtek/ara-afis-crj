package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.exception.ServiceException;
import com.model.Parameter;
import com.vo.OperateVO;
import com.services.ParameterService;
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
import java.util.Map;

/**
 * 文件名：EngineController
 * 作者：tree
 * 时间：2016/4/21
 * 描述：比对引擎 相关业务逻辑类
 * 版权：亚略特
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/parameter")
public class ParameterController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ParameterController.class);

    //注入服务
    @Autowired
    private ParameterService parameterService;

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
        logger.debug("查询系统参数列表");
        //获取所有引擎信息
        model.addAttribute("parameterListJson", JSON.toJSONString(parameterService.getAllShowParameter(), SerializerFeature.WriteNullStringAsEmpty));
        //跳转前台
        return "/system/parameter/queryParameter";
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
        Parameter parameter = null;
        if(StringUtils.isNotEmpty(anyId)){
            //根据参数标示ID获取参数信息
            parameter = parameterService.getObj(anyId);
            if(null != parameter){
                if ("WRITE_FILE_FLAG".equals(parameter.getSubKey()) || "HAS_ENGINE".equals(parameter.getSubKey())) {
                    model.addAttribute("uiInputStyle", "select");
                }
            }
        }
        model.addAttribute("parameter", parameter);
        return "/system/parameter/addParameter";
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
    public Map<String,Object> addOption(Parameter parameter, String anyId, HttpSession session) throws ServiceException {
        initController();
        //打包系统日志
        packageLogInfo("PARAMETER", OperateVO.MODEIFY_OPERATE);
        //判定数据完整性
        if (null != parameter) {
            //修改系统参数
            parameterService.updateObj(parameter, getSessionManager(session), anyId);
            msg = "系统参数 " + parameter.getSubKey() + " 修改成功";

            // TODO: 2017/5/19
            //更新系统初始数据到内存中
//            GetInitData.getInstance().reloadInitDataMap();

            //构造前台数据模型
            anyStatus = 1;
        }
        logInfo.setContent(msg);
        logInfo.setCreateBy(getSessionManager(session));
        responseMap.put("anyStatus", anyStatus);
        responseMap.put("msg", msg);
        return responseMap;
    }
}
