package com.controller;


import com.aspect.DirectLog;
import com.exception.InternalSystemException;
import com.exception.ServiceException;
import com.model.SysLogInfo;
import com.vo.PageVO;
import com.time.TimeUtil;
import com.util.CommonStringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 文件名：
 * 作者：tree
 * 时间：2016/10/26
 * 描述：
 * 版权：亚略特
 */
@Controller
public class BaseController implements DirectLog {

    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    public Map<String, Object> responseMap;
    public String msg;
    public int anyStatus;
    public String tr;
    public String anyData;
    public PageVO page;

    @Resource
    private MessageSource messageSource;

    //系统日志对象
    public SysLogInfo logInfo = new SysLogInfo();

    /**
     * 基于@ExceptionHandler异常处理总机制
     */
    @ExceptionHandler
    @ResponseBody
    public Map<String, Object> handleAndReturnData(HttpServletRequest request, Exception ex) {
        initController();
        boolean showStackFlag = true;
        responseMap.put("anyStatus", anyStatus);
        if (ex instanceof InternalSystemException || ex instanceof ServiceException) {
            logger.error("系统警告信息: "+ex.getMessage());
            responseMap.put("msg", "系统警告信息：" + ex.getMessage());
            showStackFlag = false;
        } else if (ex instanceof NullPointerException) {
            responseMap.put("msg", "此次操作出现空指针异常!");
        } else {
            responseMap.put("msg", ex.getMessage());
        }
        if (showStackFlag) {
            ex.printStackTrace();
        }
        responseMap.put("data", null);
        return responseMap;
    }

    /**
     * 初始化管理员
     *
     * @return session中用户名
     */
    public void initController() {
        responseMap = new HashMap<>();
        page = new PageVO();
        msg = null;
        anyStatus = 0;
        tr = null;
        anyData = null;
    }

    /**
     * 获取当前用户
     *
     * @return session中用户名
     */
    public String getSessionManager(HttpSession session) {
        String manager = (String) session.getAttribute("managerName");
        return manager;
    }

    public String getMessage(String key) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, null, locale);
    }

    /**
     * @param type    日志类型
     * @param operate 操作
     * @return
     * @author 作者：tree
     * @Date 时间：2017/5/24 13:51
     * 功能说明：打包日志信息
     */
    public void packageLogInfo(String type, String operate) {
        logInfo.setCreateOn(TimeUtil.getFormatDate());
        logInfo.setType(type);
        logInfo.setOperate(operate);
        if (StringUtils.isEmpty(logInfo.getCreateBy())) {
            logInfo.setCreateBy("  ");
        }
    }

    public Map<String, Object> resolveControllerParam(String data, PageVO page) {
        Map<String, Object> tableMap = CommonStringUtil.covertJsonStringToHashMap(data);
        page.setPageSize((int) (tableMap.get("length")));
        page.setStart((int) (tableMap.get("start")));
        Map<String, Object> searchMap = (Map<String, Object>) tableMap.get("search");
        tableMap.put("searchValue", (String) searchMap.get("value"));
        return tableMap;
    }

    @Override
    public SysLogInfo toGetLogInfo() {
        return logInfo;
    }
}
