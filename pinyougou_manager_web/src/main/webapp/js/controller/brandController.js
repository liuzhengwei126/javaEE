//品牌控制层
app.controller('brandController',function($scope,brandService) {
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
    //自定义搜索对象为空 防止穿过去null 造成空指针异常
    $scope.searchEntity ={};
    //条件查询
    $scope.search = function (page, rows) {
        brandService.search(page, rows,$scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//总记录数
            }
        );
    }
    //增加品牌或者修改品牌信息
    $scope.save=function(){
        var object = null;
        if($scope.entity.id!=null){ //如果他的id不为null 说明是修改更新
            object=brandService.update($scope.entity);
        }else {
            object=brandService.add($scope.entity);
        }
        object.success(
            function (response) {
                if (response.success){//如果成功
                    alert(response.message);
                    $scope.reloadList();//重新加载
                    //清空表格
                    $scope.entity = "";
                }else{
                    alert(response.message);
                }
            }
        )
    }

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
    //批量删除
    $scope.dele=function () {
        brandService.dele($scope.selectIds).success(
            function(response){
                if(response.success){
                    $scope.reloadList();//刷新列表
                }
            });
    }

    //通过id获取到要修改的信息
    $scope.findOne=function (id) {
        brandService.findOne(id).success(
            function (response) {
                $scope.entity=response;
            }
        )
    }


});