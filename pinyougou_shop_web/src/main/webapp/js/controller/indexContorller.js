app.controller('indexController',function ($scope,$controller,showloginService) {

    $scope.showLoginName = function () {
        showloginService.loginName().success(
            function (response) {
                $scope.loginName=response.loginName;
            }
        )
    }
});