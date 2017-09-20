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
@Table(name = "TAS_LOG_DATA_SYN")
public class DataSynLogInfo implements Serializable {
	@Id
	@Column(name = "LOG_DATA_SYN_ID",length = 20, nullable = false)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "native")
	private int id;            		//标示ID
	@Column(name = "SYN_LIBRARY")
	private String sourceLibrary;		//同步的库别
	@Column(name = "PERSON_ID")
	private String personId; 			//档号
	@Column(name = "FP_VERSION_CODE")
	private String versionCode; 		//版本号
	@Column(name = "FP_TEMPLATE_NO")
	private String templateNo;			//模板序号
	@Column(name = "SERIAL_NUM")
	private long serialNum;			    //同步流水号
	@Column(name = "OPERATE_TYPE")
	private String operateType;			//同步类型
	@Column(name = "IMAGE_MD5")
	private String imageSign;			//图像MD5签名
	@Column(name = "TEMPLATE")
	private String template;			//特征数据
	@Column(name = "RESULT_FLAG")
	private String resultFlag;			//数据同步结果标志
	@Column(name = "RESULT_CODE")
	private String resultCode;			//同步结果返回码
	@Column(name = "CONTENT")
	private String content;				//同步结果内容
	@Column(name = "RETRY_NUM")
	private int retryNum;			//重试次数
	@Column(name = "CREATE_DATE")
	private String createOn;   			//同步时间
	@Column(name = "MODIFY_DATE")
	private String modeifyOn;			//更新时间

	public static DataSynLogInfo convert(DataSynLogInfo dataSynLogInfo) throws CommonUtilException {
		CommonStringUtil.nullConvertNullString(dataSynLogInfo);
		parseObjectForWeb(dataSynLogInfo);
		return dataSynLogInfo;
	}

    public static void parseObjectForWeb(DataSynLogInfo dataSynLogInfo){
        switch (dataSynLogInfo.operateType) {
            case "I":
                dataSynLogInfo.setOperateType("新增");
				break;
			case "U":
                dataSynLogInfo.setOperateType("修改");
				break;
			case "D":
                dataSynLogInfo.setOperateType("删除");
				break;
        }
		switch (dataSynLogInfo.resultFlag){
			case "Y":
                dataSynLogInfo.setResultFlag("成功");
				break;
            case "N":
                dataSynLogInfo.setResultFlag("失败");
                break;
			default:
                dataSynLogInfo.setResultFlag("执行中");
				break;
		}
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSourceLibrary() {
		return sourceLibrary;
	}

	public void setSourceLibrary(String sourceLibrary) {
		this.sourceLibrary = sourceLibrary;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getTemplateNo() {
		return templateNo;
	}

	public void setTemplateNo(String templateNo) {
		this.templateNo = templateNo;
	}

	public long getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(long serialNum) {
		this.serialNum = serialNum;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getImageSign() {
		return imageSign;
	}

	public void setImageSign(String imageSign) {
		this.imageSign = imageSign;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getResultFlag() {
		return resultFlag;
	}

	public void setResultFlag(String resultFlag) {
		this.resultFlag = resultFlag;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getRetryNum() {
		return retryNum;
	}

	public void setRetryNum(int retryNum) {
		this.retryNum = retryNum;
	}

	public String getCreateOn() {
		return createOn;
	}

	public void setCreateOn(String createOn) {
		this.createOn = createOn;
	}

	public String getModeifyOn() {
		return modeifyOn;
	}

	public void setModeifyOn(String modeifyOn) {
		this.modeifyOn = modeifyOn;
	}

}
