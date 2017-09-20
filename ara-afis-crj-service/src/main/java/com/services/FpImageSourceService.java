/*
 * 文件名：${FpImageSourceService}
 * 作者：${Tree}
 * 版本：
 * 时间：${2017.6.16}
 * 修改：
 * 描述：指纹图像源    service层
 *
 *
 * 版权：亚略特
 */
package com.services;

import com.vo.FpImageSource;

import java.util.List;

public interface FpImageSourceService {
    /**
     * 分页获取指纹图像数据源对象列表
     * @return 指纹图像源对象list
     */
    List<FpImageSource> getFpImageSourcePage();
}
