/*
 * 文件名：${CodeDao}
 * 作者：${Tree}
 * 版本：
 * 时间：${2014.4.18}
 * 修改：
 * 描述：字典    dao层
 *
 *
 * 版权：亚略特
 */
package com.dao;


import com.model.SystemOrder;
import com.vo.PageVO;

import java.util.List;

public interface SystemOrderDao extends BaseDao<SystemOrder>,CommonDao<SystemOrder> {

	/**
	 * 分页获取系统命令列表
	 * @param page（分页对象）
	 * @param queryType (查询类型 1.全字段模糊查询  2. 精确查询)
	 * @param values（查询条件）
	 * @return 日志list
	 */
	List<SystemOrder> getObjListPage(PageVO page, int queryType, Object... values);
}
