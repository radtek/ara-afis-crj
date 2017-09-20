/*
 * 文件名：${FpTemplateDao}
 * 作者：${Tree}
 * 版本：
 * 时间：${2017.6.16}
 * 修改：
 * 描述：指纹特征   dao层
 *
 *
 * 版权：亚略特
 */
package com.dao;

import com.model.FpTemplate;
import com.vo.PageVO;

import java.util.List;

public interface FpTemplateDao extends BaseDao<FpTemplate> {
	/**
	 * 分页获取指纹特征
	 * @param page（分页对象）
	 * @param queryType (查询类型 1.全字段模糊查询  2. 精确查询)
	 * @param values（查询条件）
	 * @return 指纹特征list
	 */
	List<FpTemplate> getFpTemplatePage(PageVO page, int queryType, Object... values);
}
