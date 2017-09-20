/*
 * 文件名：${SystemOrder}
 * 作者：${Tree}
 * 版本：
 * 时间：${2017.4.15}
 * 修改：
 * 描述：系统命令对象  PO类
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
@Table(name = "TAS_SYS_ORDER")
public class SystemOrder implements Serializable {
	@Id
	@Column(name = "ORDER_ID",length = 20, nullable = false)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "native")
	private long id;            			//标示ID
	@Column(name = "ORDER_CODE")
	private String orderCode;			//系统命令编码
	@Column(name = "ORDER_SRC")
	private String orderSource; 		//命令来源
	@Column(name = "ORDER_LEVEL")
	private String orderLevel; 			//命令等级
	@Column(name = "REQ_IP")
	private String requestIp;			//请求IP地址
	@Column(name = "SERIAL_NUMBER")
	private long serialNum;				//变更流水号
    @Column(name = "ORDER_CONTENT")
    private String orderParam;			//命令参数信息
	@Column(name = "ACTIVE_STATU")
	private String activeStatu;			//活动状态
	@Column(name = "CREATE_DATE")
	private String createOn;   			//创建时间
	@Column(name = "CREATE_BY")
	private String createBy;			//创建者
	@Transient
	private String result;				//系统命令执行结果
	@Transient
	private String content;				//系统命令执行结果扩展信息
	@Transient
	private String spendTime;			//系统命令执行耗时
    @Transient
    private String orderCodeDes;        //系统命令说明

	public static SystemOrder convert(SystemOrder systemOrder) throws CommonUtilException {
		CommonStringUtil.nullConvertNullString(systemOrder);
		parseObjectForWeb(systemOrder);
		return systemOrder;
	}

	public static void parseObjectForWeb(SystemOrder systemOrder){
		switch (systemOrder.getOrderCode()){
			case "1001":
				systemOrder.setOrderCodeDes("重启比对模块");
				break;
			case "1002":
				systemOrder.setOrderCodeDes("重启同步模块");
				break;
			case "1003":
				systemOrder.setOrderCodeDes("重启支撑模块");
				break;
			case "1004":
				systemOrder.setOrderCodeDes("重启MQ模块");
				break;
			case "1005":
				systemOrder.setOrderCodeDes("重启主控模块");
				break;
			case "1006":
				systemOrder.setOrderCodeDes("重启建模模块");
				break;
			case "1007":
				systemOrder.setOrderCodeDes("重启全部模块");
				break;
			case "1008":
				systemOrder.setOrderCodeDes("关闭全部模块");
				break;
			case "2001":
				systemOrder.setOrderCodeDes("强制全量同步");
				break;
			case "2002":
				systemOrder.setOrderCodeDes("强制增量同步");
				break;
            case "2003":
                systemOrder.setOrderCodeDes("单条数据重新建模");
                break;
			default:
				systemOrder.setOrderCodeDes("未知系统命令");
				break;
		}
        switch (systemOrder.activeStatu){
            case "E":
                systemOrder.setActiveStatu("可用");
                break;
            default:
                systemOrder.setActiveStatu("停用");
                break;
        }
        switch (systemOrder.orderLevel){
            case "1":
                systemOrder.setOrderLevel("紧急");
                break;
            case "2":
                systemOrder.setOrderLevel("中等");
                break;
            default:
                systemOrder.setOrderLevel("延后");
                break;
        }
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public String getOrderLevel() {
		return orderLevel;
	}

	public void setOrderLevel(String orderLevel) {
		this.orderLevel = orderLevel;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public long getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(long serialNum) {
		this.serialNum = serialNum;
	}

	public String getActiveStatu() {
		return activeStatu;
	}

	public void setActiveStatu(String activeStatu) {
		this.activeStatu = activeStatu;
	}

	public String getCreateOn() {
		return createOn;
	}

	public void setCreateOn(String createOn) {
		this.createOn = createOn;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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

	public String getSpendTime() {
		return spendTime;
	}

	public void setSpendTime(String spendTime) {
		this.spendTime = spendTime;
	}

    public String getOrderParam() {
        return orderParam;
    }

    public void setOrderParam(String orderParam) {
        this.orderParam = orderParam;
    }

    public String getOrderCodeDes() {
        return orderCodeDes;
    }

    public void setOrderCodeDes(String orderCodeDes) {
        this.orderCodeDes = orderCodeDes;
    }
}
