package com.dao.impl;

import com.dao.TasLibraryDao;
import com.model.BusLogInfo;
import com.model.TasLibraryEntity;
import com.param.ConfigParam;
import com.vo.PageVO;
import com.vo.TasLibraryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;


/**
 * 指纹库别表
 *
 * @author NavyWang
 * @email wanghj@aratek.com.cn
 * @date 2017/8/4  11:23
 */
@Service
public class  TasLibraryDaoImpl extends BaseDaoImpl<TasLibraryEntity> implements TasLibraryDao {

    @Override
    public List<TasLibraryEntity> getTasLibraryEntityPage(PageVO page, int queryType, Object... values) {
        StringBuffer hql = new StringBuffer("from TasLibraryEntity ");
        List<TasLibraryEntity> tasLibraryEntityList = new ArrayList<TasLibraryEntity>();

        //若为条件查询，构造查询数据
        Map<String, Object> paramMap = new HashMap<>();
        if (values != null && values.length > 0) {
            hql.append(" where  ");
            switch (queryType) {
                //全字段模糊查询
                case ConfigParam.QUERY_TYPE_ALL:
                    hql.append(" 1 = 1  ");
                    String itemName = (String)values[1];
                    //判断是否筛选策略是全局还是指定
                    if(StringUtils.isNotBlank(itemName)){hql.append(" or " + itemName + " like :" + itemName + " ");
                        if(StringUtils.isNotBlank((String)values[0])){
                            paramMap.put(itemName, "%"+values[0]+"%");
                        }else {
                            paramMap.put(itemName, values[0]);
                        }
                    }else{
                        Field[] fields = BusLogInfo.class.getDeclaredFields();
                        //利用java的反射机制，轮询元素
                        //需要模糊查询的属性
                        List likeStrings = Arrays.asList("libraryId", "libraryActivietyFlag","libraryDesc", "modifyState");
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
//                      2017/5/24 后续增加
                    hql.append(" 1 = 1  ");
                    break;
            }
        }

        //设置排序规则   降序
        hql.append(" order by createDate desc");

        //模型参数构造
        List<TasLibraryEntity> tasLibraryEntityListTemp = null;
        if(page != null){
            tasLibraryEntityListTemp = findPage(hql.toString(),page,paramMap);
        }else{
            tasLibraryEntityListTemp = find(hql.toString(),paramMap);
        }
        if(null != tasLibraryEntityListTemp && tasLibraryEntityListTemp.size() > 0){
            for(TasLibraryEntity TasLibraryEntityTemp : tasLibraryEntityListTemp){
//                try {
//                    tasLibraryEntityList.add(TasLibraryEntity.convert(TasLibraryEntityTemp));
//                } catch (CommonUtilException e) {
//                    e.printStackTrace();
//                }
                tasLibraryEntityList.add(TasLibraryEntityTemp);
            }
        }

        return tasLibraryEntityList;
    }

    @Override
    public boolean checkExist(String s, Object o) {
        return false;
    }

    @Override
    public boolean checkExist(Object o, Object... objects) {
        return false;
    }

    public List<TasLibraryVo> getTasLibraryEntitys(PageVO page, Object... values) {
        StringBuffer hql = new StringBuffer("from TasLibraryEntity");
        ArrayList tasLibraryEntitylist = new ArrayList();
        HashMap paramMap = new HashMap();
        if(values != null && values.length > 0 && values[0] instanceof String[]) {
            ;
        }

        List tasLibraryEntityListTemp = null;
        if(page != null) {
            tasLibraryEntityListTemp = this.findPage(hql.toString(), page, paramMap);
        } else {
            tasLibraryEntityListTemp = this.find(hql.toString(), paramMap);
        }

        if(null != tasLibraryEntityListTemp && tasLibraryEntityListTemp.size() > 0) {
            Iterator var7 = tasLibraryEntityListTemp.iterator();

            while(var7.hasNext()) {
                TasLibraryEntity tasLibraryEntityTemp = (TasLibraryEntity)var7.next();

                try {
                    if("D".equals(tasLibraryEntityTemp.getLibraryActivietyFlag())){
                        continue;
                    }
                    TasLibraryVo tasLibraryVo = new TasLibraryVo();
                    BeanUtils.copyProperties(tasLibraryEntityTemp, tasLibraryVo);
                    tasLibraryEntitylist.add(TasLibraryVo.convert(tasLibraryVo));
                } catch (Exception var10) {
                    var10.printStackTrace();
                }
            }
        }

        return tasLibraryEntitylist;
    }
}
