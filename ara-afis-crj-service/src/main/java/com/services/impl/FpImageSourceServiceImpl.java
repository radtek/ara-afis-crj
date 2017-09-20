/*
 * 文件名：${FpImageSourceServiceImpl}
 * 作者：${Tree}
 * 版本：
 * 时间：${2017.6.16}
 * 修改：
 * 描述：指纹图像数据源  Service实现层
 *
 *
 * 版权：亚略特
 */
package com.services.impl;

import com.dao.FpImageSourceDao;
import com.vo.FpImageSource;
import com.services.FpImageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("fpImageSourceService")
public class FpImageSourceServiceImpl implements FpImageSourceService {

	@Autowired
	private FpImageSourceDao fpImageSourceDao;

	@Override
	public List<FpImageSource> getFpImageSourcePage() {
		return fpImageSourceDao.getFpImageSourcePage();
	}
}
