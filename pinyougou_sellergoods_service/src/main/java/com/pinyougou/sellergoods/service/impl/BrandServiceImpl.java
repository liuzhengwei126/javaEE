package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.entity.PageResult;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbBrandExample;
import com.pinyougou.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author ：请叫我伟哥.
 * @Date ：Created in 21:22 2018/8/11 0011
 * @Description：品牌处理的逻辑层实现类
 * @Modified By：
 * @Version: $version$
 */
@Service
public class BrandServiceImpl  implements BrandService{

    @Autowired
    private TbBrandMapper tbBrandMapper;

    @Override
    public PageResult findPage(TbBrand brand,int pageNum,int pageSize) {
        PageHelper.startPage (pageNum,pageSize);
        TbBrandExample example = new TbBrandExample ();
        TbBrandExample.Criteria criteria = example.createCriteria ();
        if (null !=brand){
            if (StringUtils.isNotBlank (brand.getName ())){
                criteria.andNameLike ("%"+brand.getName ()+"%");
            }
            if (StringUtils.isNotBlank (brand.getFirstChar ())){
                 criteria.andFirstCharEqualTo (brand.getFirstChar ());
            }
        }
        Page<TbBrand> page = (Page <TbBrand>) tbBrandMapper.selectByExample (example);
        return new PageResult (page.getTotal (),page.getResult ()) ;
    }

    @Override
    public void add(TbBrand brand) {
        tbBrandMapper.insert (brand);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            tbBrandMapper.deleteByPrimaryKey (id);
        }
    }

    @Override
    public TbBrand findById(Long id) {
        return tbBrandMapper.selectByPrimaryKey (id);
    }

    @Override
    public void update(TbBrand brand) {
        tbBrandMapper.updateByPrimaryKey (brand);
    }
}
