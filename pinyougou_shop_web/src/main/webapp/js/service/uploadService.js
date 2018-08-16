app.service('uploadService',function($http){

    this.uploadFile = function () {
        var formData=new FormData();
        formData.append("file",file.files[0]);
  /*    return $http({
          method:'post',
          url:'../upload.do',
          data:formData,
          header:{'Content-Type':undefined},
          transformRequest: angular.identity
      });*/
        return $http({
            method:'POST',
            url:"../upload.do",
            data: formData,
            headers: {'Content-Type':undefined},
            transformRequest: angular.identity
        });
    }
});
/*
‘Content-Type’: undefined，这样浏览器会帮我们把Content-Type 设置为 multipart/form-data.*/
/*

formData.append("file",file.files[0]);
//第一个参数：file和controller中的参数一致
// 第二个参数：file 和input 中的id一致 这里支持多图片，现在只要一个所以就选一张即可.*/

/*
通过设置transformRequest: angular.identity，anjularjstransformRequest function 将序列化我们的formdata object.*/
