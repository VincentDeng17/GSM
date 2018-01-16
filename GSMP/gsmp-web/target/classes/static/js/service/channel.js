$(document).ready(function () {
    $('#addModalLabel').on('hidden.bs.modal', function (e) {
        channel.errorMessage = "";
        channel.errorMessageShow = false;
        channel.channelId = "";
        channel.channelName = "";
        channel.level = "2";
        channel.notifyUrl = "";
        channel.callbackParams = "";
        channel.remarks = "";
    });
    //按钮绑值
    $("#channel-id-clear").bind("click", function () {
        channel.searchChannelId = "";
    });
    $("#channel-name-clear").bind("click", function () {
        channel.searchChannelName = "";
    });
});
var channel = new Vue({
    el: '#channel',
    data: {
        channelInfos: [],
        //渠道名称
        channelId: "",
        //渠道名称
        channelName: "",
        //渠道级别
        level: "",
        //通知回调URL
        notifyUrl: "",
        //回调参数
        callbackParams: "",
        //备注
        remarks: "",
        //错误信息是否显示
        errorMessageShow: false,
        //错误信息内容
        errorMessage: "",
        //搜索的渠道ID
        searchChannelId: "",
        //搜索的渠道名称
        searchChannelName: "",
        //搜索的渠道级别
        searchChannelLevel: "",
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
        //跳转页码
        skipPageNumber : "",
        //删除功能:多选框
        //checkbox状态
        checkboxStatus : [],
        //全选按钮状态
        allCheckStatus : false,
        //要更新的字段----------
        editChannelId : "",
        editChannelName : "",
        editNotifyUrl : "",
        editCallbackParams : "",
        editRemarks : "",
        editErrorMessage : "",
        editErrorMessageShow : false,
        editLevel : ""
        //要更新的字段----------
    },
    created(){
        this.getChannelInfos();
        this.userName = commonUtils.getCookie("userName");

        //checkbox初始化
        for(i=0;i<this.PAGE_SIZE;i++){
            this.checkboxStatus[i] = false;
        }
    },
    methods: {
        //获取列表
        getChannelInfos: function () {
            var data = {
                "pageIndex": this.pageIndex,
                "pageSize": this.PAGE_SIZE,
                "channelId": this.searchChannelId,
                "channelName": this.searchChannelName,
                "level": this.searchChannelLevel
            };
            var vm = this;
            var url = "/gsmp/channelManage/query";
            var type = "post";
            commonUtils.ajax(data, url, type, function (data) {
                if (data.resultCode != '0') {
                    console.log("fail");
                    return;
                }
                vm.channelInfos = [];
                vm.channelInfos = data.channelInfos;
                //总记录数
                vm.total = data.total;
                //分页
                vm.pagination(vm.total, vm.pageIndex);
            }, function (error) {
                console.log(error);
            });
        },
        //新增
        addChannel: function () {
            //参数校验
            if (this.channelId == "" || this.channelName == "" || this.level == "") {
                this.errorMessage = "必填字段不能为空";
                this.errorMessageShow = true;
                return;
            }
            //调用接口新增
            var vm = this;
            var url = "/gsmp/channelManage/add";
            var type = "post";
            var data = {
                "channelId": this.channelId,
                "channelName": this.channelName,
                "level": this.level,
                "notifyUrl": this.notifyUrl,
                "callbackParams": this.callbackParams,
                "remarks": this.remarks
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
                vm.searchChannelId = "";
                vm.searchChannelName = "";
                vm.searchChannelLevel = "";
                vm.channelId = "";
                vm.channelName = "";
                vm.level = "2";
                vm.notifyUrl = "";
                vm.callbackParams= "";
                vm.remarks = "";
                //刷新列表
                vm.getChannelInfos();
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
            this.getChannelInfos();
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
            $('#delcfmModel').modal();
        },
        //删除
        deleteChannel : function (){
            var checkFlag = false;
            for(i=0;i<this.channelInfos.length;i++){
                if(this.checkboxStatus[i]){
                    checkFlag = true;
                    //删除请求
                    var data = {
                        "channelId": this.channelInfos[i].channelId,
                    };
                    var url = "/gsmp/channelManage/del";
                    var type = "post";
                    var vm = this;
                    commonUtils.ajax(data, url, type, function (data) {
                        if (data.resultCode != '0') {
                            console.log("fail");
                            alert("删除失败!请重试");
                            return;
                        }
                        vm.getChannelInfos();
                        //checkbox初始化
                        for(i=0;i<vm.PAGE_SIZE;i++){
                            vm.checkboxStatus[i] = false;
                        }
                    }, function (error) {
                        console.log(error);
                        alert("删除失败!请重试");
                    });
                }
            }
            if(!checkFlag){
                alert("请勾选要删除的配置");
            }
        },
        //打开编辑界面
        openEditDialog : function (){
            //处理操作，最多只能选择一个进行编辑
            var count = 0 ;
            var index ;
            for(i=0;i<this.channelInfos.length;i++){
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
            this.editChannelId = this.channelInfos[index].channelId;
            this.editChannelName = this.channelInfos[index].channelName;
            this.editLevel = this.channelInfos[index].level;
            this.editNotifyUrl = this.channelInfos[index].notifyUrl;
            this.editCallbackParams = this.channelInfos[index].callbackParams;
            this.editRemarks = this.channelInfos[index].remarks;
            this.editErrorMessage = "";
            this.editErrorMessageShow = false;
            //打开编辑窗口
            $("#editModalLabel").modal('toggle');
        },
        updateChannel : function(){
            //参数校验
            if (this.editChannelId == "" || this.editChannelName == "" || this.editLevel == "") {
                this.editErrorMessage = "必填字段不能为空";
                this.editErrorMessageShow = true;
                return;
            }
            //调用接口更新
            var vm = this;
            var url = "/gsmp/channelManage/mod";
            var type = "post";
            var data = {
                "channelId": this.editChannelId,
                "channelName": this.editChannelName,
                "level": this.editLevel,
                "notifyUrl": this.editNotifyUrl,
                "callbackParams": this.editCallbackParams,
                "remarks": this.editRemarks
            };
            commonUtils.ajax(data, url, type, function (data) {
                if (data.resultCode != '0') {
                    vm.editErrorMessage = "更新失败！";
                    vm.editErrorMessageShow = true;
                    return;
                }
                //更新成功
                vm.editErrorMessageShow = false;
                vm.editErrorMessage = "";
                //隐藏弹窗
                $('#editModalLabel').modal('hide');
                //checkbox初始化
                for(i=0;i<vm.PAGE_SIZE;i++){
                    vm.checkboxStatus[i] = false;
                }
                //刷新列表
                vm.getChannelInfos();
            }, function (error) {
                console.log(error);
                vm.editErrorMessage = "更新失败！";
                vm.editErrorMessageShow = true;
            });
        }
    }
});