package com.util;

import com.model.*;
import com.vo.PageVO;
import com.param.ConfigParam;

import java.util.List;

/**
 * 文件名：
 * 作者：tree
 * 时间：2017/5/16
 * 描述：
 * 版权：亚略特
 */
public class PageUtil {
    /**
     * 构造返异步后台数据查询
     * @param list 数据
     * @param page 分页对象
     * @param tdNum 分页所占td 格数
     * @return String
     */
    @SuppressWarnings("rawtypes")
    public static String drawTable(List list, PageVO page, int tdNum){
        StringBuffer sbTable = new StringBuffer();
        if(list != null && list.size() >  0 ){
            for(int i = 0; i < list.size(); i++){
                //构造表格数据
                sbTable.append("<tr>");
                if(list.get(i) instanceof Manager){
                    Manager man = (Manager)list.get(i);
                    String roleName = "";
                    if(null != man.getRole()){
                        roleName = man.getRole().getName();
                    }
                    sbTable.append("<td title='"+man.getAccount()+"'>"+man.getAccount()+"</td><td>"+roleName+"</td><td>"+man.getStatu()+"</td><td>"+man.getLastLogin()+"&nbsp</td><td>"+man.getCreateOn()+"</td><td><a id='button_modify'>修改</a>&nbsp;&nbsp;<a id='button_del'>删除</a><input type='hidden' value='"+man.getId()+"' /><input type='hidden' value='"+page.getPageNum()+"' /></td>");
                }else if(list.get(i) instanceof Code){
                    Code code = (Code)list.get(i);
                    sbTable.append("<td>"+code.getCode()+"</td><td>"+code.getTypeMean()+"</td><td>"+code.getStatu()+"</td><td >"+code.getDescribe()+"</td><td>"+code.getCreateOn()+"</td><td><a id='button_modify'>修改</a>&nbsp;&nbsp;<a id='button_del'>删除</a><input type='hidden' value='"+code.getId()+"' /><input type='hidden' value='"+page.getPageNum()+"' /></td>");
                }else if(list.get(i) instanceof Role){
                    Role role = (Role)list.get(i);
                    sbTable.append("<td>"+role.getName()+"</td><td>"+role.getDescribe()+"</td><td><a id='button_modify'>修改</a>&nbsp;&nbsp;<a id='button_del'>删除</a><input type='hidden' value='"+role.getId()+"' /><input type='hidden' value='"+page.getPageNum()+"' /></td>");
                }else if(list.get(i) instanceof SysLogInfo){
                    SysLogInfo log = (SysLogInfo)list.get(i);
                    //重置页面td数目
                    tdNum = 4;
                    sbTable.append("<td>"+log.getType()+"</td><td>"+log.getOperate()+"</td><td title='"+log.getContent()+"'>"+log.getContent()+"</td><td>"+log.getCreateOn()+"</td>");
                }
                sbTable.append("</tr>");
            }
            //构造表格分页
            sbTable.append("<tr id='pageTr'><td colspan='"+tdNum+"' style='border:0;'><div class='pagination'><div class='results'><span>["+page.getPageNum()+"/"+page.getMaxPage()+"] Page "+ ConfigParam.PAGE_COUNT+" Item   Total "+page.getTotalResult()+" records</span></div>");
            sbTable.append("<ul class='pager'><li><a href='javascript:void(0)'  onClick='getData(1)'>First</a></li><li><a href='javascript:void(0)' onClick='getData("+page.getPrePage()+")'>Previous</a></li><li><a href='javascript:void(0)'  onClick='getData("+page.getNextPage()+")'>Next</a></li><li><a href='javascript:void(0)'  onClick='getData("+page.getMaxPage()+")'>Last</a></li></ul></div></td></tr>");
        }else{
            //sbTable.append(MethodUtil.drawMessage(0,"notice","温馨提示:","没有相关查询数据!",tdNum));
        }
        return sbTable.toString();
    }

    /**
     * 构造返异步后台数据添加，修改
     * @param obj 新添加的对象
     * @return String
     */
    public static String drawTable(Object obj){
        StringBuffer sbTable = new StringBuffer();
        if(obj != null){
            //构造表格数据
            sbTable.append("<tr>");
            if(obj instanceof Manager){
                Manager man = (Manager)obj;
                String roleName = "";
                if(null != man.getRole() && !"-1".equals(man.getRole().getId())){
                    roleName = man.getRole().getName();
                }
                String statuMean = "";
                if("E".equals(man.getStatu())){
                    statuMean = "可用";
                }else{
                    statuMean = "停用";
                }
                sbTable.append("<td>"+man.getAccount()+"</td><td>"+roleName+"</td><td>"+statuMean+"&nbsp</td><td>"+man.getLastLogin()+"</td><td>"+man.getCreateOn()+"</td>");
            }else if(obj instanceof Code){
                Code code = (Code)obj;
                String statuMean = "";
                if("E".equals(code.getStatu())){
                    statuMean = "可用";
                }else{
                    statuMean = "停用";
                }
                sbTable.append("<td>"+code.getCodeMean()+"</td><td>"+code.getCode()+"</td><td>"+code.getTypeMean()+"</td><td>"+statuMean+"</td><td >"+code.getDescribe()+"</td><td>"+code.getCreateOn()+"</td>");
            }else if(obj instanceof TasLibraryEntity){
                TasLibraryEntity libraryEntity = (TasLibraryEntity) obj;
                String activeFlag = "";
                if("Y".equals(libraryEntity.getLibraryActivietyFlag())){
                    activeFlag = "可用";
                } else if("N".equals(libraryEntity.getLibraryActivietyFlag())){
                    activeFlag = "停用";
                } else if("D".equals(libraryEntity.getLibraryActivietyFlag())){
                    activeFlag = "删除";
                }

                sbTable.append("<td>" + libraryEntity.getLibraryId()+ "</td> <td>"
                        +libraryEntity.getVerifyLibraryGoal()+ "</td> <td>" +
                        libraryEntity.getIdentifyLibraryGoal()+ "</td> <td>" +
                        activeFlag + "</td> <td>" +
                        libraryEntity.getLibraryDesc() + "</td> <td>" +
                        libraryEntity.getModifyDate()+ "</td> <td>" +
                        libraryEntity.getCreateDate() + "</td>");
            }
//            else if(obj instanceof Person){
//                Person person = (Person)obj;
//                sbTable.append("<td>"+person.getFileId()+"</td><td>"+person.getEid()+"</td><td>"+person.getFamilyName()+"</td><td>"+person.getFirstName()+"</td><td >"+person.getBirthday()+"</td><td >"+person.getNationCode()+"</td><td>"+person.getCreateOn()+"</td>");
//            }
//            else if(obj instanceof EngineNode){
//                EngineNode engineNode = (EngineNode)obj;
//                String engineType ,engineModel;
//                if("FP".equals(engineNode.getEngineModel())){
//                    engineModel = "指纹";
//                }else if("FA".equals(engineNode.getEngineModel())){
//                    engineModel = "人脸";
//                }else if("FI".equals(engineNode.getEngineModel())){
//                    engineModel = "虹膜";
//                }else{
//                    engineModel = "停用";
//                }
//                if("0".equals(engineNode.getEngineType())){
//                    engineType = "主控节点";
//                    sbTable.append("<td>"+engineNode.getMasterId()+"</td><td></td><td>"+engineType+"</td><td>"+engineModel+"</td><td></td><td></td><td ></td><td ></td>");
//                }else{
//                    engineType = "引擎节点";
//                    sbTable.append("<td>"+engineNode.getMasterId()+"</td><td>"+engineNode.getNodeId()+"</td><td>"+engineType+"</td><td>"+engineModel+"</td><td>"+engineNode.getEnrollThread()+"</td><td>"+engineNode.getVerifyThread()+"</td><td >"+engineNode.getIdentifyThread()+"</td><td >"+engineNode.getDataZone()+"</td>");
//                }
//            }
            sbTable.append("</tr>");
        }
        return sbTable.toString();
    }

}
