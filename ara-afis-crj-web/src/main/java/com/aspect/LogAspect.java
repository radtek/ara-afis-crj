/*
 * 文件名：${LogAspect}
 * 作者：${Tree}
 * 版本：
 * 时间：${2014.5.6}
 * 修改：
 * 描述：Spring AOP  日志切面类
 *
 *
 * 版权：亚略特
 */

package com.aspect;

import com.dao.impl.BaseDaoImpl;
import com.model.SysLogInfo;
import com.services.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class LogAspect extends BaseDaoImpl<SysLogInfo> {

	private DirectLog directLog;

	@Autowired
	private SysLogService sysLogService;

	//添加方法切入点
	@After(value = "execution(* com.controller.*.add*(..))  || execution(* com.controller.*.log*(..)) || execution(* com.controller.*.change*(..)) || execution(* com.controller.*.del*(..))")
	public void saveLog(JoinPoint joinPoint){

		//获取值对象
		Object object = joinPoint.getTarget();

		//开始转型
		try {
			directLog = (DirectLog)object;

			//判定是否记日志
			if(directLog.toGetLogInfo().getLogFlag()){
				sysLogService.saveLog(directLog.toGetLogInfo());
			}
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
	}
}
