<%@ page language="java"  pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
  <head>
  	<script type="text/javascript">
  			$(function(){
  				if("" != "${code}"){
  					$("#type").val(${code.type});
  					if("E" == "${code.statu}"){
						$("div.switch").bootstrapSwitch("setState",true);
  					}else{
						$("div.switch").bootstrapSwitch("setState",false);
  					}
  				}
  			});
  	</script>
  </head>

<body>
	<div class="hideDiv">
		<input type="hidden" id="action" value="addOption" />
        <input type="hidden" id="prifex" value="code" />
		<input type="hidden" id="page" value="Code" />
		<input type="hidden" id="operate"  value="<s:if test="${code != null}">modify</s:if>"/>
		<input type="hidden" id="switchInitStat" value="${code.statu}">
		<input type="hidden" id="selectInitStat" value="${code.type}">
	</div>
	
	<section class="wrapper_top">
		<div class="row">
			<div class="col-lg-12">
				<!--breadcrumbs start -->
				<ul class="breadcrumb">
					<li><a href="javascript:void(0)" onclick="loadPage('main-content','common/loadContent','')"><i class="icon-home"></i>首页</a></li>
					<li><a href="javascript:void(0)"><i class="icon-gear"></i>系统设置</a></li>
					<li class="active"><i class="icon-book"></i>添加数据字典</li>
				</ul>
				<!--breadcrumbs end -->
			</div>
		</div>
	</section>
	
	<section class="wrapper">

		<div class="row">
			<div class="col-lg-12">
				<div id="result" class="alert alert-block fade in hideDiv">
					<button data-dismiss="alert" class="close close-sm" type="button">
						<i class="icon-remove"></i>
					</button>
					<h4><i id="result_sign"></i><span id="result_title"></span></h4>
					<p id="result_msg"></p>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-lg-12">
				<section class="panel">
					<header class="panel-heading panel-bg-orgin">
						<i class="icon-book"></i>添加数据字典
					</header>
					<div class="panel-body">
						<div class="form text-nowrap">
							<form class="cmxform form-horizontal tasi-form" id="dataForm" name="dataForm" novalidate="novalidate">
								<input type="hidden" id="anyOption" name="anyOption" />
								<input type="hidden" id = "isbase" name="isbase" value="N"/>
								<input type="hidden" id="anyId" name="anyId" value="<s:choose><s:when test="${code != null}">${code.id}</s:when><s:otherwise>0</s:otherwise></s:choose>"/>
								<div class="form-group ">
									<label for="codeMean" class="control-label col-lg-1"><span class="form-req">*</span>字典名称 ：</label>
									<div class="col-lg-4">
										<input id="codeMean" name="codeMean" value="${code.codeMean}" class="form-control" type="text" maxlength="30" required data-rule-commTextIN="true"
											   data-msg-commTextIN="非有效的字典名称，只能包含：字母，数字，汉字和下划线，不能以下划线开头和结尾">
									</div>
								</div>
								
								<div class="form-group ">
									<label for="code" class="control-label col-lg-1"><span class="form-req">*</span>字典编码 ：</label>
									<div class="col-lg-4">
										<input id="code" name="code" value="${code.code}" class="form-control" type="text" maxlength="30" required data-rule-onlyNumChar="true"
											   data-msg-onlyNumChar="非有效的字典编码，只能包含：数字和字母">
									</div>
								</div>

								<div class="form-group ">
									<label for="type" class="control-label col-lg-1"><span class="form-req">*</span>字典类型 ：</label>
									<div class="col-lg-2">
										<select id="type" name="type" class="form-control" required data-msg-required="此项目为必选项">
											<option  value="">-- 选择 --</option>
                                            <s:forEach items="${typelist}" var='code'>
                                                <option  value="${code.type}" >${code.codeMean}</option>
                                            </s:forEach>
										</select>
									</div>
								</div>
								<div class="form-group ">
									<label for="statu" class="control-label col-lg-1"><span class="form-req">*</span>字典状态 ：</label>
									<div class="col-lg-11">
										<div class="switch" data-on="success" data-off="danger" data-on-label="可用" data-off-label="停用">
											<input type="checkbox" id="statu" name="statu" checked/>
										</div>
									</div>
								</div>
								<div class="form-group ">
									<label for="describe" class="control-label col-lg-1"><span class="form-req">&nbsp;</span>字典描述 ：</label>
									<div class="col-lg-8">
										<textarea class="form-control" id="describe" name="describe" maxlength="250">${code.describe}</textarea>
									</div>
								</div>
								<div class="form-group">
									<div class="col-lg-offset-1 col-lg-4">
										<button class="btn btn-danger btn-form" type="button" id="button_add">
											<i class="icon-ok-sign"></i>
                                            <s:choose>
                                                <s:when test="${code != null}">修改</s:when>
                                                <s:otherwise>添加</s:otherwise>
                                            </s:choose>
										</button>
										<button class="btn btn-default btn-form" type="button" id="button_reset">
											<i class="icon-refresh"></i> 重置
										</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</section>
			</div>
		</div>

		<div class="row">
			<div class="col-lg-12">
				<section class="panel">
					<table class="table table-striped table-advance table-hover">
						<thead>
						<tr>
							<th class="col-lg-2"><i class="icon-bullhorn"></i> 字典名称</th>
							<th class="col-lg-1"><i class="icon-bookmark"></i> 字典编码</th>
							<th class="col-lg-1"><i class="icon-question-sign"></i> 字典类型</th>
							<th class="col-lg-1"><i class="icon-off"></i> 字典状态</th>
							<th class="col-lg-5"><i class=" icon-edit"></i> 描述</th>
							<th class="col-lg-2"><i class=" icon-calendar"></i> 创建时间</th>
						</tr>
						</thead>
						<tbody id="tabBody"></tbody>
					</table>
				</section>
			</div>
		</div>
	</section>
	<script type="text/javascript" src="js/common/pageEvent.js"></script>
</body>
</html>
