/*
 * 文件名：${FpTemplate}
 * 作者：${Tree}
 * 版本：
 * 时间：${2017.6.16}
 * 修改：
 * 描述：指纹特征表 PO类
 *
 *
 * 版权：亚略特
 */
package com.model;


import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("unused")
@Entity
@Table(name = "TAS_FP_TEMPLATE")
public class FpTemplate implements Serializable {
    @Id
    @Column(name = "PERSON_ID")
	private String personId;            	//档号
    @Id
    @Column(name = "FP_TEMPLATE_NO")
    private int index;		               //指纹序号
    @Id
    @Column(name = "FP_DATASOURCE_CODE")
	private String library;                //指纹所在库别

    @Column(name="ACTIVE_STATU")
    private String activeStatu;            //指纹特征状态

    @Transient
    private String srcHaveFlag;            //源库中是否存在此记录

    @Transient
    private String desHaveFlag;            //目标库中是否存在此记录

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getLibrary() {
        return library;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    public String getActiveStatu() {
        return activeStatu;
    }

    public void setActiveStatu(String activeStatu) {
        this.activeStatu = activeStatu;
    }

    public String getSrcHaveFlag() {
        return srcHaveFlag;
    }

    public void setSrcHaveFlag(String srcHaveFlag) {
        this.srcHaveFlag = srcHaveFlag;
    }

    public String getDesHaveFlag() {
        return desHaveFlag;
    }

    public void setDesHaveFlag(String desHaveFlag) {
        this.desHaveFlag = desHaveFlag;
    }
}
