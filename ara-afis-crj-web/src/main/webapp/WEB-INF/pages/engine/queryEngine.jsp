<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head></head>

<body>
	<div class="hideDiv">
		<input type="hidden" id="action" value="toAddOption" />
		<input type="hidden" id="prifex" value="engine" />
		<input type="hidden" id="page" value="Engine" />
		<input type="hidden" id="operate"  />
	</div>
	<form id="dataForm" name="dataForm">
		<input type="hidden" id="anyOption" name="anyOption" />
		<input type="hidden" id="anyId" name="anyId" value="${anyId}"/>
		<input type="hidden" id="orderCode" name="orderCode"/>
	</form>

	<section class="wrapper_main">
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
		<!-- page start-->
		<div class="row">
			<aside class="profile-nav col-lg-3">
				<section class="panel">
					<div class="user-heading round">
						<h1>指纹引擎列表</h1>
					</div>
					<ul class="nav nav-pills nav-stacked">
						<s:if test="${fn:length(engineList) > 0}">
							<s:forEach items="${engineList}" var='engine' varStatus="status">
								<li id="node_${engine.id}" <s:if test="${anyId == engine.id}">class='active'</s:if>>
									<a href="javascript:void(0)" onclick="reloadEngineData(${engine.id});">
										<s:choose>
											<s:when test="${engine.engineType == '0'}"><i
													class="icon-cloud text-success"></i>主控节点</s:when>
											<s:otherwise><i class="icon-laptop text-primary"></i>引擎节点</s:otherwise>
										</s:choose>: ${engine.engineCode}
										<s:choose>
										<s:when test="${engine.workStationStatus == '0'}"><span
											class="label label-success pull-right r-activity"><i
											class="icon-ok-sign"></i></s:when>
										<s:when test="${engine.workStationStatus == '1'}"><span
												class="label label-warning pull-right r-activity"><i
												class="icon-exclamation-sign"></i></s:when>
											<s:otherwise><span class="label label-danger pull-right r-activity"><i
													class="icon-remove-sign"></i></s:otherwise>
                                          </s:choose>
									</a>
								</li>
							</s:forEach>
						</s:if>
					</ul>
				</section>
			</aside>
			<aside class="profile-info col-lg-9">
				<section class="panel">
					<div class="bio-graph-heading">指纹引擎信息
						<span class="pull-right">
							<div class="btn-group">
                                <button class="btn btn-shadow btn-danger" type="button">引擎模块管理</button>
                                <button data-toggle="dropdown" class="btn btn-shadow btn-danger dropdown-toggle" type="button"><span class="caret"></span></button>
                                <ul role="menu" class="dropdown-menu">
									<li><a class="a_order_2002">强制增量同步</a></li>
									<li><a class="a_order_1001">重启比对模块</a></li>
									<li><a class="a_order_1002">重启同步模块</a></li>
									<li><a class="a_order_1005">重启主控模块</a></li>
									<li><a class="a_order_1006">重启建模模块</a></li>
									<li class="divider"></li>
									<li><a class="a_order_2001">强制全量同步</a></li>
									<li><a class="a_order_1007">重启全部模块</a></li>
									<li><a class="a_order_1008">关闭全部模块</a></li>
								</ul>
                            </div>
                            <%--<button type="button" class="btn btn-shadow btn-danger button_modify">修改引擎信息</button>--%>
							<%--<button type="button" class="btn btn-shadow btn-default button_del">删除引擎节点</button>--%>
						</span>
                    </div>
					<div class="panel-body bio-graph-info">
						<h1>物理信息</h1>
						<div class="row">
							<div class="bio-row">
								<p>
									<span>
										<s:choose>
											<s:when test="${engine.engineType == '0'}">主控节点</s:when>
											<s:otherwise>引擎节点</s:otherwise>
										</s:choose>
									</span>： ${engine.engineCode}
								</p>
							</div>
							<s:if test='engine.engineType != "0"'>
							<div class="bio-row">
								<p>
									<span>主控节点名</span>： ${engine.clusterCode}
								</p>
							</div>
							</s:if>
							<div class="bio-row">
								<p>
									<span>IP地址 </span>： ${engine.ip}
								</p>
							</div>
							<div class="bio-row">
								<p>
									<span>MAC地址 </span>： ${engine.mac}
								</p>
							</div>
							<div class="bio-row">
								<p>
									<span>运行状态 </span>：
									<s:choose>
										<s:when test="${engine.workStationStatus == '0'}">正常</s:when>
										<s:when test="${engine.workStationStatus == '1'}">预警</s:when>
										<s:otherwise>故障</s:otherwise>
									</s:choose>
								</p>
							</div>
							<div class="bio-row">
								<p>
									<span>加载指纹数量 </span>： ${engine.loadFingerNum} (枚)
								</p>
							</div>
							<div class="bio-row">
								<p>
									<span>注册线程数 </span>： ${engine.loadFingerNum}
								</p>
							</div>
							<div class="bio-row">
								<p>
									<span>1：1 比对线程数 </span>： ${engineNode.verifyThread}
								</p>
							</div>
							<div class="bio-row">
								<p>
									<span>1：N 比对线程数  </span>： ${engineNode.identifyThread}
								</p>
							</div>
							<div class="bio-row">
								<p>
									<span>数据块列表 </span>： ${engineNode.dataZone}
								</p>
							</div>
							<div class="bio-row">
								<p>
									<span>已运行时长 </span>： ${engine.runningTime} (h)
								</p>
							</div>
						</div>
					</div>
				</section>
				<section>
					<div class="row">
						<div class="col-lg-4">
							<div class="panel">
								<div class="panel-body">
									<div class="bio-chart">
										<input id="cpu_percent" class="knob" data-width="100" data-height="100" data-displayPrevious=true data-thickness=".2" value="0"
											data-fgColor="#ff766c" data-bgColor="#e8e8e8">
									</div>
									<div class="bio-desk">
										<h4 class="red">CPU使用率</h4>
										<p>单位 : 百分比</p>
										<p id="cpu_date">时间 : </p>
									</div>
								</div>
							</div>
						</div>
						<div class="col-lg-4">
							<div class="panel">
								<div class="panel-body">
									<div class="bio-chart">
										<input id="disk_percent" class="knob" data-width="100" data-height="100" data-displayPrevious=true data-thickness=".2" value="0"
											data-fgColor="#4CC5CD" data-bgColor="#e8e8e8">
									</div>
									<div class="bio-desk">
										<h4 class="terques">硬盘空间使用率</h4>
										<p >单位 : 百分比</p>
										<p id="disk_date">时间 : </p>
									</div>
								</div>
							</div>
						</div>
						<div class="col-lg-4">
							<div class="panel">
								<div class="panel-body">
									<div class="bio-chart">
										<input id="memory_percent"  class="knob" data-width="100" data-height="100" data-displayPrevious=true data-thickness=".2" value="0"
											data-fgColor="#96be4b" data-bgColor="#e8e8e8">
									</div>
									<div class="bio-desk">
										<h4 class="green">内存使用率</h4>
										<p>单位 : 百分比</p>
										<p id="memory_date">时间 : </p>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-lg-12">
							<div class="panel">
								<div class="panel-body" id="fingerChart" style="height:400px;"></div>
							</div>
						</div>
					</div>
				</section>
			</aside>
		</div>

		<!-- page end-->
	</section>
	<script type="text/javascript" src="js/common/pageEvent.js"></script>
	<script type="text/javascript" src="assets/jquery-knob/js/jquery.knob.js"></script>
	<script>
		var cpuT = new Array();
		var diskT = new Array();
		var memT = new Array();

		//knob
      $(".knob").knob({
    	    'readOnly':true
      });

		$(function(){
				var anyId = $("#anyId").val();
  				$.ajax({
			   			type: "POST",
			  	 		url: convertURL("engine/queryChartData"),
			  	 		dataType: "json",
			  	 		data: "anyId="+anyId,
			   			success: function(data){
			     			if(1 == data.anyStatus){
                                $("#cpu_percent").val(data.cpuInfo.totalValue).trigger('change');
                                $("#cpu_date").html("时间 : "+data.cpuInfo.createOn);
                                $("#disk_percent").val(data.diskInfo.totalValue).trigger('change');
                                $("#disk_date").html("时间 : "+data.diskInfo.createOn);
                                $("#memory_percent").val(data.memoryInfo.totalValue).trigger('change');
                                $("#memory_date").html("时间 : "+data.memoryInfo.createOn);
			     			}
			   			}
				});
  			});

   	// 基于准备好的dom，初始化echarts实例
     var fingerChart = echarts.init(document.getElementById('fingerChart'));

      var option_finger = {
		    title : {
		        text: '今天的指纹比对数量'
		    },
		    tooltip : {
		    	formatter: "{a} <br/>{b}点 : {c}次"
		    },
		    legend: {
		        data:['1:N 指纹比对数量']
		    },
		    toolbox: {
		        show : true,
		        feature : {
 		            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
 		            restore : {show: true},
 		            saveAsImage : {show: true}
		        }
		    },
		    xAxis: [
				       {
				    	   type : "category",
				           boundaryGap : false,
				    	   name: "(小时)",
				    	   nameLocation: "end",
				    	   splitLine: {show: false},
				           data: ["0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23"]
				       }
			],
			yAxis: [
				       {
				    	   name: "(数量)",
				    	   nameLocation: "end"
				       }
			],
			calculable: true,
		    series : [
		        {
		        	 name:'1:N 指纹比对数量',
			         type:'line',
			         stack: '总量',
			         data:[${identifyNumArray}]
		        }
		    ]
		};


       // 为echarts对象加载数据
       fingerChart.setOption(option_finger);

       function reloadEngineData(anyId){
		   $("#anyId").val(anyId);
    	   loadPage("main-content","engine/toGetList?anyId="+anyId,"");
       }
  </script>
</body>
</html>
