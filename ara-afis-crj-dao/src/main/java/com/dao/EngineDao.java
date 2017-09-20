/*
 * 文件名：${EngineDao}
 * 作者：${Tree}
 * 版本：
 * 时间：${2016.5.25}
 * 修改：
 * 描述：引擎  dao层
 *
 *
 * 版权：亚略特
 */
package com.dao;


import com.model.Engine;
import com.vo.PageVO;

import java.util.List;


public interface EngineDao extends BaseDao<Engine>,CommonDao<Engine> {
	Engine getObjByObj(String param, Object value);

	/**
	 * 分页获取比对引擎
	 * @param page（分页对象）
	 * @param values（查询条件）
	 * @return 比对引擎list
	 */
	List<Engine> getEnginePage(PageVO page, Object... values);

	/**
	 * 获取比对引擎
	 * @param masterId（主控ID）
	 * @param nodeId (节点ID)
	 * @return  引擎节点信息
	 */
	Engine getEngineByMasterAndNode(String masterId, String nodeId);
}
