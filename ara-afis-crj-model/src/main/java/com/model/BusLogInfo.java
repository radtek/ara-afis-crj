/*
 * 文件名：${BusLogInfo}
 * 作者：${Tree}
 * 版本：
 * 时间：${2014.4.15}
 * 修改：
 * 描述：业务日志对象  PO类
 *
 *
 * 版权：亚略特
 */
package com.model;


import com.exception.CommonUtilException;
import com.util.CommonStringUtil;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("unused")
@Entity
@Table(name = "TAS_V_LOG_BUSINESS")
public class BusLogInfo implements Serializable {
	@Id
	@Column(name = "LOG_BUS_ID",length = 36, nullable = false)
	@GeneratedValue(generator = "systemUUID" )   //指定生成器名称
	@GenericGenerator(name = "systemUUID", strategy = "org.hibernate.id.UUIDGenerator" )  //生成器名称，uuid生成类
	private String id;            	//标示ID
	@Column(name = "ACTION_TYPE")
	private String actionType;		//业务码
	@Column(name = "REQ_DATA")
	private String requestData;     //请求数据
	@Column(name = "RES_DATA")
	private String responseData; 	//响应数据
	@Column(name = "COST")
	private String costTime;		//处理耗时  毫秒
	@Column(name = "RESULT_CODE")
	private String resultCode;		//业务返回码
	@Column(name = "RESULT_MSG")
	private String resultMSG;		//业务返回信息
	@Column(name = "RETRY_NUM")
	private String retryNum;		//重发次数
	@Column(name = "CLIENT_IP")
	private String clientIp;		//请求方IP
	@Column(name = "CREATE_DATE")
	private String createOn;   		//日志记录时间

    @Transient
    private String resultFlag;		//结果标志
    @Transient
    private String actionTypeDes;	//业务说明

	public static BusLogInfo convert(BusLogInfo busLogInfo) throws CommonUtilException {
		CommonStringUtil.nullConvertNullString(busLogInfo);
		parseObjectForWeb(busLogInfo);
		return busLogInfo;
	}

	public static void parseObjectForWeb(BusLogInfo busLogInfo){
		switch (busLogInfo.actionType){
			case "102201":
				busLogInfo.setActionTypeDes("系统参数设置");
				break;
			case "102202":
				busLogInfo.setActionTypeDes("系统心跳获取");
				break;
			case "102203":
				busLogInfo.setActionTypeDes("运行状态获取");
				break;
			case "102204":
				busLogInfo.setActionTypeDes("任务状态获取");
				break;
			case "102205":
				busLogInfo.setActionTypeDes("监控信息获取");
				break;
			case "102206":
				busLogInfo.setActionTypeDes("增量数据同步");
				break;
			case "201301":
				busLogInfo.setActionTypeDes("1对1比对");
				break;
            case "201302":
                busLogInfo.setActionTypeDes("1对1认证");
                break;
            case "201303":
                busLogInfo.setActionTypeDes("图片数据比对");
                break;
            case "201311":
                busLogInfo.setActionTypeDes("1对多比对");
                break;
            case "201321":
                busLogInfo.setActionTypeDes("X对多比对");
                break;
            case "201322":
                busLogInfo.setActionTypeDes("X指查重");
                break;
            case "201201":
                busLogInfo.setActionTypeDes("图片质量评价");
                break;
            case "201202":
                busLogInfo.setActionTypeDes("图片建模");
                break;
            case "201203":
                busLogInfo.setActionTypeDes("图片格式转换");
                break;
            case "201111":
                busLogInfo.setActionTypeDes("用户信息导出");
                break;
            case "201221":
                busLogInfo.setActionTypeDes("模板管理");
                break;
            case "201401":
                busLogInfo.setActionTypeDes("库别管理");
                break;
            case "201402":
                busLogInfo.setActionTypeDes("指纹库别查询");
                break;
			default:
				busLogInfo.setActionTypeDes("未知指纹业务");
				break;
		}
        switch (busLogInfo.resultCode) {
            case "0":
                busLogInfo.setResultFlag("成功");
                break;
            default:
                busLogInfo.setResultFlag("失败");
                break;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getCostTime() {
        return costTime;
    }

    public void setCostTime(String costTime) {
        this.costTime = costTime;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMSG() {
        return resultMSG;
    }

    public void setResultMSG(String resultMSG) {
        this.resultMSG = resultMSG;
    }

    public String getRetryNum() {
        return retryNum;
    }

    public void setRetryNum(String retryNum) {
        this.retryNum = retryNum;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getCreateOn() {
        return createOn;
    }

    public void setCreateOn(String createOn) {
        this.createOn = createOn;
    }

    public String getResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(String resultFlag) {
        this.resultFlag = resultFlag;
    }

    public String getActionTypeDes() {
        return actionTypeDes;
    }

    public void setActionTypeDes(String actionTypeDes) {
        this.actionTypeDes = actionTypeDes;
    }
}
