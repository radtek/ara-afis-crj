/*
 * 文件名：${FpImageSourceDao}
 * 作者：${Tree}
 * 版本：
 * 时间：${2017.6.16}
 * 修改：
 * 描述：指纹图像数据源    dao层
 *
 *
 * 版权：亚略特
 */
package com.dao;

import com.vo.FpImageSource;

import java.util.List;

public interface FpImageSourceDao {
	/**
	 * 分页获取指纹图像数据源
	 * @return 指纹图像数据源list
	 */
	List<FpImageSource> getFpImageSourcePage();
}
