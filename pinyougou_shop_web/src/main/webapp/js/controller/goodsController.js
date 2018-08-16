 //控制层 
app.controller('goodsController' ,function($scope,$controller,typeTemplateService,goodsService,uploadService,itemCatService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=goodsService.update( $scope.entity ); //修改  
		}else{
            $scope.entity.goodsDesc.introduction=editor.html();
            alert(editor.html());
			serviceObject=goodsService.add( $scope.entity  );//增加
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
                    alert(response.message);
                    $scope.entity={};
                    //清空富文本编辑器
                    editor.html('');
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}

	$scope.uploadFile = function () {
	    alert("controller")
        uploadService.uploadFile().success(
            function (response) {
                if (response){
                    //将url取出来
                    $scope.image_entity.url=response.data;
                }else{
                    alert(response.message);
                }
            }).error(function() {
            alert("上传发生错误");
        });
    }

    //定义实体结构
    $scope.entity = {goods:{},goodsDesc:{itemImages:[]}};
	
	//添加图片
    $scope.add_image_entity = function () {
        $scope.entity.goodsDesc.itemImages.push($scope.image_entity);
    }
    //移除图片
    $scope.romove_image_entity = function (index) {
        $scope.entity.goodsDesc.itemImages.splice(index,1);
    }

    //商品分类下拉框相关
    $scope.selectItemCat1List = function () {
        itemCatService.findByParentId(0).success(
            function (response) {
                $scope.itemCat1List =response;
            })
    }

    $scope.$watch('entity.goods.category1Id', function (newValue, oldValue) {
        itemCatService.findByParentId(newValue).success(
            function (response) {
                $scope.itemCat2List =response;
            })

    });
    $scope.$watch('entity.goods.category2Id', function (newValue, oldValue) {
        itemCatService.findByParentId(newValue).success(
            function (response) {
                $scope.itemCat3List =response;
            })

    });
    $scope.$watch('entity.goods.category3Id', function (newValue, oldValue) {
        itemCatService.findOne(newValue).success(
            function (response) {
                $scope.entity.goods.typeTemplateId =response.typeId;
            })
    });

    //根据模板对象更新规格等
    $scope.$watch('entity.goods.typeTemplateId', function (newValue, oldValue) {
        typeTemplateService.findOne(newValue).success(
            function (response) {

                $scope.typeTemplate =response;
                $scope.typeTemplate.brandIds = JSON.parse(response.brandIds);
                $scope.typeTemplate.customAttributeItems =JSON.parse(response.customAttributeItems);

            });

        typeTemplateService.findSpecList(newValue).success(
            function (response) {
                $scope.specList = response;
            }
        )

    });
    //[{“attributeName”:”规格名称”,”attributeValue”:[“规格选项1”,“规格选项2”.... ]  } , ....  ]
    //定义实体类结构
    $scope.entity={goodsDesc:{specificationItems:[],itemImages:[]}}

    $scope.updateSpecAttribute = function ($event, text, optionName) {

        var object = $scope.searchObjectByKey($scope.entity.goodsDesc.specificationItems,'attributeName',text);

    }

});	
