/*---COMMON JS----*/
$(function() {
    $('#nav-accordion').dcAccordion({
    	eventType: 'click',
        autoClose: true,
        saveState: false,
        disableLink: true,
        speed: 'normal',
        showCount: false,
        autoExpand: true,
//      cookie: 'dcjq-accordion-1',
        classExpand: 'dcjq-current-parent'
    });
    
    //sidebar dropdown menu auto scrolling
    $('#sidebar .sub-menu > a').click(function () {
        var o = ($(this).offset());
        var diff = 250 - o.top;
        if(diff>0)
            $("#sidebar").scrollTo("-="+Math.abs(diff),500);
        else
            $("#sidebar").scrollTo("+="+Math.abs(diff),500);
    });
    
    $(window).on('load', responsiveView);
    $(window).on('resize', responsiveView);
    
    //
    $('.icon-reorder').click(function () {
        if ($('#sidebar > ul').is(":visible") === true) {
            $('#main-content').css({
                'margin-left': '0px'
            });
            $('#sidebar').css({
                'margin-left': '-210px'
            });
            $('#sidebar > ul').hide();
            $("#container").addClass("sidebar-closed");
        } else {
            $('#main-content').css({
                'margin-left': '210px'
            });
            $('#sidebar > ul').show();
            $('#sidebar').css({
                'margin-left': '0'
            });
            $("#container").removeClass("sidebar-closed");
        }
    });
    
    // custom scrollbar
    $("#sidebar").niceScroll({styler:"fb",cursorcolor:"#e8403f", cursorwidth: '3', cursorborderradius: '10px', background: '#404040', spacebarenabled:false, cursorborder: ''});
    $("html").niceScroll({styler:"fb",cursorcolor:"#e8403f", cursorwidth: '6',cursorborderradius: '10px', background: '#404040', spacebarenabled:false,  cursorborder: '', zindex: '1000'});

    //  tool tips
    $('.tooltips').tooltip();
    
    //  popovers
    $('.popovers').popover();
});

//页面上的数字递增滚动
function countUp(name,number)
{
	var div_by = 100,
        speed = Math.floor(number / div_by),
        $display = $(name),
        run_count = 1,
        int_speed = 24,force_count = 1;

    var int = setInterval(function() {
        if(run_count < div_by && run_count < number){
            $display.text(speed * run_count);
            run_count++;
        } else if(parseInt($display.text()) < number) {
        	if(force_count < 200){
        		var curr_count = parseInt($display.text()) + 1;
                $display.text(curr_count);
                force_count++;
        	}else{
        		$display.text(number);
        	}
        } else {
            clearInterval(int);
        }
    }, int_speed);
}

//sidebar toggle
function responsiveView() {
    var wSize = $(window).width();
    if (wSize <= 768) {
        $('#container').addClass('sidebar-close');
        $('#sidebar > ul').hide();
    }

    if (wSize > 768) {
        $('#container').removeClass('sidebar-close');
        $('#sidebar > ul').show();
    }
}


//重置form表單中的元素
function reset(form) {
	//重置Form表单
	$("#"+form).get(0).reset();   //注意一定要加get(0)
	$("#dataForm").validate().resetForm();
	$("#dataForm div.has-error").removeClass("has-error");

	//是否存在Switch按钮
	if(typeof($("#switchInitStat").val()) != "undefined"){
		//重置switch按钮
		var operate = $("#operate").val();
		$.each($("#switchInitStat").val().split(","), function (idx, item) {
			if ("E" == item || "" == operate) {
				$("div.switch:eq("+idx+")").bootstrapSwitch("setState", true);
			} else {
				$("div.switch:eq("+idx+")").bootstrapSwitch("setState", false);
			}
		});
	}
	//是否存在Select按钮
	if(typeof($("#selectInitStat").val()) != "undefined"){
		//重置下拉框
		$.each($("#selectInitStat").val().split(","), function (idx, item) {
			$("Select:eq("+idx+")").val(item);
		});
	}

	//重置树插件
	var page = $("#page").val();
	if("Role" == page ){
		$("#rolePreview").css("border-color","#b3b3b3");
		var treeObj = $.fn.zTree.getZTreeObj("roleTree");
		treeObj.checkAllNodes(false);
		$.each(treeInitNodes, function (idx, item) {
			treeObj.checkNode(treeInitNodes[idx], true, true);
		});
	}
}

//加時間戳
function convertURL(url) {
	var timstamp = (new Date()).valueOf();
	if (url.indexOf("?") >= 0) {
		url = url + "&t=" + timstamp;
	} else {
		url = url + "?t=" + timstamp;
	}
	return url;
}


//时间比较
function dateCompare(date1,date2){
	date1 = date1.replace(/\-/gi,"/");
	date2 = date2.replace(/\-/gi,"/");
	var time1 = new Date(date1).getTime();
	var time2 = new Date(date2).getTime();
	if(time2 >= time1){
		return 1;
	}else{
		return 0;
	}
}

function filterSelect(item,text){
    $("#filter_item_des").val(text);
    $("#filter_item").val(item);
    $('#dataTable').DataTable().ajax.reload();
}

//校验表单
function checkForm(page){
	var flag = false;

	if($("#dataForm").valid()){
		flag = true;
	}else{
		return flag;
	}

	if("Role" == page){
		var treeObj = $.fn.zTree.getZTreeObj("roleTree");
		var nodes =  treeObj.getCheckedNodes(true);
		if(nodes.length > 0){
			var str='';
			for(var i=0;i<nodes.length - 1;i++){
				str += nodes[i].code + ",";
			}
			str += nodes[nodes.length - 1].code;

			$("div.form-group:has('div#rolePreview')").removeClass("has-error");

			$("#rolePreview").css("border-color","#b3b3b3");
			$("#rolePreview-error").remove();
			flag = true;
		}else{
			$("div.form-group:has('div#rolePreview')").addClass("has-error");
			$("#rolePreview").css("border-color","#b94a48");
			$("#rolePreview").after('<span id="rolePreview-error" class="help-block">× 请分配权限信息</span>');
			flag = false;
		}
		$("#purview").val(str);
	}
	return flag;
}


//加載頁面
function changeLu() {
	$.post("/common/changeLu");
	//window.location.reload();
}

//加載頁面
function loadPage(area,url,form) {
	if(form == ""){
		$("#"+area).load(convertURL(url));
	}else{
		$("#"+area).load(convertURL(url),$("#"+form).serialize());
	}
}

//加載頁面
function hrefPage(url) {
	location.href=url;
}

/* 构建详细数据 */
function fnFormatDetails (detail)
{
	var array_value = detail.split(";");
	var detail_key = $("#detail_key").val();
	var array_key = detail_key.split(",");
	var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
	$.each(array_value, function(i, n){
		sOut += '<tr><td>'+array_key[i]+':</td><td>'+n+'</td></tr>';
	});
	sOut += '</table>';
	return sOut;
}

//通用于异步更新信息
function updateInfo(url,form,div){
	$.ajax({
   			type: "POST",
  	 		url: convertURL(url),
  	 		dataType: "json",
   			data: $("#"+form).serialize(),
   			success: function(data){
				var page = $("#page").val();
     			var operate = $("#operate").val();
     			getResultDiv(div,data.anyStatus,data.msg);
         		if("query" == operate){
         			$("#"+div).html(data.msg);
         		}else if("add" == operate){
         			$("#tabBody").append(data.tr);
         			if(1== data.anyStatus){
         				//reset(form);
         			}
         		}else if("modify" == operate){
         			$("#tabBody").append(data.tr);
					if("Engine" == page){
						$("#button_add").hide();
					}
         		}else if("del" == operate){
         			if( 1 == data.anyStatus){
						if("Engine" == page){
							var anyId = $("#anyId").val();
							$("#node_"+anyId).remove();
						}else{
							$('#dataTable').dataTable().fnDeleteRow( $("tr.delFlag")[0]);
						}
         			}
         		}else if("push" == operate){
         			$("#"+div).html(data.msg);
         			reset(form);
         		}else if("testService" == operate){
         			$("#tabBody").html(data.tr);
         		}else if("decrypt" == operate){
         			$("#plain").text(data.plainData);
         		}
         		
         		//$("td[id$='ck']").html("");
         		//$("span[id$='ck']").html("");
         		//$("#fileAddress").val("");
   			}
	});
}

function getData(pageNum){
	var action = $("#action").val();
	var page = $("#page").val();
	$("#operate").val("query");
	var flag = true;
	$.each( $("input:checked[name='anyArray']").toArray(), function(i, n){
		  var a = $(this).attr("id").substr($(this).attr("id").indexOf("_")+1);
		  if("time" != a){
			  var type = $("#"+a).get(0).tagName;
			  if("INPUT" == type){
				  if(checkObject(0,a,a+"_ck")){flag = false;return;}
			  }else if("SELECT" == type){
				  if(checkObject(1,a,a+"_ck")){flag = false;return;}
			  }
		  }else{
			  if(checkObject(2,a,a+"_ck")){flag = false;return;}
		  }
	});
	
	if(flag){
		$("#anyOption").val("get"+page+"Json");
		if(page == "SysLog" || page == "BusLog" || page == "ExamLog"){
			updateInfo("toGet"+page+"List_"+action+".action?pageVO.pageNum="+pageNum,"dataForm","tabBody");
		}else{
			updateInfo("toGetList_"+action+".action?pageVO.pageNum="+pageNum,"dataForm","tabBody");
		}
	}
}


//页面时间显示
function getTime(){
	var x = new Date();
	var y = "yyyy-MM-dd hh:mm:ss";
	var z = {y:x.getFullYear(),M:x.getMonth()+1,d:x.getDate(),h:x.getHours(),m:x.getMinutes(),s:x.getSeconds()};
	var time =  y.replace(/(y+|M+|d+|h+|m+|s+)/g,function(v) {return ((v.length>1?"0":"")+eval('z.'+v.slice(-1))).slice(-(v.length>2?v.length:2))});
	$("#time").html(time);
}

function getResultDiv(div,status,msg){
	$("#"+div).show();
	if(1 == status){
		$("#"+div+"_title").html(" 成功 !")
		$("#result").removeClass("alert-danger").addClass("alert-success");
		$("#"+div+"_sign").removeClass("icon-remove-sign").addClass("icon-ok-sign");
	}else{
		$("#"+div+"_title").html(" 失败 !")	
		$("#"+div).removeClass("alert-success").addClass("alert-danger");
		$("#"+div+"_sign").removeClass("icon-ok-sign").addClass("icon-remove-sign");
	}
	$("#"+div+"_msg").html(msg)
}

//处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外
function banBackSpace(e){
	//alert(event.keyCode)
	var ev = e || window.event;//获取event对象
	var obj = ev.target || ev.srcElement;//获取事件源
	var t = obj.type || obj.getAttribute('type');//获取事件源类型
	//获取作为判断条件的事件类型
	var vReadOnly = obj.readOnly;
	var vDisabled = obj.disabled;
	//处理undefined值情况
	vReadOnly = (vReadOnly == undefined) ? false : vReadOnly;
	vDisabled = (vDisabled == undefined) ? true : vDisabled;
	//当敲Backspace键时，事件源类型为密码或单行、多行文本的，
	//并且readOnly属性为true或disabled属性为true的，则退格键失效
	var flag1 = ev.keyCode == 8 && (t == "password" || t == "text" || t == "textarea") && (vReadOnly == true || vDisabled == true);
	//当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效
	var flag2 = ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea";
	//判断
	if (flag2 || flag1)
		event.returnValue = false;//这里如果写 return false 无法实现效果
}

//用于获取tr数据对象中具体的Key值对应的value
function getTrElementValue(trData,key){
	var elementValue;
	$.each(trData, function(i, n){
		if(key == i){
			elementValue = n;
		}
	});
	return elementValue;
}

