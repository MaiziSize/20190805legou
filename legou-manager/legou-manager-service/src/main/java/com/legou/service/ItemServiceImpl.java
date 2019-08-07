package com.legou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legou.Service.ItemService;
import com.legou.mapper.TbItemMapper;
import com.legou.pojo.TbItem;

@Service
public class ItemServiceImpl implements ItemService{
	
	@Autowired
	private TbItemMapper tbItemMapper;
	
	@Override
	public TbItem getItem(long itemId) {
		return tbItemMapper.selectByPrimaryKey(itemId);
	}

}
