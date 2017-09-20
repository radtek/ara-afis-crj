/**
 * ThreadTask.java.
 *
 */
package com.dao.expand;

import com.dao.FpImageSourceDao;
import com.dao.FpTemplateDao;
import com.dao.impl.BaseDaoImpl;
import com.model.FpTemplate;
import com.model.SystemConfig;
import com.param.ConfigParam;
import com.time.TimeUtil;
import com.util.DaoMethodUtil;
import com.vo.FpImageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 作者：tree
 *
 * @author 功能说明：定时统计数据
 *
 * @author 创建时间：2016-5-26.
 * .
 *
 */
@SuppressWarnings("unused")
@Repository
public class ThreadTaskImpl extends BaseDaoImpl<ThreadTaskImpl> {

	private static Logger logger = LoggerFactory.getLogger(ThreadTaskImpl.class);

    @Autowired
    private FpImageSourceDao fpImageSourceDao;
    @Autowired
    private FpTemplateDao fpTemplateDao;
    @Autowired
    private SystemConfig systemConfig;

	public ThreadTaskImpl() {
        logger.info("[SYS_LOG][Start thread to collect data]");
	}

	/**
	 * 线程执行方法
	 */
	public void queryHomeData() {
        logger.info("[SYS_LOG][Start collect data][{}]", TimeUtil.getFormatDate());
        long start = Instant.now().toEpochMilli();
        List<?> list;
        Map<String, Object> paramMap = new HashMap<>();
        ConfigParam.ANALYSE_DATA.clear();
        //获取在库的指纹数
        String sql1 = " FROM FpTemplate WHERE activeStatu = :activeStatu";
        paramMap.put("activeStatu", ConfigParam.ACTIVE_STATU.AVAILABLE.getElementName());
        long fpNum = count(sql1, paramMap);
        logger.debug("[SYS_LOG][Avaliable FP NUM : {}]",fpNum);
        ConfigParam.ANALYSE_DATA.put("fpNum", fpNum);
        paramMap.clear();

        //获取可用的库别数
        String sql2 = " FROM TasLibraryEntity WHERE libraryActivietyFlag = :libraryActivietyFlag";
        paramMap.put("libraryActivietyFlag", "Y");
        long libraryNum = count(sql2, paramMap);
        logger.debug("[SYS_LOG][Avaliable Library NUM : {}]",libraryNum);
        ConfigParam.ANALYSE_DATA.put("libraryNum", libraryNum);
        paramMap.clear();

        //获取在线的引擎数
        String sql3 = " FROM TasMonEnvStatisticEntity WHERE  engineNetworkStatus !=:engineNetworkStatus AND engineWorkstationStatus =:engineWorkstationStatus AND engineServerType = :engineServerType ";
        paramMap.put("engineNetworkStatus", "1");
        paramMap.put("engineWorkstationStatus", "0");
        paramMap.put("engineServerType", ConfigParam.ENGINE_TYPE.NODE.getElementName());
        long serverNum = count(sql3, paramMap);
        logger.debug("[SYS_LOG][Online engine  NUM : {}]",serverNum);
        ConfigParam.ANALYSE_DATA.put("serverNum", serverNum);
        paramMap.clear();

        String tdData = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        //获取今天的业务数
        String sql4 = " FROM BusLogInfo WHERE actionType IN(201301,201302,201311,201321,201322,201201," +
                "201202,201203,201221,201401) AND SUBSTRING(createOn,1,10) = :tdData";
        paramMap.put("tdData", tdData);
        long busNum = count(sql4, paramMap);
        logger.debug("[SYS_LOG][Today business NUM : {}]",busNum);
        ConfigParam.ANALYSE_DATA.put("busNum", busNum);
        paramMap.clear();

        //获取今天失败的业务数
        String sql5 = " FROM BusLogInfo WHERE actionType IN(201301,201302,201311,201321,201322,201201," +
                "201202,201203,201221,201401) AND SUBSTRING(createOn,1,10) = :tdData AND resultCode != :resultCode";
        paramMap.put("tdData", tdData);
        paramMap.put("resultCode", "0");
        long busFailNum = count(sql5, paramMap);
        logger.debug("[SYS_LOG][Today fail business NUM : {}]",busFailNum);
        ConfigParam.ANALYSE_DATA.put("busFailNum", busFailNum);
        paramMap.clear();

        String sql6;
        String sql7;
        switch (systemConfig.getDbType()){
            //oracle
            case "2":
                sql6 = "SELECT TO_CHAR(TO_DATE(CREATE_DATE, 'YYYY-MM-DD HH24:MI:SS'), 'DD')  KEYTIME, COUNT(*) NUM FROM TAS_FP_TEMPLATE  WHERE  ACTIVE_STATU = ?0 AND CREATE_DATE >= TO_CHAR(SYSDATE,'YYYY-MM') GROUP BY TO_CHAR(TO_DATE(CREATE_DATE, 'YYYY-MM-DD HH24:MI:SS'), 'DD') ORDER BY KEYTIME";
                sql7 = "SELECT ACTION_TYPE, COUNT(*) NUM FROM TAS_LOG_BUSINESS  WHERE ACTION_TYPE NOT IN("+systemConfig.getBusCodes()+") AND  CREATE_DATE >= TO_CHAR(SYSDATE,'YYYY-MM') GROUP BY ACTION_TYPE";
            default:
                sql6 = "select date_format(CREATE_DATE,'%d')  keyTime, count(*) NUM from TAS_FP_TEMPLATE  where ACTIVE_STATU = ?0 AND  date_format(CREATE_DATE,'%Y-%m')=date_format(now(),'%Y-%m') group by date_format(CREATE_DATE,'%d') ORDER BY keyTime";
                sql7 = "SELECT ACTION_TYPE, COUNT(*) NUM FROM TAS_V_LOG_BUSINESS  WHERE ACTION_TYPE IN("+systemConfig.getBusCodes()+") AND  date_format(CREATE_DATE,'%Y-%m')=date_format(now(),'%Y-%m') GROUP BY ACTION_TYPE";
        }

        //获取当月每天增长的指纹数量
        list = findListBySql(sql6, ConfigParam.ACTIVE_STATU.AVAILABLE.getElementName());
        ConfigParam.FP_ADD_DATA.clear();
        for (Object object : list) {
            logger.debug("[SYS_LOG][Every day add FP NUM][{}][{}]",((Object[]) object)[0],((BigInteger) ((Object[]) object)[1]).longValue());
            ConfigParam.FP_ADD_DATA.put(Integer.valueOf((String) (((Object[]) object)[0])), ((BigInteger) ((Object[]) object)[1]).longValue());
        }

        //获取当月业务类型分布趋势
        list = findListBySql(sql7);
        ConfigParam.BUS_TYPE_DATA.clear();
        for (Object object : list) {
            logger.debug("[SYS_LOG][Every month add business NUM][{}][{}]",((Object[]) object)[0],((BigInteger) ((Object[]) object)[1]).longValue());
            ConfigParam.BUS_TYPE_DATA.put((Integer) ((Object[]) object)[0], ((BigInteger) ((Object[]) object)[1]).longValue());
        }

        ConfigParam.SERVER_STATU_DATA.clear();
        //获取当前引擎状态数据（正常）
        String sql8 = " FROM  TasMonEnvStatisticEntity WHERE  engineNetworkStatus !=:engineNetworkStatus AND engineWorkstationStatus =:engineWorkstationStatus AND engineServerType = :engineServerType";
        paramMap.put("engineNetworkStatus", "1");
        paramMap.put("engineWorkstationStatus", "0");
        paramMap.put("engineServerType", ConfigParam.ENGINE_TYPE.NODE.getElementName());
        long sEngineNum = count(sql8, paramMap);
        logger.debug("[SYS_LOG][Online engine  NUM : {}]",sEngineNum);
        ConfigParam.SERVER_STATU_DATA.put(0, sEngineNum);
        paramMap.clear();

        //获取当前引擎状态数据（告警）
        String sql9 = " FROM TasMonEnvStatisticEntity WHERE  engineWarnField IS NOT NULL AND engineWorkstationStatus !=:engineWorkstationStatus AND engineServerType =:engineServerType";
        paramMap.put("engineWorkstationStatus", "2");
        paramMap.put("engineServerType", ConfigParam.ENGINE_TYPE.NODE.getElementName());
        long wEngineNum = count(sql9, paramMap);
        logger.debug("[SYS_LOG][Current engine W statu  NUM : {}]",wEngineNum);
        ConfigParam.SERVER_STATU_DATA.put(ConfigParam.ENGINE_WARN_CODE, wEngineNum);
        paramMap.clear();

        //获取当前引擎状态数据（故障）
        String sql11 = " FROM  TasMonEnvStatisticEntity WHERE engineNetworkStatus =:engineNetworkStatus  OR engineWorkstationStatus =:engineWorkstationStatus AND engineServerType =:engineServerType";
        paramMap.put("engineNetworkStatus", "1");
        paramMap.put("engineWorkstationStatus", "2");
        paramMap.put("engineServerType", ConfigParam.ENGINE_TYPE.NODE.getElementName());
        long fEngineNum = count(sql11, paramMap);
        logger.debug("[SYS_LOG][Current engine F statu  NUM : {}]",fEngineNum);
        ConfigParam.SERVER_STATU_DATA.put(ConfigParam.ENGINE_FAULT_CODE, fEngineNum);
        paramMap.clear();

        //获取任务状态数据
        String sql10 = "SELECT TASK_STATE, COUNT(*) FROM TAS_TASK  WHERE SUBSTRING(CREATE_DATE,1,10) = ?0 GROUP BY TASK_STATE ORDER BY TASK_STATE";
        list = findListBySql(sql10,tdData);
        ConfigParam.TASK_STATU_DATA.clear();
        for (Object object : list) {
            logger.debug("[SYS_LOG][Current task statu][{}][{}]",((Object[]) object)[0],((BigInteger) ((Object[]) object)[1]).longValue());
            ConfigParam.TASK_STATU_DATA.put(Integer.valueOf((String) (((Object[]) object)[0])), ((BigInteger) ((Object[]) object)[1]).longValue());
        }
        long end = Instant.now().toEpochMilli();
        logger.info("[SYS_LOG][Finish collect data][Use time : {}]",end - start);
    }

    public void queryLibraryDiff(){
        logger.debug("---------源库数据差异性比较统计线程运行开始 "+ TimeUtil.getFormatDate()+"-----------------------------");
        List<FpImageSource> imageList = fpImageSourceDao.getFpImageSourcePage();
        List<FpTemplate> templateList = fpTemplateDao.getFpTemplatePage(null, 1);
        ConfigParam.SYSTEM_STORE_DATA.put("differData",DaoMethodUtil.compareDifferList(imageList, templateList));
        ConfigParam.SYSTEM_STORE_DATA.put("diffColTime", TimeUtil.getFormatDate());
        logger.debug("---------源库数据差异性比较统计线程运行结束 "+ TimeUtil.getFormatDate()+"-----------------------------");
    }
}
