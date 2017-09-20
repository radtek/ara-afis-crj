/**
 * @author: tree
 * @version: 1.0
 * date: 2017/9/20 16:57
 * @description:
 * own: Aratek
 */
package com.services.impl;

import com.dao.BusLogDao;
import com.model.BusLogInfo;
import com.param.ConfigParam;
import com.services.BusLogService;
import com.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("busLogService")
public class BusLogServiceImpl  implements BusLogService {

	@Autowired
	private BusLogDao busLogDao;

	@Override
	public void saveLog(BusLogInfo log) {
		busLogDao.save(log);
	}

	@Override
	public List<BusLogInfo> getObjListPage(PageVO page, int queryType, Object... values) {
		return busLogDao.getLogPage(page,queryType, values);
	}

	@Override
	public List<BusLogInfo> getObjList(Object... values) {
		return busLogDao.getLogPage(null, ConfigParam.QUERY_TYPE_SOME, values);
	}

	@Override
	public void truncateTable(String tableName) {
		busLogDao.truncateTable(tableName);
	}

	@Override
	public String getTimeArea() {
		return busLogDao.getTimeArea();
	}

	@Override
	public String getTableSize(String tablename) {
		return busLogDao.getTableSize(tablename);
	}

    @Override
    public List countIP(String startTime, String endTime) {
        return busLogDao.countIP(startTime, endTime);
    }

    @Override
    public List countActionTypeByIP(String ip, String startTime, String endTime) {
        return busLogDao.countActionTypeByIP(ip,startTime,endTime);
    }
}
