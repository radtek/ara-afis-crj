/*
 * 文件名：${EngineNodeServiceImpl}
 * 作者：${Tree}
 * 版本：
 * 时间：${2014.4.27}
 * 修改：
 * 描述：字典  Service实现层
 *
 *
 * 版权：亚略特
 */
package com.services.impl;

import com.dao.EngineDao;
import com.dao.EngineNodeDao;
import com.exception.CommonUtilException;
import com.exception.ServiceException;
import com.model.Engine;
import com.model.EngineNode;
import com.services.EngineNodeService;
import com.time.TimeUtil;
import com.util.CommonObjectUtil;
import com.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("engineNodeService")
public class EngineNodeServiceImpl implements EngineNodeService {

	@Autowired
	private EngineNodeDao engineNodeDao;
	@Autowired
	private EngineDao engineDao;

	@Override
	public void saveObj(EngineNode obj, String operator, Object... values)
			throws ServiceException {
        //此处默认传入的values[0]  为一个engine对象
        Engine engineTemp = null;
        if(values.length > 0 && values[0] instanceof Engine){
            engineTemp = (Engine) values[0];
            engineTemp.setClusterCode(obj.getMasterId());
            if ("0".equals(engineTemp.getEngineType())) {
                //如果是主控节点，则设置引擎节点名等于主控节点名
                engineTemp.setEngineCode(obj.getMasterId());
            } else {
                engineTemp.setEngineCode(obj.getNodeId());
            }
        }else{
            throw new ServiceException("需要添加的引擎信息不完整!");
        }
        //判别添加的字典是否已存在   0 标示增加
        if(!engineDao.checkExist(0,engineTemp.getClusterCode(),engineTemp.getEngineCode())){
			engineTemp.setCreateOn(TimeUtil.getFormatDate());
			engineTemp.setWorkStationStatus("2");
			engineTemp.setNetworkStatus("1");

            if ("1".equals(engineTemp.getEngineType())) {
                //保存比对引擎对象
                engineNodeDao.save(obj);
            }
            //对引擎做内容初始化并保存引擎信息
			try {
				engineDao.save(Engine.init(engineTemp));
			} catch (CommonUtilException e) {
				e.printStackTrace();
			}
		}else{
			throw new ServiceException("此引擎节点已经存在!");
		}
	}

	@Override
	public void updateObj(EngineNode obj, String operator, Object id, Object... values)
			throws ServiceException {
		//此处默认传入的values[0]  为一个engine对象
		Engine engineTemp = null;
		if(values.length > 0 && values[0] instanceof Engine){
			engineTemp = (Engine) values[0];
            engineTemp.setClusterCode(obj.getMasterId());
            if ("0".equals(engineTemp.getEngineType())) {
                //如果是主控节点，则设置引擎节点名等于主控节点名
                engineTemp.setEngineCode(obj.getMasterId());
            } else {
                engineTemp.setEngineCode(obj.getNodeId());
            }
		}else{
			throw new ServiceException("需要修改的引擎信息不完整!");
		}
		//判别添加的引擎节点是否已存在
        boolean existFlag = engineDao.checkExist(0,engineTemp.getClusterCode(),engineTemp.getEngineCode());
		if(!existFlag || (existFlag && obj.getMasterId().equals(obj.getOldMasterId()) && obj.getNodeId().equals(obj.getOldNodeId()))){
			EngineNode engineNodeTemp = engineNodeDao.getEngineNodeByMasterAndNode(obj.getOldMasterId(),obj.getOldNodeId());
			Engine engineNow = engineDao.getEngineByMasterAndNode(obj.getOldMasterId(),obj.getOldNodeId());

            if(null != engineNow){
                CommonObjectUtil.Copy(engineNow, engineTemp);
                if ("1".equals(engineNow.getEngineType())) {
                    if (null != engineNodeTemp) {
						CommonObjectUtil.Copy(engineNodeTemp, obj);
                        engineNodeDao.delete(engineNodeTemp);
                        engineNodeDao.save(obj);
                    } else {
                        throw new ServiceException("比对引擎数据异常，比对引擎信息与引擎节点信息存在不一致!");
                    }
                }
                engineTemp.setModifiedOn(TimeUtil.getFormatDate());
                engineDao.merge(engineTemp);
            }else{
                throw new ServiceException("该比对引擎已经被删除!");
            }
		}else{
			throw new ServiceException("此引擎节点已经存在!");
		}
	}

	@Override
	public void delObj(Object id, Object... values) throws ServiceException {
		EngineNode engineNodeTemp = engineNodeDao.getEngineNodeByMasterAndNode((String) values[0],(String)values[1]);
		Engine engineTemp = engineDao.getEngineByMasterAndNode((String) values[0],(String)values[1]);

		if(null != engineTemp){
            if ("1".equals(engineTemp.getEngineType())) {
                if (null != engineNodeTemp) {
                    engineNodeDao.delete(engineNodeTemp);
                } else {
                    throw new ServiceException("比对引擎数据异常，比对引擎信息与引擎节点信息存在不一致!");
                }
            }
            engineDao.delete(engineTemp);
        }else{
			throw new ServiceException("该比对引擎已经被删除!");
		}
	}

	@Override
	public List<EngineNode> getObjListPage(PageVO page, int queryType, Object... values) {
		return null;
	}

	@Override
	public List<EngineNode> getObjList(Object... values) {
		return null;
	}

	@Override
	public EngineNode getObj(Object id) {
		return engineNodeDao.findById((String)id);
	}

	@Override
	public EngineNode getObjByObj(String param, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EngineNode getEngineNodeByMasterAndNode(String masterId, String nodeId) {
		return engineNodeDao.getEngineNodeByMasterAndNode(masterId,nodeId);
	}
}
