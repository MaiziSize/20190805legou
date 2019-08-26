package com.legou.Service;

import com.legou.Utils.utils.LegouResult;
import com.legou.common.EasyUIDataGridResult;
import com.legou.pojo.TbItem;
import com.legou.pojo.TbItemDesc;

public interface ItemService {

	TbItem getItem(long itemId);

	EasyUIDataGridResult selectList(Integer page, Integer rows);

	LegouResult save(TbItem tbItem, String desc);

	LegouResult tbItemUpdata(TbItem tbItem);

	TbItemDesc getDescById(long itemid);


}
