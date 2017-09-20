/*
 * 文件名：${MonitorInfo}
 * 作者：${Tree}
 * 版本：
 * 时间：${2016.5.25}
 * 修改：
 * 描述：比对引擎监控信息  PO类
 *
 *
 * 版权：亚略特
 */
package com.model;

import com.util.CommonStringUtil;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("unused")
@Entity
@Table(name = "TAS_MON_SERVICE_STATISTIC")
public class MonitorInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "MON_SERVICE_ID")
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "native")
	private int id;					//标示ID
	@Column(name = "ENGINE_CLUSTER_CODE")
	private String clusterCode;			//引擎集群编码
	@Column(name = "ENGINE_SERVER_CODE")
	private String engineCode;			//引擎节点编码
	@Column(name = "SERVICE_KEY")
	private String mainKey; 			//业务名称
	@Column(name = "TOTAL_VALUE")
	private String totalValue; 			//业务统计值
	@Column(name = "CREATE_DATE")
	private String createOn;			//记录生成时间



	public static MonitorInfo convert(MonitorInfo engine) throws Exception {
		CommonStringUtil.nullConvertNullString(engine);
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

	public String getCreateOn() {
		return createOn;
	}


	public void setCreateOn(String createOn) {
		this.createOn = createOn;
	}

	public String getMainKey() {
		return mainKey;
	}


	public void setMainKey(String mainKey) {
		this.mainKey = mainKey;
	}

	public String getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}

}
