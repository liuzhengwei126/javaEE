package com.pinyougou.protal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.content.service.ContentService;
import com.pinyougou.pojo.TbContent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author ：请叫我伟哥.
 * @Date ：Created in 20:43 2018/8/17 0017
 * @Description：${description}
 * @Modified By：
 * @Version: $version$
 */
@RestController
@RequestMapping("/content")
public class ContentController {

    @Reference
    private ContentService contentService;

    @RequestMapping("/findByCategory")
    public List<TbContent> findByCategory(Long categoryId){
        return    contentService.findByCatrgory (categoryId);
    }
}
