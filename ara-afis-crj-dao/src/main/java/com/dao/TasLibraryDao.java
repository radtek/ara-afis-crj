package com.dao;


import com.model.Manager;
import com.model.TasLibraryEntity;
import com.vo.PageVO;
import com.vo.TasLibraryVo;

import java.util.List;

/**
 * 指纹库别表
 *
 * @author NavyWang
 * @email wanghj@aratek.com.cn
 * @date 2017/8/4  11:22
 */
public interface TasLibraryDao extends BaseDao<TasLibraryEntity> , CommonDao<Manager> {
    /**
     * 分页获取日志
     * @param page（分页对象）
     * @param queryType (查询类型 1.全字段模糊查询  2. 精确查询)
     * @param values（查询条件）
     * @return 日志list
     */
    List<TasLibraryEntity> getTasLibraryEntityPage(PageVO page, int queryType, Object... values);

    public List<TasLibraryVo> getTasLibraryEntitys(PageVO page, Object... values);
}
