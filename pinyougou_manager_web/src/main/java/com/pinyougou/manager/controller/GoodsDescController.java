package com.pinyougou.manager.controller;
import java.util.List;

import com.pinyougou.entity.PageResult;
import com.pinyougou.entity.ResultModel;
import com.pinyougou.service.GoodsDescService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbGoodsDesc;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/goodsDesc")
public class GoodsDescController {

	@Reference
	private GoodsDescService goodsDescService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoodsDesc> findAll(){			
		return goodsDescService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(int page, int rows){
		return goodsDescService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param goodsDesc
	 * @return
	 */
	@RequestMapping("/add")
	public ResultModel add(@RequestBody TbGoodsDesc goodsDesc){
		try {
			goodsDescService.add(goodsDesc);
			return new ResultModel(true, "增加成功",null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultModel(false, "增加失败",null);
		}
	}
	
	/**
	 * 修改
	 * @param goodsDesc
	 * @return
	 */
	@RequestMapping("/update")
	public ResultModel update(@RequestBody TbGoodsDesc goodsDesc){
		try {
			goodsDescService.update(goodsDesc);
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
	public TbGoodsDesc findOne(Long id){
		return goodsDescService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public ResultModel delete(Long [] ids){
		try {
			goodsDescService.delete(ids);
			return new ResultModel(true, "删除成功",null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultModel(false, "删除失败",null);
		}
	}
	
		/**
	 * 查询+分页
	 * @param
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbGoodsDesc goodsDesc, int page, int rows  ){
		return goodsDescService.findPage(goodsDesc, page, rows);		
	}
	
}
