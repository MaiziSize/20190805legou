package com.legou.pagehelper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.legou.mapper.TbItemMapper;
import com.legou.pojo.TbItem;
import com.legou.pojo.TbItemExample;

public class PageHelperTest {
public static void main(String[] args) {
	 	// 创建一个spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		 // 从spring容器中获取mapper代理对象
		TbItemMapper tbItemMapper = applicationContext.getBean(TbItemMapper.class);
		//分页处理，显示第一页的10条数据 ，必须放在执行查询语句的上面
		PageHelper.startPage(1, 10);
		// 执行查询语句
		TbItemExample example = new TbItemExample();
		List<TbItem> list = tbItemMapper.selectByExample(example);

		//取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
		//获取pageInfo中的所有属性
		List<TbItem> list2 = pageInfo.getList();
		//获取pageInfo中的Total属性
		long total = pageInfo.getTotal();
		System.out.println("total:"+total);
		//获取第一页的前10条信息
		for (TbItem tbItem : list) {
			System.out.println(tbItem.getTitle());			
		}

		
		
		
	
		
		
		
		
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
//		
//		TbItemMapper tbItemMapper = applicationContext.getBean(TbItemMapper.class);
//		
//		PageHelper.startPage(1, 10);
//		
//		TbItemExample example = new TbItemExample();
//		List<TbItem> list = tbItemMapper.selectByExample(example);
//		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
//		
//		List<TbItem> list2 = pageInfo.getList();
//		int nextPage = pageInfo.getNextPage();
//		System.out.println("nextPage:"+nextPage);
//		int pageNum = pageInfo.getPageNum();
//		System.out.println("pageNum:"+pageNum);
//		int endRow = pageInfo.getEndRow();
//		System.out.println("endRow:"+endRow);
//		int pages = pageInfo.getPages();
//		System.out.println("pages:"+pages);
//		long total = pageInfo.getTotal();
//		System.out.println("total:"+total);
//		
//		for (TbItem tbItem : list) {
//			System.out.println(tbItem);
//			
//		}							
	}
}
