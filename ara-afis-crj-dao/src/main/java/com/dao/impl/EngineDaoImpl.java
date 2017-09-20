/*
 * 文件名：${EngineDaoImpl}
 * 作者：${Tree}
 * 版本：
 * 时间：${2016.5.25}
 * 修改：
 * 描述：引擎  Dao实现层
 *
 *
 * 版权：亚略特
 */
package com.dao.impl;

import com.dao.EngineDao;
import com.model.Engine;
import com.vo.PageVO;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("engineDao")
public class EngineDaoImpl extends BaseDaoImpl<Engine> implements EngineDao {

	@Override
	public Engine getObjByObj(String param, Object value) {
		StringBuffer hql = new StringBuffer("from Engine where "+param+" = ?"+param+" ");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(param, value);
		List<Engine> list = find(hql.toString(),paramMap);
		return list!=null&&list.size()>0?list.get(0):null;
	}

	@Override
	public List<Engine> getEnginePage(PageVO page, Object... values) {
		StringBuffer hql = new StringBuffer("from Engine where 1 = 1 ");
		List<Engine> enginelist = new ArrayList<Engine>();
		//若为条件查询，构造查询数据
        Map<String, Object> paramMap = new HashMap<>();
		if (values != null && values.length > 0) {
			//第一个参数为引擎类型
			if(values[0] instanceof String){
				hql.append(" and engineType = :engineType ");
                paramMap.put("engineType", values[0]);
			}
		}

		//设置排序规则   降序
		hql.append(" order by engineCode");

		//模型参数构造
		List<Engine> engineListTemp = null;
		if(page != null){
			engineListTemp = findPage(hql.toString(),page,paramMap);
		}else{
			engineListTemp = find(hql.toString(),paramMap);
		}
		if(null != engineListTemp && engineListTemp.size() > 0){
			for(Engine engineTemp : engineListTemp){
				try {
					enginelist.add(Engine.convert(engineTemp));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return enginelist;
	}

	@Override
	public Engine getEngineByMasterAndNode(String masterId, String nodeId) {
		StringBuffer hql = new StringBuffer("from Engine where clusterCode = :clusterCode and engineCode = :engineCode ");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("clusterCode", masterId);
        paramMap.put("engineCode", nodeId);
		List<Engine> list = find(hql.toString(),paramMap);
		return list!=null&&list.size()>0?list.get(0):null;
	}

	@Override
	public boolean checkExist(String obj, Object id) {
		return false;
	}

	@Override
	public boolean checkExist(Object id, Object... values) {
		StringBuffer hql = new StringBuffer("from Engine where clusterCode = :clusterCode and engineCode = :engineCode ");

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("clusterCode", values[0]);
        paramMap.put("engineCode", values[1]);
		List<Engine> list = find(hql.toString(), paramMap);
		if(list != null){
			Iterator<Engine> i = list.iterator();
			while(i.hasNext()){
				return true;
			}
		}
		return false;
	}
}
