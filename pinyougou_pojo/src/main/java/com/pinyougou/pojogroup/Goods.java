package com.pinyougou.pojogroup;

import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbItem;

import java.io.Serializable;
import java.util.List;

/**
 * @Author ：请叫我伟哥.
 * @Date ：Created in 11:00 2018/8/15 0015
 * @Description：${description}
 * @Modified By：
 * @Version: $version$
 */
public class Goods implements Serializable{

    private TbGoods goods;

    private TbGoodsDesc goodsDesc;

    private List<TbItem> itemList;

    public TbGoods getGoods() {
        return goods;
    }

    public void setGoods(TbGoods goods) {
        this.goods = goods;
    }

    public TbGoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(TbGoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public List <TbItem> getItemList() {
        return itemList;
    }

    public void setItemList(List <TbItem> itemList) {
        this.itemList = itemList;
    }
}
