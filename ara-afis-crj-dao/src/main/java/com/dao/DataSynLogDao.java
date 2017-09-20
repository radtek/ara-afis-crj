/*
 * 文件名：${LogDao}
 * 作者：${Tree}
 * 版本：
 * 时间：${2014.4.25}
 * 修改：
 * 描述：日志    dao层
 *
 *
 * 版权：亚略特
 */
package com.dao;

import com.model.DataSynLogInfo;
import com.vo.PageVO;

import java.util.List;

public interface DataSynLogDao extends BaseDao<DataSynLogInfo> {
	/**
	 * 分页获取日志
	 * @param page（分页对象）
	 * @param queryType (查询类型 1.全字段模糊查询  2. 精确查询)
	 * @param values（查询条件）
	 * @return 日志list
	 */
	List<DataSynLogInfo> getLogPage(PageVO page, int queryType, Object... values);
}
