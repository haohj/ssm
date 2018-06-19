package com.hao.service.impl;

import com.hao.mapper.ItemsMapper;
import com.hao.mapper.ItemsMapperCustom;
import com.hao.po.Items;
import com.hao.po.ItemsCustom;
import com.hao.po.ItemsQueryVo;
import com.hao.service.ItemsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ItemsServiceImpl implements ItemsService {
    @Autowired
    private ItemsMapperCustom itemsMapperCustom;
    @Autowired
    private ItemsMapper itemsMapper;

    @Override
    public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo) throws Exception {
        return itemsMapperCustom.findItemsList(itemsQueryVo);
    }

    @Override
    public ItemsCustom findItemsById(Integer id) throws Exception {
        ItemsCustom itemsCustom = null;
        Items items = itemsMapper.selectByPrimaryKey(id);
        if (items != null) {
            itemsCustom = new ItemsCustom();
            BeanUtils.copyProperties(items, itemsCustom);
        }
        return itemsCustom;
    }

    @Override
    public void updateItems(Integer id, ItemsCustom itemsCustom) throws Exception {
        if (id != null) {
            itemsCustom.setId(id);
            itemsMapper.updateByPrimaryKeyWithBLOBs(itemsCustom);
        }
    }
}
