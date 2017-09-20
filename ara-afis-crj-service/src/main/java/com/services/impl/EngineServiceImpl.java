/*
 * 文件名：${EngineServiceImpl}
 * 作者：${Tree}
 * 版本：
 * 时间：${2016.5.25}
 * 修改：
 * 描述：引擎  Service实现层
 *
 *
 * 版权：亚略特
 */
package com.services.impl;

import com.dao.EngineDao;
import com.exception.ServiceException;
import com.model.Engine;
import com.services.EngineService;
import com.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("engineService")
public class EngineServiceImpl implements EngineService {

	@Autowired
	private EngineDao engineDao;

	@Override
	public List<Engine> getAllEngine() {
		return engineDao.getEnginePage(null);
	}

	@Override
	public Engine getEngineByMasterAndNode(String masterId, String nodeId) {
		return engineDao.getEngineByMasterAndNode(masterId,nodeId);
	}

	@Override
	public void saveObj(Engine obj, String operator, Object... values)
			throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateObj(Engine obj, String operator, Object id, Object... values)
			throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delObj(Object id, Object... values) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Engine> getObjListPage(PageVO page, int queryType, Object... values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Engine> getObjList(Object... values) {
		// TODO Auto-generated method stub
		return engineDao.getEnginePage(null,values);
	}

	@Override
	public Engine getObj(Object id) {
		// TODO Auto-generated method stub
		return engineDao.findById((int)id);
	}

	@Override
	public Engine getObjByObj(String param, Object value) {
		return engineDao.getObjByObj(param, value);
	}


}
