/**
 * @author: tree
 * @version: 1.0
 * date: 2017/9/20 16:57
 * @description:
 * own: Aratek
 */
package com.services;

import com.model.BusLogInfo;
import com.vo.PageVO;

import java.util.List;

public interface BusLogService {
	/**
	 * 保存日志
	 * @param log（日志对象）
	 * @return
	 */
	void saveLog(BusLogInfo log);

	/**
	 * 分页获取日志对象列表
	 * @param page（分页对象）
	 * @param queryType (查询类型 1.全字段模糊查询  2. 精确查询)
	 * @param values（查询条件）
	 * @return 日志对象list
	 */
	List<BusLogInfo> getObjListPage(PageVO page, int queryType, Object... values);

	/**
	 * 获取对象列表
	 * @param values（查询条件）
	 * @return 对象list
	 */
	List<BusLogInfo> getObjList(Object... values);

	/**
	 * 清空表
	 * @param tableName（表名）
	 */
	void truncateTable(String tableName);

	/**
	 * 获取日志表中的时间区段
	 * @return 日志时间区段
	 */
	String getTimeArea();

	/**
	 * 获取日志表中的大小
	 * @return 日志表大小(单位M)
	 */
	String getTableSize(String tablename);

    /**
     * 查看每个IP的请求次数
     * @return
     */
    List countIP(String startTime,String endTime);

    /**
     * 查看IP的各个请求类型的次数
     * @return
     */
    List countActionTypeByIP(String ip,String startTime,String endTime);
}
