package com.pinyougou.sellergoods.service.impl;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pinyougou.entity.PageResult;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;
import com.pinyougou.pojogroup.Goods;
import com.pinyougou.service.GoodsService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.pojo.TbGoodsExample.Criteria;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private TbGoodsMapper goodsMapper;
	@Autowired
    private TbGoodsDescMapper goodsDescMapper;
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbBrandMapper brandMapper;
    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Autowired
    private TbSellerMapper sellerMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbGoods> findAll() {
		return goodsMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbGoods> page=   (Page<TbGoods>) goodsMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Goods goods) {
        //设置为未审核
	    goods.getGoods ().setAuditStatus ("0");
        goodsMapper.insert (goods.getGoods ());
        //int x=1/0;
        //设置goodsDesc中的商品Id
        goods.getGoodsDesc ().setGoodsId (goods.getGoods ().getId ());
        //插入商品扩展数据
        goodsDescMapper.insert (goods.getGoodsDesc ());
        //抽取了方法
        SaveItemList (goods);
    }

    private void setItemValue(Goods goods,TbItem ti){
        ti.setGoodsId (goods.getGoods ().getId ());//商品spu编号
        ti.setSellerId (goods.getGoods ().getSellerId ());//商品所属商家
        ti.setCategoryid (goods.getGoods ().getCategory3Id ());//商品所属目录
        ti.setCreateTime (new Date ());//创建日期
        ti.setUpdateTime (new Date ());//修改日期
        //品牌名称
        TbBrand brand = brandMapper.selectByPrimaryKey (goods.getGoods ().getBrandId ());
        ti.setBrand (brand.getName ());

        //分类名称
        TbItemCat item = itemCatMapper.selectByPrimaryKey (goods.getGoods ().getCategory3Id ());
        ti.setCategory (item.getName ());
        //商家名称
        TbSeller seller = sellerMapper.selectByPrimaryKey (goods.getGoods ().getSellerId ());
        ti.setSeller (seller.getNickName ());

        //图片地址（取spu的第一个图片）
        List <Map> list = JSON.parseArray (goods.getGoodsDesc ().getItemImages (), Map.class);
        if (list.size ()>0){
            ti.setImage ((String) list.get (0).get ("url"));
        }


    }

	
	/**
	 * 修改
	 */
	@Override
	public void update(Goods goods){
	    //更新基本数据
		goodsMapper.updateByPrimaryKey(goods.getGoods ());
		//更新拓展数据
		goodsDescMapper.updateByPrimaryKey (goods.getGoodsDesc ());
		//先删除SKU的原数据 再添加
        TbItemExample example = new TbItemExample ();
        TbItemExample.Criteria criteria = example.createCriteria ();
        criteria.andGoodsIdEqualTo (goods.getGoods ().getId ());
        itemMapper.deleteByExample (example);
        //插入新数据
        SaveItemList (goods);

	}	
    //插入SKU商品数据
	private void SaveItemList(Goods goods){

        String title = goods.getGoods ().getGoodsName ();

        if ("1".equals (goods.getGoods ().getIsEnableSpec ())){

            List <TbItem> itemList = goods.getItemList ();
            for (TbItem ti : itemList) {
                //title 标题
                Map <String, Object> specMap = JSON.parseObject (ti.getSpec ());
                for (String key : specMap.keySet ()) {
                    title += " " + specMap.get (key);
                }
                ti.setTitle (title);
                setItemValue (goods,ti);
                itemMapper.insert (ti);
            }
        }else {
            TbItem item = new TbItem ();
            item.setTitle (goods.getGoods ().getGoodsName ());
            item.setNum (9999);
            item.setPrice (goods.getGoods ().getPrice ());
            item.setStatus ("1");//状态
            item.setIsDefault ("1");//是否默认
            item.setSpec ("{}");
            setItemValue (goods,item);
            itemMapper.insert(item);

        }
    }

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Goods findOne(Long id){
        Goods goods = new Goods ();
        TbGoods tbGoods = goodsMapper.selectByPrimaryKey (id);
        goods.setGoods (tbGoods);
        TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByPrimaryKey (id);
        goods.setGoodsDesc (tbGoodsDesc);

        TbItemExample example = new TbItemExample ();
        TbItemExample.Criteria criteria = example.createCriteria ();
        criteria.andGoodsIdEqualTo (id);
        List <TbItem> list = itemMapper.selectByExample (example);
        goods.setItemList (list);

        return goods;
	}

	/**
	 * 批量删除 逻辑删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
            TbGoods tbGoods = goodsMapper.selectByPrimaryKey (id);
            tbGoods.setIsDelete ("1");
            goodsMapper.updateByPrimaryKey (tbGoods);
        }
	}
	
	
    @Override
	public PageResult findPage(TbGoods goods, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbGoodsExample example=new TbGoodsExample();
		Criteria criteria = example.createCriteria();
                 criteria.andIsDeleteIsNull();//非删除状态
		
		if(goods!=null){			
            if(goods.getSellerId()!=null && goods.getSellerId().length()>0){
                criteria.andSellerIdEqualTo(goods.getSellerId());
			}
			if(goods.getGoodsName()!=null && goods.getGoodsName().length()>0){
				criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
			}
			if(goods.getAuditStatus()!=null && goods.getAuditStatus().length()>0){
				criteria.andAuditStatusEqualTo (goods.getAuditStatus ());
			}
			if(goods.getIsMarketable()!=null && goods.getIsMarketable().length()>0){
				criteria.andIsMarketableLike("%"+goods.getIsMarketable()+"%");
			}
			if(goods.getCaption()!=null && goods.getCaption().length()>0){
				criteria.andCaptionLike("%"+goods.getCaption()+"%");
			}
			if(goods.getSmallPic()!=null && goods.getSmallPic().length()>0){
				criteria.andSmallPicLike("%"+goods.getSmallPic()+"%");
			}
			if(goods.getIsEnableSpec()!=null && goods.getIsEnableSpec().length()>0){
				criteria.andIsEnableSpecLike("%"+goods.getIsEnableSpec()+"%");
			}
			if(goods.getIsDelete()!=null && goods.getIsDelete().length()>0){
				criteria.andIsDeleteLike("%"+goods.getIsDelete()+"%");
			}
	
		}
		
		Page<TbGoods> page= (Page<TbGoods>)goodsMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}
    @Override
    public void updateStatus(Long[] ids, String status) {
        for (Long id : ids) {
            TbGoods tbGoods = goodsMapper.selectByPrimaryKey (id);
            tbGoods.setAuditStatus (status);
            goodsMapper.updateByPrimaryKey (tbGoods);
        }
    }

    @Override
    public void updateIsMarketable(Long[] ids, String status) {
        for (Long id : ids) {
            TbGoods tbGoods = goodsMapper.selectByPrimaryKey (id);
            tbGoods.setIsMarketable (status);
            goodsMapper.updateByPrimaryKey (tbGoods);
        }
    }
}
