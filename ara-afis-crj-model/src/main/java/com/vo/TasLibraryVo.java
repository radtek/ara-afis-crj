/**
 * @author: NavyWang
 * @version: 1.0
 * date: 2017/9/14 11:38
 * @description:
 * own: Aratek
 */
package com.vo;

import com.exception.CommonUtilException;
import com.model.TasLibraryEntity;
import com.util.CommonStringUtil;

public class TasLibraryVo extends TasLibraryEntity {
    private String id;
    private String libraryActivietyFlagStr;
    private String modifyStateStr;

    public String getLibraryActivietyFlagStr() {
        return libraryActivietyFlagStr;
    }

    public void setLibraryActivietyFlagStr(String libraryActivietyFlagStr) {
        this.libraryActivietyFlagStr = libraryActivietyFlagStr;
    }

    public String getModifyStateStr() {
        return modifyStateStr;
    }

    public void setModifyStateStr(String modifyStateStr) {
        this.modifyStateStr = modifyStateStr;
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public static TasLibraryEntity convert(TasLibraryEntity tasLibraryEntity) throws CommonUtilException {
        CommonStringUtil.nullConvertNullString(tasLibraryEntity);
//        parseObjectForWeb(tasLibraryEntity);
        return tasLibraryEntity;
    }

    public static TasLibraryEntity convert(TasLibraryVo TasLibraryVo) throws CommonUtilException {
        CommonStringUtil.nullConvertNullString(TasLibraryVo);
        parseObjectForWeb(TasLibraryVo);
        return TasLibraryVo;
    }

    public static void parseObjectForWeb(TasLibraryVo tasLibraryVo) {
        tasLibraryVo.setId(tasLibraryVo.getLibraryId());

        if("Y".equals(tasLibraryVo.getLibraryActivietyFlag())){
            tasLibraryVo.setLibraryActivietyFlagStr("可用");
        } else if("N".equals(tasLibraryVo.getLibraryActivietyFlag())){
            tasLibraryVo.setLibraryActivietyFlagStr("停用");
        } else if("D".equals(tasLibraryVo.getLibraryActivietyFlag())){
            tasLibraryVo.setLibraryActivietyFlagStr("删除");
        }

        if("N".equals(tasLibraryVo.getModifyState())){
            tasLibraryVo.setModifyStateStr("可用");
        } else if("U".equals(tasLibraryVo.getModifyState())){
            tasLibraryVo.setModifyStateStr("停用");
        } else if("D".equals(tasLibraryVo.getModifyState())){
            tasLibraryVo.setModifyStateStr("删除");
        }
    }
}
