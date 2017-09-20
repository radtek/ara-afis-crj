$(function () {
    // Switch
    $("div.switch").bootstrapSwitch();

    /*
     * 在详细按钮上添加点击绑定事件
     */
    $("#dataTable").on('click','.details-control', function () {
        var nTr = $(this).closest('tr');
        var trData = oTable.fnGetData(nTr);
        //判定当前详细栏是否打开
        if (oTable.fnIsOpen(nTr)){
            //已打开，关闭它
            nTr.removeClass('shown');
            oTable.fnClose( nTr );
        }else{
            //未打开，打开它
            nTr.addClass('shown');
            var detail_data_key = $("#detail_data_key").val().split(",");
            var arrayTemp = new Array()
            $.each(trData, function(i, n){
                var num = $.inArray(i, detail_data_key);
                if(num != -1){
                    arrayTemp[num] = n;
                }
            });
            var detail = arrayTemp.join(";");
            oTable.fnOpen(nTr, fnFormatDetails(detail), 'details');
        }
    } );

    $("#dataForm").validate({
        errorElement : 'span',
        errorClass : 'help-block',
        onsubmit : false,
        highlight : function(element) {
            $(element).closest('div').addClass('has-error');
        },
        success : function(label) {
            label.closest('div').removeClass('has-error');
            label.remove();
        },
        errorPlacement : function(error, element) {
            element.parent('div').append(error);
        }
    });
});
