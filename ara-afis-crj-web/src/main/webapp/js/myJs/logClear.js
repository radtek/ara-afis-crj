/**
 * Created by de1ck on 2017/8/11.
 */

var vm = new Vue({
    el: '#rrapp',
    data: {
        logPCT: [],

    },
    methods: {
        percentNum: function (num, num2) {
            return (Math.round(num / num2 * 10000) / 100.00 + "%"); //小数点后两位百分比
        },
        getLog: function(){
            $.post("../clearlog/cout", {}, function (r) {
                var logCountList = r.logs.count_log;
                var logClearList = r.logs.clear_log;
                var logPCT = [];
                for (var i = 0; i < 4; i++) {
                    logCountList[i] === 0 ? logPCT[i] = '0%' : logPCT[i] = vm.percentNum(logClearList[i], logCountList[i]);
                }
                vm.logPCT = logPCT;
            });
        },
        init: function () {
            //获取日志数量
            $.post("../clearlog/cout", {}, function (r) {
                var logCountList = r.logs.count_log;
                var logClearList = r.logs.clear_log;
                var logPCT = [];
                for (var i = 0; i < 4; i++) {
                    logCountList[i] === 0 ? logPCT[i] = '0%' : logPCT[i] =vm.percentNum(logClearList[i], logCountList[i]);
                }
                vm.logPCT = logPCT;
                // 基于准备好的dom，初始化echarts实例
                var barChart = echarts.init(document.getElementById('echarts-bar'));
                // 指定图表的配置项和数据
                var option_bar = {
                    title: {
                        text: '日志指标'
                    },
                    tooltip: {},
                    legend: {
                        data: ['三月前', '日志总量']
                    },
                    xAxis: {
                        data: ["任务日志", "系统日志", "设备运行日志", "设备硬件日志"]
                    },
                    yAxis: {},
                    series: [
                        {
                            name: '三月前',
                            type: 'bar',
                            data: r.logs.clear_log
                        },
                        {
                            name: '日志总量',
                            type: 'bar',
                            data: r.logs.count_log
                        }
                    ]
                };
                // 使用刚指定的配置项和数据显示图表。
                barChart.setOption(option_bar);
            })
        },
        clear: function (type) {
            var url = "../clearlog/" + type;
            this.$Modal.confirm({
                content: '确定要清楚该日志过期数据？',
                onOk: function () {
                    operateAjax({
                        url: url,
                        data: {},
                    }, function () {
                        vm.reload();
                    })
                },
                onCancel: function () {
                    // this.$Message.info('点击了取消');
                }
            });
        },
        reload: function () {
            this.getLog();
        }
    },
    mounted: function () {
        this.$nextTick(function () {
            this.init();
        })
    }
})