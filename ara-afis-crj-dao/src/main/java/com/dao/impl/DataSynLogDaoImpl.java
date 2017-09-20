/*
 * 文件名：${BusLogDaoImpl}
 * 作者：${Tree}
 * 版本：
 * 时间：${2014.4.27}
 * 修改：
 * 描述：日志  Dao实现层
 *
 *
 * 版权：亚略特
 */
package com.dao.impl;

import com.dao.DataSynLogDao;
import com.exception.CommonUtilException;
import com.model.DataSynLogInfo;
import com.param.ConfigParam;
import com.vo.PageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.*;

@Repository("dataSynLogDao")
public class DataSynLogDaoImpl extends BaseDaoImpl<DataSynLogInfo> implements DataSynLogDao {

    @Override
    public List<DataSynLogInfo> getLogPage(PageVO page, int queryType, Object... values) {
        StringBuffer hql = new StringBuffer("FROM DataSynLogInfo ");
        List<DataSynLogInfo> loglist = new ArrayList<DataSynLogInfo>();
        //若为条件查询，构造查询数据
        Map<String, Object> paramMap = new HashMap<>();
        if (values != null && values.length > 0) {
            hql.append(" where  ");
            switch (queryType) {
                //全字段模糊查询
                case ConfigParam.QUERY_TYPE_ALL:
                    hql.append(" 1 = 2  ");
                    String itemName = (String)values[1];
                    //判断是否筛选策略是全局还是指定
                    if(StringUtils.isNotBlank(itemName)){hql.append(" or " + itemName + " like :" + itemName + " ");
                        if(StringUtils.isNotBlank((String)values[0])){
                            paramMap.put(itemName, "%"+values[0]+"%");
                        }else {
                            paramMap.put(itemName, values[0]);
                        }
                    }else{
                        Field[] fields = DataSynLogInfo.class.getDeclaredFields();
                        //利用java的反射机制，轮询元素
                        List likeStrings = Arrays.asList("personId", "resultFlag", "createOn");
                        Arrays.asList(fields).stream().filter(filed -> likeStrings.contains(filed.getName())).forEach(name -> {
                            hql.append(" or " + name.getName() + " like :" + name.getName() + " ");
                            if(StringUtils.isNotBlank((String)values[0])){
                                paramMap.put(name.getName(), "%"+values[0]+"%");
                            }else {
                                paramMap.put(itemName, values[0]);
                            }
                        });
                    }
                    break;
                //分条件精确查询
                case ConfigParam.QUERY_TYPE_SOME:
                    // TODO: 2017/5/24 后续增加
                    hql.append(" 1 = 1  ");
                    break;
            }
        }
        //设置排序规则   降序
        hql.append(" order by createOn desc");
        //模型参数构造
        List<DataSynLogInfo> logListTemp = null;
        if(page != null){
            logListTemp = findPage(hql.toString(),page,paramMap);
        }else{
            logListTemp = find(hql.toString(),paramMap);
        }
        if(null != logListTemp && logListTemp.size() > 0){
            for(DataSynLogInfo logTemp : logListTemp){
                try {
                    loglist.add(DataSynLogInfo.convert(logTemp));
                } catch (CommonUtilException e) {
                    e.printStackTrace();
                }
            }
        }
        return loglist;
    }
}
