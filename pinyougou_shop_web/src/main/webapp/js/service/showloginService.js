app.service('showloginService',function ($http) {
    //条件查询 和 分页
    this.loginName = function () {
        return $http.get('../showlogin/name.do');
    }


});
