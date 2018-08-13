package com.pinyougou.manager.controller;
import java.util.List;

import com.pinyougou.entity.PageResult;
import com.pinyougou.entity.ResultModel;
import com.pinyougou.service.SellerService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSeller;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/seller")
public class SellerController {

	@Reference
	private SellerService sellerService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSeller> findAll(){			
		return sellerService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(int page, int rows){
		return sellerService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param seller
	 * @return
	 */
	@RequestMapping("/add")
	public ResultModel add(@RequestBody TbSeller seller){
		try {
			sellerService.add(seller);
			return new ResultModel(true, "增加成功",null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultModel(false, "增加失败",null);
		}
	}
	
	/**
	 * 修改
	 * @param seller
	 * @return
	 */
	@RequestMapping("/update")
	public ResultModel update(@RequestBody TbSeller seller){
		try {
			sellerService.update(seller);
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
	public TbSeller findOne(String id){
		return sellerService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public ResultModel delete(String [] ids){
		try {
			sellerService.delete(ids);
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
	public PageResult search(@RequestBody TbSeller seller, int page, int rows  ){
		return sellerService.findPage(seller, page, rows);		
	}
	
}
