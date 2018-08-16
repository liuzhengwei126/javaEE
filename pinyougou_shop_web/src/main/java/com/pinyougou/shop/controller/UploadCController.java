package com.pinyougou.shop.controller;

import com.pinyougou.entity.ResultModel;
import com.pinyougou.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.security.krb5.internal.PAData;

import java.io.File;
import java.util.UUID;

/**
 * @Author ：请叫我伟哥.
 * @Date ：Created in 15:29 2018/8/15 0015
 * @Description：${description}
 * @Modified By：
 * @Version: $version$
 */
@RestController
public class UploadCController {

    @Value ("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    @RequestMapping("/upload")
    public ResultModel upload(MultipartFile file){


        //获取文件原名
        String filename = file.getOriginalFilename ();
        //通过切割拼接产生新的文件名
        String extName  = filename.substring (filename.lastIndexOf (".") + 1);
        //创建上传文件的客户端
        try {
            //FastDFSClient client = new FastDFSClient ("classpath:config/fdfs_client.conf");
            FastDFSClient client
                    = new FastDFSClient("classpath:config/fdfs_client.conf");
            //执行上传任务
            String path = client.uploadFile (file.getBytes (), extName);
            //拼接返回的url和ip地址，拼装成完整的url
            String url = IMAGE_SERVER_URL + path;
            System.out.println (url);
            return new ResultModel (true,"上传成功",url);

        } catch (Exception e) {
            e.printStackTrace ();
            return new ResultModel (false, "上传失败",null);
        }

    }
}
