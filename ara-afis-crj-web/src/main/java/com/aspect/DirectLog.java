/*
 * 文件名：${DirectLog}
 * 作者：${Tree}
 * 版本：
 * 时间：${2014.4.27}
 * 修改：
 * 描述：日志接口层
 *
 *
 * 版权：亚略特
 */
package com.aspect;

import com.model.SysLogInfo;

/**
 * 可实现的管理日志写入接口
 */
public interface DirectLog {
	/**
	 * 实现该方法已返回的日志实体
	 * @return
	 */
	public SysLogInfo toGetLogInfo();
}
