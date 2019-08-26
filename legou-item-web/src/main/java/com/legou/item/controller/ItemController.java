package com.legou.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.legou.Service.ItemService;
import com.legou.item.pojo.ItemDetail;
import com.legou.pojo.TbItem;
import com.legou.pojo.TbItemDesc;

@Controller
public class ItemController {
	
	@Autowired
	ItemService itemService;
	
	@RequestMapping("/item/{itemid}")
	public String itemWeb(@PathVariable Long itemid,Model model) {
		TbItem	tbItem	= itemService.getItem(itemid);
		ItemDetail itemDetail = new ItemDetail(tbItem);
		TbItemDesc itemDesc = itemService.getDescById(itemid);
		model.addAttribute("item", itemDetail);
		model.addAttribute("itemDesc", itemDesc);
		return "item";		
	}
}



