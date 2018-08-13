package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.entity.PageResult;
import com.pinyougou.entity.ResultModel;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author ：请叫我伟哥.
 * @Date ：Created in 21:26 2018/8/11 0011
 * @Description：管理品牌的controller
 * @Modified By：
 * @Version: $version$
 */
@Controller
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;

    @RequestMapping("/search")
    @ResponseBody
    public PageResult findAll(@RequestBody TbBrand brand,int page,int rows){
        return brandService.findPage (brand,page,rows);
    }

    @RequestMapping("/add")
    @ResponseBody
    public ResultModel save(@RequestBody TbBrand brand){
        try {
            brandService.add (brand);
            return new ResultModel (true,"增加成功",null);
        } catch (Exception e) {
            e.printStackTrace ();
            return new ResultModel (false,"增加失败",null);
        }
    }
    @RequestMapping("/delete")
    @ResponseBody
    public ResultModel delete(Long[] ids){
        try {
            brandService.delete (ids);
            return new ResultModel (true,"删除成功",null);
        } catch (Exception e) {
            e.printStackTrace ();
            return new ResultModel (true,"删除失败",null);
        }
    }
    @ResponseBody
    @RequestMapping("/findById")
    public TbBrand findById(Long id){
        return brandService.findById (id);
    }
    @RequestMapping("/update")
    @ResponseBody
    public ResultModel update(@RequestBody TbBrand brand){
        try {
            brandService.update (brand);
            return new ResultModel (true,"修改成功",null);
        } catch (Exception e) {
            e.printStackTrace ();
            return new ResultModel (true,"修改失败",null);
        }
    }

}
