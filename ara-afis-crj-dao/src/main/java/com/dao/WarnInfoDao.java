/*
 * 文件名：${WarnInfoDao}
 * 作者：${Tree}
 * 版本：
 * 时间：${2016.5.26}
 * 修改：
 * 描述：监控报警信息 dao层
 *
 *
 * 版权：亚略特
 */

package com.dao;

import com.model.WarnInfo;

import java.util.List;

public interface WarnInfoDao extends BaseDao<WarnInfo> {
	/**
	 * 获取尚未处理的监控报警信息
	 * @return 尚未处理的监控报警信息列表
	 */
	List<WarnInfo> getAvailableWarnData();
}
