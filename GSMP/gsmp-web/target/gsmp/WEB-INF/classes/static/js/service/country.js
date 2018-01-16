$(document).ready(function () {
    $('#addModalLabel').on('hidden.bs.modal', function (e) {
        country.errorMessage = "";
        country.errorMessageShow = false;
        country.countryAbbreviation = "";
        country.countryName = "";
        country.englishName = "";
        country.notifyUrl = "";
        country.telCode = "";
        country.timeZone = "";
        country.suggestPrice = "";
    });
    //按钮绑值
    $("#country-abbreviation-clear").bind("click", function () {
        country.searchCountryAbbreviation = "";
    });
    $("#country-name-clear").bind("click", function () {
        country.searchCountryName = "";
    });
    $("#english-name-clear").bind("click", function () {
        country.searchEnglishName = "";
    });
    $("#tel-code-clear").bind("click", function () {
        country.searchTelCode = "";
    });
    $("#time-zone-clear").bind("click", function () {
        country.searchTimeZone = "";
    });
    $("#suggest-price-clear").bind("click", function () {
        country.searchSuggestPrice = "";
    });
});
var country = new Vue({
    el: '#country',
    data: {
        countryInfos: [],
        //渠道名称
        countryId: "",
        //渠道名称
        countryAbbreviation: "",
        //全选按钮状态
        allCheckStatus: false,
        //checkbox状态
        checkboxStatus: [],
        //渠道级别
        countryName: "",
        //通知回调URL
        englishName: "",
        //电话代码
        telCode: "",
        //创建时间
        createTime: "",
        //所属时区
        timeZone : "",
        //建议价格
        suggestPrice : "",
        //错误信息是否显示
        errorMessageShow: false,
        //错误信息内容
        errorMessage: "",
        //搜索的渠道ID
        searchCountryAbbreviation: "",
        //搜索的渠道名称
        searchCountryName: "",
        //搜索的渠道级别
        searchEnglishName: "",
        //搜索的渠道级别
        searchTelCode: "",
        //搜索的时区
        searchTimeZone: "",
        //搜索的建议价格
        searchSuggestPrice : "",
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
        skipPageNumber: "",
        /*更新字段*/
        edit_countryId: "",
        edit_countryAbbreviation: "",
        edit_countryName: "",
        edit_englishName: "",
        edit_telCode: "",
        edit_timeZone: "",
        edit_suggestPrice : "",
        edit_errorMessageShow: false,
        //错误信息内容
        edit_errorMessage: ""
    },
    created(){
        this.getCountryInfos();
        this.userName = commonUtils.getCookie("userName");
    },
    methods: {
        //获取列表
        getCountryInfos: function () {
            var data = {
                "pageIndex": this.pageIndex,
                "pageSize": this.PAGE_SIZE,
                "countryAbbreviation": this.searchCountryAbbreviation,
                "countryName": this.searchCountryName,
                "englishName": this.searchEnglishName,
                "telCode": this.searchTelCode,
                "timeZone" : this.searchTimeZone,
                "suggestPrice" : this.searchSuggestPrice
            };
            var vm = this;
            var url = "/gsmp/country/query";
            var type = "post";
            commonUtils.ajax(data, url, type, function (data) {
                if (data.resultCode != '0') {
                    console.log("fail");
                    return;
                }
                vm.countryInfos = [];
                vm.countryInfos = data.countryInfos;
                //总记录数
                vm.total = data.total;
                //分页
                vm.pagination(vm.total, vm.pageIndex);
            }, function (error) {
                console.log(error);
            });
        },
        //新增
        addCountry: function () {
            //参数校验
            if (this.countryAbbreviation == "") {
                this.errorMessage = "必填字段不能为空";
                this.errorMessageShow = true;
                return;
            }
            if(this.timeZone == ""){
                this.errorMessage = "必填字段不能为空";
                this.errorMessageShow = true;
                return;
            }
            if(this.suggestPrice == ""){
                this.errorMessage = "请输入合法的价格";
                this.errorMessageShow = true;
                return;
            }
            //调用接口新增
            var vm = this;
            var url = "/gsmp/country/add";
            var type = "post";
            var data = {
                "countryAbbreviation": this.countryAbbreviation,
                "countryName": this.countryName,
                "englishName": this.englishName,
                "telCode": this.telCode,
                "timeZone" : this.timeZone,
                "suggestPrice" : this.suggestPrice
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
                vm.searchCountryAbbreviation = "";
                vm.searchCountryName = "";
                vm.searchEnglishName = "";
                vm.searchTelCode = "";
                vm.countryAbbreviation = "";
                vm.countryName = "";
                vm.englishName = "2";
                vm.telCode = "";
                //刷新列表
                vm.getCountryInfos();
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
            this.getCountryInfos();
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
        selectAll: function () {
            if (this.allCheckStatus) {
                for (i = 0; i < this.PAGE_SIZE; i++) {
                    this.checkboxStatus[i] = true;
                }
            } else {
                for (i = 0; i < this.PAGE_SIZE; i++) {
                    this.checkboxStatus[i] = false;
                }
            }
        },
        del: function () {
            var idList = [];
            for(i=0;i<this.countryInfos.length;i++){
                if(this.checkboxStatus[i]){
                    idList.push(this.countryInfos[i].countryId)
                }
            }
            if(idList.length == 0){
                alert("请勾选要删除的配置");
                return;
            }
            $('#delcfmModel').modal();
        },
        deleteCountry: function () {

            var idList = [];
            for(i=0;i<this.countryInfos.length;i++){
                if(this.checkboxStatus[i]){
                    idList.push(this.countryInfos[i].countryId)
                }
            }
            var data = {
                "countryIdList":idList
            }
            var url = "/gsmp/country/del";
            var type = "post";
            var vm = this;
            commonUtils.ajax(data, url, type, function (data) {
                if (data.resultCode != '0') {
                    console.log("fail");
                    alert("删除失败!请重试");
                    return;
                }
                vm.getCountryInfos();
                //checkbox初始化
                for (i = 0; i < vm.PAGE_SIZE; i++) {
                    vm.checkboxStatus[i] = false;
                }
            }, function (error) {
                console.log(error);
                alert("删除失败!请重试");
            });
        },
        openEditDialog: function () {
            //处理操作，最多只能选择一个进行编辑
            var count = 0;
            var index;
            for (i = 0; i < this.countryInfos.length; i++) {
                if (this.checkboxStatus[i]) {
                    count++;
                    index = i;
                }
            }
            if (count != 1) {
                alert("要选择一个才能进行编辑~~");
                return;
            }
            if(count > 1){
                alert("只能选择一个选项~~");
                return;
            }
            //要编辑的数据获取
            this.edit_countryId = this.countryInfos[index].countryId;
            this.edit_countryAbbreviation = this.countryInfos[index].countryAbbreviation;
            this.edit_countryName = this.countryInfos[index].countryName;
            this.edit_englishName = this.countryInfos[index].englishName;
            this.edit_telCode = this.countryInfos[index].telCode;
            this.edit_timeZone = this.countryInfos[index].timeZone;
            this.edit_suggestPrice = this.countryInfos[index].suggestPrice;

            this.edit_errorMessage = "";
            this.edit_errorMessageShow = false;
            //打开编辑窗口
            $("#modifyModalLabel").modal('toggle');
        },
        updateChannel: function () {
            //参数校验
            if (this.edit_countryId == "" || this.edit_countryAbbreviation == "") {
                this.edit_errorMessage = "必填字段不能为空";
                this.edit_errorMessageShow = true;
                return;
            }
            if(this.edit_timeZone == ""){
                this.edit_errorMessage = "必填字段不能为空";
                this.edit_errorMessageShow = true;
                return;
            }
            if(this.edit_suggestPrice == ""){
                this.edit_errorMessage = "请输入合法的价格";
                this.edit_errorMessageShow = true;
                return;
            }
            //调用接口更新
            var vm = this;
            var url = "/gsmp/country/mod";
            var type = "post";
            var data = {
                "countryId": this.edit_countryId,
                "countryAbbreviation": this.edit_countryAbbreviation,
                "countryName": this.edit_countryName,
                "englishName": this.edit_englishName,
                "telCode": this.edit_telCode,
                "timeZone" : this.edit_timeZone,
                "suggestPrice": this.edit_suggestPrice
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
                for (i = 0; i < vm.PAGE_SIZE; i++) {
                    vm.checkboxStatus[i] = false;
                }
                //刷新列表
                vm.getCountryInfos();
            }, function (error) {
                console.log(error);
                vm.edit_errorMessage = "更新失败！";
                vm.edit_errorMessageShow = true;
            });
        }
    }
});