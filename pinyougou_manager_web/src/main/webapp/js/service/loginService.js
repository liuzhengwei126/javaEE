app.service('loginService',function ($http) {
    //条件查询 和 分页
    this.loginName = function () {
        return $http.get('../login/name.do');
    }


});
