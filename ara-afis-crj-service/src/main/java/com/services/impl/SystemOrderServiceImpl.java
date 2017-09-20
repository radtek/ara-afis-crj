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

import com.dao.SystemOrderDao;
import com.exception.ServiceException;
import com.model.SystemOrder;
import com.param.ConfigParam;
import com.services.SystemOrderService;
import com.time.TimeUtil;
import com.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("systemOrderService")
public class SystemOrderServiceImpl implements SystemOrderService {

	@Autowired
	private SystemOrderDao systemOrderDao;

	@Override
	public void saveObj(SystemOrder obj, String operator, Object... values) throws ServiceException {
		//判断对象非空
		if(Optional.of(obj).isPresent()){
            //构造系统命令数据
			obj.setOrderSource(ConfigParam.SYSTEM_MODULE.MAN.getElementName());
			obj.setOrderLevel(ConfigParam.ORDER_LEVEL.URGENT.getElementName());
			obj.setActiveStatu(ConfigParam.ACTIVE_STATU.AVAILABLE.getElementName());
//            obj.setActiveStatu(ConfigParam.ACTIVE_STATU.DISABLED.getElementName());
			// TODO: 2017/5/31 需要具体处理流水号的获取方式
			obj.setSerialNum(1L);
			obj.setCreateOn(TimeUtil.getFormatDate());
			obj.setCreateBy(ConfigParam.SYSTEM_MODULE.MAN.getElementName());
			systemOrderDao.save(obj);
		}else{
			throw new ServiceException("系统命令对象为空!");
		}
	}

	@Override
	public void updateObj(SystemOrder obj, String operator, Object id, Object... values) throws ServiceException {

	}

	@Override
	public void delObj(Object id, Object... values) throws ServiceException {
		SystemOrder systemOrder = getObj(id);
		if(null != systemOrder){
			systemOrderDao.delete(systemOrder);
		}
	}

	@Override
	public List<SystemOrder> getObjListPage(PageVO page, int queryType, Object... values) {
		return systemOrderDao.getObjListPage(page,queryType,values);
	}

	@Override
	public List<SystemOrder> getObjList(Object... values) {
		return null;
	}

	@Override
	public SystemOrder getObj(Object id) {
		return systemOrderDao.findById((long)id);
	}

	@Override
	public SystemOrder getObjByObj(String param, Object value) {
		return null;
	}
}
