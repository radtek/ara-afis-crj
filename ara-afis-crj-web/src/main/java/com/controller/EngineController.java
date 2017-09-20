package com.controller;

import com.exception.CommonUtilException;
import com.exception.ServiceException;
import com.model.Engine;
import com.model.EngineNode;
import com.model.MonitorInfo;
import com.vo.OperateVO;
import com.param.ConfigParam;
import com.services.EngineNodeService;
import com.services.EngineService;
import com.services.MonitorInfoService;
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
 * 文件名：EngineController
 * 作者：tree
 * 时间：2016/4/21
 * 描述：比对引擎 相关业务逻辑类
 * 版权：亚略特
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/engine")
public class EngineController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(EngineController.class);

    //注入服务
    @Autowired
    private EngineService engineService;
    @Autowired
    private MonitorInfoService monitorInfoService;
    @Autowired
    private EngineNodeService engineNodeService;

    /**
     * @author 作者：tree
     * @Date 时间：2016/7/15 18:04
     * 功能说明：公共查询模块的对应方法
     * 处理流程：
     * @param
     * @return
     */
    @RequestMapping(value = "/toGetList")
    public String toGetList(Model model, int anyId){
        logger.debug("查询引擎列表");
        //获取所有引擎信息
        List<Engine> engineList = engineService.getObjList(ConfigParam.ENGINE_TYPE.NODE.getElementName());   //引擎列表
        Engine engine = null;
        if (null != engineList && engineList.size() > 0) {
            //判定是否指定了引擎
            if (0 != anyId) {
                engine = engineService.getObj(anyId);
            } else {
                //未指定就取第一个
                engine = engineList.get(0);
                anyId = engine.getId();
            }
            //获取引擎节点信息
            EngineNode engineNode = engineNodeService.getEngineNodeByMasterAndNode(engine.getClusterCode(), engine.getEngineCode());
            //对应1：1 比对的数据队列
//            String verifyNumArray = monitorInfoService.getMonitorFingerData(engine.getClusterCode(), engine.getEngineCode(), "1");
            //对应1：N 比对的数据队列
            String identifyNumArray = monitorInfoService.getMonitorFingerData(engine.getClusterCode(), engine.getEngineCode(), "2");
            model.addAttribute("engineList", engineList);
            model.addAttribute("anyId", anyId);
            model.addAttribute("engine", engine);
            model.addAttribute("engineNode", engineNode);
//            model.addAttribute("verifyNumArray", verifyNumArray);
            model.addAttribute("identifyNumArray", identifyNumArray);
        }
        //跳转前台
        return "/engine/queryEngine";
    }

    /**
     * @author 作者：tree
     * @Date 时间：2016/7/15 18:04
     * 功能说明：异步获取引擎的CPU,内存，硬盘使用数据
     * 处理流程：
     * @param
     * @return
     */
    @RequestMapping(value = "/queryChartData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> queryChartData(int anyId){
        initController();
        //判定是否指定了引擎
        if (0 != anyId) {
            //通过引擎对象主键获取引擎对象
            Engine engine = engineService.getObj(anyId);
            //拿cpu使用率数据  key值为4
            MonitorInfo cpuInfo = monitorInfoService.getLastMonitorData(engine.getClusterCode(), engine.getEngineCode(), "4");
            //拿内存使用率数据  key值为5
            MonitorInfo memoryInfo = monitorInfoService.getLastMonitorData(engine.getClusterCode(), engine.getEngineCode(), "5");
            //拿硬盘使用率数据  key值为6
            MonitorInfo diskInfo = monitorInfoService.getLastMonitorData(engine.getClusterCode(), engine.getEngineCode(), "6");
            responseMap.put("cpuInfo", cpuInfo);
            responseMap.put("memoryInfo", memoryInfo);
            responseMap.put("diskInfo", diskInfo);
            responseMap.put("anyStatus", 1);
        }
        return responseMap;
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
    public String toAddOption(Model model, String anyOption, int anyId){
        EngineNode engineNode = null;
        //判别要执行的是添加还是修改操作
        if (anyOption.contains("add")) {
            //初始化引擎节点对象，主要是为了获取几个线程的初始值
            engineNode = new EngineNode();
        } else {
            //判定是否指定了引擎
            //通过引擎对象主键获取引擎对象
            Engine engine = engineService.getObj(anyId);
            //如果引擎对象存在
            if (null != engine) {
                //通过引擎节点对象主键获取引擎几点对象
                engineNode = engineNodeService.getEngineNodeByMasterAndNode(engine.getClusterCode(), engine.getEngineCode());
                //如果是主控节点，则初始化节点对象
                if (null == engineNode) {
                    engineNode = new EngineNode();
                }
            }
            model.addAttribute("engine", engine);
        }
        model.addAttribute("engineNode", engineNode);
        return "/engine/addEngine";
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
    public Map<String,Object> addOption(Engine engine, EngineNode engineNode, String anyOption, HttpSession session){
        initController();
        //打包系统日志
        packageLogInfo("ENGINE", OperateVO.ADD_OPERATE);
        //初始化参数
        String masterId = "",nodeId = "",resultMsg= "";
        try{
            //判定数据完整性
            if(null != engineNode && null != engine){
                masterId = engineNode.getMasterId();
                nodeId = engineNode.getNodeId();
                //判别要执行的是添加还是修改操作
                if(anyOption.contains("add")){
                    nodeId = engineNode.getNodeId();
                    //保存比对引擎对象
                    engineNodeService.saveObj(engineNode, getSessionManager(session),engine);
                    resultMsg = "添加成功";
                }else{
                    logInfo.setOperate(OperateVO.MODEIFY_OPERATE);
                    //修改字典
                    engineNodeService.updateObj(engineNode, getSessionManager(session),0,engine);
                    resultMsg = "修改成功";
                }
                //设置数据
                engineNode.setEngineType(engine.getEngineType());
                engineNode.setEngineModel(engine.getBiometricsModel());
                //构造前台数据模型
                anyStatus = 1;
                tr = PageUtil.drawTable(EngineNode.convert(engineNode));
            }
        } catch (CommonUtilException e) {
            resultMsg = "添加失败,失败原因: " + e.getMessage();
        }catch (ServiceException e){
            msg = "添加失败,失败原因: " + e.getMessage();
        }
        if(StringUtils.isNotEmpty(nodeId) && !masterId.equals(nodeId)){
            msg = "主控节点 "+masterId+" 下的比对引擎: "+nodeId + " " + resultMsg ;
        }else{
            msg = "主控节点 "+masterId+ " " + resultMsg ;
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
    public Map<String,Object> delOption(int anyId, HttpSession session) throws ServiceException {
        initController();
        //打包系统日志
        packageLogInfo("ENGINE", OperateVO.DELETE_OPERATE);
        //初始化参数
        String masterId = "",nodeId = "",resultMsg = "";
        //判定是否指定了引擎
        if (0 != anyId) {
            //通过引擎对象主键获取引擎对象
            Engine engine = engineService.getObj(anyId);
            if (null != engine) {
                masterId = engine.getClusterCode();
                nodeId = engine.getEngineCode();
                //删除指定的引擎对象，0为定值，无意义
                engineNodeService.delObj(0, masterId, nodeId);
                anyStatus = 1;
                resultMsg = "删除成功";
            }
        }
        if(StringUtils.isNotEmpty(nodeId) && !masterId.equals(nodeId)){
            msg = "主控节点 "+masterId+" 下的比对引擎: "+nodeId + " " + resultMsg ;
        }else{
            msg = "主控节点 "+masterId+ " " + resultMsg ;
        }
        logInfo.setContent(msg);
        logInfo.setCreateBy(getSessionManager(session));
        responseMap.put("anyStatus", anyStatus);
        responseMap.put("msg", msg);
        return responseMap;
    }
}
