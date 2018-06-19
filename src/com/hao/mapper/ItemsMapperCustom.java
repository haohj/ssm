package com.hao.mapper;



import java.util.List;

import com.hao.po.ItemsCustom;
import com.hao.po.ItemsQueryVo;
import org.apache.ibatis.annotations.Param;

public interface ItemsMapperCustom {
    //商品查询列表
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo)throws Exception;
}