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
import com.vo.SystemOrderResultKey;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("unused")
@Entity
@Table(name = "TAS_SYS_ORDER_RESULT")
@IdClass(SystemOrderResultKey.class)
public class SystemOrderResult implements Serializable {
    @Id
    @Column(name = "ORDER_ID")
	private long id;            			//标示ID
    @Id
	@Column(name = "MODEL_ID")
	private String modelID;					//模块ID
	@Column(name = "SPEND_TIME")
	private double spendTime; 				//命令执行耗时
	@Column(name = "RESULT")
	private String result; 					//命令执行结果
	@Column(name = "CONTENT")
	private String content;					//结果扩展信息
	@Column(name = "CREATE_DATE")
	private String createOn;   				//完成时间

	public static SystemOrderResult convert(SystemOrderResult systemOrderResult) throws CommonUtilException {
		CommonStringUtil.nullConvertNullString(systemOrderResult);
		parseObjectForWeb(systemOrderResult);
		return systemOrderResult;
	}

	public static void parseObjectForWeb(SystemOrderResult systemOrderResult){
		switch (systemOrderResult.getResult()){
			case "Y":
				systemOrderResult.setResult("成功");
				break;
			case "N":
				systemOrderResult.setResult("失败");
				break;
			default:
				systemOrderResult.setResult("执行中");
				break;
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getModelID() {
		return modelID;
	}

	public void setModelID(String modelID) {
		this.modelID = modelID;
	}

	public double getSpendTime() {
		return spendTime;
	}

	public void setSpendTime(double spendTime) {
		this.spendTime = spendTime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateOn() {
		return createOn;
	}

	public void setCreateOn(String createOn) {
		this.createOn = createOn;
	}
}
