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
            url : 'taslibrary/queryDataTaslibrary',//这个就是请求地址对应sAjaxSource
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
    // bAutoWidth : false,
    "pagingType":   "full_numbers",
    columns: [
        {
            "class": 'details-control',
            "data": null,
            "defaultContent": ''
        },
        { "data": "libraryId" },
        { "data": "verifyLibraryGoal"},
        { "data": "identifyLibraryGoal" },
        { "data": "libraryActivietyFlag" },
        { "data": "modifyState" },
        { "data": "libraryDesc"},
        { "data": "createDate"},
        { "data": "modifyDate"}
    ],
    "createdRow": function (row,data,index ) {
        if('失败' == data.resultFlag){
            $(row).children().eq("5").css("background-color","#c7abab");
            $(row).children().eq("7").html('<button class="btn btn-primary btn-xs hor-btn-space" onclick="retryDataSyn(\''+data.sourceLibrary+'\',\''+data.personId+'\',\''+data.templateNo+'\',\''+data.operateType+'\')"><i class="icon-repeat"></i>');
        }
    },
    "deferRender": true,
    "processing": true,
    "aoColumnDefs": [
        { "bSortable": false, "aTargets": [ 0 ] }
    ],
    "aaSorting": [[1, 'desc']],
    // "oLanguage": { //国际化配置
    //     "sProcessing" : "正在加载数据，请稍后...",
    //     "sLengthMenu" : "显示 _MENU_ 条",
    //     "sZeroRecords" : "没有您要搜索的内容",
    //     "sInfo" : "从 _START_ 到  _END_ 条记录 总记录数为 _TOTAL_ 条",
    //     "sInfoEmpty" : "记录数为0",
    //     "sInfoFiltered" : "(全部记录数 _MAX_ 条)",
    //     "sInfoPostFix" : "",
    //     "sSearch" : "搜索",
    //     "sUrl" : "",
    //     "oPaginate": {
    //         "sFirst" : "第一页",
    //         "sPrevious" : "上一页",
    //         "sNext" : "下一页",
    //         "sLast" : "最后一页"
    //     }
    // },
});
function retryDataSyn(library,personId,templateNo,operateType){
    if('新增' == operateType){
        operateType = "I";
    }else if('修改' == operateType){
        operateType = "U";
    }else if('删除' == operateType){
        operateType = "D";
    }
    $.ajax({
        type: "POST",
        url: "systemOrder/addOption",
        dataType: "json",
        data:'orderCode=2003&orderParam={"kb":"'+library+'","dh":"'+personId+'","czlb":"'+operateType+'","xh":"'+templateNo+'"}',
        success: function(data){
            getResultDiv("result",data.anyStatus,data.msg);
        }
    });
}