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
    $("#start-time-clear").bind("click", function () {
        app.startTime = "";
        $("#start-time").val("");
    });
    $("#end-time-clear").bind("click", function () {
        app.endTime = "";
        $("#end-time").val("");
    });
    /*新增下拉框时间绑定*/
    $("#add-country-list").bind("change", function () {
        var val = $('#add-country-list :selected').val().trim();
        if ("" != val) {
            var countryAbbreviation = $('#add-country-list :selected').text().trim();
            $("#add-name-one").text(countryAbbreviation + "-");
        }
    });
    $("#add-service-list").bind("change", function () {
        var val = $('#add-service-list :selected').val().trim();
        if ("" != val) {
            var servceName = $('#add-service-list :selected').text().trim();
            $("#add-name-two").text(servceName + "-");
        }
    });
    $("#add-app-list").bind("change", function () {
        var val = $('#add-app-list :selected').val().trim();
        if ("" != val) {
            var appName = $('#add-app-list :selected').text().trim();
            $("#add-name-three").text(appName);
        }
    });
    /*编辑下拉框时间绑定*/
    $("#modify-country-list").bind("change", function () {
        var val = $('#modify-country-list :selected').val().trim();
        if ("" != val) {
            var countryAbbreviation = $('#modify-country-list :selected').text().trim();
            $("#modify-name-one").text(countryAbbreviation + "-");
        }
    });
    $("#modify-service-list").bind("change", function () {
        var val = $('#modify-service-list :selected').val().trim();
        if ("" != val) {
            var servceName = $('#modify-service-list :selected').text().trim();
            $("#modify-name-two").text(servceName + "-");
        }
    });
    $("#modify-app-list").bind("change", function () {
        var val = $('#modify-app-list :selected').val().trim();
        if ("" != val) {
            var appName = $('#modify-app-list :selected').text().trim();
            $("#modify-name-three").text(appName);
        }
    });

});
var app = new Vue({
    el: '#config',
    data: {

        /*下拉框初始化字段定义开始*/
        appList: [],
        countryList: [],
        serviceList: [],
        appConfigList: [],
        countryInfo: "",
        serviceInfo: "",
        appInfo: "",
        appConfig: "",
        /*下拉框初始化字段定义结束*/

        userName: "",
        startTime: "",
        endTime: "",

        configId: "",
        country_Id: "",
        search_countryId: "",
        countryAbbreviation: "",
        search_serviceId: "",
        service_Id: "",
        serviceName: "",
        search_appName: "",
        app_Id: "",
        originalAppName: "",
        originalUrl: "",
        createTime: "",
        realAppName: "",

        //错误信息是否显示
        errorMessageShow: false,
        //错误信息内容
        errorMessage: "",

        //checkbox状态
        checkboxStatus: [],
        //全选按钮状态
        allCheckStatus: false,
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
        skipPageNumber: "",
        /*更新字段定义*/
        edit_config_Id: "",
        edit_country_Id: "",
        edit_service_Id: "",
        edit_app_Id: "",
        edit_appName: "",
        edit_realAppName: "",
        edit_originalUrl: "",
        edit_errorMessage: "",
        edit_errorMessageShow: false,

        /*应用真实名称 实时展示*/
        name_one: "",
        name_two: "",
        name_three: ""
    },
    created(){
        var nowDate = commonUtils.getNowFormatDate(-29);
        var nextDate = commonUtils.getNowFormatDate(1);
        this.startTime = nowDate;
        this.endTime = nextDate;
        this.getAppInfos();
        this.getServiceInfos();
        this.getCountryInfos();
        this.getConfigInfos();
        this.userName = commonUtils.getCookie("userName");
    },
    methods: {
        //获取国家列表
        getCountryInfos: function () {
            var data = {
                "flag": "1"
            };
            var vm = this;
            var url = "/gsmp/country/query";
            var type = "post";
            commonUtils.ajax(data, url, type, function (data) {
                if (data.resultCode != '0') {
                    console.log("fail");
                    return;
                }
                vm.countryList = [];
                vm.countryList = data.countryInfos;
            }, function (error) {
                console.log(error);
            });
        },
        //获取运营商列表
        getServiceInfos: function () {
            var data = {
                "flag": "1"
            };
            var vm = this;
            var url = "/gsmp/serviceInfo/query";
            var type = "post";
            commonUtils.ajax(data, url, type, function (data) {
                if (data.resultCode != '0') {
                    console.log("fail");
                    return;
                }
                vm.serviceList = [];
                vm.serviceList = data.serviceInfos;
            }, function (error) {
                console.log(error);
            });
        },
        //获取应用列表
        getAppInfos: function () {
            var data = {};
            var vm = this;
            var url = "/gsmp/app/qryAppByPara";
            var type = "post";
            commonUtils.ajax(data, url, type, function (data) {
                if (data.resultCode != '0') {
                    console.log("fail");
                    return;
                }
                vm.appList = [];
                vm.appList = data.appVos;
            }, function (error) {
                console.log(error);
            });
        },
        //获取应用配置列表
        getConfigInfos: function () {
            var data = {
                "pageIndex": this.pageIndex,
                "pageSize": this.PAGE_SIZE,
                "countryId": this.search_countryId,
                "serviceId": this.search_serviceId,
                "appName": this.search_appName,
                "startTime": this.startTime,
                "endTime": this.endTime
            };
            var vm = this;
            var url = "/gsmp/appConfig/query";
            var type = "post";
            commonUtils.ajax(data, url, type, function (data) {
                if (data.resultCode != '0') {
                    console.log("fail");
                    return;
                }
                vm.appConfigList = [];
                vm.appConfigList = data.appConfigs;

                //总记录数
                vm.total = data.total;
                //分页
                vm.pagination(vm.total, vm.pageIndex);
            }, function (error) {
                console.log(error);
            });
        },
        //退出登录
        logout: function () {
            commonMethods.logout(function (data) {

            }, function (error) {

            });
        },
        //全选
        selectAll: function () {
            if (this.allCheckStatus) {
                for (var i = 0; i < this.PAGE_SIZE; i++) {
                    this.checkboxStatus[i] = true;
                }
            } else {
                for (var i = 0; i < this.PAGE_SIZE; i++) {
                    this.checkboxStatus[i] = false;
                }
            }
        },
        //pageIndex : 当前页
        search: function (pageIndex) {
            //页码规则校验
            if (pageIndex == "") {
                return;
            }
            if (isNaN(pageIndex)) {
                return;
            }
            if (parseInt(pageIndex) > this.totalPage || parseInt(pageIndex) < 1) {
                return;
            }
            this.pageIndex = parseInt(pageIndex);
            //防止数据请求太慢导致出现0页问题
            this.next_status = false;
            this.previous_status = false;
            this.getConfigInfos();
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
        addAppConfig: function () {

            //参数校验
            if (this.country_Id == "" || this.service_Id == "" || this.app_Id == "") {
                this.errorMessage = "必填字段不能为空";
                this.errorMessageShow = true;
                return;
            }
            this.countryAbbreviation = $('#add-country-list :selected').text().trim();
            this.serviceName = $('#add-service-list :selected').text().trim();
            this.originalAppName = $('#add-app-list :selected').text().trim();
            this.realAppName = this.countryAbbreviation + "-" + this.serviceName + "-" + this.originalAppName;

            //调用接口新增
            var vm = this;
            var url = "/gsmp/appConfig/add";
            var type = "post";
            var data = {
                "countryId": this.country_Id,
                "serviceId": this.service_Id,
                "appId": this.app_Id,
                "appName": this.realAppName,
                "originalUrl": this.originalUrl
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

                vm.country_Id = "";
                vm.service_Id = "";
                vm.app_Id = "";
                vm.appName = "";
                vm.originalUrl = "";
                vm.countryAbbreviation = "";
                vm.serviceName = "";
                vm.originalAppName = "";
                vm.realAppName = "";
                $("#add-name-one").text("");
                $("#add-name-two").text("");
                $("#add-name-three").text("");
                //刷新列表
                vm.getConfigInfos();

            }, function (error) {
                console.log(error);
                vm.errorMessage = "新增失败！";
                vm.errorMessageShow = true;
            });
        },
        openEditDialog: function () {
            //处理操作，最多只能选择一个进行编辑
            var count = 0;
            var index;
            for (i = 0; i < this.appConfigList.length; i++) {
                if (this.checkboxStatus[i]) {
                    count++;
                    index = i;
                }
            }
            if (count < 1) {
                alert("要选择一个才能进行编辑~~");
                return;
            }
            if (count > 1) {
                alert("只能选择一个选项~~");
                return;
            }

            //要编辑的数据获取

            this.edit_config_Id = this.appConfigList[index].configId;
            this.edit_country_Id = this.appConfigList[index].countryId;
            this.edit_service_Id = this.appConfigList[index].serviceId;
            this.edit_app_Id = this.appConfigList[index].appId;
            this.edit_originalUrl = this.appConfigList[index].originalUrl;

            var countryName= this.appConfigList[index].countryAbbreviation;
            var serviceName = this.appConfigList[index].serviceName;
            var originalAppName = this.appConfigList[index].originalAppName;
            $("#modify-name-one").text(countryName + "-");
            $("#modify-name-two").text(serviceName + "-");
            $("#modify-name-three").text(originalAppName);

            this.edit_errorMessage = "";
            this.edit_errorMessageShow = false;
            //打开编辑窗口
            $("#modifyModalLabel").modal('toggle');
        },
        updateConfig: function () {
            //参数校验
            if (this.edit_country_Id == "" || this.edit_service_Id == "" || this.edit_app_Id == "") {
                this.edit_errorMessage = "必填字段不能为空";
                this.edit_errorMessageShow = true;
                return;
            }

            this.countryAbbreviation = $('#modify-country-list :selected').text().trim();
            this.serviceName = $('#modify-service-list :selected').text().trim();
            this.originalAppName = $('#modify-app-list :selected').text().trim();
            this.edit_realAppName = this.countryAbbreviation + "-" + this.serviceName + "-" + this.originalAppName;


            //调用接口更新
            var vm = this;
            var url = "/gsmp/appConfig/modify";
            var type = "post";
            var data = {
                "configId": this.edit_config_Id,
                "countryId": this.edit_country_Id,
                "serviceId": this.edit_service_Id,
                "appId": this.edit_app_Id,
                "appName": this.edit_realAppName,
                "originalUrl": this.edit_originalUrl
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
                for (var i = 0; i < vm.PAGE_SIZE; i++) {
                    vm.checkboxStatus[i] = false;
                }
                //刷新列表
                vm.getConfigInfos();

                vm.countryAbbreviation = "";
                vm.serviceName = "";
                vm.originalAppName = "";

                vm.edit_config_Id = "";
                vm.edit_country_Id = "";
                vm.edit_service_Id = "";
                vm.edit_app_Id = "";

                vm.edit_realAppName = "";
                vm.edit_originalUrl = "";

                this.edit_errorMessage = "";
                this.edit_errorMessageShow = false;
            }, function (error) {
                console.log(error);
                vm.edit_errorMessage = "更新失败！";
                vm.edit_errorMessageShow = true;
            });
        },
        del: function () {
            var idList = [];
            for (var i = 0; i < this.appConfigList.length; i++) {
                if (this.checkboxStatus[i]) {
                    idList.push(this.appConfigList[i].configId)
                }
            }
            if (idList.length == 0) {
                alert("请勾选要删除的配置");
                return;
            }
            $('#delcfmModel').modal();
        },
        deleteConfig: function () {

            var idList = [];
            for (var i = 0; i < this.appConfigList.length; i++) {
                if (this.checkboxStatus[i]) {
                    idList.push(this.appConfigList[i].configId)
                }
            }
            var data = {
                "configIdList": idList
            }
            var vm = this;
            var url = "/gsmp/appConfig/batchDelete";
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
                    vm.allCheckStatus = false;

                    vm.getConfigInfos();
                    //checkbox初始化
                    for (var i = 0; i < vm.PAGE_SIZE; i++) {
                        vm.checkboxStatus[i] = false;
                    }
                },
                function (e) {
                    console.log(e);
                    vm.errorMessage = "删除失败！";
                    vm.errorMessageShow = true;
                });

        },

    }
});