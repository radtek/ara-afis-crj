/*
 * 文件名：${MonitorInfoDaoImpl}
 * 作者：${Tree}
 * 版本：
 * 时间：${2016.5.26}
 * 修改：
 * 描述：监控信息  Dao实现层
 *
 *
 * 版权：亚略特
 */
package com.dao.impl;

import com.dao.MonitorInfoDao;
import com.model.MonitorInfo;
import com.param.ConfigParam;
import com.time.TimeUtil;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("monitorInfoDao")
public class MonitorInfoDaoImpl extends BaseDaoImpl<MonitorInfo> implements MonitorInfoDao {


	@Override
	public List<Object[]> getMonitorFingerData(Object... values) {
        String sql="SELECT HOUR(CREATE_DATE)  keyTime, sum(TOTAL_VALUE) from TAS_MON_SERVICE_STATISTIC  where ENGINE_CLUSTER_CODE = '"+values[0]+"' and ENGINE_SERVER_CODE = '"+values[1]+"'  and  SERVICE_KEY = '"+values[2]+"' and CREATE_DATE >= DATE_FORMAT(NOW(),'%Y-%m-%d') group by HOUR(CREATE_DATE) ORDER BY keyTime";
        SQLQuery d = getCurrentSession().createSQLQuery(sql);
        return d.list();
	}

	@Override
	public List<MonitorInfo> getMonitorData(Object... values) {
		//获取当前时间前一个小时的时间
		String dataTime = TimeUtil.getMinusDateTime(ConfigParam.TIME_TYPE.HOUR.getElementName(), 1);
		String hql="FROM MonitorInfo where clusterCode = :clusterCode and engineCode = :engineCode  and mainKey = :mainKey  and createOn > '"+dataTime+"' ORDER BY createOn ASC";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("clusterCode", values[0]);
        paramMap.put("engineCode", values[1]);
        paramMap.put("mainKey", values[2]);
		return find(hql,paramMap);
	}

    @Override
    public MonitorInfo getLastMonitorData(Object... values) {
        String dataTime = TimeUtil.getMinusDateTime(ConfigParam.TIME_TYPE.HOUR.getElementName(), 1);
        String hql="FROM MonitorInfo where clusterCode = :clusterCode and engineCode = :engineCode  and mainKey = :mainKey and createOn > '"+dataTime+"'  ORDER BY createOn DESC";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("clusterCode", values[0]);
        paramMap.put("engineCode", values[1]);
        paramMap.put("mainKey", values[2]);
        return findTopOne(hql,paramMap);
    }
}
