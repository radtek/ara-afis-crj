/*
 * 文件名：${BusLogServiceImpl}
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

import com.dao.DataSynLogDao;
import com.model.DataSynLogInfo;
import com.param.ConfigParam;
import com.services.DataSynLogService;
import com.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dataSynService")
public class DataSynLogServiceImpl implements DataSynLogService {

	@Autowired
	private DataSynLogDao dataSynLogDao;

	@Override
	public List<DataSynLogInfo> getObjListPage(PageVO page, int queryType, Object... values) {
		return dataSynLogDao.getLogPage(page,queryType, values);
	}

	@Override
	public List<DataSynLogInfo> getObjList(Object... values) {
		return dataSynLogDao.getLogPage(null, ConfigParam.QUERY_TYPE_SOME, values);
	}
}
