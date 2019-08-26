package com.legou.Controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.Service.ItemService;
import com.legou.Utils.utils.LegouResult;
import com.legou.common.EasyUIDataGridResult;
import com.legou.pojo.TbItem;



@Controller
public class ItemController {
	//需要调用service查询
	
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable long itemId) {		
		TbItem item = itemService.getItem(itemId);		
		return item;
		
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult list(int page ,int rows) {		
		return itemService.selectList(page,rows);		
	}
	
	
	@RequestMapping("/item/save")
	@ResponseBody
	//返回json类LegouResult
	public LegouResult itemSave(TbItem tbItem,String desc) {
		//通过itemService来进行增加商品功能
		LegouResult legouResult = itemService.save(tbItem,desc);
		return legouResult;		
	}
	@RequestMapping("/rest/item/delete")
	@ResponseBody
	public LegouResult delete(TbItem tbItem) {
		LegouResult legouResult = itemService.tbItemUpdata(tbItem);
		return legouResult;
	}
	
	@RequestMapping("/test")
	@ResponseBody
	public String test() {
				
		return "test";
	}
}