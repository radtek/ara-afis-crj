<%@ page language="java"  pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
  <head></head>

<body>
	<section class="wrapper_main">
              <!--state overview start-->
              <div class="row state-overview">
                  <div class="col-lg-3 col-sm-6">
                      <section class="panel">
                          <div class="symbol blue">
                              <i class="icon-hand-right"></i>
                          </div>
                          <div class="value">
                              <h1 class="count">
                                  0
                              </h1>
                              <p>在库的指纹数</p>
                          </div>
                      </section>
                  </div>
                  <div class="col-lg-3 col-sm-6">
                      <section class="panel">
                          <div class="symbol color-biometric">
                              <i class="icon-laptop"></i>
                          </div>
                          <div class="value">
                          <h1 class=" count3">
                              0
                              </h1>
                              <p>在线的引擎数</p>
                          </div>
                      </section>
                  </div>
                  <div class="col-lg-3 col-sm-6">
                  <section class="panel">
                      <div class="symbol yellow">
                          <i class="icon-tags"></i>
                          </div>
                          <div class="value">
                          <h1 class=" count4">
                              0
                              </h1>
                              <p>今天的业务数</p>
                          </div>
                      </section>
                  </div>
                  <div class="col-lg-3 col-sm-6">
                  <section class="panel">
                      <div class="symbol terques">
                          <i class="icon-sitemap"></i>
                          </div>
                          <div class="value">
                          <h1 class="count2">
                              0
                              </h1>
                              <p>指纹库别数</p>
                          </div>
                      </section>
                  </div>
              </div>
        <!--chart start-->
        <!--state overview end-->
        <div class="row">
              <div class="col-lg-8">
                  <section class="panel">
                      <header class="panel-heading">
                        <h4>指纹库增长趋势图(本月)</h4>
                        </header>
                          <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
                          <div id="fp_add_Chart" style="height:350px;display: block;"></div>
                      </section>
                  </div>
                  <div class="col-lg-4">
                  <section class="panel">
                     <header class="panel-heading">
                        <h4>业务类型分布趋势图(本月)</h4>
                        </header>
                         <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
                         <div id="fp_type_Chart" style="height:350px;display: block;"></div>
                     </section>
                  </div>
              </div>
        <!--statu start-->
        <!--chart end-->
        <div class="row">
              <div class="col-lg-6">
                  <section class="panel">
                      <div class="weather-bg color-biometric">
                          <div class="panel-body">
                              <div class="row">
                                  <div class="col-xs-6">
                                      <i class="icon-laptop"></i>
                                      </div>
                                      <div class="col-xs-6">
                                      <div class="degree">
                                          引擎状态
                                          </div>
                                      </div>
                                  </div>
                              </div>
                          </div>
                          <footer class="weather-category">
                          <ul id="server-status"></ul>
                          </footer>
                      </section>
                  </div>
                  <%--<div class="col-lg-4">--%>
                  <%--<section class="panel">--%>
                      <%--<div class="weather-bg color-franchise">--%>
                          <%--<div class="panel-body">--%>
                              <%--<div class="row">--%>
                                  <%--<div class="col-xs-6">--%>
                                      <%--<i class="icon-globe"></i>--%>
                                      <%--</div>--%>
                                      <%--<div class="col-xs-6">--%>
                                      <%--<div class="degree">--%>
                                          <%--任务状态--%>
                                          <%--</div>--%>
                                      <%--</div>--%>
                                  <%--</div>--%>
                              <%--</div>--%>
                          <%--</div>--%>
                          <%--<footer class="weather-category">--%>
                              <%--<ul id="task-status"></ul>--%>
                          <%--</footer>--%>
                      <%--</section>--%>
                  <%--</div>--%>
                  <div class="col-lg-6">
                      <!--weather statement start-->
                      <section class="panel">
                          <div class="weather-bg color-device">
                              <div class="panel-body">
                                  <div class="row">
                                      <div class="col-xs-6">
                                        <i class="icon-cloud"></i>
                                      </div>
                                      <div class="col-xs-6">
                                          <div class="degree">
                                              业务失败率
                                          </div>
                                      </div>
                                  </div>
                              </div>
                          </div>
                          <footer class="weather-category">
                              <ul id="bus-status"></ul>
                          </footer>
                      </section>
                      <!--weather statement end-->
                  </div>
              </div>
              <!--statu start-->
              <!--log start-->
              <div class="row">
                  <div class="col-lg-12">
                      <!--work progress start-->
                      <section class="panel">
                          <div class="panel-body progress-panel">
                              <div class="task-progress">
                                  <h1>最近的日志信息</h1>
                              </div>
                              <div class="task-option">
                                  <select id="log-table" class="styled">
                                      <option>业务日志</option>
                                      <option>系统日志</option>
                                  </select>
                                  <p></p>
                              </div>
                          </div>
                          <table id="bus-log-table" class="table personal-task">
                              <s:if test="${fn:length(busLogInfolist) > 0}">
								<s:forEach items="${busLogInfolist}" var='buslog' varStatus="status">
	                              <tr>
	                                  <td><s:out value="${status.index+1}"/></td>
	                                  <td>${buslog.actionType}</td>
                                      <td>${buslog.resultFlag}</td>
                                      <td>${buslog.resultCode}</td>
                                      <td>${buslog.resultMSG}</td>
                                      <td>${buslog.clientIp}</td>
                                      <td>${buslog.costTime} 毫秒</td>
                                      <td>${buslog.createOn}</td>
	                              </tr>
                              </s:forEach>
								</s:if>
                          </table>
                          <table id="sys-log-table" class="table personal-task hideDiv">
                              <s:if test="${fn:length(sysLogInfolist) > 0}">
                                  <s:forEach items="${sysLogInfolist}" var='syslog' varStatus="status">
                                      <tr>
                                          <td><s:out value="${status.index+1}"/></td>
                                          <td>${syslog.content}</td>
                                          <td><span class="badge bg-info">${syslog.operate}</span></td>
                                          <td>
                                              <div>${syslog.createOn}</div>
                                          </td>
                                      </tr>
                                  </s:forEach>
                              </s:if>
                          </table>
                      </section>
                      <!--work progress end-->
                  </div>
              </div>
			  <!--log start-->
          </section>
	<script src="js/chart/echarts-all.js"></script>
	<script type="text/javascript">
        //计数首页统计数据
        countUp('.count', ${homeData.fpNum});
        countUp('.count3', ${homeData.serverNum});
        countUp('.count4', ${homeData.busNum});
        countUp('.count2', ${homeData.userNum});

        // 初始化echarts图表对象
        var fpChart = echarts.init(document.getElementById('fp_add_Chart'));
        var busChart = echarts.init(document.getElementById('fp_type_Chart'));

        var fp_option = {
            tooltip: {
                formatter: "{a} <br/>{b}号 : {c}个"
            },
            xAxis: [
                {
                    name: "(日期)",
                    nameLocation: "end",
                    splitLine: {show: false},
                    data: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"]
                }
            ],
            yAxis: [
                {
                    name: "(数量)",
                    nameLocation: "end"
                }
            ],
            toolbox: {
                show: true,
                feature: {
                    magicType: {show: true, type: ['line', 'bar']},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            series: [
                {
                    name: "指纹增长数量",
                    type: "line",
                    data: [${homeData.fpAddData}]

                }
            ]
        };

        var bus_option = {
            tooltip: {
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            toolbox: {
                show: true,
                feature: {
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
//            legend: {
//                orient: 'vertical',
//                x: 'right',
//                y: 'center',
//                data: ['1对1比对', '1对1认证', '1对多比对', 'X对多比对', 'X指查重', '图片质量评价', '图片建模', '图片格式转换', '模板管理', '库别管理']
//            },
            calculable: true,
            animation: false,
//          selectedOffset: 20,         // 选中是扇区偏移量
            series: [
                {
                    name: '业务类型',
                    type: 'pie',
                    radius: '55%',
                    center: ['38%', '50%'],
                    data: [${homeData.busTypeData}]
                }
            ],
            noDataLoadingOption: {
                text: '暂无数据',
                effect: 'bubble',
                effectOption: {
                    effect: {
                        n: 0
                    }
                }
            },
        };

        // 为echarts对象加载数据
        fpChart.setOption(fp_option);
        busChart.setOption(bus_option);

        var serverStatus = new Array("正常", "预警", "故障")
        $.each(${homeData.serverStatuData}, function (i, n) {
            $("#server-status").append("<li class='moreLi'><h5>" + serverStatus[i] + "</h5>" + n + "</li>");
        });

        var taskStatus = new Array("等待处理", "处理中", "完成")
        $.each(${homeData.taskStatuData}, function (i, n) {
            $("#task-status").append("<li class='moreLi'><h5>" + taskStatus[i] + "</h5>" + n + "</li>");
        });

        var busSucNum = 100 - ${homeData.busFailPercent};
        $("#bus-status").html("<li><h5>成功</h5>" + busSucNum + " %</li><li><h5>失败</h5>" + ${homeData.busFailPercent}+" %</li>");

        $("#log-table").on("change", function () {
            $("#sys-log-table").toggle();
            $("#bus-log-table").toggle();
        });
	</script>
</body>
</html>
