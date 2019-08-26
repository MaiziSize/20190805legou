package com.legou.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.Utils.utils.LegouResult;
import com.legou.content.Contentservice.ContentService;
import com.legou.pojo.TbContent;


@Controller
public class ContentController {
	
	@Autowired
	ContentService contentService;
	
	
	
	@RequestMapping("/content/save")
	@ResponseBody
	public LegouResult saveContent(TbContent tbContent){
		
		LegouResult legouResult = contentService.saveContent(tbContent);
		
		return legouResult;
		
	}

}
