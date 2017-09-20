package com.controller;

import com.exception.ServiceException;
import com.model.WarnInfo;
import com.vo.OperateVO;
import com.services.WarnInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 文件名：WarnInfoController
 * 作者：tree
 * 时间：2016/6/26
 * 描述：引擎告警信息 相关业务逻辑类
 * 版权：亚略特
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/warnInfo")
public class WarnInfoController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(WarnInfoController.class);

    //注入服务
    @Autowired
    private WarnInfoService warnInfoService;

    /**
     * @author 作者：tree
     * @Date 时间：2016/7/15 18:04
     * 功能说明：公共查询模块的对应方法，获取可用的报警信息
     * 处理流程：
     * @param
     * @return
     */
    @RequestMapping(value = "/queryWarnInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> queryWarnInfo() {
        initController();
        List<WarnInfo> warnInfoList = warnInfoService.getAvailableWarnData();
        responseMap.put("anyStatus", 1);
        responseMap.put("warInfoNum",warnInfoList.size());
        responseMap.put("show", warnInfoList.size() > 0 ? true:false);
        responseMap.put("warnInfoList",warnInfoList);
        return responseMap;
    }

    /**
     * @author 作者：tree
     * @Date 时间：2016/7/15 18:06
     * 功能说明：公共添加/修改模块的对应方法
     * 处理流程：
     * @param
     * @return
     */
    @RequestMapping(value = "/delOption", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> delOption(WarnInfo warnInfo, HttpSession session) throws ServiceException {
        initController();
        //打包系统日志
        packageLogInfo("WARN", OperateVO.DELETE_OPERATE);
        //判定数据完整性
        if (Optional.of(warnInfo).isPresent()) {
            //修改系统参数
            warnInfoService.delObj(warnInfo.getId());
            msg = "系统预警信息: "+warnInfo.getMessage()+" 已处理完成!";
            //构造前台数据模型
            anyStatus = 1;
        }
        logInfo.setContent(msg);
        logInfo.setCreateBy(getSessionManager(session));
        responseMap.put("anyStatus", anyStatus);
        responseMap.put("warnInfoList",warnInfoService.getAvailableWarnData());
        responseMap.put("msg", msg);
        return responseMap;
    }
}
