/**
 * @author: yvanj
 * @version: 1.0
 * date: 2017/9/14 11:35
 * @description:
 * own: Aratek
 */
package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@SuppressWarnings("unused")
@Entity(name = "tas_log_system")
public class TasLogSystemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//
	private String logSystemId;
	//
	private String type;
	//
	private String operate;
	//
	private String content;
	//
	private String createDate;
	//
	private String createBy;

    @Id
	@Column(name = "LOG_SYSTEM_ID", unique = true, nullable = false, length = 36)
	public String getLogSystemId() {
		return logSystemId;
	}
    public void setLogSystemId(String logSystemId) {
        this.logSystemId = logSystemId;
    }

	@Column(name = "TYPE", nullable = false, length = 15)
	public String getType() {
		return type;
	}
    public void setType(String type) {
        this.type = type;
    }

	@Column(name = "OPERATE", nullable = false, length = 20)
	public String getOperate() {
		return operate;
	}
    public void setOperate(String operate) {
        this.operate = operate;
    }

	@Column(name = "CONTENT", nullable = false, length = 500)
	public String getContent() {
		return content;
	}
    public void setContent(String content) {
        this.content = content;
    }

	@Column(name = "CREATE_DATE", nullable = false, length = 20)
	public String getCreateDate() {
		return createDate;
	}
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

	@Column(name = "CREATE_BY", nullable = false, length = 30)
	public String getCreateBy() {
		return createBy;
	}
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

}
