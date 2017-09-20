<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head></head>

<body>
<section class="wrapper_top">
    <div class="row">
        <div class="col-lg-12">
            <!--breadcrumbs start -->
            <ul class="breadcrumb">
                <li><a href="javascript:void(0)" onclick="loadPage('main-content','common/loadContent','')"><i
                        class="icon-home"></i>首页</a></li>
                <li><a href="javascript:void(0)"><i class="icon-bar-chart"></i>统计分析</a></li>
                <li class="active"><i class="icon-bar-chart"></i>请求业务类型分析</li>
            </ul>
            <!--breadcrumbs end -->
        </div>
    </div>
</section>
<section class="wrapper">
    <div class="row">
        <div class="col-lg-12">
            <section class="panel">
                <header class="panel-heading panel-bg-orgin">
                    <i class="icon-book"></i>筛选条件
                </header>
                <div class="panel-body">
                    <div class="col-lg-10 ">
                        <label class="control-label pull-left" style="margin-top: 11px;">时间区段：</label>
                        <div class="col-lg-4 pull-left daterangepicker_div">
                            <input type="text" id="dateTimeRange" name="dateTimeRange" class="form-control" readonly>
                            <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-6">
            <section class="panel">
                <header class="panel-heading">
                    请求IP分布图
                </header>
                <div class="panel-body">
                    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
                    <div id="ip_count_pie_Chart" style="height:350px;display: block;"></div>
                </div>
            </section>
        </div>
        <div class="col-lg-6">
            <section class="panel">
                <header class="panel-heading">
                    <span id="showClickIP"></span>&nbsp;&nbsp;&nbsp;业务类型分布图
                </header>
                <div class="panel-body">
                    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
                    <div id="action_count_Chart" style="height:350px;display: block;"></div>
                </div>
            </section>
        </div>
    </div>
</section>
<script src="${pageContext.request.contextPath}/js/chart/echarts-all.js"></script>
<script type="text/javascript">
    var chart = {
        //ip请求图表
        ipChart: null,  //ip请求柱状图
        ipPieChart: null, //ip请求饼图
        ip_option: null, //ip请求柱状图option
        ipPie_option: null,//ip请求饼图 option
        clickIP: null, //图中点击ip
        //安照请求类型查看图标
        actionChart: null, //请求类型图
        actionPieChart: null,//请求类型饼图
        action_option: null, //请求类型图option
        actionPieChart: null, // 请求类型option
        dateTimeRange: null,
        init: {
            initData: function () {
                chart.cFunction.init();
                chart.init.initEvent();
            },
            initEvent: function () {
                chart.cFunction.initDateTimeRange();
                chart.cFunction.initchart();
                chart.cFunction.bindChartClickEvent();
                chart.cFunction.bindDateRangeEvent();
            }
        },
        cFunction: {
            init: function () {
                chart.ipPieChart = echarts.init(document.getElementById('ip_count_pie_Chart'));
                chart.actionChart = echarts.init(document.getElementById('action_count_Chart'));
            },
            initchart: function () {
                chart.dateTimeRange = $("#dateTimeRange").val();
                $.ajax({
                    url: "${pageContext.request.contextPath}/busLog/ipCountByDate",
                    data: {
                        dateTimeRange: chart.dateTimeRange
                    },
                    method: "POST",
                    success: function (data) {
                        chart.cFunction.chartRenerder(chart.ipPieChart, JSON.parse(data), "pie");
                        if (!(chart.clickIP)) {
                            chart.cFunction._reloadActionType(chart.clickIP, chart.dateTimeRange);
                        }

                    }
                });
            },
            initDateTimeRange: function () {
                $("#dateTimeRange").daterangepicker({
                    "timePicker": true,
                    "timePicker24Hour": true,
                    "timePickerSeconds": true,
                    "autoApply": true,
                    "startDate": moment().startOf('day'),
                    "endDate": moment(),
                    "maxDate": new Date(),
                    ranges: {
                        '最近1小时': [moment().subtract('hours', 1), moment()],
                        '今日': [moment().startOf('day'), moment()],
                        '昨日': [moment().subtract('days', 1).startOf('day'), moment().subtract('days', 1).endOf('day')],
                        '最近7日': [moment().subtract('days', 6), moment()],
                        '最近30日': [moment().subtract('days', 29), moment()]
                    },
                    "locale": {
                        "direction": "ltr",
                        "format": "YYYY/MM/DD HH:mm:ss",
                        "separator": " - ",
                        "applyLabel": "确定",
                        "cancelLabel": "取消",
                        "fromLabel": "起始时间",
                        "toLabel": "结束时间",
                        "customRangeLabel": "自定义",
                        "daysOfWeek": ['日', '一', '二', '三', '四', '五', '六'],
                        "monthNames": ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
                        "firstDay": 1
                    },
                    "alwaysShowCalendars": true
                }, function (start, end, label) {
//                        console.log("New date range selected: ' + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')");
                });

            },
            bindChartClickEvent: function () {
                chart.ipPieChart.on('click', function (params) {
                    chart.cFunction.chartClickEvent(params);
                });
            },
            chartClickEvent: function (params) {
                chart.clickIP = params.name;
                chart.cFunction.showClickIP();
                chart.cFunction._reloadActionType(chart.clickIP, chart.dateTimeRange);
            },
            _reloadActionType: function (ip, dateTimeRange) {
                var url;
                if (ip) {
                    url = "${pageContext.request.contextPath}/busLog/countActionTypeByIPAndDate";
                    $.ajax({
                        url: url,
                        data: {
                            ip: ip,
                            dateTimeRange: dateTimeRange
                        },
                        method: "POST",
                        success: function (data) {
                            console.log(data.toString());
                            chart.cFunction.chartRenerder(chart.actionChart, JSON.parse(data), "bar", "访问类型");
                        }
                    })
                }

            },
            showClickIP: function () {
                $("#showClickIP").text(chart.clickIP);
            },
            bindDateRangeEvent: function () {
                $("#dateTimeRange").unbind("change")
                $("#dateTimeRange").bind("change", function () {
                    chart.dateTimeRange = $("#dateTimeRange").val();
                    console.log(chart.dateTimeRange);
                    $.ajax({
                        url: "${pageContext.request.contextPath}/busLog/ipCountByDate",
                        data: {
                            dateTimeRange: chart.dateTimeRange
                        },
                        method: "POST",
                        success: function (data) {
                            chart.cFunction.chartRenerder(chart.ipPieChart, JSON.parse(data), "pie");
                            if (chart.clickIP) {
                                chart.cFunction._reloadActionType(chart.clickIP, chart.dateTimeRange);
                            }

                        }
                    });

                })
            },
            /**
             *
             * @param chartTarget 目标图表对象
             * @param data 数据 如果是柱形和折线类需要XList(data.XList)和YList， 饼图需要XList和pieList
             * @param type 图表类型 饼图为pie、折线line、柱形图bar
             */
            chartRenerder: function (chartTarget, data, type, xName) {
                if ("pie" == type) {
                    chart.cFunction._pieChartRenerder(chartTarget, data);
                } else if ("line" == type || "bar" == type) {
                    chart.cFunction._lineOrBarChartRenerder(chartTarget, data, type, xName);
                }
            },
            _pieChartRenerder: function (chartTarget, data) {
                var pie_option = {
                    tooltip: {
                        formatter: "{b} <br/>{a} : {c}次 ({d}%)"
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    legend: {
                        orient: 'vertical',
                        x: 'right',
                        y: 'center',
                        data: data.xList
                    },
                    calculable: true,
                    animation: false,
//          selectedOffset: 20,         // 选中是扇区偏移量
                    series: [
                        {
                            name: '访问次数',
                            type: 'pie',
                            radius: '55%',
                            roseType: 'angle',
                            center: ['38%', '50%'],
                            data: data.pieList
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
                chartTarget.setOption(pie_option, {"notMerge": true});

            },
            _lineOrBarChartRenerder: function (chartTarget, data, type, xName) {
                var option = {
                    tooltip: {
                        formatter: "{b} <br/>{a} : {c}次"
                    },
                    xAxis: [
                        {
                            name: "(" + xName + ")",
                            nameLocation: "end",
                            splitLine: {show: false},
                            data: data.xList,
                            axisLabel: {
                                rotate: 30,
                                interval: 0
                            },
                        }
                    ],
                    yAxis: [
                        {
                            name: "(次)",
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
                            name: "访问次数",
                            type: type,
                            data: data.yList

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

                chartTarget.setOption(option, {"notMerge": true});
            }
        }
    }
    chart.init.initData();
</script>
</body>
</html>
