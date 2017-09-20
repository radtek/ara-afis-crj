package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.exception.ServiceException;
import com.model.Page;
import com.model.Role;
import com.vo.OperateVO;
import com.services.PageService;
import com.services.RoleService;
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
 * 文件名：RoleController
 * 作者：tree
 * 时间：2016/4/21
 * 描述：系统角色 相关业务逻辑类
 * 版权：亚略特
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/role")
public class RoleController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;
    @Autowired
    private PageService pageService;

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
        logger.debug("查询系统角色列表");
        //查询所有基本数据字典类型
        model.addAttribute("roleListJson", JSON.toJSONString(roleService.getObjList(), SerializerFeature.WriteNullStringAsEmpty));
        //跳转前台
        return "/system/role/queryRole";
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
        //判别要执行的是添加还是修改操作
        //判定是否指定了角色
        if (StringUtils.isNotEmpty(anyId)) {
            //根据role标示ID获取role信息
            Role role = roleService.getObj(anyId);
            //校验页面权限，对页面进行筛选
            List<Page> pagelistTemp = pageService.getAllEnablePages();
            for (Page pageTemp : pagelistTemp) {
                if (role.getPurview().contains(pageTemp.getCode())) {
                    pageTemp.setChecked(true);
                }
            }
            msg = JSONArray.toJSONString(pagelistTemp, SerializerFeature.WriteNullStringAsEmpty);
            //如果角色对象存在
            if (null != role) {
                model.addAttribute("role", role);
            }
        } else {
            //获取所有有效页面模块
            msg = JSONArray.toJSONString(pageService.getAllEnablePages(), SerializerFeature.WriteNullStringAsEmpty);
        }
        model.addAttribute("msg", msg);
        //跳转到添加系统角色界面
        return "/system/role/addRole";
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
    public Map<String,Object> addOption(Role role, String anyOption, String anyId, HttpSession session) throws ServiceException {
        initController();
        //打包系统日志
        packageLogInfo("ROLE", OperateVO.ADD_OPERATE);
        //初始化参数
        String roleName = "",resultMsg = "";
        //判定数据完整性
        if (null != role) {
            roleName = role.getName();
            //判别要执行的是添加还是修改操作
            if (anyOption.contains("add")) {
                logInfo.setOperate(OperateVO.ADD_OPERATE);
                //保存角色
                try {
                    roleService.saveObj(role, getSessionManager(session));
                    resultMsg = "添加成功";
                }catch (ServiceException e){
                    resultMsg = "添加失败";
                }
            } else {
                logInfo.setOperate(OperateVO.MODEIFY_OPERATE);
                //修改角色
                roleService.updateObj(role, getSessionManager(session), anyId);
                resultMsg = "修改成功";
            }
            //构造前台数据模型
            anyStatus = 1;
        }
        msg = "角色 : "+roleName + " " + resultMsg ;
        logInfo.setContent(msg);
        logInfo.setCreateBy(getSessionManager(session));
        responseMap.put("anyStatus", anyStatus);
        responseMap.put("msg", msg);
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
        packageLogInfo("ROLE", OperateVO.DELETE_OPERATE);
        //初始化参数
        String roleName = "",resultMsg = "";
        //判定是否指定了引擎
        if(StringUtils.isNotEmpty(anyId)) {
            //通过角色主键获取角色对象
            Role role = roleService.getObj(anyId);
            if(null != role){
                roleName = role.getName();
                roleService.delObj(anyId);
                anyStatus = 1;
                resultMsg = "删除成功";
            }
        }
        msg = "角色 : "+roleName + " " + resultMsg ;
        logInfo.setContent(msg);
        logInfo.setCreateBy(getSessionManager(session));
        responseMap.put("anyStatus", anyStatus);
        responseMap.put("msg", msg);
        return responseMap;
    }
}
