/*
 * 文件名：${WarnInfoServiceImpl}
 * 作者：${Tree}
 * 版本：
 * 时间：2016.5.26
 * 修改：
 * 描述：监控报警信息  Service实现层
 *
 *
 * 版权：亚略特
 */
package com.services.impl;

import com.dao.WarnInfoDao;
import com.exception.ServiceException;
import com.model.WarnInfo;
import com.services.WarnInfoService;
import com.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("warnInfoService")
public class WarnInfoServiceImpl implements WarnInfoService {

	@Autowired
	private WarnInfoDao warnInfoDao;

    @Override
    public List<WarnInfo> getAvailableWarnData() {
        return warnInfoDao.getAvailableWarnData();
    }

    @Override
    public void saveObj(Object obj, String operator, Object... values) throws ServiceException {

    }

    @Override
    public void updateObj(Object obj, String operator, Object id, Object... values) throws ServiceException {

    }

    @Override
    public void delObj(Object id, Object... values) throws ServiceException {
        WarnInfo objTemp = warnInfoDao.findById((long)id);
        if(Optional.of(objTemp).isPresent()){
            warnInfoDao.delete(objTemp);
        }else {
            throw new ServiceException("该条命令已被处理");
        }
    }

    @Override
    public List getObjListPage(PageVO page, int queryType, Object... values) {
        return null;
    }

    @Override
    public List getObjList(Object... values) {
        return null;
    }

    @Override
    public Object getObj(Object id) {
        return null;
    }

    @Override
    public Object getObjByObj(String param, Object value) {
        return null;
    }
}
