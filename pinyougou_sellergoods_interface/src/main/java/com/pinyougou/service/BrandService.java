package com.pinyougou.service;

import com.pinyougou.entity.PageResult;
import com.pinyougou.pojo.TbBrand;

import java.util.List;

/**
 * @Author ：请叫我伟哥.
 * @Date ：Created in 21:21 2018/8/11 0011
 * @Description：${description}
 * @Modified By：
 * @Version: $version$
 */
public interface BrandService {
    /**
     * 查询所有品牌的方法
     * @return
     */
    public PageResult findPage(TbBrand brand,int pageNum,int pageSize);

    /**
     * 增加品牌的方法
     * @param brand
     */
    public void add(TbBrand brand);

    /**
     * 删除品牌的方法
     * @param id
     */
    public void delete(Long[] id);

    /**
     * 通过id查找到要修改的品牌信息
     * @param id
     * @return
     */
    public TbBrand findById(Long id);

    /**
     * 修改品牌信息
     * @param brand
     */
    public void update(TbBrand brand);
}
