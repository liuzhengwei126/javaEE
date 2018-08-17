 //控制层 
app.controller('goodsController' ,function($scope,$location,$controller,typeTemplateService,goodsService,uploadService,itemCatService){
	
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
	$scope.findOne=function(){
	    var id = $location.search()['id'];
	    if (id==null){
	        return;
        }
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;
				editor.html($scope.entity.goodsDesc.introduction);//富文本显示
               //都要把字符串转换成对象才能够显示
                $scope.entity.goodsDesc.itemImages = JSON.parse($scope.entity.goodsDesc.itemImages);//图片显示 要转换类型
                //customAttributeItems拓展属性
                $scope.entity.goodsDesc.customAttributeItems = JSON.parse($scope.entity.goodsDesc.customAttributeItems);
                //规格属性展示
                $scope.entity.goodsDesc.specificationItems = JSON.parse($scope.entity.goodsDesc.specificationItems);
                //显示SKU的规格对象

                for(var i=0;i<$scope.entity.itemList.length;i++){
                    $scope.entity.itemList[i].spec = JSON.parse( $scope.entity.itemList[i].spec);
                }
			}
		);				
	}
	
	//保存 
	$scope.save=function(){
        $scope.entity.goodsDesc.introduction=editor.html();
		var serviceObject;//服务层对象

		if($scope.entity.goods.id!=null){//如果有ID
			serviceObject=goodsService.update( $scope.entity ); //修改
		}else{
			serviceObject=goodsService.add( $scope.entity  );//增加
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					/*//重新查询
                    alert(response.message);
                    $scope.entity={};
                    //清空富文本编辑器
                    editor.html('');*/
					location.href='goods.html';
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
                if($location.search()['id']==null){
                $scope.goodsDesc.customAttributeItems =JSON.parse(response.typeTemplate.customAttributeItems);
                }

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


    //这里有点懵逼啊 需要在看看
    $scope.updateSpecAttribute = function ($event, name, value) {

        //console.log($scope.entity.goodsDesc.specificationItems);

        var object = $scope.searchObjectByKey($scope.entity.goodsDesc.specificationItems,'attributeName',name);

        if(object !=null){
            if ($event.target.checked){
                object.attributeValue.push(value);
            }else {//取消勾选
                object.attributeValue.splice(object.attributeValue.indexOf(value),1);
                //如果选项都取消了 则移除该记录
                if (object.attributeValue.length==0){
                    $scope.entity.goodsDesc.specificationItems.splice($scope.entity.goodsDesc.specificationItems.indexOf(object),1);
                }
            }
        }else {
            $scope.entity.goodsDesc.specificationItems.push({"attributeName":name,"attributeValue":[value]});
        }
    }


    //创建suk列表
    $scope.createItemList = function () {
        console.log( $scope.entity.itemList)
        // 初始化列表
        $scope.entity.itemList = [{spec:{},price:0,num:9999,status:'0',isDefault:'0'}];
        //将规格信息赋值给item 便于循环
        var item = $scope.entity.goodsDesc.specificationItems;
        //遍历item 拿到attributeName 和 attributeValue
        for(var i=0;i<item.length;i++){
            $scope.entity.itemList =  addColumn($scope.entity.itemList,item[i].attributeName,item[i].attributeValue);

        }
    }

    //创建一个增加列的函数
    addColumn = function (list, columnName, columnValues) {
        //返回新集合
        var newList = [];
            //根据list集合循环嵌套
        for(var i = 0;i < list.length;i++){
            var oldRow = list[i];
            for(var j=0;j<columnValues.length;j++){
                //深克隆  将上次遍历的结果转换成对象
                var newRow = JSON.parse(JSON.stringify(oldRow));
                newRow.spec[columnName] = columnValues[j];
                //将新对象放到新集合中
                newList.push(newRow);
            }
        }
        return newList;
    }

    //定义数组 用来显示商品状态
    $scope.status=['未审核','已审核','审核未通过','关闭'];//商品状态

    //查找出所有的类目名称 组成数组 然后用来显示
    $scope.itemCatList=[];//商品分类列表
    $scope.findItemCatList = function () {
        itemCatService.findAll().success(
            function (response) {
                for (var i=0;i<response.length;i++){
                    //根据id进行排列
                    $scope.itemCatList[response[i].id] = response[i].name;
                }
            });
    }

    //读取商品规格属性 修改的时候
    $scope.checkAttributeValue = function (specName,optionName) {
        var item = $scope.entity.goodsDesc.specificationItems;
        var object = $scope.searchObjectByKey(item,"attributeName",specName);
        if (object == null){
            return false;
        }else {
            if (object.attributeValue.indexOf(optionName)>=0){
                return true;
            }else {
                return false;
            }
        }
    }

    //商品状态数组
    $scope.status2 = ['下架','上架'];
    //上架下架
    $scope.updateIsMarketable = function (status) {

        goodsService.updateIsMarketable($scope.selectIds,status).success(
            function (response) {
                if (response){
                    alert(response.message);
                    $scope.reloadList();//刷新列表
                    //要清空选项
                    $scope.selectIds=[];
                }else {
                    alert(response.message);
                }
            }
        )
    }

});	
