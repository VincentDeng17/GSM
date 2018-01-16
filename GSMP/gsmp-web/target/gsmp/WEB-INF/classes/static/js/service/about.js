$(document).ready(function(){
	//获取用户名
	var userName = commonUtils.getCookie("userName");
	$("#name-text").text(userName);
});