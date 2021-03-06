/**
 * @author: tree
 * @version: 1.0
 * date: 2017/9/14 11:38
 * @description:
 * own: Aratek
 */
package com.vo;

import java.io.Serializable;

public class SystemOrderResultKey implements Serializable {
    // 主键属性
    private long id;
    // 主键属性
    private String modelID;

    /**
     * 无参数的public构造方法，必须要有
     */
    public SystemOrderResultKey(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModelID() {
        return modelID;
    }

    public void setModelID(String modelID) {
        this.modelID = modelID;
    }

    /**
     * 覆盖hashCode方法，必须要有
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + (int)(id == 0 ? 0 : id);
        result = PRIME * result + (modelID == null ? 0 : modelID.hashCode());
        return result;
    }

    /**
     * 覆盖equals方法，必须要有
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null) return false;
        SystemOrderResultKey objKey = (SystemOrderResultKey)obj;
        if(id == objKey.id && modelID.equalsIgnoreCase(objKey.modelID)) {
            return true;
        }
        return false;
    }
}
