var app = new Vue({
	el : '#app',
	data:{
		//用户名
		userName: "",
		//密码
		password : "",
		//错误信息
		errorMessage : "",
		//错误信息是否显示
		errorisShow : false
	},
	created(){

	},
	methods:{
		//登录
		login : function(){
			console.log("userName:"+this.userName+"||password:"+this.password)
			//参数校验
			if(this.userName =="" || this.password ==""){
				console.log("parameter error");
				this.errorMessage = "用户名和密码不能为空";
				this.errorisShow = true;
				return;
			}
			var vm = this;
			//登录
			commonMethods.login(this.userName , this.password , function(data){
				//请求成功
				if(data.resultCode =='0' && data.userName != null){
					//跳转到首页
					window.location.href= "index.html";
				}else if(data.resultCode =='901'){
					console.log(data.resultMsg);
					vm.errorMessage = "用户名和密码不匹配";
					vm.errorisShow = true;
				}else{
					console.log(data.resultMsg);
					vm.errorMessage = "登录失败";
					vm.errorisShow = true;
				}

			},function(error){
				//请求失败
				console.log("login fail:"+error);
				vm.errorMessage = "登录失败";
				vm.errorisShow = true;
			});
		}
	}
});