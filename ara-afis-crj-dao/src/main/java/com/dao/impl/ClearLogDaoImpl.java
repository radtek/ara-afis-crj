package com.dao.impl;


import com.dao.ClearLogDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author NavyWang
 * @email wanghj@aratek.com.cn
 * @date 2017/8/25  10:45
 */
@Service
public class ClearLogDaoImpl implements ClearLogDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int clearEntity(Class<?> entityClass, int days, String createTime) {
        Session session = sessionFactory.getCurrentSession();
        //HQL如何执行update，delete相关的createQuery http://sjkgxf7191.iteye.com/blog/511304
        Query query = session.createQuery("delete from " + entityClass.getName() +
                " obj where obj." + createTime + "< :deadline");
        query.setParameter("deadline", LocalDateTime.now().minusDays(days));
        return query.executeUpdate();
    }

    @Override
    public int coutClearEntity(Class<?> entityClass, int days, String createTime) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(*) from " + entityClass.getName() +
                " obj where obj." + createTime + "< :deadline");
        query.setParameter("deadline", LocalDateTime.now().minusDays(days));
        return (int) (long) query.uniqueResult();
    }

    @Override
    public int[] coutEntity(String[] entityClassName) {
        int len = entityClassName.length;
        int[] count = new int[len];
        Session session = sessionFactory.getCurrentSession();
        for (int i = 0; i < len; i++) {
//            Query query = session.createQuery("select count(*) from " + "com.aratek.collect.entity." + entityClassName[i]);
//            count[i] = (int) (long) query.uniqueResult();
        }
//        return (int) (long) query.uniqueResult();
        return count;
    }

}
