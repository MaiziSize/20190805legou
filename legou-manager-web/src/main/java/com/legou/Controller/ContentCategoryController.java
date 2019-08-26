package com.legou.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.Utils.utils.LegouResult;
import com.legou.common.EasyUITreeNode;
import com.legou.content.Contentservice.ContentCategoryService;


@Controller
public class ContentCategoryController {
	
	@Autowired
	ContentCategoryService contentCategoryService;
	
	@RequestMapping("/content/category/list")
	@ResponseBody    //返回json类型
	public List<EasyUITreeNode> contentList(@RequestParam(value="id",defaultValue="0") long id) {
		List<EasyUITreeNode> conList = contentCategoryService.getContentList(id);
		return conList;		
	}
	
	@RequestMapping("/content/category/create")
	@ResponseBody
	public LegouResult createContentNode(Long parentId,String name) {
		
		LegouResult legouResult=contentCategoryService.addContentCategoryNode(parentId,name);
		
		return legouResult;
	}
}
