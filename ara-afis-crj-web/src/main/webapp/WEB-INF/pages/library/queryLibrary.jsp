<%@ page language="java"  pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE HTML>
<html>
  <head></head>

<body >
	<div class="hideDiv">
        <input type="hidden" id="action" value="toUpdate" />
        <input type="hidden" id="prifex" value="taslibrary" />
		<input type="hidden" id="page" value="Taslibrary"/>
		<input type="hidden" id="operate"  />
		<input type="hidden" id="detail_key" value="库描述"/>
		<input type="hidden" id="detail_data_key" value="describe"/>
	</div>
	<section class="wrapper_top">
		<div class="row">
			<div class="col-lg-12">
				<!--breadcrumbs start -->
				<ul class="breadcrumb">
					<li><a href="javascript:void(0)" onclick="loadPage('main-content','common/loadContent','')"><i class="icon-home"></i>首页</a></li>
					<li><a href="javascript:void(0)"><i class="icon-hand-right"></i>管理指纹库别</a></li>
					<%--<li class="active"><i class="icon-group"></i>管理库别</li>--%>
				</ul>
				<!--breadcrumbs end -->
			</div>
		</div>
	</section>

	<section class="wrapper">
		<form id="dataForm" name="dataForm">
			<input type="hidden" id="anyOption" name="anyOption" />
			<input type="hidden" id="anyId" name="anyId" />
		</form>

		<div class="row">
			<div class="col-lg-12">
				 <!-- page start-->
              <section class="panel">
					  <header class="panel-heading panel-bg-orgin"><i class="icon-book" ></i>管理指纹库别


				  </header>
                  <div class="panel-body">

                        <div class="adv-table">
							<div class="clearfix">

								<div class="btn-group pull-right">
									<button class="btn green"
											onclick="loadPage('main-content','taslibrary/addPage','');">
										<i class="icon-adn"></i>
										添加指纹库别
									</button>
								</div>
							</div>
                            <table cellpadding="0" cellspacing="0" border="0" class="display table table-bordered  table-hover" id="dataTable" width="100%">
                                <thead>
	                                <tr>
										<th style="display: none"></th>
										<th style="width:26px;"></th>
										<th>指纹库ID</th>
										<th>1:1比对阈值</th>
										<th>1:N比对阈值</th>
										<th style="display: none"></th>
										<th>指纹库状态</th>
										<th style="display: none"></th>
										<th>指纹库更新状态</th>
										<th>指纹库描述</th>
										<th>建库时间</th>
										<th>库修改时间</th>
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
			"data": ${libraryListJson},
			columns: [
                {
                    "class": "hidden",
                    "data": "id"
                },
				{
					"class": 'details-control',
					"data": null,
					"defaultContent": ''
				},
                { "data": "libraryId"  },
                { "data": "verifyLibraryGoal"},
                { "data": "identifyLibraryGoal" },
                {
                    "class": "hidden",
					"data": "libraryActivietyFlag"
                },
                { "data": "libraryActivietyFlagStr" },
                {
                    "class": "hidden",
					"data": "modifyState"
                },
                { "data": "modifyStateStr" },
                { "data": "libraryDesc"},
                { "data": "createDate"},
                { "data": "modifyDate"},
				{
					"data": null,
					"defaultContent": '<button class="btn btn-primary btn-xs hor-btn-space button_modify" ><i class="icon-pencil"></i></button><button class="btn btn-danger btn-xs button_del"><i class="icon-trash "></i></button>'
				}
			],
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
            }
		});
	</script>
	<script type="text/javascript" src="js/common/pageEvent.js"></script>
</body>
</html>
