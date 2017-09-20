/**
 * @author: yvanj
 * @version: 1.0
 * date: 2017/9/14 11:37
 * @description:
 * own: Aratek
 */
package com.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@SuppressWarnings("unused")
@Entity(name = "tas_task")
public class TasTaskEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//
	private String taskUuid;
	//
	private String taskId;
	//
	private String foreinRequestId;
	//
	private String taskComCode;
	//
	private String taskComment;
	//
	private String taskResult;
	//
	private String taskRetCode;
	//
	private String taskState;
	//
	private String taskCompleteDate;
	//
	private String taskReceiveFlag;
	//
	private String taskReceiveDate;
	//
	private String createDate;





    @Id
    @GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "org.hibernate.id.UUIDGenerator" )
	@Column(name = "TASK_UUID", unique = true, nullable = false, length = 20)
	public String getTaskUuid() {
		return taskUuid;
	}
    public void setTaskUuid(String taskUuid) {
        this.taskUuid = taskUuid;
    }

	@Column(name = "TASK_ID", length = 50)
	public String getTaskId() {
		return taskId;
	}
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

	@Column(name = "FOREIN_REQUEST_ID", length = 50)
	public String getForeinRequestId() {
		return foreinRequestId;
	}
    public void setForeinRequestId(String foreinRequestId) {
        this.foreinRequestId = foreinRequestId;
    }

	@Column(name = "TASK_COM_CODE", nullable = false, length = 4)
	public String getTaskComCode() {
		return taskComCode;
	}
    public void setTaskComCode(String taskComCode) {
        this.taskComCode = taskComCode;
    }

	@Column(name = "TASK_COMMENT", nullable = false)
	public String getTaskComment() {
		return taskComment;
	}
    public void setTaskComment(String taskComment) {
        this.taskComment = taskComment;
    }

	@Column(name = "TASK_RESULT")
	public String getTaskResult() {
		return taskResult;
	}
    public void setTaskResult(String taskResult) {
        this.taskResult = taskResult;
    }

	@Column(name = "TASK_RET_CODE", length = 4)
	public String getTaskRetCode() {
		return taskRetCode;
	}
    public void setTaskRetCode(String taskRetCode) {
        this.taskRetCode = taskRetCode;
    }

	@Column(name = "TASK_STATE", length = 1)
	public String getTaskState() {
		return taskState;
	}
    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

	@Column(name = "TASK_COMPLETE_DATE", length = 20)
	public String getTaskCompleteDate() {
		return taskCompleteDate;
	}
    public void setTaskCompleteDate(String taskCompleteDate) {
        this.taskCompleteDate = taskCompleteDate;
    }

	@Column(name = "TASK_RECEIVE_FLAG", nullable = false, length = 1)
	public String getTaskReceiveFlag() {
		return taskReceiveFlag;
	}
    public void setTaskReceiveFlag(String taskReceiveFlag) {
        this.taskReceiveFlag = taskReceiveFlag;
    }

	@Column(name = "TASK_RECEIVE_DATE", length = 20)
	public String getTaskReceiveDate() {
		return taskReceiveDate;
	}
    public void setTaskReceiveDate(String taskReceiveDate) {
        this.taskReceiveDate = taskReceiveDate;
    }

	@Column(name = "CREATE_DATE", nullable = false, length = 20)
	public String getCreateDate() {
		return createDate;
	}
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

}
