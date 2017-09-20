<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE HTML>
<html xmlns:v-on="http://www.w3.org/1999/xhtml">
<head></head>

<body >
<div class="hideDiv">
    <input type="hidden" id="action" value="toAddOption"/>
    <input type="hidden" id="prifex" value="analysis" />
    <input type="hidden" id="page" value="Analysis"/>
    <input type="hidden" id="operate"/>
    <input type="hidden" id="filter_item"/>
</div>
<section class="wrapper_top">
    <div class="row">
        <div class="col-lg-12">
            <!--breadcrumbs start -->
            <ul class="breadcrumb">
                <li><a href="javascript:void(0)" onclick="loadPage('main-content','common/loadContent','')"><i
                        class="icon-home"></i>首页</a></li>
                <li><a href="javascript:void(0)"><i class="icon-gear"></i>统计分析</a></li>
                <li class="active"><i class="icon-shield"></i>指纹库数据差异性比较</li>
            </ul>
            <!--breadcrumbs end -->
        </div>
    </div>
</section>

<section class="wrapper" id="app-content">
    <%--Vue组件  操作结果元素--%>
    <result-div></result-div>
    <div class="row">
        <div class="col-lg-12">
            <!-- page start-->
            <section class="panel" >
                <header class="panel-heading panel-bg-orgin" id="app-header"><i class="icon-book"></i>指纹库数据差异性比较<span class="pull-right">数据统计于: {{diffColTime}}</span></header>
                <div class="panel-body">
                    <div class="adv-table">
                        <div class="clearfix">
                            <div class="input-group pull-left" style="width: 20%">
                                <div class="input-group-btn">
                                    <button type="button" class="btn green button_export"><i class="icon-cloud-download"></i> 日志下载 </button>
                                    <button tabindex="-1" data-toggle="dropdown" class="btn btn-white dropdown-toggle" type="button">
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a href="javascript:void(0)" @click="changeFileType('Excel')">Excel文件</a></li>
                                        <li><a href="javascript:void(0)" @click="changeFileType('Log')">Log文件</a></li>
                                    </ul>
                                </div>
                                <input id="down_file_type" name="down_file_type"  type="text" class="form-control" style="width: 60px" :value="fileType" readonly>
                            </div>
                            <div class="btn-group pull-right">
                                <button class="btn green" v-on:click="refreshData">
                                    重新统计 <i class="icon-refresh"></i>
                                </button>
                            </div>
                        </div>
                        <table cellpadding="0" cellspacing="0" border="0"
                               class="display table table-bordered  table-hover" id="dataTable" width="100%">
                            <thead>
                                <tr>
                                    <th>库别</th>
                                    <th>档号</th>
                                    <th>指纹序号</th>
                                    <th>比对库</th>
                                    <th>梅沙库</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </section>
            <!-- page end-->
        </div>
    </div>
</section>
<script type="text/javascript">
    var appContent = new Vue({
        el: '#app-content',
        data: {
            fileType: 'Excel',
            diffColTime: '${diffColTime}'
        },
        methods: {
            changeFileType: function (fileType) {
                this.fileType = fileType;
            },
            refreshData: function () {
                $.ajax({
                    type: "POST",
                    url: "analysis/compareSourceFPData",
                    dataType: "json",
                    success: function(data){
                        if(1 == data.anyStatus){
                            appContent.diffColTime = data.diffColTime;
                            oTable.fnClearTable();
                            if(data.dataListJson.length > 2){
                                oTable.fnAddData(eval(data.dataListJson));
                            }
                        }
                        getResultDiv("result",data.anyStatus,data.msg);
                    }
                });
            }
        }
    });

    var oTable = $('#dataTable').dataTable({
        "data": ${dataListJson},
        columns: [
            { "data": "library" },
            { "data": "personId"},
            { "data": "index"},
            { "data": "desHaveFlag"},
            { "data": "srcHaveFlag"}
        ],
        "processing": true,
        "oLanguage": { //国际化配置
            "sProcessing" : "正在获取差异数据，请稍后...",
            "sLengthMenu" : "显示 _MENU_ 条",
            "sZeroRecords" : "没有差异数据",
            "sInfo" : "从 _START_ 到  _END_ 条记录 总差异记录数为 _TOTAL_ 条",
            "sInfoEmpty" : "差异记录数为0",
            "sInfoFiltered" : "(全部差异记录数 _MAX_ 条)",
            "sInfoPostFix" : "",
            "sSearch" : "搜索",
            "sUrl" : "",
            "oPaginate": {
                "sFirst" : "第一页",
                "sPrevious" : "上一页",
                "sNext" : "下一页",
                "sLast" : "最后一页"
            }
        },
    });
</script>
<script type="text/javascript" src="js/common/pageEvent.js"></script>
</body>
</html>
