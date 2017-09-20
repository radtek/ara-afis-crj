package com.services.impl;

import com.dao.TasLibraryDao;
import com.exception.ServiceException;
import com.model.TasLibraryEntity;
import com.services.TasLibraryService;
import com.time.TimeUtil;
import com.vo.PageVO;
import com.vo.TasLibraryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("tasLibraryService")
public class TasLibraryServiceImpl implements TasLibraryService {
	@Autowired
	private TasLibraryDao tasLibraryDao;

	@Override
	public void saveObj(TasLibraryEntity tasLibraryEntity, String s, Object... objects) throws ServiceException {
		tasLibraryEntity.setCreateDate(TimeUtil.getFormatDate());
		if("on".equals(tasLibraryEntity.getLibraryActivietyFlag())){
			tasLibraryEntity.setLibraryActivietyFlag("Y");
		} else {
			tasLibraryEntity.setLibraryActivietyFlag("N");
		}
		tasLibraryEntity.setModifyState("N");

		tasLibraryDao.save(tasLibraryEntity);
	}

	@Override
	public void updateObj(TasLibraryEntity tasLibraryEntity, String s, Object o, Object... objects) throws ServiceException {
		tasLibraryEntity.setModifyDate(TimeUtil.getFormatDate());
		tasLibraryEntity.setModifyState("U");
		if("on".equals(tasLibraryEntity.getLibraryActivietyFlag())){
			tasLibraryEntity.setLibraryActivietyFlag("Y");
		} else {
			tasLibraryEntity.setLibraryActivietyFlag("N");
		}
		tasLibraryDao.update(tasLibraryEntity);
	}

	@Override
	public void delObj(Object o, Object... objects) throws ServiceException {
		TasLibraryEntity tasLibraryEntity = (TasLibraryEntity) o;
		tasLibraryEntity.setLibraryActivietyFlag("D");
		tasLibraryEntity.setModifyDate(TimeUtil.getFormatDate());
		tasLibraryEntity.setModifyState("D");
		tasLibraryDao.update(tasLibraryEntity);
	}

	@Override
	public List<TasLibraryEntity> getObjListPage(PageVO page, int queryType, Object... values) {
		return tasLibraryDao.getTasLibraryEntityPage(page, queryType, values);
	}

	@Override
	public List<TasLibraryEntity> getObjList(Object... values) {
//		return tasLibraryDao.getTasLibraryEntitys((PageVO)null, values);
		return null;
	}

	public List<TasLibraryVo> getTasLibraryVoList(Object... values) {
		return tasLibraryDao.getTasLibraryEntitys((PageVO)null, values);
	}

	@Override
	public TasLibraryEntity getObj(Object o) {
		return null;
	}

	@Override
	public TasLibraryEntity getObjByObj(String s, Object o) {
		return tasLibraryDao.findById(s);
	}

}
