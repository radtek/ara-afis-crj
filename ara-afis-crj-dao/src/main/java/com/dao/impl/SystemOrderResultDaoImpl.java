/*
 * 文件名：${ManagerDaoImpl}
 * 作者：${Tree}
 * 版本：
 * 时间：${2014.4.21}
 * 修改：
 * 描述：管理员  Dao实现层
 *
 *
 * 版权：亚略特
 */
package com.dao.impl;

import com.dao.SystemOrderResultDao;
import com.exception.CommonUtilException;
import com.model.SystemOrderResult;
import com.param.ConfigParam;
import com.vo.PageVO;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.*;

@Repository("systemOrderResultDao")
public class SystemOrderResultDaoImpl extends BaseDaoImpl<SystemOrderResult> implements SystemOrderResultDao {

	@Override
	public List<SystemOrderResult> getObjListPage(PageVO page, int queryType, Object... values) {
		StringBuffer hql = new StringBuffer(" from SystemOrderResult ");
		List<SystemOrderResult> systemOrderResultlist = new ArrayList<SystemOrderResult>();

		//若为条件查询，构造查询数据
        Map<String, Object> paramMap = new HashMap<>();
		if (values != null && values.length > 0) {
			hql.append(" where  ");
			switch (queryType) {
				//全字段模糊查询
				case ConfigParam.QUERY_TYPE_ALL:
					hql.append(" 1 = 2  ");
					Field fields[] = SystemOrderResult.class.getDeclaredFields();
					//利用java的反射机制，轮询元素
                    List likeStrings = Arrays.asList("");
                    Arrays.asList(fields).stream().filter(filed -> likeStrings.contains(filed.getName())).forEach(name -> {
                        hql.append(" or " + name.getName() + " like :" + name.getName() + " ");
                        paramMap.put(name.getName(), "%"+values[0]+"%");
                    });
					break;
				//分条件精确查询
				case ConfigParam.QUERY_TYPE_SOME:
					// TODO: 2017/5/24 后续增加
					hql.append(" 1 = 1  ");
					//如果有实际查询的命令ID，实际的命令ID都为大于0的正整数
					if((long)values[0] > 0){
						hql.append(" and id = :id ");
                        paramMap.put("id", values[0]);
					}
					break;
			}
		}

		//设置排序规则   降序
		hql.append(" order by createOn desc");

		//模型参数构造
		List<SystemOrderResult> systemOrderResultListTemp = null;
		if(page != null){
            systemOrderResultListTemp = findPage(hql.toString(),page,paramMap);
		}else{
			systemOrderResultListTemp = find(hql.toString(),paramMap);
		}
		if(null != systemOrderResultListTemp && systemOrderResultListTemp.size() > 0){
			for(SystemOrderResult systemOrderResultTemp : systemOrderResultListTemp){
				try {
					systemOrderResultlist.add(SystemOrderResult.convert(systemOrderResultTemp));
				} catch (CommonUtilException e) {
					e.printStackTrace();
				}
			}
		}

		return systemOrderResultlist;
	}

	@Override
	public boolean checkExist(String obj, Object id) {
		return false;
	}

	@Override
	public boolean checkExist(Object id, Object... values) {
		return false;
	}
}
