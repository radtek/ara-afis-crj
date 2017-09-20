/*
 * 文件名：${EngineNode}
 * 作者：${Tree}
 * 版本：
 * 时间：${2016.5.25}
 * 修改：
 * 描述：比对引擎节点  PO类
 *
 *
 * 版权：亚略特
 */
package com.model;

import com.exception.CommonUtilException;
import com.util.CommonStringUtil;
import com.vo.FpNodeMapKey;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("unused")
@Entity
@Table(name = "TAS_FP_NODE")
@IdClass(FpNodeMapKey.class)
public class EngineNode implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="master_id")
	private String masterId;			//主控ID
	@Id
	@Column(name="node_id")
	private String nodeId;				//节点ID
	@Column(name="enr_thread")
	private int enrollThread = 1;			//注册线程
	@Column(name="ver_thread")
	private int verifyThread = 4; 			//1:1 线程
	@Column(name="ide_thread")
	private int identifyThread = 4; 		//1:N 线程
	@Column(name="data_zone")
	private String dataZone; 			//载入的数据块列表，可以设多个数据块，用逗号分隔
	@Column(name="online_stat")
	private String onlineStatus = "OFF"; 		//在线状态
	@Column(name="enr_c_hour")
	private long enrollHour; 			//最近一小时注册数（已过时）
	@Column(name="enr_c_day")
	private long enrollDay; 			//最近一天注册数（已过时）
	@Column(name="ver_c_hour")
	private long verifyHour; 			//最近一小时比对数（已过时）
	@Column(name="ver_c_day")
	private long verifyDay; 			//最近一天比对数（已过时）
	@Column(name="ide_c_hour")
	private long identifyHour;			//最近一小时认证数（已过时）
	@Column(name="ide_c_day")
	private long identifyDay;           //最近一天认证数（已过时）
	@Transient
	private String oldMasterId;          //修改的时候使用
	@Transient
	private String oldNodeId;			//修改的时候使用
	@Transient
	private String engineType;
	@Transient
	private String engineModel;

	public static EngineNode convert(EngineNode engineNode) throws CommonUtilException {
		CommonStringUtil.nullConvertNullString(engineNode);
		return engineNode;
	}


	public String getMasterId() {
		return masterId;
	}

	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public int getEnrollThread() {
		return enrollThread;
	}

	public void setEnrollThread(int enrollThread) {
		this.enrollThread = enrollThread;
	}

	public int getVerifyThread() {
		return verifyThread;
	}

	public void setVerifyThread(int verifyThread) {
		this.verifyThread = verifyThread;
	}

	public int getIdentifyThread() {
		return identifyThread;
	}

	public void setIdentifyThread(int identifyThread) {
		this.identifyThread = identifyThread;
	}

	public String getDataZone() {
		return dataZone;
	}

	public void setDataZone(String dataZone) {
		this.dataZone = dataZone;
	}

	public String getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public long getEnrollHour() {
		return enrollHour;
	}

	public void setEnrollHour(long enrollHour) {
		this.enrollHour = enrollHour;
	}

	public long getEnrollDay() {
		return enrollDay;
	}

	public void setEnrollDay(long enrollDay) {
		this.enrollDay = enrollDay;
	}

	public long getVerifyHour() {
		return verifyHour;
	}

	public void setVerifyHour(long verifyHour) {
		this.verifyHour = verifyHour;
	}

	public long getVerifyDay() {
		return verifyDay;
	}

	public void setVerifyDay(long verifyDay) {
		this.verifyDay = verifyDay;
	}

	public long getIdentifyHour() {
		return identifyHour;
	}

	public void setIdentifyHour(long identifyHour) {
		this.identifyHour = identifyHour;
	}

	public long getIdentifyDay() {
		return identifyDay;
	}

	public void setIdentifyDay(long identifyDay) {
		this.identifyDay = identifyDay;
	}

	public String getOldMasterId() {
		return oldMasterId;
	}

	public void setOldMasterId(String oldMasterId) {
		this.oldMasterId = oldMasterId;
	}

	public String getOldNodeId() {
		return oldNodeId;
	}

	public void setOldNodeId(String oldNodeId) {
		this.oldNodeId = oldNodeId;
	}

	public String getEngineType() {
		return engineType;
	}

	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}

	public String getEngineModel() {
		return engineModel;
	}

	public void setEngineModel(String engineModel) {
		this.engineModel = engineModel;
	}
}
