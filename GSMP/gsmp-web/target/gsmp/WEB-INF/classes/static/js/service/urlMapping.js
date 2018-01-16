$(document).ready(function () {
    $('#addModalLabel').on('hidden.bs.modal', function (e) {
        app.errorMessage = "";
        app.errorMessageShow = false;
        app.appName = "";
        app.clickIdName = "Quanten";
        app.clickId = "10076";
        // app.subChannel = "";
        app.thirdSubChannel = "";
        app.hwUrl = "";
        app.price = "";
        // app.subChannels= [];
        // app.thirdSubChannels= [];
    });
    //按钮绑值
    $("#app-name-clear").bind("click", function () {
        app.searchAppName = "";
    });
});
var app = new Vue({
    el: '#app',
    data: {
        urlManageInfos: [],
        //华为URL
        //hwUrl: "",
        //一级渠道名称
        clickIdName: "Quanten",
        //一级渠道
        clickId: "10076",
        //二级渠道
        subChannel: "",
        //三级渠道
        thirdSubChannel: "",
        //应用名称
        appName: "",
        //价格
        price: "",
        //错误信息是否显示
        errorMessageShow: false,
        //错误信息内容
        errorMessage: "",
        //搜索的名称
        searchAppName: "",
        //搜索的二级渠道
        searchSubChannelsID: "",
        //搜索的三级渠道
        searchThirdSubChannelsID: "",
        //当前页
        pageIndex: 1,
        //总记录数
        total: 0,
        //总页数
        totalPage: 1,
        //每页记录数
        PAGE_SIZE: 10,
        //上一页是否能按
        previous_status: true,
        //下一页是否能按
        next_status: true,
        //用户名
        userName: "",
        //二级渠道列表
        subChannels: [],
        //三级渠道列表
        thirdSubChannels: [],
        //跳转页码
        skipPageNumber : "",
        //checkbox状态
        checkboxStatus : [],
        //全选按钮状态
        allCheckStatus : false,
        //要更新的数据----------------
        //ID
        editmId : "",
        //应用名称
        editAppName : "",
        //华为URL
        editHwUrl : "",
        //价格
        editPrice : "",
        //二级渠道ID
        editSubChannelsID : "",
        //三级渠道ID
        editThirdSubChannelsID : "",
        //更新时候的错误信息
        editErrorMessage  : "",
        //更新时候的错误信息是否显示
        editErrorMessageShow : false,
        //要更新的数据----------------
        //应用配置信息----------------
        appConfigInfos : []
    },
    created(){
        //获取二、三级渠道列表
        this.getChannelInfos();
        this.getUrlMappings();
        this.userName = commonUtils.getCookie("userName");
        //查询应用配置信息
        this.getAppConfig();
        //checkbox初始化
        for(i=0;i<this.PAGE_SIZE;i++){
            this.checkboxStatus[i] = false;
        }
    },
    computed : {
        hwUrl : function(){
            if(this.appConfigInfos.length ==0 || this.appName == ""){
                return "";
            }
            var index ;
            for(var i=0;i<this.appConfigInfos.length;i++){
                if(this.appConfigInfos[i].appName == this.appName){
                    index = i ;
                    break;
                }
            }
            return this.appConfigInfos[index].originalUrl;
        }
    },
    methods: {
        //获取二三级渠道列表
        getChannelInfos: function () {
            var data = {
                "initType": "1"
            };
            var vm = this;
            var url = "/gsmp/page/init";
            var type = "post";
            commonUtils.ajax(data, url, type, function (data) {
                if (data.resultCode != '0') {
                    console.log("fail");
                    return;
                }
                vm.subChannels = [];
                vm.subChannels = data.subChannels;
                vm.thirdSubChannels = [];
                vm.thirdSubChannels = data.thirdSubChannels;
            }, function (error) {
                console.log(error);
            });
        },
        //查询应用配置信息
        getAppConfig: function(){
            var data = {
                "queryType" : "1"
            };
            var vm = this;
            var url = "/gsmp/appConfig/query";
            var type = "post";
            commonUtils.ajax(data, url, type, function (data) {
                if (data.resultCode != '0') {
                    console.log("fail");
                    return;
                }
                vm.appConfigInfos = data.appConfigs;
            }, function (error) {
                console.log(error);
            });
        },
        //获取列表
        getUrlMappings: function () {
            var data = {
                "pageIndex": this.pageIndex,
                "pageSize": this.PAGE_SIZE,
                "appName": this.searchAppName,
                "subChannel": this.searchSubChannelsID,
                "thirdSubChannel": this.searchThirdSubChannelsID
            };
            var vm = this;
            var url = "/gsmp/urlManage/query";
            var type = "post";
            commonUtils.ajax(data, url, type, function (data) {
                if (data.resultCode != '0') {
                    console.log("fail");
                    return;
                }
                vm.urlManageInfos = [];
                vm.urlManageInfos = data.urlManageInfos;
                //总记录数
                vm.total = data.total;
                //分页
                vm.pagination(vm.total, vm.pageIndex);
            }, function (error) {
                console.log(error);
            });
        },
        //新增
        addChannelUrl: function () {
            //参数校验
            if (this.hwUrl == "" || this.appName == ""  || this.price == "") {
                this.errorMessage = "必填字段不能为空";
                this.errorMessageShow = true;
                return;
            }
            //调用接口新增
            var vm = this;
            var url = "/gsmp/urlManage/add";
            var type = "post";
            var data = {
                "appName": this.appName,
                "clickId": this.clickId,
                "subChannel": this.subChannel,
                "thirdSubChannel": this.thirdSubChannel,
                "hwUrl": this.hwUrl,
                "price": this.price
            };
            commonUtils.ajax(data, url, type, function (data) {
                if (data.resultCode != '0') {
                    vm.errorMessage = "新增失败！";
                    vm.errorMessageShow = true;
                    return;
                }
                //新增成功
                vm.errorMessageShow = false;
                vm.errorMessage = "";
                //隐藏弹窗
                $('#addModalLabel').modal('hide');
                vm.searchAppName = "";
                vm.appName = "";
                vm.subChannel = "";
                vm.thirdSubChannel = "";
                vm.hwUrl = "";
                //刷新列表
                vm.getUrlMappings();
            }, function (error) {
                console.log(error);
                vm.errorMessage = "新增失败！";
                vm.errorMessageShow = true;
            });
        },
        //搜索
        //pageIndex : 当前页
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
            this.getUrlMappings();
        },
        //分页
        //total :总记录数
        //pageIndex : 当前页
        //PAGE_SIZE : 每页记录数
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
        },
        //导出
        exportChannel: function () {
            console.log("123");
            var data = {
                "appName": this.searchAppName,
                "subChannel": this.subChannel,
                "thirdSubChannel": this.thirdSubChannel
            };
            var url = "/gsmp/urlManage/export";
            var type = "post";
            commonUtils.ajax(data, url, type, function (data) {
                if (data.resultCode != '0') {
                    console.log("fail");
                    return;
                }
                console.log("导出成功");
            }, function (error) {
                console.log(error);
            });
        },
        //全选
        selectAll : function (){
            if(this.allCheckStatus){
                for(i=0;i<this.PAGE_SIZE;i++){
                    this.checkboxStatus[i] = true;
                }
            }else{
                for(i=0;i<this.PAGE_SIZE;i++){
                    this.checkboxStatus[i] = false;
                }
            }
        },
        del: function () {
            var idList = [];
            for (var i = 0; i < this.urlManageInfos.length; i++) {
                if (this.checkboxStatus[i]) {
                    idList.push(this.urlManageInfos[i].mId)
                }
            }
            if (idList.length == 0) {
                alert("请勾选要删除的配置");
                return;
            }
            $('#delcfmModel').modal();
        },
        //删除
        deleteConfig : function (){
            var idList = [];
            for (var i = 0; i < this.urlManageInfos.length; i++) {
                if (this.checkboxStatus[i]) {
                    idList.push(this.urlManageInfos[i].mId)
                }
            }
            var data = {
                "midList": idList
            }
            var vm = this;
            var url = "/gsmp/urlManage/del";
            var type = "post";
            commonUtils.ajax(data, url, type,
                function (data) {
                    //接口鉴权不通过，返回登录页
                    if (data.resultCode == '902') {
                        window.location.href = "login.html";
                        return;
                    }
                    if (data.resultCode != '0') {
                        vm.errorMessage = "删除失败！";
                        vm.errorMessageShow = true;
                        return;
                    }
                    vm.errorMessage = "";
                    vm.errorMessageShow = false;
                    //刷新列表
                    vm.getUrlMappings();
                    //checkbox初始化
                    for(var i=0;i<vm.PAGE_SIZE;i++){
                        vm.checkboxStatus[i] = false;
                    }
                },
                function (e) {
                    console.log(e);
                    vm.errorMessage = "删除失败！";
                    vm.errorMessageShow = true;
                });
        },
        //打开编辑窗口
        openEditDialog : function(){
            //处理操作，最多只能选择一个进行编辑
            var count = 0 ;
            var index ;
            for(i=0;i<this.urlManageInfos.length;i++){
                if(this.checkboxStatus[i]){
                    count ++;
                    index = i;
                }
            }
            if(count!=1){
                alert("要选择一个才能进行编辑~~");
                return;
            }
            if(count > 1){
                alert("只能选择一个选项~~");
                return;
            }
            //要编辑的数据获取
            this.editmId = this.urlManageInfos[index].mId;
            this.editAppName = this.urlManageInfos[index].appName;
            this.editHwUrl = this.urlManageInfos[index].hwUrl;
            this.editPrice = this.urlManageInfos[index].price;
            this.editErrorMessage = "";
            this.editErrorMessageShow = false;
            //二级渠道获取
            var subChannelName = this.urlManageInfos[index].subChannel;
            for(j=0;j<this.subChannels.length;j++){
                if(subChannelName==this.subChannels[j].channelName) {
                    this.editSubChannelsID = this.subChannels[j].channelId;
                }
            }
            //三级渠道获取
            var thirdSubChannelName = this.urlManageInfos[index].thirdSubChannel;
            for(k=0;k<this.thirdSubChannels.length;k++){
                if(thirdSubChannelName==this.thirdSubChannels[k].channelName) {
                    this.editThirdSubChannelsID = this.thirdSubChannels[k].channelId;
                }
            }
            //打开编辑窗口
            $("#editModalLabel").modal('toggle');
        },
        //更新配置
        updateChannelUrl : function(){
            //参数校验
            if (this.editHwUrl == "" || this.editAppName == ""  || this.editPrice == "") {
                this.editErrorMessage = "必填字段不能为空";
                this.editErrorMessageShow = true;
                return;
            }
            //调用接口更新
            var vm = this;
            var url = "/gsmp/urlManage/mod";
            var type = "post";
            var data = {
                "mId" : this.editmId,
                "appName": this.editAppName,
                "clickId": this.clickId,
                "subChannel": this.editSubChannelsID,
                "thirdSubChannel": this.editThirdSubChannelsID,
                "hwUrl": this.editHwUrl,
                "price": this.editPrice
            };
            commonUtils.ajax(data, url, type, function (data) {
                if (data.resultCode != '0') {
                    console.log("fail");
                    vm.editErrorMessage = "更新失败";
                    vm.editErrorMessageShow = true;
                    return;
                }
                //更新成功
                vm.editErrorMessageShow = false;
                vm.editErrorMessage = "";
                //隐藏弹窗
                $('#editModalLabel').modal('hide');
                vm.getChannelInfos();
                vm.getUrlMappings();
                //checkbox初始化
                for(i=0;i<vm.PAGE_SIZE;i++){
                    vm.checkboxStatus[i] = false;
                }
            }, function (error) {
                console.log(error);
                vm.editErrorMessage = "更新失败";
                vm.editErrorMessageShow = true;
            });
        }
    }
});