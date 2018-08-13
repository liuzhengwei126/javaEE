//父类控制层
app.controller('baseController',function($scope) {
    //重新加载列表
    $scope.reloadList = function () {
        //切换页码
        $scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    }
    //设置页码等信息
    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 10,
        itemsPerPage: 10,
        perPageOptions: [10, 20, 30, 40, 50],
        onChange: function () {
            $scope.reloadList();//重新加载
        }
    };
    //自定义集合 存放要删除的id
    $scope.selectIds=[];
    //复选框更新
    $scope.updateSelection = function ($event,id) {
        if ($event.target.checked){//如果被选中
            $scope.selectIds.push(id)
        }else {
            var idx = $scope.selectIds.indexOf(id);
            $scope.selectIds.splice(idx,1);//删除该id
        }
    }
});