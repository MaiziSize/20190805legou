package com.legou.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.Service.ItemService;
import com.legou.pojo.TbItem;



@Controller
public class TestController {
	//需要调用service查询
	
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable long itemId) {		
		TbItem item = itemService.getItem(itemId);		
		return item;
		
	}
	
	
	@RequestMapping("/test")
	@ResponseBody
	public String test() {
				
		return "test";
	}
}