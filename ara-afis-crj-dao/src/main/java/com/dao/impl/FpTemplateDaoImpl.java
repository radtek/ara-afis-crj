/*
 * 文件名：${FpImageSourceDaoImpl}
 * 作者：${Tree}
 * 版本：
 * 时间：${2017.6.16}
 * 修改：
 * 描述：指纹图像数据源  Dao实现层
 *
 *
 * 版权：亚略特
 */
package com.dao.impl;

import com.dao.FpTemplateDao;
import com.model.FpTemplate;
import com.vo.PageVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("fpTemplateDao")
public class FpTemplateDaoImpl extends BaseDaoImpl<FpTemplate> implements FpTemplateDao {

    @Override
    public List<FpTemplate> getFpTemplatePage(PageVO page, int queryType, Object... values) {
        StringBuffer hql = new StringBuffer(" from FpTemplate ");
        //模型参数构造
        List<FpTemplate> loglist = null;
        if(page != null){
            loglist = findPage(hql.toString(),page,null);
        }else{
            loglist = find(hql.toString(),null);
        }
        return loglist;
    }
}
