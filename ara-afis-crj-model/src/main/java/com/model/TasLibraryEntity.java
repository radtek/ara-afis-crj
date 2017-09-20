/**
 * @author: NavyWang
 * @version: 1.0
 * date: 2017/9/14 11:32
 * @description:
 * own: Aratek
 */
package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;


@SuppressWarnings("unused")
@Entity
@Table(name = "TAS_LIBRARY")
public class TasLibraryEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	@Id
	@Column(name = "LIBRARY_ID")
	private String libraryId;

	@Column(name = "VERIFY_LIBRARY_GOAL")
	private BigDecimal verifyLibraryGoal;

	@Column(name = "IDENTIFY_LIBRARY_GOAL")
	private BigDecimal identifyLibraryGoal;

	@Column(name = "LIBRARY_ACTIVIETY_FLAG", nullable = false, length = 1)
	private String libraryActivietyFlag;

	@Column(name = "MODIFY_STATE", length = 1)
	private String modifyState;

	@Column(name = "LIBRARY_DESC", length = 250)
	private String libraryDesc;

	@Column(name = "CREATE_DATE", nullable = false, length = 20)
	private String createDate;

	@Column(name = "MODIFY_DATE", length = 20)
	private String modifyDate;



	public String getLibraryId() {
		return libraryId;
	}
    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }


	public BigDecimal getVerifyLibraryGoal() {
		return verifyLibraryGoal;
	}
    public void setVerifyLibraryGoal(BigDecimal verifyLibraryGoal) {
        this.verifyLibraryGoal = verifyLibraryGoal;
    }


	public BigDecimal getIdentifyLibraryGoal() {
		return identifyLibraryGoal;
	}
    public void setIdentifyLibraryGoal(BigDecimal identifyLibraryGoal) {
        this.identifyLibraryGoal = identifyLibraryGoal;
    }


	public String getLibraryActivietyFlag() {
		return libraryActivietyFlag;
	}
    public void setLibraryActivietyFlag(String libraryActivietyFlag) {
        this.libraryActivietyFlag = libraryActivietyFlag;
    }


	public String getModifyState() {
		return modifyState;
	}
    public void setModifyState(String modifyState) {
        this.modifyState = modifyState;
    }


	public String getLibraryDesc() {
		return libraryDesc;
	}
    public void setLibraryDesc(String libraryDesc) {
        this.libraryDesc = libraryDesc;
    }


	public String getCreateDate() {
		return createDate;
	}
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }


	public String getModifyDate() {
		return modifyDate;
	}
    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

}
