/*
 * 文件名：${EngineNodeDaoImpl}
 * 作者：${Tree}
 * 版本：
 * 时间：${2016.6.23}
 * 修改：
 * 描述：EngineNodeDaoImpl  dao实现层
 *
 *
 * 版权：亚略特
 */
package com.dao.impl;


import com.dao.EngineNodeDao;
import com.model.EngineNode;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Repository("engineNodeDao")
public class EngineNodeDaoImpl extends BaseDaoImpl<EngineNode> implements EngineNodeDao {

	@Override
	public boolean checkExist(String obj, Object id) {
		return false;
	}

	@Override
	public boolean checkExist(Object id, Object... values) {
		StringBuffer hql = new StringBuffer("from EngineNode where masterId = :masterId and nodeId = :nodeId ");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("masterId", values[0]);
        paramMap.put("nodeId", values[1]);
		List<EngineNode> list = find(hql.toString(), paramMap);
		if(list != null){
			Iterator<EngineNode> i = list.iterator();
			while(i.hasNext()){
				return true;
			}
		}
		return false;
	}

	@Override
	public EngineNode getEngineNodeByMasterAndNode(String masterId, String nodeId) {
		StringBuffer hql = new StringBuffer("from EngineNode where masterId = :masterId and nodeId = :nodeId ");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("masterId", masterId);
        paramMap.put("nodeId", nodeId);
		List<EngineNode> list = find(hql.toString(),paramMap);
		return list!=null&&list.size()>0?list.get(0):null;
	}
}
