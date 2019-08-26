package com.legou.Controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.Service.ItemCatService;
import com.legou.common.EasyUITreeNode;

@Controller
public class ItemCatController {
	
	@Autowired
	ItemCatService itemCatService;
			
	@RequestMapping("/item/cat/list")
	@ResponseBody
	//@RequestParam注解将请求参数绑定到你控制器的方法参数上 (value="id",defaultValue="0")指定id参数的默认值为0
	public List<EasyUITreeNode> getItemCat(@RequestParam(value="id",defaultValue="0") long id){
		//调用service接口的方法斌并返回list集合
		List<EasyUITreeNode> itemcatList= itemCatService.getItemCatList(id);
		return itemcatList;	
	}	
}
