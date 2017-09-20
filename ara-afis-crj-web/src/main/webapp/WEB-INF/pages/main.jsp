<%@ page language="java"  pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <!--[if IE]><meta http-equiv='X-UA-Compatible' content="IE=edge,IE=9,IE=8,chrome=1" /><![endif]-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="img/favicon.ico">
    <title><spring:message code="system_name" /></title>
    <!--external css-->
    <link href="assets/jquery-easy-pie-chart/jquery.easy-pie-chart.css" rel="stylesheet" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/owl.carousel.css" type="text/css">
      <link href="assets/advanced-datatable/css/jquery.dataTables.css" rel="stylesheet" />
    <!-- Custom styles for this template -->
    <link href="css/style.css" rel="stylesheet">
    <link href="css/style-responsive.css" rel="stylesheet" />
    <link href="assets/bootstrap-daterangepicker/css/daterangepicker.css" rel="stylesheet"/>
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
    <!--[if lt IE 9]>

      <script src="js/other/html5shiv.js"></script>
      <script src="js/other/respond.min.js"></script>
    <![endif]-->
  </head>
  <body class="has-js">
  <section id="container" >
      <!--header start-->
      <header class="header white-bg">
            <div class="sidebar-toggle-box">
                <div data-original-title="<spring:message code="toggle_naviga" />" data-placement="right" class="icon-reorder tooltips"></div>
            </div>
            <!--logo start-->
            <a href="javascript:void(0)" class="logo" onclick="loadPage('main-content','common/loadContent','')"><span><spring:message code="system_name" /></span></a>
            <!--logo end-->

          <div class="nav notify-row" id="top-notify">
              <!--  notification start -->
              <ul class="nav top-menu">
                  <!-- notification dropdown start-->
                  <li id="header_notification_bar" class="dropdown">
                      <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                          <i class="icon-bell-alt"></i>
                          <span class="badge bg-important" v-if="show">{{warInfoNum}}</span>
                      </a>
                      <ul class="dropdown-menu extended notification">
                          <div class="notify-arrow notify-arrow-yellow"></div>
                          <li>
                              <p class="yellow">您有{{warInfoNum}}条信息需要处理</p>
                          </li>
                          <li v-for="warnInfo in warnInfoList">
                              <a href="javascript:void(0)">
                                  <span class="label label-danger"><i class="icon-bell"></i></span>
                                  {{ warnInfo.message }}
                                  <span class="label label-success pull-right" @click="dealWarnInfo(warnInfo.id,warnInfo.message)"><i class="icon-trash"></i></span>
                              </a>
                          </li>
                          <%--<li>--%>
                              <%--<a href="#">See all notifications</a>--%>
                          <%--</li>--%>
                      </ul>
                  </li>
                  <!-- notification dropdown end -->
              </ul>
              <!--  notification end -->
          </div>

            <div class="top-nav ">
                <!--search & user info start-->
                <ul class="nav pull-right top-menu">
                    <!-- user login dropdown start-->
                    <li class="dropdown">
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#" aria-haspopup="true" aria-expanded="false">
                            <img src="img/avatar-mini.jpg">
                            <%--<span class="glyphicon glyphicon-user" style="vertical-align: middle;font-size:24px;width: 29px;height: 32px;"></span>--%>
                            <span class="username">${managerName}</span>
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu extended logout">
                          <div class="log-arrow-up"></div>
                          <%--<li><a href="javascript:void(0)" onclick="changeLu();"><i class="icon-flag"></i><spring:message code="change_lan" /></a></li>--%>
                          <li><a href="javascript:void(0)" onclick="loadPage('main-content','managerInfo/toChangeOption','')"><i class="icon-cog"></i><spring:message code="change_pwd" /></a></li>
                          <li><a href="javascript:void(0)" onclick="hrefPage('logout')"><i class="icon-key"></i><spring:message code="login_out" /></a></li>
                      </ul>
                    </li>
                    <!-- user login dropdown end -->
                </ul>
                <!--search & user info end-->
            </div>
        </header>
      <!--header end-->

      <!--sidebar start-->
      <aside>
          <div id="sidebar"  class="nav-collapse ">
              <!-- sidebar menu start-->
              <ul class="sidebar-menu" id="nav-accordion">
                  <li class="<s:if test="${!fn:contains(purview,'A')}">hideDiv</s:if>">
                      <a class="active" href="javascript:void(0)" onclick="loadPage('main-content','common/loadContent','')">
                          <i class="icon-dashboard"></i>
                          <span><spring:message code="home" /></span>
                      </a>
                  </li>
                  <li class="sub-menu <s:if test="${!fn:contains(purview,'B')}">hideDiv</s:if>">
                      <a href="javascript:;">
                      	<i class="icon-laptop"></i> <span><spring:message code="server" /></span>
                      </a>
                      <ul class="sub">
                          <li class="<s:if test="${!fn:contains(purview,'B01')}">hideDiv</s:if>">
                          	<a href="javascript:void(0)" onclick="loadPage('main-content','engine/toGetList?anyId=0','');">
                          		<spring:message code="monitor_server" />
                          	</a>
                          </li>
                          <%--<li class="<s:if test="${!fn:contains(purview,'B02')}">hideDiv</s:if>">--%>
                              <%--<a href="javascript:void(0)" onclick="loadPage('main-content','engine/toAddOption?anyOption=addEngine&anyId=0','');">--%>
                                  <%--添加引擎--%>
                              <%--</a>--%>
                          <%--</li>--%>
                          <li class="<s:if test="${!fn:contains(purview,'B03')}">hideDiv</s:if>">
                              <a href="javascript:void(0)" onclick="loadPage('main-content','systemOrder/toGetList','');">
                                  查询系统命令
                              </a>
                          </li>
                      </ul>
                  </li>
                  <li class="sub-menu <s:if test="${!fn:contains(purview,'D')}">hideDiv</s:if>">
                      <a href="javascript:;">
                          <i class="icon-hand-right"></i> <span>指纹管理</span>
                      </a>
                      <ul class="sub">
                          <li class="<s:if test="${!fn:contains(purview,'D03')}">hideDiv</s:if>">
                              <a href="javascript:;" onclick="loadPage('main-content','taslibrary/toGetList','');">
                                  <i class="icon-hand-right"></i> <span>管理指纹库别</span>
                              </a>
                          </li>
                      </ul>
                  </li>
                  <li class="sub-menu <s:if test="${!fn:contains(purview,'F')}">hideDiv</s:if>">
                      <a href="javascript:;">
                          <i class="icon-bar-chart"></i>
                          <span>统计分析</span>
                      </a>
                      <ul class="sub">
                          <li class="<s:if test="${!fn:contains(purview,'F01')}">hideDiv</s:if>">
                              <a href="javascript:"
                                 onclick="loadPage('main-content','analysis/toCompareSourceFPData','');">数据差异性比较</a>
                          </li>
                          <li class="<s:if test="${!fn:contains(purview,'F02')}">hideDiv</s:if>">
                              <a href="javascript:"
                                 onclick="loadPage('main-content','busLog/ipCount','');">请求业务类型分析</a>
                          </li>
                      </ul>
                  </li>

                  <li class="sub-menu <s:if test="${!fn:contains(purview,'E')}">hideDiv</s:if>">
                      <a href="javascript:;">
                          <i class="icon-list-alt"></i>
                          <span><spring:message code="log" /></span>
                      </a>
                      <ul class="sub">
                          <li class="<s:if test="${!fn:contains(purview,'E01')}">hideDiv</s:if>">
                              <a href="javascript:void(0)" onclick="loadPage('main-content','logInfo/toQueryBusLog','');"><spring:message code="bus_log" /></a>
                          </li>
                          <li class="<s:if test="${!fn:contains(purview,'E04')}">hideDiv</s:if>">
                              <a href="javascript:void(0)" onclick="loadPage('main-content','logInfo/toQueryDataSynLog','');">数据同步日志</a>
                          </li>
                          <li class="<s:if test="${!fn:contains(purview,'E02')}">hideDiv</s:if>">
                              <a href="javascript:void(0)" onclick="loadPage('main-content','logInfo/toQuerySysLog','');"><spring:message code="system_log" /></a>
                          </li>
                          <%--<li class="<s:if test="${!fn:contains(purview,'E03')}">hideDiv</s:if>">--%>
                              <%--<a href="javascript:void(0)" onclick="loadPage('main-content','clearlog/toClearlogPage','');">日志清理</a>--%>
                          <%--</li>--%>
                      </ul>
                  </li>

                  <li class="sub-menu <s:if test="${!fn:contains(purview,'H')}">hideDiv</s:if>">
                      <a href="javascript:;" >
                          <i class="icon-gear"></i>
                          <span><spring:message code="setting" /></span>
                      </a>
                      <ul class="sub">
                          <li class="sub-menu <s:if test="${!fn:contains(purview,'H01')}">hideDiv</s:if>">
                              <a  href="#">
                              <i class="icon-group"></i>
                              	<spring:message code="admin" />
                              </a>
                              <ul class="sub">
                                  <li class="<s:if test="${!fn:contains(purview,'H0101')}">hideDiv</s:if>"><a  href="javascript:void(0)" onclick="loadPage('main-content','managerInfo/toGetList','');"><spring:message code="manage_admin" /></a></li>
                                  <li class="<s:if test="${!fn:contains(purview,'H0102')}">hideDiv</s:if>"><a  href="javascript:void(0)" onclick="loadPage('main-content','managerInfo/toAddOption','');"><spring:message code="add_admin" /></a></li>
                              </ul>
                          </li>
                          <li class="sub-menu <s:if test="${!fn:contains(purview,'H02')}">hideDiv</s:if>">
                              <a  href="#">
	                              <i class="icon-shield"></i>
	                              <spring:message code="role" />
                              </a>
                              <ul class="sub">
                                  <li class="<s:if test="${!fn:contains(purview,'H0201')}">hideDiv</s:if>"><a  href="javascript:void(0)" onclick="loadPage('main-content','role/toGetList','');"><spring:message code="manage_role" /></a></li>
                                  <li class="<s:if test="${!fn:contains(purview,'H0202')}">hideDiv</s:if>"><a  href="javascript:void(0)" onclick="loadPage('main-content','role/toAddOption','');"><spring:message code="add_role" /></a></li>
                              </ul>
                          </li>
                          <%--<li class="sub-menu <s:if test="${!fn:contains(purview,'H03')}">hideDiv</s:if>">--%>
                              <%--<a  href="boxed_page.html">--%>
	                              <%--<i class="icon-book"></i>--%>
	                              <%--<spring:message code="code" />--%>
                              <%--</a>--%>
                              <%--<ul class="sub">--%>
                                  <%--<li class="<s:if test="${!fn:contains(purview,'H0301')}">hideDiv</s:if>"><a href="javascript:void(0)" onclick="loadPage('main-content','code/toGetList','');"><spring:message code="manage_code" /></a></li>--%>
                                  <%--<li class="<s:if test="${!fn:contains(purview,'H0302')}">hideDiv</s:if>"><a href="javascript:void(0)" onclick="loadPage('main-content','code/toAddOption','');"><spring:message code="add_code" /></a></li>--%>
                              <%--</ul>--%>
                          <%--</li>--%>
                          <li class="<s:if test="${!fn:contains(purview,'H04')}">hideDiv</s:if>">
                              <a  href="javascript:void(0)" onclick="loadPage('main-content','parameter/toGetList','');">
	                              <i class="icon-reorder"></i>
	                              <spring:message code="parameter_set" />
                              </a>
                          </li>
                      </ul>
                  </li>
                  <%--库别管理--%>
                  <li class="sub-menu <s:if test="${!fn:contains(purview,'J')}">hideDiv</s:if>">
                      <a href="javascript:;" onclick="loadPage('main-content','taslibrary/toGetList','');">
                          <i class="icon-laptop"></i> <span><spring:message code="library" /></span>
                      </a>
                  </li>
                  <%--库别管理--%>
                  <!--multi level menu end-->
              </ul>
              <!-- sidebar menu end-->
          </div>
      </aside>
      <!--sidebar end-->

      <!--main content start-->
      <section id="main-content"> </section>
      <!--main content end-->
  </section>

  <!--footer start-->
      <footer class="site-footer">
          <div class="text-center">
              Copyright &copy; Aratek 2016. All Rights Reserved.
              <a href="#" class="go-top">
                  <i class="icon-angle-up"></i>
              </a>
          </div>
      </footer>
      <!--footer end-->

    <!-- js placed at the end of the document so the pages load faster -->
    <script src="js/jquery/jquery.js"></script>
    <script src="js/vue/vue.js"></script>
    <script src="js/bootstrap/bootstrap.min.js"></script>
    <script src="js/bootstrap/bootstrap-switch.js"></script>
    <script class="include" type="text/javascript" src="js/jquery/jquery.dcjqaccordion.2.7.min.js"></script>
    <script src="js/jquery/jquery.scrollTo.min.js"></script>
    <script src="js/jquery/jquery.cookie.js"></script>
    <script src="js/jquery/jquery.nicescroll.js" type="text/javascript"></script>
    <script src="js/jquery/jquery.sparkline.js" type="text/javascript"></script>
    <script src="assets/jquery-easy-pie-chart/jquery.easy-pie-chart.js"></script>
    <script src="assets/advanced-datatable/js/jquery.dataTables.js"></script>
    <script src="js/other/owl.carousel.js" ></script>
    <script src="js/jquery/jquery.customSelect.min.js" ></script>
    <script src="js/other/respond.min.js" ></script>
    <script type="text/javascript" src="js/jquery/validate/jquery.validate.min.js" ></script>
    <script type="text/javascript" src="js/jquery/validate/additional-methods.js" ></script>
    <script type="text/javascript" src="js/jquery/validate/messages_zh.min.js" ></script>
    <script src="assets/bootstrap-daterangepicker/js/moment.min.js" ></script>
    <script  src="assets/bootstrap-daterangepicker/js/daterangepicker.js" ></script>


    <!--common script for all pages-->
    <script src="js/common/common.js"></script>
    <script type="text/javascript" src="js/common/operator.js"></script>

    <!--script for this page-->
    <script src="js/other/easy-pie-chart.js"></script>
    <script type="text/javascript">
        //加载首页模块
        loadPage("main-content","common/loadContent","");

        //绑定Vue实例
        var topNotify = new Vue({
            el: '#top-notify',
            data: {
                warnInfoList: '',
                warInfoNum: '0',
                show:''
            },
            methods: {
                dealWarnInfo: function (id,message) {
                    $.ajax({
                        type: "POST",
                        url: "/TAC/warnInfo/delOption",
                        data: "id="+id+"&message="+message,
                        dataType: "json",
                        success: function(data){
                            if("1" == data.anyStatus){
                                queryWarnInfoInterval();
                            }else{
                                alert(data.msg);
                            }
                        }
                    });
                }
            }
        });

        //定义一个组件，名称为result-div ，表示异步结果信息
        Vue.component("result-div", {
            template: "<div class='row'><div class='col-lg-12'><div id='result' class='alert alert-block fade in hideDiv'> <button data-dismiss='alert' class='close close-sm' type='button'> <i class='icon-remove'></i> </button> <h4><i id='result_sign'></i><span id='result_title'></span></h4> <p id='result_msg'></p> </div> </div> </div>",
        });

        //超时定时器设置,18分钟
        setInterval("lockScreenInterval()",1000000);//1000为1秒钟
        //锁屏检测定时器
        function lockScreenInterval()
        {
            //客户端校验用户是否长时间未操作，Session超时
            $.getJSON("checkTimeOut", function(data){
                //获取登录用户名
                var account = $(".username").text();
                if(data.msg == "true"){
                    //系统进入锁屏模式
                    hrefPage('logTimeout?account='+account);
                }
            });
        }

        //超时定时器设置,10分钟
      setInterval("queryWarnInfoInterval()",600000);//1000为1秒钟
//        setInterval("queryWarnInfoInterval()",5000);//1000为1秒钟
        //锁屏检测定时器
        function queryWarnInfoInterval()
        {
            $.ajax({
                type: "POST",
                url: "/TAC/warnInfo/queryWarnInfo",
                dataType: "json",
                success: function(data){
                    topNotify.warnInfoList = data.warnInfoList;
                    topNotify.warInfoNum = data.warInfoNum;
                    topNotify.show = data.show;
                }
            });
        }
    </script>
  </body>
</html>
