<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
    <title>锁屏界面</title>
    <!--[if IE]>
    <meta http-equiv='X-UA-Compatible' content="IE=edge,IE=9,IE=8,chrome=1"/><![endif]-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="img/favicon.ico">

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-reset.css" rel="stylesheet">
    <!--external css-->
    <link href="assets/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>
    <!-- Custom styles for this template -->
    <link href="css/style.css" rel="stylesheet">
    <link href="css/style-responsive.css" rel="stylesheet"/>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="js/other/html5shiv.js"></script>
    <script src="js/other/respond.min.js"></script>
    <![endif]-->
</head>

<body class="lock-screen" onload="startTime()" onkeydown="BindEnter(event)">
<div class="hideDiv">
    <input type="hidden" id="pw_empty" value="<spring:message code="pw_empty" />" />
</div>
<div class="lock-wrapper">
    <div id="time"></div>
    <div class="lock-box text-center">
        <img src="img/avatar-big.jpg"/>
        <h1>${account}</h1>
        <span class="locked" id="errorMsg">系统已锁定</span>
        <form role="form" id="dataForm" class="form-inline" action="login" method="POST" onsubmit="return checkLock();">
            <div class="form-group col-lg-12">
                <input type="hidden" id="account" name="account" value="${account}">
                <input type="password" id="password" name="password"  placeholder="<spring:message code="pass_word" />" class="form-control lock-input" autofocus>
                <button class="btn btn-no-space btn-lock" type="submit" id="btn">
                    <i class="icon-arrow-right"></i>
                </button>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" src="js/jquery/jquery.js"></script>
<script type="text/javascript" src="js/common/login.js"></script>
<script type="text/javascript">
    function startTime() {
        var today = new Date();
        var h = today.getHours();
        var m = today.getMinutes();
        var s = today.getSeconds();
        // add a zero in front of numbers<10
        m = checkTime(m);
        s = checkTime(s);
        document.getElementById('time').innerHTML = h + ":" + m + ":" + s;
        t = setTimeout(function () {
            startTime()
        }, 500);
    }

    function checkTime(i) {
        if (i < 10) {
            i = "0" + i;
        }
        return i;
    }
</script>
</body>
</html>
