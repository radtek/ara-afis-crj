/*
 * 文件名：${EngineService}
 * 作者：${Tree}
 * 版本：
 * 时间：${2016.5.25}
 * 修改：
 * 描述：引擎    Service接口层
 *
 *
 * 版权：亚略特
 */
package com.services;


import com.model.Engine;

import java.util.List;


public interface EngineService extends CommonService<Engine> {
	/**
	 * 获取所有页面对象
	 * @param
	 * @return 页面List
	 */
	List<Engine> getAllEngine();

	/**
	 * 获取比对引擎
	 * @param masterId（主控ID）
	 * @param nodeId (节点ID)
	 * @return  引擎节点信息
	 */
	Engine getEngineByMasterAndNode(String masterId, String nodeId);
}
