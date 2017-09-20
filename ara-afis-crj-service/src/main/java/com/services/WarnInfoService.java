/*
 * 文件名：${WarnInfoService}
 * 作者：${Tree}
 * 版本：
 * 时间：2016.5.26
 * 修改：
 * 描述：监控报警信息 service层
 *
 *
 * 版权：亚略特
 */
package com.services;


import com.model.WarnInfo;

import java.util.List;

public interface WarnInfoService extends CommonService {
    /**
     * 获取尚未处理的监控报警信息
     * @return 尚未处理的监控报警信息列表
     */
    List<WarnInfo> getAvailableWarnData();
}
