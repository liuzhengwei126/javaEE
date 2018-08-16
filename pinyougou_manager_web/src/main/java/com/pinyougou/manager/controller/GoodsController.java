package com.pinyougou.manager.controller;
import java.util.List;

import com.pinyougou.entity.PageResult;
import com.pinyougou.entity.ResultModel;
import com.pinyougou.pojogroup.Goods;
import com.pinyougou.service.GoodsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbGoods;

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
	public ResultModel update(@RequestBody TbGoods goods){
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
	public TbGoods findOne(Long id){
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
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbGoods goods, int page, int rows  ){
		return goodsService.findPage(goods, page, rows);		
	}
	
}
