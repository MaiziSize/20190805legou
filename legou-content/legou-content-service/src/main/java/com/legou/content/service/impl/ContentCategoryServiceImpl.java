package com.legou.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legou.Utils.utils.LegouResult;
import com.legou.common.EasyUITreeNode;
import com.legou.content.Contentservice.ContentCategoryService;
import com.legou.mapper.TbContentCategoryMapper;
import com.legou.pojo.TbContentCategory;
import com.legou.pojo.TbContentCategoryExample;
import com.legou.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService{

	@Autowired
	TbContentCategoryMapper tbContentCategoryMapper;

	@Override
	public List<EasyUITreeNode> getContentList(long id) {
		//实例化TbContentCategoryExample
		TbContentCategoryExample example= new TbContentCategoryExample();	
		//加载sql后面where条件语句
		Criteria createCriteria = example.createCriteria();	
		//添加条件根据id进行查询
		createCriteria.andParentIdEqualTo(id);
		//example.createCriteria().andParentIdEqualTo(id);
		//向数据库查询所有信息并返回TbContentCategory的list集合对象
		List<TbContentCategory>  categoryList= tbContentCategoryMapper.selectByExample(example);	
		//创建一个EasyUITreeNode的list集合对象
		List<EasyUITreeNode> nodelisTreeNodes= new ArrayList<EasyUITreeNode>();	
		//遍历List<TbItemCat>中的值返回TbContentCategory对象并设值
		for(TbContentCategory category :categoryList) {	
			//创建EasyUITreeNode的实例对象
			EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
			//对id、text、state进行设置
			easyUITreeNode.setId(category.getId());
			easyUITreeNode.setText(category.getName());
			easyUITreeNode.setState(category.getIsParent()?"closed":"open");
			//把EasyUITreeNode对象中的值放入List<EasyUITreeNode>集合中并返回其对象
			nodelisTreeNodes.add(easyUITreeNode);			
		}		
		return nodelisTreeNodes;
	}

	@Override
	public LegouResult addContentCategoryNode(Long parentId, String name) {
		//创建TbContentCategory类
		TbContentCategory tbContentCategory = new TbContentCategory();
		//往bean中设置内容
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setName(name);
		tbContentCategory.setStatus(1);
		tbContentCategory.setSortOrder(1);
		tbContentCategory.setIsParent(false);
		//设置时间
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setUpdated(new Date());
		//向mapper.xml中运行sql语句
		tbContentCategoryMapper.insert(tbContentCategory);
		//通过id查找值并返回TbContentCategory
		TbContentCategory parentNodeCategory= tbContentCategoryMapper.selectByPrimaryKey(parentId);
		//判断如果是在子文件夹下添加文件则将子文件改变成父文件
		if(!parentNodeCategory.getIsParent()) {
			parentNodeCategory.setIsParent(true);
			tbContentCategoryMapper.updateByPrimaryKey(parentNodeCategory);
		}	
		//返回数据源
		return LegouResult.ok(tbContentCategory);
	}

}
