<%@ page language="java"  pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
  <head></head>

<body>
	<div class="hideDiv">
		<input type="hidden" id="action" value="changeOption" />
		<input type="hidden" id="prifex" value="managerInfo" />
		<input type="hidden" id="page" value="Manager" />
		<input type="hidden" id="operate" value="changePwd" />
	</div>

	<section class="wrapper_top">
		<div class="row">
			<div class="col-lg-12">
				<!--breadcrumbs start -->
				<ul class="breadcrumb">
					<li><a href="javascript:void(0)" onclick="loadPage('main-content','common/loadContent','')"><i class="icon-home"></i>首页</a></li>
					<li><a href="javascript:void(0)"><i class="icon-gear"></i>系统设置</a></li>
					<li><a href="javascript:void(0)"><i class="icon-group"></i>管理员</a></li>
					<li class="active"><i class="icon-unlink"></i>更改密码</li>
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
						<i class="icon-book"></i>更改密码
					</header>
					<div class="panel-body">
						<div class="form text-nowrap">
							<form class="cmxform form-horizontal tasi-form" id="dataForm" name="dataForm" novalidate="novalidate">
								<input type="hidden" id="anyOption" name="anyOption" />
								<input type="hidden" id="anyId" name="anyId" value="<s:choose><s:when test="${manager != null}">${manager.id}</s:when><s:otherwise>0</s:otherwise></s:choose>"/>
                                <!--
                                <div class="form-group ">
									<label for="password" class="control-label col-lg-1"><span class="form-req">*</span>旧密码 ：</label>
									<div class="col-lg-4">
										<input id="password" name="password" class="form-control" minlength="6" maxlength="30" type="password" required>
									</div>
								</div>
								-->
								<div class="form-group " id="passwd">
										<label for="newPasswd" class="control-label col-lg-1"><span class="form-req">*</span>新密码 ：</label>
										<div class="col-lg-4">
											<input id="newPasswd" name="newPasswd" class="form-control"  minlength="6" maxlength="30" type="password" required>
										</div>
								</div>

								<div class="form-group ">
									<label for="newPasswdCopy" class="control-label col-lg-1"><span class="form-req">*</span>确认新密码 ：</label>
										<div class="col-lg-4">
											<input id="newPasswdCopy" name="newPasswdCopy" class="form-control" minlength="6" maxlength="30" type="password" required data-rule-equalTo="#newPasswd"
												   data-msg-equalTo="× 前后两次输入的密码不一致">
										</div>
								</div>
								<div class="form-group">
									<div class="col-lg-offset-1 col-lg-4">
										<button class="btn btn-danger btn-form" type="button" id="button_change">
											<i class="icon-ok-sign"></i> 提交
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
                                  <th class="col-lg-2"><i class="icon-bullhorn"></i> 账号</th>
                                   <th class="col-lg-1"><i class="icon-bookmark"></i> 角色</th>
                                  <th class="col-lg-1"><i class="icon-question-sign"></i> 状态</th>
                                  <th class="col-lg-1"><i class="icon-off"></i> 最近一次登录</th>
                                  <th class="col-lg-2"><i class=" icon-calendar"></i> 创建时间</th>
                              </tr>
                              </thead>
                              <tbody id="tabBody">
                              		<tr>
										<td title="${manager.account}"> ${manager.account} </td>
										<td>${manager.role.name}</td>
										<td>${manager.statu}</td>
										<td>${manager.lastLogin}</td>
										<td>${manager.createOn}</td>
									</tr>
                              </tbody>
                          </table>
                      </section>
                  </div>
              </div>
	</section>
	<script type="text/javascript" src="js/common/pageEvent.js"></script>
</body>
</html>
