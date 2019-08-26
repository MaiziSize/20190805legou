package com.legou.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.legou.common.SearchResult;
import com.legou.search.service.SolrSearchService;

@Controller
public class SearchController {
	
	@Autowired
	SolrSearchService solrSearchService;
	
	@RequestMapping("/search")
	public String search(String keyword,Model model) throws Exception {
		//tomcat默认全部都是用ISO-8859-1编码,不管你页面用什么显示,Tomcat最终还是会替你将所有字符转做ISO-8859-1
		//所以我们需要避免乱码,当然UTF-8可以换成GBK，unicode
		keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
		//调用service方法返回一个SearchResult,SearchResult中存有list集合
		SearchResult searchResult = solrSearchService.search(keyword);
		//利用Model将数据传到前端
		model.addAttribute("itemList", searchResult.getItemList());
		//返回页面
		return "search";
	}
	
}
