/*
 * 文件名：${WarnInfo}
 * 作者：${Tree}
 * 版本：
 * 时间：${2016.5.25}
 * 修改：
 * 描述：告警信息  PO类
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
@Table(name = "TAS_MON_WARN")
public class WarnInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "WARN_ID")
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "native")
	private long id;					            //标示ID
	@Column(name = "WARN_CODE")
	private String code;			            //引擎集群编码
	@Column(name = "WARN_MESSAGE")
	private String message;			            //引擎集群编码
	@Column(name = "WARN_LEVEL")
	private String level;			            //引擎集群编码
	@Column(name = "WARN_SERVER_NAME")
	private String serverName;			        //引擎集群编码
	@Column(name = "WARN_SERVER_IP")
	private String serverIP;			        //引擎集群编码
	@Column(name = "WARN_SERVER_MAC")
	private String serverMac;			        //引擎集群编码
	@Column(name = "NOTICE_WAY")
	private String noticeWay;			        //引擎集群编码
	@Column(name = "NOTICE_STATU")
	private String noticeStatus;			    //引擎集群编码
	@Column(name = "WARN_STATU")
	private String warnStatus;			        //引擎集群编码
    @Column(name = "CREATE_DATE")
    private String createOn;	                //创建时间
    @Column(name = "CREATE_BY")
    private String createBy;	                //创建者
    @Column(name = "MODIFY_DATE")
    private String modifiedOn;	                //修改时间
    @Column(name = "MODIFY_BY")
    private String modifiedBy;	                //修改者

	public static WarnInfo convert(WarnInfo engine) throws Exception {
		CommonStringUtil.nullConvertNullString(engine);
		return engine;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public String getServerMac() {
        return serverMac;
    }

    public void setServerMac(String serverMac) {
        this.serverMac = serverMac;
    }

    public String getNoticeWay() {
        return noticeWay;
    }

    public void setNoticeWay(String noticeWay) {
        this.noticeWay = noticeWay;
    }

    public String getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(String noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    public String getWarnStatus() {
        return warnStatus;
    }

    public void setWarnStatus(String warnStatus) {
        this.warnStatus = warnStatus;
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

    public String getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
