package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.entity.PageResult;
import com.pinyougou.entity.ResultModel;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojogroup.Goods;
import com.pinyougou.service.GoodsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

	@Reference
	private GoodsService goodsService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoods> findAll(){			
		return goodsService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(int page, int rows){
		return goodsService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param goods
	 * @return
	 */
	@RequestMapping("/add")
	public ResultModel add(@RequestBody Goods goods){
	    //因为表中要存商家名称
        String name = SecurityContextHolder.getContext ().getAuthentication ().getName ();
        //设置商家id
        goods.getGoods ().setSellerId (name);
        try {
			goodsService.add(goods);
			return new ResultModel(true, "增加成功",null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultModel(false, "增加失败",null);
		}
	}
	
	/**
	 * 修改
	 * @param goods
	 * @return
	 */
	@RequestMapping("/update")
	public ResultModel update(@RequestBody Goods goods){
        //判断该商品是否属于登录的商家
        String sellerId = SecurityContextHolder.getContext ().getAuthentication ().getName ();

        Goods goods1 = goodsService.findOne (goods.getGoods ().getId ());

        if (!goods1.getGoods ().getSellerId ().equals (sellerId) || !goods.getGoods ().getSellerId ().equals (sellerId )){
            return new ResultModel(false, "非法操作",null);
        }

        try {
			goodsService.update(goods);
			return new ResultModel(true, "修改成功",null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultModel(false, "修改失败",null);
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public Goods findOne(Long id){
		return goodsService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public ResultModel delete(Long [] ids){
		try {
			goodsService.delete(ids);
			return new ResultModel(true, "删除成功",null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultModel(false, "删除失败",null);
		}
	}
	
		/**
	 * 查询+分页
	 * @param goods
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbGoods goods, int page, int rows  ){
        String name = SecurityContextHolder.getContext ().getAuthentication ().getName ();
        goods.setSellerId (name);
        PageResult page1 = goodsService.findPage (goods, page, rows);
        return page1;
    }

    /**
     * 上架下架
     * @param ids
     * @param status
     * @return
     */
	@RequestMapping("/updateIsMarketable")
	public  ResultModel updateIsMarketable(Long[] ids,String status) {

	    //有点不严谨 要判断上架的是否是属于该商家的商品
        String sellerId = SecurityContextHolder.getContext ().getAuthentication ().getName ();
        for (Long id : ids) {
            Goods goods = goodsService.findOne (id);
            String sellerId1 = goods.getGoods ().getSellerId ();
            if (!sellerId.equals (sellerId1)){
                return new ResultModel (false,"非法操作",null);
            }
        }

        try {
            goodsService.updateIsMarketable (ids, status);
            return new ResultModel (true,"操作成功",null);
        } catch (Exception e) {
            e.printStackTrace ();
            return  new ResultModel (false,"操作失败",null);
        }

    }
	
}
