<%@ page language="java"  pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
  <head>
  	<meta charset="utf-8">
    <title><spring:message code="system_name"/></title>
	<!--[if IE]><meta http-equiv='X-UA-Compatible' content="IE=edge,IE=9,IE=8,chrome=1" /><![endif]-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="img/favicon.ico">

    <link href="css/style.css" rel="stylesheet">
    <link href="css/style-responsive.css" rel="stylesheet" />

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="js/other/html5shiv.js"></script>
    <script src="js/other/respond.min.js"></script>
    <![endif]-->
  </head>

  <body onkeydown="BindEnter(event)" class="login-body">
	<div class="container" style="margin-top: 80px;">
		<form class="form-signin" action="login" id="dataForm" name="dataForm"  onsubmit="return checking();" method="post">
			<div class="form-signin-heading text-center">
				<%--<img src="img/logoAra.png" />--%>
				<img src="img/logoAra.png" />
				<div class="logoTitle" >
					<spring:message code="system_name" />
				</div>
			</div>
			<div class="login-wrap">
				<div class="registration">
					<div id="message-error" class="message-error hideDiv">
						<div class="image">
							<img src="img/icon/error.png" height="32" />
						</div>
						<div class="text" id="errorMsg">${msg}</div>
					</div>
				</div>
				<input type="text" id="account" name="account" value="${manager.account}" class="form-control" placeholder="<spring:message code="user_name" />" autofocus>
				<input type="password" id="password" name="password"  class="form-control" placeholder="<spring:message code="pass_word" />">
				<button class="btn btn-lg btn-login btn-block" type="submit" id="btn">
					<h4><spring:message code="login_in" /></h4>
				</button>
				<label class="checkbox">
					<span class="pull-right"><input id="remember" name="remember" type="checkbox" value="true"> <spring:message code="remember_me" /></span>
				</label>
			</div>
		</form>
		<input type="hidden" id="id_empty" value="<spring:message code="id_empty" />" />
		<input type="hidden" id="pw_empty" value="<spring:message code="pw_empty" />" />
	</div>
	<script type="text/javascript" src="js/jquery/jquery.js"></script>
	<script type="text/javascript" src="js/bootstrap/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/bootstrap/modernizr.min.js"></script>
    <script type="text/javascript" src="js/common/login.js"></script>
    <script type="text/javascript">
		$(function() {
			if ("" != "${msg}") {
				$("#message-error").show();
			}
		});
	</script>
  </body>
</html>
