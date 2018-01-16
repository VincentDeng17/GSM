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
    $("#app-id-clear").bind("click", function () {
        app.search_appId = "";
        $("#app-id").val("");
    });
    $("#app-name-clear").bind("click", function () {
        app.search_appName = "";
        $("#app-name").val("");
    });
    $("#start-time-clear").bind("click", function () {
        app.startTime = "";
        $("#start-time").val("");
    });
    $("#end-time-clear").bind("click", function () {
        app.endTime = "";
        $("#end-time").val("");
    });
});
var app = new Vue({
    el: '#app',
    data: {
        /*应用信息字段定义开始*/
        appVos: [],
        //应用ID
        appId: "",
        //应用名称
        appName: "",
        //应用描述
        appDesc: "",
        //备份
        remarks: "",
        //创建时间
        createTime: "",
        /*应用信息字段定义结束*/

        //全选按钮状态
        allCheckStatus : false,
        //checkbox状态
        checkboxStatus : [],
        //错误信息是否显示
        errorMessageShow: false,
        //错误信息内容
        errorMessage: "",
        /*应用搜索字段定义开始*/
        //应用ID
        search_appId: "",
        //搜索的渠道名称
        search_appName: "",
        //搜索的渠道级别
        startTime: "",
        //搜索的渠道级别
        endTime: "",
        /*应用搜索字段定义结束*/

        userName: "",
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
        //跳转页码
        skipPageNumber : "",
        /*更新字段定义开始*/
        edit_appId: "",
        edit_appName: "",
        edit_appDesc: "",
        edit_remarks: "",
        edit_errorMessageShow: false,
        //错误信息内容
        edit_errorMessage: ""
        /*更新字段定义结束*/
    },
    created(){
        var nowDate = commonUtils.getNowFormatDate(-29);
        var nextDate = commonUtils.getNowFormatDate(1);
        this.startTime = nowDate;
        this.endTime = nextDate;
        this.getAppInfos();
        this.userName = commonUtils.getCookie("userName");
    },
    methods: {
        //获取列表
        getAppInfos: function () {
            var data = {
                "pageIndex": this.pageIndex,
                "pageSize": this.PAGE_SIZE,
                "appId":this.search_appId,
                "appName":this.search_appName,
                "startTime":this.startTime,
                "endTime":this.endTime
            };
            var vm = this;
            var url = "/gsmp/app/qryAppByPara";
            var type = "post";
            commonUtils.ajax(data, url, type, function (data) {
                if (data.resultCode != '0') {
                    console.log("fail");
                    return;
                }
                vm.appVos = [];
                vm.appVos = data.appVos;
                //总记录数
                vm.total = data.total;
                //分页
                vm.pagination(vm.total, vm.pageIndex);
            }, function (error) {
                console.log(error);
            });
        },
        //新增
        addApp: function () {
            //参数校验
            if (this.appName == "") {
                this.errorMessage = "必填字段不能为空";
                this.errorMessageShow = true;
                return;
            }
            //调用接口新增
            var vm = this;
            var url = "/gsmp/app/addApp";
            var type = "post";
            var data = {
                "appName": this.appName,
                "appInfo": this.appDesc,
                "remark": this.remarks
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
                vm.appName = "";
                vm.appDesc = "";
                vm.remarks = "";
                //刷新列表
                vm.getAppInfos();
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
            this.getAppInfos();
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
            var idList = [];
            for(i=0;i<this.appVos.length;i++){
                if(this.checkboxStatus[i]){
                    idList.push(this.appVos[i].appId)
                }
            }
            if(idList.length == 0){
                alert("请勾选要删除的配置");
                return;
            }
            $('#delcfmModel').modal();
        },
        deleteApp :function () {

            var idList = [];
            for(i=0;i<this.appVos.length;i++){
                if(this.checkboxStatus[i]){
                    idList.push(this.appVos[i].appId)
                }
            }
            var data = {
                "appIds":idList
            }
            var vm =this;
            $.ajax({
                url: "/gsmp/app/batchDelApp",
                type: "POST",
                data: JSON.stringify(data),
                dataType: 'json',
                contentType: "application/json",
                success: function (data) {
                    //接口鉴权不通过，返回登录页
                    if(data.resultCode=='902'){
                        window.location.href= "login.html";
                        return;
                    }
                    if(data.resultCode !='0'){
                        vm.errorMessage = "删除失败！";
                        vm.errorMessageShow = true;
                        return;
                    }
                    vm.errorMessage = "";
                    vm.errorMessageShow = false;

                    vm.getAppInfos();
                    //checkbox初始化
                    for(var i=0;i<vm.PAGE_SIZE;i++){
                        vm.checkboxStatus[i] = false;
                    }
                },
                error: function (e) {
                    console.log(e);
                    this.errorMessage = "删除失败！";
                    this.errorMessageShow = true;
                }
            });

        },
        openEditDialog : function (){
            //处理操作，最多只能选择一个进行编辑
            var count = 0 ;
            var index ;
            for(i=0;i<this.appVos.length;i++){
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
            this.edit_appId = this.appVos[index].appId;
            this.edit_appName = this.appVos[index].appName;
            this.edit_appDesc = this.appVos[index].appInfo;
            this.edit_remarks = this.appVos[index].remark;

            this.edit_errorMessage = "";
            this.edit_errorMessageShow = false;
            //打开编辑窗口
            $("#modifyModalLabel").modal('toggle');
        },
        updateApp : function(){
            //参数校验
            if (this.edit_appId == "" || this.edit_appName == "") {
                this.edit_errorMessage = "必填字段不能为空";
                this.edit_errorMessageShow = true;
                return;
            }
            //调用接口更新
            var vm = this;
            var url = "/gsmp/app/modifyApp";
            var type = "post";
            var data = {
                "appId": this.edit_appId,
                "appName": this.edit_appName,
                "appInfo": this.edit_appDesc,
                "remark": this.edit_remarks
            };
            commonUtils.ajax(data, url, type, function (data) {
                if (data.resultCode != '0') {
                    vm.edit_errorMessage = "更新失败！";
                    vm.edit_errorMessageShow = true;
                    return;
                }
                //更新成功
                vm.edit_errorMessageShow = false;
                vm.edit_errorMessage = "";
                //隐藏弹窗
                $('#modifyModalLabel').modal('hide');
                //checkbox初始化
                for(i=0;i<vm.PAGE_SIZE;i++){
                    vm.checkboxStatus[i] = false;
                }
                //刷新列表
                vm.getAppInfos();
            }, function (error) {
                console.log(error);
                vm.edit_errorMessage = "更新失败！";
                vm.edit_errorMessageShow = true;
            });
        }
    }
});