<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE HTML>
<html>
<head></head>

<body>
<div class="hideDiv">
    <input type="hidden" id="action" value="toAddOption"/>
    <input type="hidden" id="prifex" value="role"/>
    <input type="hidden" id="page" value="Role"/>
    <input type="hidden" id="operate"/>
</div>
<section class="wrapper_top">
    <div class="row">
        <div class="col-lg-12">
            <!--breadcrumbs start -->
            <ul class="breadcrumb">
                <li><a href="javascript:void(0)" onclick="loadPage('main-content','common/loadContent','')"><i
                        class="icon-home"></i>首页</a></li>
                <li><a href="javascript:void(0)"><i class="icon-gear"></i>系统设置</a></li>
                <li class="active"><i class="icon-shield"></i>管理角色</li>
            </ul>
            <!--breadcrumbs end -->
        </div>
    </div>
</section>

<section class="wrapper">
    <form id="dataForm" name="dataForm">
        <input type="hidden" id="anyOption" name="anyOption"/>
        <input type="hidden" id="anyId" name="anyId"/>
    </form>
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
            <!-- page start-->
            <section class="panel">
                <header class="panel-heading panel-bg-orgin"><i class="icon-book"></i>角色</header>
                <div class="panel-body">
                    <div class="adv-table">
                        <table cellpadding="0" cellspacing="0" border="0"
                               class="display table table-bordered  table-hover" id="dataTable" width="100%">
                            <thead>
                                <tr>
                                    <th>角色名称</th>
                                    <th>角色描述</th>
                                    <th style="width:150px;">操作</th>
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
    var oTable = $('#dataTable').dataTable({
        "data": ${roleListJson},
        columns: [
            { "data": "name" },
            { "data": "describe"},
            {
                "data": null,
                "defaultContent": '<button class="btn btn-primary btn-xs hor-btn-space button_modify" ><i class="icon-pencil"></i></button><button class="btn btn-danger btn-xs button_del"><i class="icon-trash "></i></button>'
            }
        ],
        "processing": true,
        "oLanguage": { //国际化配置
            "sProcessing" : "正在加载数据，请稍后...",
            "sLengthMenu" : "显示 _MENU_ 条",
            "sZeroRecords" : "没有您要搜索的内容",
            "sInfo" : "从 _START_ 到  _END_ 条记录 总记录数为 _TOTAL_ 条",
            "sInfoEmpty" : "记录数为0",
            "sInfoFiltered" : "(全部记录数 _MAX_ 条)",
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
