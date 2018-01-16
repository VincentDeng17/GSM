$("#logout-button").click(function(){
    commonMethods.logout();
});
//公共工具类
var commonUtils = {
	/**
	 * ajax请求
	 * @param  {[type]}  data            请求参数
	 * @param  {[type]}  url             请求的URL
	 * @param  {[type]}  type            请求类型
	 * @param  {[type]}  successCallBack 成功回调
	 * @param  {[type]}  errorCallBack   失败回调
	 * @param  {Boolean} isSync          是否同步
	 * @return {[type]}                  
	 */
	ajax : function(data, url, type, successCallBack, errorCallBack, isSync){
		//是否同步
        var sync = true;
        if (!!isSync) {
            sync = isSync;
        }
        $.ajax({
        	url: url,
            type: type,
            data: JSON.stringify(data),
            async: sync,
            dataType: 'json',
            contentType: "application/json",
            success: function (data) {
                //接口鉴权不通过，返回登录页
                if(data.resultCode=='902'){
                    window.location.href= "login.html";
                    return;
                }
            	successCallBack(data);
            },
            error: function (e) {
                errorCallBack(e);
            }
        });
	},
    /**
     * 获取cookie
     * @Author    HenryGentry
     * @DateTime  2017-09-15
     * @copyright [copyright]
     * @license   [license]
     * @version   [version]
     * @param     {[type]}    name [description]
     * @return    {[type]}         [description]
     */
    getCookie : function(name){
        var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
        if(arr=document.cookie.match(reg)){
            return unescape(arr[2]);
        }else{
            return null ;
        }
    },
    /**
     * 获取当前日期(yyyy-mm-dd)
     * @Author    HenryGentry
     * @DateTime  2017-10-26
     * @copyright [copyright]
     * @license   [license]
     * @version   [version]
     * @param     number    day 偏移量（单位为天）
     * @return    yyyy-mm-dd 格式的时间
     */
     getNowFormatDate : function(day){
        var  nowDate = new Date();
        var seperator = "-";
        var timestamp = Date.parse(nowDate);
        console.log("当前时间戳:"+timestamp);

        offset = Math.abs(day) * 86400000;
        var resultTimeStamp = day>=0?(timestamp + offset):(timestamp-offset);
        console.log("偏移时间戳:"+resultTimeStamp);

        var offsetDate = new Date();
        offsetDate.setTime(resultTimeStamp);
        var offsetYear = offsetDate.getFullYear();
        var offsetMonth = offsetDate.getMonth() + 1;
        var offsetDay = offsetDate.getDate();

        var currentdate = offsetYear + seperator + offsetMonth + seperator + offsetDay;
        return currentdate;
     },
    /**
     * 获取格式化时间(hh:mm:ss)
     * @Author    HenryGentry
     * @DateTime  2017-10-26
     * @copyright [copyright]
     * @license   [license]
     * @version   [version]
     * @param     [number]    hour
     * @param     [number]    minutes
     * @param     [number]    seconds
     * @return    yyyy-mm-dd 格式的时间
     */
    getFormateTime : function(hour,minutes,seconds){
        var seperator = ":";
        //格式化
        //若不足10要补0
        if(hour>=0 && hour<=9){
            hour = "0" + hour;
        }
        if(minutes >=0 && minutes <=9){
            minutes = "0" + minutes;
        }
        if(seconds >=0 && seconds <=9){
            seconds = "0" + seconds;
        }
        return hour + seperator + minutes + seperator + seconds;
    }
};
/**
 * 公共方法类
 * @type
 */
var commonMethods = {

    /**
     * @Author    HenryGentry
     * @DateTime  2017-09-13
     * @copyright [copyright]
     * @license   [license]
     * @version   [version]
     * @param     用户名
     * @param     密码
     * @param     请求成功回调函数
     * @param     请求失败回调函数
     * @return    
     */
    login  : function(userName , password , successCallBack,errorCallBack){
        //请求参数
        var data = {
            "userName" : userName ,
            "password" : password 
        };
        //请求地址
        var url = "/gsmp/user/login";
        var type = "post";
        commonUtils.ajax(data,url,type,function(data){
            successCallBack(data);
        },function(error){
            errorCallBack(error);
        });
    },

    /**
     * @Author    HenryGentry
     * @DateTime  2017-09-13
     * @copyright [copyright]
     * @license   [license]
     * @version   [version]
     * @param     请求成功回调函数
     * @param     请求失败回调函数
     * @return    {[type]}
     */
    logout : function(successCallBack,errorCallBack){
        var data = {};
        //请求地址
        var url = "/gsmp/user/logout";
        var type = "post";
        commonUtils.ajax(data,url,type,function(data){
            //请求成功
            if(data.resultCode =='0'){
                window.location.href= "login.html";
            }else{
                console.log("logout fail");
                successCallBack(data);
            }
        },function(error){
            errorCallBack(error);
        });
    }
};