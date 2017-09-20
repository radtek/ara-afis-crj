/*
 * 文件名：${LogServiceImpl}
 * 作者：${Tree}
 * 版本：
 * 时间：${2014.4.27}
 * 修改：
 * 描述：日志  Service实现层
 *
 *
 * 版权：亚略特
 */
package com.services.impl;

import com.dao.SystemOrderResultDao;
import com.exception.ServiceException;
import com.model.SystemOrderResult;
import com.param.ConfigParam;
import com.services.SystemOrderResultService;
import com.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("systemOrderResultService")
public class SystemOrderResultServiceImpl implements SystemOrderResultService {

	@Autowired
	private SystemOrderResultDao systemOrderResultDao;

	@Override
	public void saveObj(SystemOrderResult obj, String operator, Object... values) throws ServiceException {
	}

	@Override
	public void updateObj(SystemOrderResult obj, String operator, Object id, Object... values) throws ServiceException {

	}

	@Override
	public void delObj(Object id, Object... values) throws ServiceException {

	}

	@Override
	public List<SystemOrderResult> getObjListPage(PageVO page, int queryType, Object... values) {
		return systemOrderResultDao.getObjListPage(page,queryType,values);
	}

	@Override
	public List<SystemOrderResult> getObjList(Object... values) {
		return systemOrderResultDao.getObjListPage(null, ConfigParam.QUERY_TYPE_SOME,values);
	}

	@Override
	public SystemOrderResult getObj(Object id) {
		return null;
	}

	@Override
	public SystemOrderResult getObjByObj(String param, Object value) {
		return null;
	}
}
