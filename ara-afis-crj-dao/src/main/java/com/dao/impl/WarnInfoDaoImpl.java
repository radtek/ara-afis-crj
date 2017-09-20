/*
 * 文件名：${WarnInfoDaoImpl}
 * 作者：${Tree}
 * 版本：
 * 时间：${2016.5.26}
 * 修改：
 * 描述：监控报警信息  Dao实现层
 *
 *
 * 版权：亚略特
 */
package com.dao.impl;

import com.dao.WarnInfoDao;
import com.model.WarnInfo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("warnInfoDao")
public class WarnInfoDaoImpl extends BaseDaoImpl<WarnInfo> implements WarnInfoDao {

	@Override
	public List<WarnInfo> getAvailableWarnData() {
		String hql="FROM WarnInfo where warnStatus = :warnStatus ORDER BY createOn ASC";
        Map<String, Object> paramMap = new HashMap<>();
        //处理状态为未处理，1：未处理，2：处理中，3：已处理
        paramMap.put("warnStatus", "1");
		return find(hql,paramMap);
	}

}
