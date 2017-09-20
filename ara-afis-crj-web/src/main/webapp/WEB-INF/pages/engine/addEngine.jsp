<%@ page language="java"  pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
  <head></head>

<body>
	<div class="hideDiv">
		<input type="hidden" id="action" value="addOption" />
		<input type="hidden" id="prifex" value="engine" />
		<input type="hidden" id="page" value="Engine" />
		<input type="hidden" id="operate"  value="<s:if test="${engine != null}">modify</s:if>"/>
	</div>
	
	<section class="wrapper_top">
		<div class="row">
			<div class="col-lg-12">
				<!--breadcrumbs start -->
				<ul class="breadcrumb">
					<li><a href="javascript:void(0)" onclick="loadPage('main-content','common/loadContent','')"><i class="icon-home"></i>首页</a></li>
					<li><a href="javascript:void(0)"><i class="icon-laptop"></i>引擎管理</a></li>
					<li class="active"><i class="icon-laptop"></i>添加引擎</li>
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
						<i class="icon-book"></i>添加引擎
					</header>
					<div class="panel-body">
						<div class="form text-nowrap">
							<form class="cmxform form-horizontal tasi-form" id="dataForm" name="dataForm" novalidate="novalidate">
								<input type="hidden" id="anyOption" name="anyOption" />
								<input type="hidden" id="oldMasterId" name="oldMasterId" value="${engine.clusterCode}"/>
								<input type="hidden" id="oldNodeId" name="oldNodeId" value="${engine.engineCode}"/>

								<div class="form-group ">
									<div class="col-lg-6">
										<label for="masterId" class="control-label col-lg-3"><span class="form-req">*</span>主控节点名 ：</label>
										<div class="col-lg-8">
											<input id="masterId" name="masterId" value="${engine.clusterCode}" class="form-control" type="text" maxlength="7" required data-rule-onlyNumChar="true"
												   data-msg-onlyNumChar="非有效的主控节点名，只能包含：数字和字母">
										</div>
									</div>
									<div class="col-lg-6">
										<label for="nodeId" class="control-label col-lg-3"><span class="form-req">*</span>引擎节点名 ：</label>
										<div class="col-lg-8">
											<input id="nodeId" name="nodeId" value="${engineNode.nodeId}" class="form-control" type="text" maxlength="7" required data-rule-onlyNumChar="true"
												   data-msg-onlyNumChar="非有效的字典编码，只能包含：数字和字母">
										</div>
									</div>
								</div>
								<div class="form-group ">
									<div class="col-lg-6">
										<label for="engineType" class="control-label col-lg-3"><span class="form-req">*</span>引擎类别 ：</label>
										<div class="col-lg-5">
											<select id="engineType" name="engineType" class="form-control" required data-msg-required="此项目为必选项">
												<option  value="1" <s:if test="${1 == engine.engineType}">selected="selected"</s:if>>引擎节点</option>
												<option  value="0" <s:if test="${0 == engine.engineType}">selected="selected"</s:if>>主控节点</option>
											</select>
										</div>
									</div>
									<div class="col-lg-6">
										<label for="biometricsModel" class="control-label col-lg-3"><span class="form-req">*</span>生物识别模态 ：</label>
										<div class="col-lg-5">
											<select id="biometricsModel" name="biometricsModel" class="form-control" required data-msg-required="此项目为必选项">
												<option  value="FP" <s:if test="${'FP' == engine.biometricsModel}">selected="selected"</s:if>>指纹</option>
												<option  value="FA" <s:if test="${'FA' == engine.biometricsModel}">selected="selected"</s:if>>人脸</option>
												<option  value="FI" <s:if test="${'FI' == engine.biometricsModel}">selected="selected"</s:if>>虹膜</option>
												<option  value="FV" <s:if test="${'FV' == engine.biometricsModel}">selected="selected"</s:if>>指静脉</option>
											</select>
										</div>
									</div>
								</div>
								<div class="form-group ">
									<div class="col-lg-6">
										<label for="enrollThread" class="control-label col-lg-3"><span class="form-req">*</span>注册线程数 ：</label>
										<div class="col-md-9">
											<div id="spinner_enr">
												<div class="input-group spinner-input-length">
													<input id="enrollThread" name="enrollThread" type="text" class="spinner-input form-control"  readonly>
													<div class="spinner-buttons input-group-btn">
														<button type="button" class="btn btn-warning spinner-up" style="margin-right: -2px">
															<i class="icon-plus"></i>
														</button>
														<button type="button" class="btn btn-danger spinner-down">
															<i class="icon-minus"></i>
														</button>
													</div>
												</div>
											</div>
											<span class="help-block-exist">需要根据服务器配置设置合适的值，太大和太小都会影响引擎进行指纹注册的性能</span>
										</div>
									</div>
									<div class="col-lg-6">
										<label for="verifyThread" class="control-label col-lg-3"><span class="form-req">*</span>1:1 线程数 ：</label>
										<div class="col-md-9">
											<div id="spinner_ver">
												<div class="input-group spinner-input-length">
													<input type="text" id="verifyThread" name="verifyThread" class="spinner-input form-control"  readonly>
													<div class="spinner-buttons input-group-btn">
														<button type="button" class="btn btn-warning spinner-up" style="margin-right: -2px">
															<i class="icon-plus"></i>
														</button>
														<button type="button" class="btn btn-danger spinner-down">
															<i class="icon-minus"></i>
														</button>
													</div>
												</div>
											</div>
											<span class="help-block-exist">需要根据服务器配置设置合适的值，太大和太小都会影响引擎进行1：1比对的性能</span>
										</div>
									</div>
								</div>
								<div class="form-group ">
									<div class="col-lg-6">
										<label for="identifyThread" class="control-label col-lg-3"><span class="form-req">*</span>1:N 线程数 ：</label>
										<div class="col-md-9">
											<div id="spinner_ide">
												<div class="input-group spinner-input-length">
													<input type="text" id="identifyThread" name="identifyThread" class="spinner-input form-control"  readonly>
													<div class="spinner-buttons input-group-btn">
														<button type="button" class="btn btn-warning spinner-up" style="margin-right: -2px">
															<i class="icon-plus"></i>
														</button>
														<button type="button" class="btn btn-danger spinner-down">
															<i class="icon-minus"></i>
														</button>
													</div>
												</div>
											</div>
											<span class="help-block-exist">需要根据服务器配置设置合适的值，太大和太小都会影响引擎进行1：N比对的性能</span>
										</div>
									</div>
									<div class="col-lg-6">
										<label for="dataZone" class="control-label col-lg-3"><span class="form-req">*</span>数据块列表 ：</label>
										<div class="col-lg-9">
											<input id="dataZone" name="dataZone" value="${engineNode.dataZone}" class="form-control"  type="text" maxlength="150" required data-rule-onlyNumComma="true"
												   data-msg-onlyNumComma="错误：非有效的数据块列表，只能包含：数字和逗号，不能以逗号开头和结尾">
											<span class="help-block-exist">说明：取模运算后的数据块列表，可以设多个数据块，用逗号(英文输入法)分隔,示例：0,1</span>
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="col-lg-6">
										<div class="col-lg-offset-3 col-lg-12">
											<button class="btn btn-danger btn-form" type="button" id="button_add">
												<i class="icon-ok-sign"></i>
												<s:choose>
													<s:when test="${engine != null}">修改</s:when>
													<s:otherwise>添加</s:otherwise>
												</s:choose>
											</button>
										</div>
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
							<th class="col-lg-1"><i class="icon-th"></i> 主控节点名</th>
							<th class="col-lg-1"><i class="icon-th-large"></i> 引擎节点名</th>
							<th class="col-lg-1"><i class="icon-cogs"></i> 引擎类别</th>
							<th class="col-lg-1"><i class="icon-eye-open"></i> 生物识别模态</th>
							<th class="col-lg-1"><i class="icon-cloud-download"></i> 注册线程数</th>
							<th class="col-lg-1"><i class="icon-zoom-in"></i> 1：1 线程数</th>
							<th class="col-lg-1"><i class="icon-zoom-out"></i> 1：N 线程数</th>
							<th class="col-lg-5"><i class="icon-list-ol"></i> 数据块列表</th>
						</tr>
						</thead>
						<tbody id="tabBody"></tbody>
					</table>
				</section>
			</div>
		</div>
	</section>
	<script type="text/javascript" src="assets/fuelux/js/spinner.min.js"></script>
	<script type="text/javascript" src="js/common/pageEvent.js"></script>
	<script type="text/javascript">
        var engineType = $("#engineType").val();
        //区分主控节点和引擎节点的添加内容
        if( 0 == engineType){
            hideElement();
        }

        $("#spinner_enr").spinner({value:${engineNode.enrollThread}, min: 1});
		$("#spinner_ver").spinner({value:${engineNode.verifyThread}, min: 1});
		$("#spinner_ide").spinner({value:${engineNode.identifyThread}, min: 1});
        $("#engineType").change(function(){
            hideElement();
            $("#nodeId").val("");
        });

        function hideElement(){
            $("#nodeId").parent().parent().toggle();
            $("#enrollThread").parent().parent().parent().parent().parent().toggle();
            $("#dataZone").parent().parent().parent().toggle();
        }
	</script>
</body>
</html>
