$(document).ready(function () {
    $('#start-time').datetimepicker({
        todayBtn: true,
        minView : 2
    });
    $('#end-time').datetimepicker({
        todayBtn: true,
        minView : 2
    });
    /*隐藏时候赋值*/
    $('#start-time').datetimepicker()
        .on('hide', function (ev) {
            app.startTime = $("#start-time").val();
        });
    $('#end-time').datetimepicker()
        .on('hide', function (ev) {
            app.endTime = $("#end-time").val();
        });
    //按钮绑值
    $("#app-name-clear").bind("click", function () {
        app.searchAppName = "";
    });
    $("#start-time-clear").bind("click", function () {
        app.startTime = "";
        $("#start-time").val("");
    });
    //结束时间
    $("#end-time-clear").bind("click", function () {
        app.endTime = "";
        $("#end-time").val("");
    });
    //点击ID
    $("#link-id-clear").bind("click", function () {
        app.searchLinkId = "";
    });
});
var app = new Vue({
    el: '#app',
    data: {
        downloadDetails: [],
        //搜索的名称
        searchAppName: "",
        //渠道通知结果状态
        searchNotifyStatus:"",
        //点击ID
        searchLinkId:"",
        //二级渠道
        subChannel: "",
        //三级渠道
        thirdSubChannel: "",
        //点击ID
        linkId: "",
        //下游渠道通知结果
        subNotifyResult: "",
        //通知时间
        notifyTime: "",
        //开始时间
        startTime: "",
        //结束时间
        endTime: "",
        //当前页
        pageIndex: 1,
        //总记录数
        total: 0,
        //总页数
        totalPage: 1,
        //每页记录数
        PAGE_SIZE: 10,
        //上一页是否能按
        previous_status: false,
        //下一页是否能按
        next_status: false,
        //用户名
        userName: "",
        //二级渠道列表
        subChannels: [],
        //三级渠道列表
        thirdSubChannels: [],
        //跳转页码
        skipPageNumber : ""
    },
    created(){

        var nowDate = commonUtils.getNowFormatDate(0);
        var nextDate = commonUtils.getNowFormatDate(1);
        this.startTime = nowDate;
        this.endTime = nextDate;

        //获取二、三级渠道列表
        this.getChannelInfos();
        var data = {
            "appName": this.searchAppName,
            "subChannel": this.subChannel,
            "notifyStatus": this.searchNotifyStatus,
            "linkId": this.searchLinkId,
            "startTime": this.startTime,
            "endTime": this.endTime,
            "pageIndex": this.pageIndex,
            "pageSize": this.PAGE_SIZE
        }
        this.getDownloadList(data);
        this.userName = commonUtils.getCookie("userName");

    },
    methods: {
        //获取二三级渠道列表
        getChannelInfos: function () {
            var data = {
                "initType": "3"
            };
            var vm = this;
            var url = "/gsmp/page/init";
            var type = "post";
            commonUtils.ajax(data, url, type, function (data) {
                if (data.resultCode != '0') {
                    console.log("fail");
                    return;
                }
                console.log(data);
                vm.subChannels = [];
                vm.subChannels = data.subChannels;
                console.log(vm.subChannels);
                vm.thirdSubChannels = [];
                vm.thirdSubChannels = data.thirdSubChannels;
            }, function (error) {
                console.log(error);
            });
        },
        /**
         * 获取列表
         * @param  {[type]} data 请求数据
         * @return {[type]}
         */
        getDownloadList: function (data) {
            var vm = this;
            var url = "/gsmp/download/queryDetails";
            var type = "post";
            commonUtils.ajax(data, url, type, function (data) {
                if (data.resultCode != '0') {
                    console.log("fail");
                    return;
                }
                vm.downloadDetails = [];
                vm.downloadDetails = data.downloadDetailsList;
                //总记录数
                vm.total = data.total;
                //分页
                vm.pagination(vm.total, vm.pageIndex);
            }, function (error) {
                console.log(error);
            });
        },
        /**
         * 搜索
         * @param  {[type]} pageIndex 当前页
         * @return {[type]}
         */
        search: function (pageIndex) {
            //页码规则校验
            if(pageIndex == ""){
                return;
            }
            if(isNaN(pageIndex)){
                return;
            }
            if(parseInt(pageIndex) > this.totalPage || parseInt(pageIndex) < 1){
                return;
            }
            this.pageIndex = parseInt(pageIndex);
            //防止数据请求太慢导致出现0页问题
            this.next_status = false;
            this.previous_status = false;
            var data = {
                "appName": this.searchAppName,
                "subChannel": this.subChannel,
                "notifyStatus": this.searchNotifyStatus,
                "linkId": this.searchLinkId,
                "startTime": this.startTime,
                "endTime": this.endTime,
                "pageIndex": this.pageIndex,
                "pageSize": this.PAGE_SIZE
            };
            this.getDownloadList(data);
        },
        /**
         * 分页
         * @param  {[type]} total     [description]
         * @param  {[type]} pageIndex [description]
         * @return {[type]}           [description]
         */
        pagination: function (total, pageIndex) {
            //总页数
            this.totalPage = parseInt((total + this.PAGE_SIZE - 1) / this.PAGE_SIZE);
            //若总页数为0，则修改为1
            if (this.totalPage == 0) {
                this.totalPage = 1;
            }
            //当前页为最后一页
            if (pageIndex == this.totalPage) {
                //没有下一页
                this.next_status = false;
            } else {
                this.next_status = true;
            }
            //当前页为第一页
            if (pageIndex == 1) {
                //没有上一页
                this.previous_status = false;
            } else {
                this.previous_status = true;
            }
        },
        //退出登录
        logout: function () {
            commonMethods.logout(function (data) {

            }, function (error) {

            });
        }
    }
});