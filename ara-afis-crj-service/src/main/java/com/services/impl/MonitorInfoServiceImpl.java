/*
 * 文件名：${MonitorInfoServiceImpl}
 * 作者：${Tree}
 * 版本：
 * 时间：2016.5.26
 * 修改：
 * 描述：监控信息  Service实现层
 *
 *
 * 版权：亚略特
 */
package com.services.impl;

import com.dao.MonitorInfoDao;
import com.model.MonitorInfo;
import com.services.MonitorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("monitorInfoService")
public class MonitorInfoServiceImpl  implements MonitorInfoService {

	@Autowired
	private MonitorInfoDao monitorInfoDao;


	@Override
	public List<MonitorInfo> getMonitorData(Object... values) {
		return monitorInfoDao.getMonitorData(values);
	}

    @Override
    public MonitorInfo getLastMonitorData(Object... values) {
        return monitorInfoDao.getLastMonitorData(values);
    }

    @Override
	public String getMonitorFingerData(Object... values) {
		// TODO Auto-generated method stub
		StringBuffer result = new StringBuffer();
		String[] arrayTemp = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
		List<Object[]> listTemp = monitorInfoDao.getMonitorFingerData(values);
		if(null != listTemp && listTemp.size() > 0){
			for(String strTemp : arrayTemp){
				boolean flagTemp = false;
				for(Object[] object : listTemp){
					if(strTemp.equals(object[0])){
						result.append(object[1]+",");
						flagTemp = true;
						break;
					}
				}
				if(!flagTemp){
					result.append(0+",");
				}
			}

		}
		if(result.length() > 0){
			return result.substring(0, result.length()-1);
		}else{
			return "";
		}
	}
}
