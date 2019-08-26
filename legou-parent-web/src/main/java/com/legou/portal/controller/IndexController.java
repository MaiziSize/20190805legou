package com.legou.portal.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.legou.content.Contentservice.ContentService;
import com.legou.pojo.TbContent;

@Controller
public class IndexController {
	
	@Autowired
	ContentService contentService;
	
	@RequestMapping("/index")
	public String index(Model model) {	
		
		List<TbContent> aD1List = contentService.getContentById((long)89);
		model.addAttribute("aD1List", aD1List);
		return "index";
	}

}
