package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.exception.CommonUtilException;
import com.exception.ServiceException;
import com.jce.KeyTools;
import com.model.Manager;
import com.model.Role;
import com.vo.OperateVO;
import com.services.ManagerService;
import com.services.RoleService;
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
 * 文件名：ManagerController
 * 作者：tree
 * 时间：2016/4/21
 * 描述：管理员管理 相关业务逻辑类
 * 版权：亚略特
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/managerInfo")
public class ManagerController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ManagerController.class);

    @Autowired
    private ManagerService managerService;
    @Autowired
    private RoleService roleService;

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
        logger.debug("查询管理员列表");
        //查询所有管理员
        //查询所有基本数据字典类型
        model.addAttribute("managerListJson", JSON.toJSONString(managerService.getObjList(false), SerializerFeature.WriteNullStringAsEmpty));
        //跳转前台
        return "/system/manager/queryManager";
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
        List<Role> roleList = roleService.getObjList();
        //判定是否指定了数据字典
        if(StringUtils.isNotEmpty(anyId)){
            //根据Manager标示ID获取Manager信息
            Manager manager = managerService.getObj(anyId);
            model.addAttribute("manager", manager);
        }
        model.addAttribute("roleList", roleList);
        //跳转到添加数据字典界面
        return "/system/manager/addManager";
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
    public Map<String,Object> addOption(Manager manager, String anyOption, String anyId, HttpSession session){
        initController();
        //打包系统日志
        packageLogInfo("MANAGER", OperateVO.ADD_OPERATE);
        try{
            //判定数据完整性
            if(null != manager){
                if("on".equals(manager.getStatu())){
                    manager.setStatu("E");
                }else{
                    manager.setStatu("D");
                }
                //判别要执行的是添加还是修改操作
                if(anyOption.contains("add")){
                    //保存管理员
                    managerService.saveObj(manager, getSessionManager(session));
                    msg = "管理员 "+manager.getAccount()+" 添加成功";
                }else{
                    logInfo.setOperate(OperateVO.MODEIFY_OPERATE);
                    //修改管理员
                    managerService.updateObj(manager, getSessionManager(session),anyId);
                    msg = "管理员 "+manager.getAccount()+" 修改成功";
                }
                //构造前台数据模型，返回前台显示
                Role roleTemp = roleService.getObj(manager.getRole().getId());
                manager.setRole(roleTemp);
                //构造前台数据模型
                anyStatus = 1;
                tr = PageUtil.drawTable(Manager.convert(manager));
            }
        } catch (CommonUtilException e) {
            msg = "添加失败,失败原因: " + e.getMessage();
        }catch (ServiceException e){
            msg = "添加失败,失败原因: " + e.getMessage();
        }
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
        packageLogInfo("MANAGER", OperateVO.DELETE_OPERATE);
        String managerName = "",resultMsg = "";
        //初始化参数
        //判定是否指定了引擎
        if(StringUtils.isNotEmpty(anyId)) {
            //通过数据字典对象主键获取数据字典对象
            Manager manager = managerService.getObj(anyId);
            if (null != manager) {
                managerName = manager.getAccount();
                //删除指定的数据字典对象
                managerService.delObj(anyId);
                anyStatus = 1;
                resultMsg = "删除成功";
            }
        }
        msg = "管理员 "+managerName+" " + resultMsg;
        logInfo.setContent(msg);
        logInfo.setCreateBy(getSessionManager(session));
        responseMap.put("anyStatus", anyStatus);
        responseMap.put("msg", msg);
        return responseMap;
    }

    /**
     * @author 作者：tree
     * @Date 时间：2016/7/15 18:06
     * 功能说明：初始化修改密码页面的对应方法
     * 处理流程：
     * @param
     * @return
     */
    @RequestMapping(value = "/toChangeOption")
    public String toChangeOption(Model model, Manager manager, String anyId, HttpSession session){
        //判定当前修改的执行人员为普通管理员还是超级管理员
        if(StringUtils.isEmpty(anyId)){
            manager = managerService.getObjByObj("account", getSessionManager(session));
        }else{
            //根据Manager标示ID获取Manager信息
            manager = managerService.getObj(anyId);
        }
        //构造前台数据模型，返回前台显示
        try {
            manager = Manager.convert(manager);
        } catch (CommonUtilException e) {
            e.printStackTrace();
        }
        model.addAttribute("manager", manager);
        //跳转到添加数据字典界面
        return "/system/manager/changePasswd";
    }

    /**
     * @author 作者：tree
     * @Date 时间：2016/7/15 18:06
     * 功能说明：修改管理员密码的对应方法
     * 处理流程：
     * @param
     * @return
     */
    @RequestMapping(value = "/changeOption", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> changeOption(Manager manager, String anyId, String newPasswd, HttpSession session) throws ServiceException {
        initController();
        //打包系统日志
        packageLogInfo("MANAGER", OperateVO.MODEIFY_OPERATE);

        //判定数据完整性
        if(StringUtils.isNotEmpty(anyId)) {
            Manager managerTemp = managerService.getObj(anyId);
            if (null != managerTemp) {
                //判定密码是否匹配
//                if (KeyTools.GetPasswordMD5(manager.getPassword()).equals(managerTemp.getPassword())) {
                    manager.setPassword(KeyTools.GetPasswordMD5(newPasswd));
                    //修改密码
                    managerService.updateObj(manager, getSessionManager(session), anyId, "");
                    msg = "修改管理员 " + manager.getAccount() + " 密码成功";
                    //构造前台数据模型
                    anyStatus = 1;
//                } else {
//                    msg = "修改密码失败，旧密码不正确";
//                }
            }
        }
        logInfo.setContent(msg);
        logInfo.setCreateBy(getSessionManager(session));
        responseMap.put("anyStatus", anyStatus);
        responseMap.put("msg", msg);
        return responseMap;
    }
}
