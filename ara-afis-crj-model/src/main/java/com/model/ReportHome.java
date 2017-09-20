/*
 * 文件名：${ReportHome}
 * 作者：${Tree}
 * 版本：
 * 时间：${2016.5.21}
 * 修改：
 * 描述：首页对象  PO类
 *
 *
 * 版权：亚略特
 */
package com.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@SuppressWarnings("unused")
@Entity
@Table(name = "TAS_REPORT_HOME")
public class ReportHome {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	private int id;
	private long fpNum;			//在库指纹数目
	private long userNum;   		//在库用户数目
	private long serverNum;			//在线的引擎数目
	private long busNum;    		//当天的业务数
	private int busFailPercent;		//当天的业务失败率
	private String fpAddData;		//当月指纹数据增长趋势数据
	private String busTypeData;		//当月业务类型分布数据
	private String serverStatuData;	//引擎状态数据
	private String taskStatuData;	//当月业务处理状态数据

	private long busFailNum;        //当天成功的业务数

	@Id
	@Column(name = "HOME_ID")
	@GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "FP_NUM")
	public long getFpNum() {
		return fpNum;
	}
	public void setFpNum(long fpNum) {
		this.fpNum = fpNum;
	}

	@Column(name = "USER_NUM")
	public long getUserNum() {
		return userNum;
	}
	public void setUserNum(long userNum) {
		this.userNum = userNum;
	}

	@Column(name = "SERVER_NUM")
	public long getServerNum() {
		return serverNum;
	}
	public void setServerNum(long serverNum) {
		this.serverNum = serverNum;
	}

	@Column(name = "BUS_NUM")
	public long getBusNum() {
		return busNum;
	}
	public void setBusNum(long busNum) {
		this.busNum = busNum;
	}

	@Column(name = "BUS_FAIL_NUM")
	public int getBusFailPercent() {
		return busFailPercent;
	}
	public void setBusFailPercent(int busFailPercent) {
		this.busFailPercent = busFailPercent;
	}

	@Column(name = "FP_ADD_DATA")
	public String getFpAddData() {
		return fpAddData;
	}
	public void setFpAddData(String fpAddData) {
		this.fpAddData = fpAddData;
	}

	@Column(name = "BUS_TYPE_DATA")
	public String getBusTypeData() {
		return busTypeData;
	}
	public void setBusTypeData(String busTypeData) {
		this.busTypeData = busTypeData;
	}

	@Column(name = "SERVER_STATU_DATA")
	public String getServerStatuData() {
		return serverStatuData;
	}
	public void setServerStatuData(String serverStatuData) {
		this.serverStatuData = serverStatuData;
	}

	@Column(name = "TASK_STATU_DATA")
	public String getTaskStatuData() {
		return taskStatuData;
	}
	public void setTaskStatuData(String taskStatuData) {
		this.taskStatuData = taskStatuData;
	}

	@Transient
	public long getBusFailNum() {
		return busFailNum;
	}
	public void setBusFailNum(long busFailNum) {
		this.busFailNum = busFailNum;
	}


}
