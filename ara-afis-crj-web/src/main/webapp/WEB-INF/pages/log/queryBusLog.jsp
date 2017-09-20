<%@ page language="java"  pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE HTML>
<html>
  <head></head>
<body >
	<div class="hideDiv">
		<input type="hidden" id="action" value="toAddOption"/>
		<input type="hidden" id="prifex" value="logInfo" />
		<input type="hidden" id="page" value="BusLog"/>
		<input type="hidden" id="operate"/>
		<input type="hidden" id="detail_key" value="重发次数,请求数据,响应数据"/>
		<input type="hidden" id="detail_data_key" value="retryNum,requestData,responseData"/>
	</div>
	<section class="wrapper_top">
		<div class="row">
			<div class="col-lg-12">
				<!--breadcrumbs start -->
				<ul class="breadcrumb">
					<li><a href="javascript:void(0)" onclick="loadPage('main-content','common/loadContent','')"><i class="icon-home"></i>首页</a></li>
					<li><a href="javascript:void(0)"><i class="icon-list-alt"></i>日志管理</a></li>
					<li class="active"><i class="icon-list-alt"></i>查询业务日志</li>
				</ul>
				<!--breadcrumbs end -->
			</div>
		</div>
	</section>

	<section class="wrapper" id="app-content">
		<form id="dataForm" name="dataForm">
			<input type="hidden" id="anyOption" name="anyOption" />
			<input type="hidden" id="anyId" name="anyId" />
		</form>
        <%--Vue组件  操作结果元素--%>
        <result-div></result-div>
		<div class="row">
			<div class="col-lg-12">
				 <!-- page start-->
              <section class="panel">
                  <header class="panel-heading panel-bg-orgin"><i class="icon-list-alt" ></i>业务日志</header>
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
                                <div class="input-group pull-right" style="width: 15%">
                                    <div class="input-group-btn">
                                        <button type="button" class="btn green dropdown-toggle" data-toggle="dropdown">筛选项 <span class="caret"></span></button>
                                        <ul class="dropdown-menu">
                                            <li><a href="javascript:void(0)" onclick="filterSelect('actionType','业务类型')">业务码</a></li>
                                            <li><a href="javascript:void(0)" onclick="filterSelect('resultCode','业务返回码')">业务返回码</a></li>
                                            <li><a href="javascript:void(0)" onclick="filterSelect('resultMSG','业务返回信息')">业务返回信息</a></li>
                                            <li><a href="javascript:void(0)" onclick="filterSelect('clientIp','请求方IP')">请求方IP</a></li>
                                            <li><a href="javascript:void(0)" onclick="filterSelect('createOn','操作时间')">操作时间</a></li>
                                            <li class="divider"></li>
                                            <li><a href="javascript:void(0)" onclick="filterSelect('','')">全部</a></li>
                                        </ul>
                                    </div>
                                    <input id="filter_item_des" name="filter_item_des"  type="text" class="form-control" readonly>
                                    <input id="filter_item" name="filter_item"  type="hidden" class="form-control">
                                </div>
                            </div>
                            <table cellpadding="0" cellspacing="0" border="0" class="display table table-bordered  table-hover" id="dataTable" width="100%">
                                <thead>
	                                <tr>
	                                    <th style="width:26px;"></th>
	                                    <th>业务码</th>
	                                    <th>业务说明</th>
	                                    <th>业务结果</th>
	                                    <th>业务返回码</th>
	                                    <th>业务返回信息</th>
	                                    <th>处理耗时(毫秒)</th>
	                                    <th>请求方IP</th>
	                                    <th>操作时间</th>
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
        //绑定Vue实例
        var appContent = new Vue({
            el: '#app-content',
            data: {
                fileType: 'Excel'
            },
            methods: {
                changeFileType: function (fileType) {
                    this.fileType = fileType;
                }
            }
        });

		var oTable = $('#dataTable').dataTable({
			serverSide: true,
			fnServerData: function retrieveData( source,data, callback){
                var filterItem = $("#filter_item").val();
				$.ajax({
					url : 'logInfo/queryBusLog',//这个就是请求地址对应sAjaxSource
					data : {"aoData":JSON.stringify(data),"filterItem":filterItem},//这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数
					type : 'POST',
					dataType : 'json',
                    success : function(data) {
                        if(0 == data.anyStatus){
                            getResultDiv("result",data.anyStatus,data.msg);
                        }else{
                            callback(data);//把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
                        }
                    }
				});
			}, // 获取数据的处理函数
			"pagingType":   "full_numbers",
			columns: [
				{
					"class": 'details-control',
					"data": null,
					"defaultContent": ''
				},
				{ "data": "actionType" },
				{ "data": "actionTypeDes" },
				{ "data": "resultFlag"},
				{ "data": "resultCode" },
				{ "data": "resultMSG" },
				{ "data": "costTime" },
				{ "data": "clientIp" },
				{ "data": "createOn"}
			],
			"createdRow": function (row,data,index ) {
				if('失败' == data.resultFlag){
					$(row).children().eq("3").css("background-color","#c7abab");
				}
			},
			"deferRender": true,
            "processing": true,
			"aoColumnDefs": [
				{ "bSortable": false, "aTargets": [ 0 ] }
			],
			"aaSorting": [[1, 'desc']],
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
