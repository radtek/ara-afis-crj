/*
 * 文件名：${EngineNodeDao}
 * 作者：${Tree}
 * 版本：
 * 时间：${2016.6.23}
 * 修改：
 * 描述：EngineNodeDao  dao层
 *
 *
 * 版权：亚略特
 */
package com.dao;


import com.model.EngineNode;

public interface EngineNodeDao extends BaseDao<EngineNode>,CommonDao<EngineNode> {

	/**
	 * 获取引擎节点信息
	 * @param masterId（主控ID）
	 * @param nodeId (节点ID)
	 * @return  引擎节点信息
	 */
	EngineNode getEngineNodeByMasterAndNode(String masterId, String nodeId);



}
