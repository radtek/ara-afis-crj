//登录文本框校验
function checking() {
	if ($("#account").val().replace(/\ /g, '') == "") {
		$("#errorMsg").html($("#id_empty").val());
		$("#account").focus();
		$("#message-error").show();
		return false;
	} else if ($("#password").val().replace(/\ /g, '') == "") {
		$("#errorMsg").html($("#pw_empty").val());
		$("#password").focus();
		$("#message-error").show();
		return false;
	}
	return true;
}

function checkLock(){
	if ($("#password").val().replace(/\ /g, '') == "") {
		$("#errorMsg").html($("#pw_empty").val());
		$("#password").focus();
		$("#errorMsg").css("color","#b94a48");
		return false;
	}else{
		return true;
	}
}


//頁面綁定Enter建
function BindEnter(obj) {
	if (obj.keyCode == 13) {
		$("#dataForm").submit();
		obj.returnValue = false;
	}
}