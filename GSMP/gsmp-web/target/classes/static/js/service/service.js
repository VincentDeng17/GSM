$(document).ready(function () {
    $('#addModalLabel').on('hidden.bs.modal', function (e) {
        service.errorMessage = "";
        service.errorMessageShow = false;
        service.serviceCode = "";
        service.serviceName = "";
        service.remarks = "";
    });
    //按钮绑值
    $("#service-code-clear").bind("click", function () {
        service.searchServiceCode = "";
    });
    $("#service-name-clear").bind("click", function () {
        service.searchServiceName = "";
    });
});
var service = new Vue({
    el: '#service',
    data: {
        serviceInfos: [],
        //渠道名称
        serviceId: "",
        //渠道名称
        serviceCode: "",
        //渠道级别
        serviceName: "",
        //通知回调URL
        remarks: "",
        //备注
        createTime: "",
        //错误信息是否显示
        errorMessageShow: false,
        //错误信息内容
        errorMessage: "",
        //搜索的渠道ID
        searchServiceCode: "",
        //搜索的渠道名称
        searchServiceName: "",
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
        //全选按钮状态
        allCheckStatus : false,
        //checkbox状态
        checkboxStatus : [],
        /*更新字段*/
        edit_serviceId : "",
        edit_serviceCode : "",
        edit_serviceName : "",
        edit_remarks : "",
        edit_errorMessageShow: false,
        //错误信息内容
        edit_errorMessage: ""
    },
    created(){
        this.getServiceInfos();
        this.userName = commonUtils.getCookie("userName");
    },
    methods: {
        //获取列表
        getServiceInfos: function () {
            var data = {
                "pageIndex": this.pageIndex,
                "pageSize": this.PAGE_SIZE,
                "serviceCode": this.searchServiceCode,
                "serviceName": this.searchServiceName
            };
            var vm = this;
            var url = "/gsmp/serviceInfo/query";
            var type = "post";
            commonUtils.ajax(data, url, type, function (data) {
                if (data.resultCode != '0') {
                    console.log("fail");
                    return;
                }
                vm.serviceInfos = [];
                vm.serviceInfos = data.serviceInfos;
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
            if (this.serviceCode == "" || this.serviceName == "") {
                this.errorMessage = "必填字段不能为空";
                this.errorMessageShow = true;
                return;
            }
            //调用接口新增
            var vm = this;
            var url = "/gsmp/serviceInfo/add";
            var type = "post";
            var data = {
                "serviceCode": this.serviceCode,
                "serviceName": this.serviceName,
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
                vm.searchServiceCode = "";
                vm.searchServiceName = "";
                vm.serviceCode = "";
                vm.serviceName = "";
                vm.remarks = "";
                //刷新列表
                vm.getServiceInfos();
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
            this.getServiceInfos();
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
        deleteService: function () {
            var checkFlag = false;
            for(i=0;i<this.serviceInfos.length;i++){
                if(this.checkboxStatus[i]){
                    checkFlag = true;
                    //删除请求
                    var data = {
                        "serviceId": this.serviceInfos[i].serviceId,
                    };
                    var url = "/gsmp/serviceInfo/del";
                    var type = "post";
                    var vm = this;
                    commonUtils.ajax(data, url, type, function (data) {
                        if (data.resultCode != '0') {
                            console.log("fail");
                            alert("删除失败!请重试");
                            return;
                        }
                        vm.getServiceInfos();
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
        openEditDialog : function (){
            //处理操作，最多只能选择一个进行编辑
            var count = 0 ;
            var index ;
            for(i=0;i<this.serviceInfos.length;i++){
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
            this.edit_serviceId =  this.serviceInfos[index].serviceId;
            this.edit_serviceCode = this.serviceInfos[index].serviceCode;
            this.edit_serviceName = this.serviceInfos[index].serviceName;
            this.edit_remarks = this.serviceInfos[index].remarks;

            this.edit_errorMessage = "";
            this.edit_errorMessageShow = false;
            //打开编辑窗口
            $("#modifyModalLabel").modal('toggle');
        },
        updateService : function(){
            //参数校验
            if (this.edit_serviceCode == "" || this.edit_serviceName == "") {
                this.edit_errorMessage = "必填字段不能为空";
                this.edit_errorMessageShow = true;
                return;
            }
            //调用接口更新
            var vm = this;
            var url = "/gsmp/serviceInfo/mod";
            var type = "post";
            var data = {
                "serviceId":this.edit_serviceId,
                "serviceCode": this.edit_serviceCode,
                "serviceName": this.edit_serviceName,
                "remarks": this.edit_remarks
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
                vm.getServiceInfos();
            }, function (error) {
                console.log(error);
                vm.edit_errorMessage = "更新失败！";
                vm.edit_errorMessageShow = true;
            });
        }
    }
});