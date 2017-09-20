/*
 * 文件名：${Engine}
 * 作者：${Tree}
 * 版本：
 * 时间：${2016.5.25}
 * 修改：
 * 描述：比对引擎  PO类
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
@Table(name = "TAS_MON_ENV_STATISTIC")
public class Engine implements Serializable {
	@Id
	@Column(name = "MON_ENV_ID")
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "native")
	private int id;						//标示ID
	@Column(name = "ENGINE_CLUSTER_CODE")
	private String clusterCode;			//引擎集群编码
	@Column(name = "ENGINE_SERVER_CODE")
	private String engineCode;			//引擎节点编码
	@Column(name = "ENGINE_SERVER_BIOMETRICS_MODEL")
	private String biometricsModel; 	//引擎生物识别模态
	@Column(name = "ENGINE_SERVER_TYPE")
	private String engineType; 			//引擎类别  0 ： 主控服务器 1 ： 比对引擎
	@Column(name = "ENGINE_SERVER_IP")
	private String ip; 					//引擎IP地址
	@Column(name = "ENGINE_SERVER_MAC")
	private String mac; 				//引擎MAC地址
	@Column(name = "ENGINE_SERVER_CPU")
	private String cpuNum; 				//引擎CPU主频核数
	@Column(name = "ENGINE_SERVER_PORT")
	private String enginePort; 			//引擎端口
	@Column(name = "ENGINE_WORKSTATION_STATUS")
	private String workStationStatus; 	//引擎运行状态
	@Column(name = "ENGINE_NETWORK_STATUS")
	private String networkStatus; 		//引擎网络状态
	@Column(name = "ENGINE_RUNNING_TIME")
	private int runningTime;			//引擎运行时长
	@Column(name = "ENGINE_LODA_FINGER_NUMBER")
	private long loadFingerNum;         //引擎加载指纹数量
	@Column(name = "CREATE_DATE")
	private String createOn;			//引擎创建时间
	@Column(name = "MODIFY_DATE")
	private String modifiedOn;			//引擎修改时间

	public static Engine convert(Engine engine) throws CommonUtilException {
		CommonStringUtil.nullConvertNullString(engine);
		return engine;
	}

	public static Engine init(Engine engine) throws CommonUtilException {
		CommonStringUtil.nullConvertInitString(engine);
		return engine;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getClusterCode() {
		return clusterCode;
	}


	public void setClusterCode(String clusterCode) {
		this.clusterCode = clusterCode;
	}

	public String getEngineCode() {
		return engineCode;
	}


	public void setEngineCode(String engineCode) {
		this.engineCode = engineCode;
	}

	public String getBiometricsModel() {
		return biometricsModel;
	}


	public void setBiometricsModel(String biometricsModel) {
		this.biometricsModel = biometricsModel;
	}

	public String getEngineType() {
		return engineType;
	}


	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}

	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMac() {
		return mac;
	}


	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getCpuNum() {
		return cpuNum;
	}


	public void setCpuNum(String cpuNum) {
		this.cpuNum = cpuNum;
	}

	public String getEnginePort() {
		return enginePort;
	}


	public void setEnginePort(String enginePort) {
		this.enginePort = enginePort;
	}

	public String getWorkStationStatus() {
		return workStationStatus;
	}


	public void setWorkStationStatus(String workStationStatus) {
		this.workStationStatus = workStationStatus;
	}

	public String getNetworkStatus() {
		return networkStatus;
	}


	public void setNetworkStatus(String networkStatus) {
		this.networkStatus = networkStatus;
	}

	public int getRunningTime() {
		return runningTime;
	}


	public void setRunningTime(int runningTime) {
		this.runningTime = runningTime;
	}

	public long getLoadFingerNum() {
		return loadFingerNum;
	}


	public void setLoadFingerNum(long loadFingerNum) {
		this.loadFingerNum = loadFingerNum;
	}

	public String getCreateOn() {
		return createOn;
	}


	public void setCreateOn(String createOn) {
		this.createOn = createOn;
	}

	public String getModifiedOn() {
		return modifiedOn;
	}


	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}


}
