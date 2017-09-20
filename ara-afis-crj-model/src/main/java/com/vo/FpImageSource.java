/*
 * 文件名：${FpImageSource}
 * 作者：${Tree}
 * 版本：
 * 时间：${2017.6.16}
 * 修改：
 * 描述：同步的指纹图像数据来源 PO类
 *
 *
 * 版权：亚略特
 */
package com.vo;


import java.io.Serializable;

@SuppressWarnings("unused")
public class FpImageSource implements Serializable {
	private String personId;            	//档号
    private int index;		               //指纹序号
	private String library;                //指纹所在库别

    public FpImageSource(String personId, int index, String library) {
        this.personId = personId;
        this.index = index;
        this.library = library;
    }

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
}
