/*
 * 文件名：${MonitorInfoDao}
 * 作者：${Tree}
 * 版本：
 * 时间：${2016.5.26}
 * 修改：
 * 描述：监控信息 dao层
 *
 *
 * 版权：亚略特
 */

package com.dao;

import com.model.MonitorInfo;

import java.util.List;

public interface MonitorInfoDao extends BaseDao<MonitorInfo> {
	/**
	 * 获取监控信息表中的时间区段
	 * @return 日志时间区段
	 */
	List<MonitorInfo> getMonitorData(Object... values);

    /**
     * 获取最新的监控数据
     * @return MonitorInfo 最新的监控数据
     */
    MonitorInfo getLastMonitorData(Object... values);

	List<Object[]> getMonitorFingerData(Object... values);
}
