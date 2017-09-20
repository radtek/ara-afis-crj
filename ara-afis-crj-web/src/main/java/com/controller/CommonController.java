package com.controller;

import com.model.*;
import com.util.BusCodeUtil;
import com.vo.OperateVO;
import com.vo.PageVO;
import com.param.ConfigParam;
import com.services.BusLogService;
import com.services.ManagerService;
import com.services.PageService;
import com.services.SysLogService;
import com.time.TimeUtil;
import com.util.CommonStringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 文件名：
 * 作者：tree
 * 时间：2016/4/21
 * 描述：
 * <p/>
 * 版权：亚略特
 */
@Controller
@Scope("prototype")
public class CommonController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ManagerService managerService;
    @Autowired
    private PageService pageService;
    @Autowired
    private SysLogService sysLogService;
    @Autowired
    private BusLogService busLogService;
    @Autowired
    private SystemConfig systemConfig;

    /**S
     * @author 作者：tree
     * @Date 时间：2016/84 12:13
     * 功能说明：判断Cookie中是否留存有系统的登录数据，有则进行登录，否则跳转到登录界面
     * 处理流程：
     *
     * @param
     *
     * @return
     */
    @RequestMapping(value = "/")
    public String loginCookie(HttpServletRequest request, HttpSession session) {
        initController();
        logInfo.setLogFlag(false);
        //打包系统日志
        packageLogInfo("LOGIN", OperateVO.LOGIN_OPERATE);
        //获取Cookie对象
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length > 0){
            for (Cookie cookie : cookies) {
                if (ConfigParam.MANAGER_COOKIE.equals(cookie.getName())) {
                    String value = cookie.getValue();
                    if (StringUtils.isNotBlank(value)) {
                        String[] split = value.split("#");
                        String account = split[0];
                        String password = split[1];
                        if (managerService.checkLogin(account, password)) {
                            session.setAttribute("managerName", account);
                            Manager managerTemp = managerService.getObjByObj("account", account);
                            //异常判定
                            if (null != managerTemp) {
                                //获取有效页面
                                List<Page> pagelistTemp = pageService.getAllEnablePages();
                                //根据有效页面，对权限数据进行再次筛选
                                String purview = BusCodeUtil.filterString(pagelistTemp, managerTemp.getRole().getPurview());
                                session.setAttribute("purview", purview);

                                //更改用户最近登录时间
                                managerTemp.setLastLogin(TimeUtil.getFormatDate());
                                try {
                                    managerService.updateObj(managerTemp, (String) session.getAttribute("managerName"), managerTemp.getId());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            logInfo.setCreateBy(account);
                            logInfo.setLogFlag(true);
                            logInfo.setContent(getMessage("manager") + " " + managerTemp.getAccount() + " " + getMessage("login_success"));
                            return "main";
                        }
                    }
                }
            }
        }
        return "common/login";
    }

    /**
     *处理用户登入请求
     *return ： 是否允许登入系统
     */
    @RequestMapping(value = "/login")
    public String login(Model model, Manager manager, String remember, HttpSession session, HttpServletResponse response){
        logger.debug("------------------------登录进系统-------------------------------");
        initController();
        //打包系统日志
        packageLogInfo("LOGIN", OperateVO.LOGIN_OPERATE);
        try{
            if(Optional.of(manager).isPresent()){
                if(managerService.checkLogin(manager.getAccount(), manager.getPassword())){
                    Manager managerTemp = managerService.getObjByObj("account", manager.getAccount());
                    if("E".equals(managerTemp.getStatu())){
                        //用户选择了记住我
                        if("true".equals(remember)){
                            Cookie cookie = new Cookie(ConfigParam.MANAGER_COOKIE,manager.getAccount()+"#"+manager.getPassword());
                            //设置cookie有效期为2周
                            cookie.setMaxAge(60 * 60 * 24 * 14);
                            response.addCookie(cookie);
                        }
                        session.setAttribute("managerName", manager.getAccount());
                        //异常判定
                        if(null != managerTemp){
                            //获取有效页面
                            List<Page> pagelistTemp = pageService.getAllEnablePages();
                            //根据有效页面，对权限数据进行再次筛选
                            String purview = BusCodeUtil.filterString(pagelistTemp,managerTemp.getRole().getPurview());
                            session.setAttribute("purview", purview);

                            //更改用户最近登录时间
                            managerTemp.setLastLogin(TimeUtil.getFormatDate());
                            managerService.updateObj(managerTemp,getSessionManager(session),managerTemp.getId());
                        }
                        logInfo.setContent(getMessage("manager")+" "+manager.getAccount()+" "+getMessage("login_success"));
                        return "/main";
                    }else{
                        msg = "该登录账号已被管理员停用";
                        logInfo.setContent(getMessage("manager")+" "+manager.getAccount()+" 登录失败，原因："+msg);
                    }
                }else{
                    msg = getMessage("id_pw_error");
                    logInfo.setCreateBy(manager.getAccount());
                    logInfo.setContent(getMessage("manager")+" "+manager.getAccount()+" "+getMessage("login_fail"));
                }
            }else{
                logInfo.setLogFlag(false);
            }
        }catch (Exception e) {
            e.printStackTrace();
            logInfo.setContent(getMessage("login_fail")+":"+e.getMessage());
        }
        model.addAttribute("msg", msg);
        return "common/login";
    }

    /**
     * @author 作者：tree
     * @Date 时间：2016/5/15 15:04
     * 功能说明：封装首页展示数据内容
     * 处理流程：
     * @param
     * @return
     */
    @RequestMapping(value = "/common/loadContent")
    public String loadContent(Model model){
        ReportHome homeData = new ReportHome();
        List<SysLogInfo> sysLogInfolist;
        List<BusLogInfo> busLogInfolist;
        try{
            //从内存中获取在库的有效指纹数
            if(null != ConfigParam.ANALYSE_DATA.get("fpNum")){
                homeData.setFpNum(ConfigParam.ANALYSE_DATA.get("fpNum"));
            }
            //从内存中获取在库的有效用户数
            if(null != ConfigParam.ANALYSE_DATA.get("libraryNum")){
                homeData.setUserNum(ConfigParam.ANALYSE_DATA.get("libraryNum"));
            }
            //从内存中获取比对引擎服务器数目
            if(null != ConfigParam.ANALYSE_DATA.get("serverNum")){
                homeData.setServerNum(ConfigParam.ANALYSE_DATA.get("serverNum"));
            }
            //从内存中获取今天的业务数目
            if(null != ConfigParam.ANALYSE_DATA.get("busNum")){
                homeData.setBusNum(ConfigParam.ANALYSE_DATA.get("busNum"));
            }
            //从内存中获取今天失败的业务数目
            if(homeData.getBusNum() > 0){
                DecimalFormat df = new DecimalFormat("0.00");//格式化小数
                homeData.setBusFailPercent((int)(Double.valueOf(df.format((double) ConfigParam.ANALYSE_DATA.get("busFailNum")/homeData.getBusNum()))*100));
            }else{
                homeData.setBusFailPercent(0);
            }

            //从内存中获取本月的指纹增长数据，按天计算
            Calendar calendar = Calendar.getInstance(Locale.CHINA);
            String addFpData = CommonStringUtil.mapConvertString(ConfigParam.FP_ADD_DATA, 1, calendar.get(Calendar.DAY_OF_MONTH));
            homeData.setFpAddData(addFpData);

            //从内存中获取本月的业务类型分布数据
            if(ConfigParam.BUS_TYPE_DATA.size() > 0){
                String[] busCodeArray = systemConfig.getBusCodes().split(",");
                String busTypeData = BusCodeUtil.changeBusCodeData(ConfigParam.BUS_TYPE_DATA,busCodeArray);
                homeData.setBusTypeData(busTypeData);
            }

            //从内存中获取在库的服务器运行状态数据
            String serverStatuData = "[" + CommonStringUtil.mapConvertString(ConfigParam.SERVER_STATU_DATA, 0, 2)+ "]";
            homeData.setServerStatuData(serverStatuData);

            //从内存中获取当前在库的任务执行状态数据
            String taskStatuData = "[" + CommonStringUtil.mapConvertString(ConfigParam.TASK_STATU_DATA, 0, 2)+ "]";
            homeData.setTaskStatuData(taskStatuData);
            //获取最近5条系统日志
            sysLogInfolist = sysLogService.getObjListPage(new PageVO(5), ConfigParam.QUERY_TYPE_SOME);
            //获取最近5条业务日志
            busLogInfolist = busLogService.getObjListPage(new PageVO(5), ConfigParam.QUERY_TYPE_SOME);
            model.addAttribute("homeData", homeData);
            model.addAttribute("sysLogInfolist", sysLogInfolist);
            model.addAttribute("busLogInfolist", busLogInfolist);
            //跳转前台页面
            return "common/content";
        }catch (Exception e) {
            e.printStackTrace();
            //跳转前台页面
            return "common/login";
        }
    }

    /**
     * @author 作者：tree
     * @Date 时间：2017/5/11 18:10
     * 功能说明：处理用户退出请求
     * @param
     * @return
     */
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session){
        logInfo.setCreateBy(getSessionManager(session));
        //打包系统日志
        packageLogInfo("LOGIN", OperateVO.LOGIN_OPERATE);
        logInfo.setContent(getMessage("manager")+" "+session.getAttribute("managerName")+" "+getMessage("login_out"));
        session.removeAttribute("managerName");
        session.removeAttribute("purview");
        return "common/login";
    }

    /**
     * @author 作者：tree
     * @Date 时间：2017/5/12 11:21
     * 功能说明：处理用户超时退出
     * @param
     * @return
     */
    @RequestMapping(value = "/logTimeout")
    public String logTimeout(Model model, String account, HttpSession session){
        model.addAttribute("account", account);
        //构造日志内容
        logInfo.setCreateBy(account);
        //打包系统日志
        packageLogInfo("LOGIN", OperateVO.LOGIN_OPERATE);
        logInfo.setContent("页面超时,锁屏界面.");
        //设置不记日志
        logInfo.setLogFlag(false);
        return "common/lockScreen";
    }

    /**
     * @author 作者：tree
     * @Date 时间：2017/5/12 11:26
     * 功能说明：处理用户越权操作
     * @param
     * @return
     */
    @RequestMapping(value = "/checkTimeOut")
    @ResponseBody
    public Map<String,Object> checkTimeOut(HttpSession session){
        initController();
        if(getSessionManager(session) == null){
            msg = "true";
        }else{
            msg = "false";
        }
        responseMap.put("msg", msg);
        return responseMap;
    }

    /**
     * @author 作者：tree
     * @Date 时间：2017/5/12 13:19
     * 功能说明：处理用户越权操作
     * @param
     * @return
     */
    @RequestMapping(value = "/logNopower")
    public String logNopower(){
        //构造日志内容
        logInfo.setOperate(OperateVO.OVERPOWER_OPERATE);
        logInfo.setContent("拒绝访问");
        return "common/login";
    }

    /**
     * @author 作者：tree
     * @Date 时间：2017/5/11 17:28
     * 功能说明：更改系统语言
     * @param
     * @return
     */
    @RequestMapping(value = "/common/changeLu")
    @ResponseBody
    public Model changeLu(Model model,HttpSession session) {
        //设置不记日志
        logInfo.setLogFlag(false);
        // TODO: 2017/5/11 此处存在BUG 暂时不解决
        //判定当前的语言
        Locale l = (Locale)session.getAttribute("WW_TRANS_I18N_LOCALE");
        //切换语言
        if(l.getLanguage().equals("zh")){
            l = new Locale("en", "US");
        }else{
            l = new Locale("zh","CN");
        }
        //设置语言
        session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, l);
        //将语言保存到回话中
        session.setAttribute("WW_TRANS_I18N_LOCALE", l);
        return model;
    }

}
