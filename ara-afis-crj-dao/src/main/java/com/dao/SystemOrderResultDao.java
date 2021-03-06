/*
 * 文件名：${SystemOrderResultDao}
 * 作者：${Tree}
 * 版本：
 * 时间：${2017.6.6}
 * 修改：
 * 描述：系统命令结果    dao层
 *
 *
 * 版权：亚略特
 */
package com.dao;


import com.model.SystemOrderResult;
import com.vo.PageVO;

import java.util.List;

public interface SystemOrderResultDao extends BaseDao<SystemOrderResult>,CommonDao<SystemOrderResult> {

	/**
	 * 分页获取系统命令列表
	 * @param page（分页对象）
	 * @param queryType (查询类型 1.全字段模糊查询  2. 精确查询)
	 * @param values（查询条件）
	 * @return 日志list
	 */
	List<SystemOrderResult> getObjListPage(PageVO page, int queryType, Object... values);
}
