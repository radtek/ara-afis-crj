<%@ page language="java"  pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <title>日志清理页面</title>
</head>
<body>
<!--  v-cloak 隐藏未编译的 Mustache 标签直到实例准备完毕 防止页面加载的时候出现vuejs的变量名，
开发时候可以选择关闭 ，但构建vm完毕后已经要加上
https://cn.vuejs.org/v2/api/#v-cloak-->
<div id="rrapp" v-cloak>
    <div class="row">
        <section class=" col-xs-12 col-lg-12 connectedSortable">
            <!-- Calendar -->
            <div class="box box-solid">
                <div class="box-header">
                    <!--<i class="fa fa-calendar"></i>-->
                    <!--<h3 class="box-title">过期日志清理工作</h3>-->
                    <!-- tools box -->
                    <div class="pull-right box-tools">
                        <!-- button with a dropdown -->
                        <div class="btn-group">
                            <button type="button" class="btn  btn-sm dropdown-toggle" data-toggle="dropdown">
                                <i class="fa fa-bars"></i></button>
                            <ul class="dropdown-menu pull-right" role="menu">
                                <li><a @click="clear('schedulelog')">清空过期任务日志</a></li>
                                <li><a @click="clear('syslog')">清空过期系统日志</a></li>
                                <li> <a @click="clear('runtimelog')">清空过期设备运行日志</a></li>
                                <li><a @click="clear('hbs')">清空过期设备硬件日志</a></li>
                            </ul>
                        </div>
                    </div>
                    <!-- /. tools -->
                </div>
                <!-- /.box-header -->
                <div class="box-body no-padding">
                    <!--The calendar -->
                    <!-- 为ECharts准备一个具备大小（宽高）的Dom // 引入柱状图-->
                    <div id="echarts-bar" style="width: 100%;height:400px;"></div>
                </div>
                <!-- /.box-body -->
                <div class="box-footer text-black">
                    <div class="row">
                        <h5>过期日志占比</h5>
                    </div>
                    <div class="row">
                        <div class="col-sm-6">
                            <!-- Progress bars -->
                            <div class="clearfix">
                                <span class="pull-left">任务日志 #1</span>
                                <small class="pull-right">{{logPCT[0]}}</small>
                            </div>
                            <div class="progress xs">
                                <div class="progress-bar progress-bar-green"  :style="{ width: logPCT[0] }"></div>
                            </div>

                            <div class="clearfix">
                                <span class="pull-left">系统日志 #2</span>
                                <small class="pull-right">{{logPCT[1]}}</small>
                            </div>
                            <div class="progress xs">
                                <div class="progress-bar progress-bar-green" :style="{ width: logPCT[1] }"></div>
                            </div>
                        </div>
                        <!-- /.col -->
                        <div class="col-sm-6">
                            <div class="clearfix">
                                <span class="pull-left">设备运行日志 #3</span>
                                <small class="pull-right">{{logPCT[2]}}</small>
                            </div>
                            <div class="progress xs">
                                <div class="progress-bar progress-bar-green" :style="{ width: logPCT[2] }"></div>
                            </div>

                            <div class="clearfix">
                                <span class="pull-left">设备硬件日志 #4</span>
                                <small class="pull-right">{{logPCT[3]}}</small>
                            </div>
                            <div class="progress xs">
                                <div class="progress-bar progress-bar-green" :style="{ width: logPCT[3] }"></div>
                            </div>
                        </div>
                        <!-- /.col -->
                    </div>
                    <!-- /.row -->
                </div>
            </div>
            <!-- /.box -->
        </section>
    </div><!-- /.row -->
</div>
<script src="${pageContext.request.contextPath}/js/chart/echarts-all.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/myJs/logClear.js"></script>
</body>
</html>
