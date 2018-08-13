app.service('brandService',function ($http) {
    //条件查询 和 分页
    this.search = function (page,rows,searchEntity) {
        return $http.post('../brand/search.do?page=' + page + "&rows=" + rows,searchEntity);
    }
    //增加
    this.add =function (entity) {
        return $http.post('../brand/add.do',entity);
    }
    //修改
    this.update = function (entity) {
        return $http.post('../brand/update.do',entity);
    }
    //删除
    this.dele = function (selectIds){
        return $http.get('../brand/delete.do?ids='+selectIds);
    }
    //获取
    this.findOne = function (id) {
        return $http.get('../brand/findById.do?id='+id);
    }
    this.selectOptionList = function () {
        return $http.get('../brand/selectOptionList.do');
    }

});
