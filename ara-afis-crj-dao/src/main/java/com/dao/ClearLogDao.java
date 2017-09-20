package com.dao;

/**
 * @author NavyWang
 * @email wanghj@aratek.com.cn
 * @date 2017/8/25  10:46
 */
public interface ClearLogDao {
    int clearEntity(Class<?> entityClass, int days, String createTime);

    int coutClearEntity(Class<?> entityClass, int days, String createTime);

    int[] coutEntity(String[] entityClassName);
}
