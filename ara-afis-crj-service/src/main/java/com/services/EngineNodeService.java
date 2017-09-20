/*
 * 文件名：${EngineNodeService}
 * 作者：${Tree}
 * 版本：
 * 时间：${2016.6.25}
 * 修改：
 * 描述：比对引擎节点  Service接口层
 *
 *
 * 版权：亚略特
 */
package com.services;


import com.model.EngineNode;

public interface EngineNodeService extends CommonService<EngineNode> {

	/**
	 * 获取引擎节点信息
	 * @param masterId（主控ID）
	 * @param nodeId (节点ID)
	 * @return  引擎节点信息
	 */
	EngineNode getEngineNodeByMasterAndNode(String masterId, String nodeId);

}
