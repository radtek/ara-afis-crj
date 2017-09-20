package com.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.exception.CommonUtilException;
import com.exception.ServiceException;
import com.model.TasLibraryEntity;
import com.vo.TasLibraryVo;
import com.services.TasLibraryService;
import com.util.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;


/**
 *
 *
 * @author NavyWang
 * @email wanghj@aratek.com.cn
 * @date 2017/8/4  12:00
 */
@Controller
@RequestMapping("taslibrary")
public class TasLibraryController extends BaseController {
	@Autowired
	private TasLibraryService tasLibraryService;

	/**
	 * 初始化库列表显示及跳转
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toGetList")
	public String toGetList(Model model){
		//查询所有库
		model.addAttribute("libraryListJson", JSON.toJSONString(tasLibraryService.getTasLibraryVoList(false), SerializerFeature.WriteNullStringAsEmpty));
		//跳转前台
		return "/library/queryLibrary";
	}

	/**
	 * 跳转添加页面
	 */
	@RequestMapping(value = "/addPage")
	public String addPage(){
		return "/library/addLibrary";
	}

	/**
	 * 新增库
	 */
	@ResponseBody
	@RequestMapping(value = "/save")
	public Map<String, Object> save( TasLibraryEntity tasLibrary, Model model) throws ServiceException, CommonUtilException {
		initController();
		TasLibraryEntity tasLibraryEntity = tasLibraryService.getObjByObj(tasLibrary.getLibraryId(),"");
		if(null == tasLibraryEntity){
			tasLibraryService.saveObj(tasLibrary,null);
			msg = "库ID:"+tasLibrary.getLibraryId()+"保存成功";
			anyStatus = 1;
			tr = PageUtil.drawTable(TasLibraryVo.convert(tasLibrary));
		} else {
			msg = "库ID已被占用，请重新输入";
			anyStatus = 0;
		}

//		logInfo.setContent(msg);
		responseMap.put("anyStatus", anyStatus);
		responseMap.put("msg", msg);
		responseMap.put("tr", tr);
		return responseMap;
	}


	@RequestMapping(value = "/toUpdate")
	public String toUpdatePage(Model model, String anyId){
		TasLibraryEntity tasLibraryEntity = tasLibraryService.getObjByObj(anyId,"");
		model.addAttribute("tasLibrary", tasLibraryEntity);

		return "/library/update";
	}

	/**
	 * 更新指纹库别
	 * @param tasLibrary
	 * @param model
	 * @return
	 * @throws ServiceException
	 * @throws CommonUtilException
	 */
	@ResponseBody
	@RequestMapping(value = "/update")
	public Map<String, Object> Update(TasLibraryEntity tasLibrary, Model model) throws ServiceException, CommonUtilException {
		initController();
		TasLibraryEntity tasLibrarySql = tasLibraryService.getObjByObj(tasLibrary.getLibraryId(),"");
		if(Optional.ofNullable(tasLibrarySql).isPresent()){
			//对为空的属性从数据库中取出填充
			tasLibrary.setCreateDate(tasLibrarySql.getCreateDate());
			BeanUtils.copyProperties(tasLibrary, tasLibrarySql);

			tasLibraryService.updateObj(tasLibrarySql,null,null);
			msg = "库ID:"+tasLibrary.getLibraryId()+"修改成功";
			anyStatus = 1;
			tr = PageUtil.drawTable(TasLibraryVo.convert(tasLibrarySql));
		} else {
			msg = "库ID:"+tasLibrary.getLibraryId()+"不存在或其他错误！请刷新后再试";
			anyStatus = 0;
		}


		responseMap.put("anyStatus", anyStatus);
		responseMap.put("msg", msg);
		responseMap.put("tr", tr);
		return responseMap;
	}


	/**
	 * 删除库
	 * @param anyId
	 * @param session
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/delOption", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> delOption(String anyId, HttpSession session) throws ServiceException {
		initController();
		TasLibraryEntity tasLibrarySql = tasLibraryService.getObjByObj(anyId,"");
		if(Optional.ofNullable(tasLibrarySql).isPresent()){

			tasLibraryService.delObj(tasLibrarySql);
			msg = "库ID:"+anyId+"删除成功";
			anyStatus = 1;

		} else {
			msg = "库ID:"+anyId+"不存在或其他错误！请联系管理员";
			anyStatus = 0;
		}


		responseMap.put("anyStatus", anyStatus);
		responseMap.put("msg", msg);
		return responseMap;
	}

}
