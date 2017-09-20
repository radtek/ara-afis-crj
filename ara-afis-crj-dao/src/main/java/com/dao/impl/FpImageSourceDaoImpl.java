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

import com.dao.FpImageSourceDao;
import com.vo.FpImageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("fpImageSourceDao")
public class FpImageSourceDaoImpl implements FpImageSourceDao {

    /**
     * 注解引入对应的jdbcTemplate实例
     */
    @Resource(name = "sourceJdbcTemplate")
    private JdbcTemplate sourceJdbcTemplate;

    @Override
    public List<FpImageSource> getFpImageSourcePage() {
        String sql = "SELECT DH,XH,KB from BJ_YW_V_SWZW_ALL";
        return sourceJdbcTemplate.query(sql, (rs, index) -> new FpImageSource(rs.getString("DH"), rs.getInt("XH"), rs.getString("KB")));
    }
}
