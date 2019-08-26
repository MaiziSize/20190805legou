package com.legou.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.legou.Service.ItemCatService;
import com.legou.common.EasyUITreeNode;
import com.legou.mapper.TbItemCatMapper;
import com.legou.pojo.TbItemCat;
import com.legou.pojo.TbItemCatExample;
import com.legou.pojo.TbItemCatExample.Criteria;
@Service
public class ItemCatServiceImpl implements ItemCatService{
	@Autowired
	TbItemCatMapper tbItemCatMapper;
	
	@Override
	public List<EasyUITreeNode> getItemCatList(long id) {
		//实例化TbItemCatExample
		TbItemCatExample example = new TbItemCatExample();
		//加载sql后面where条件语句
		Criteria criteria = example.createCriteria();
		//添加条件根据id进行查询
		criteria.andParentIdEqualTo(id);		
		//example.createCriteria().andParentIdEqualTo(id);
		//向数据库查询所有信息并返回TbItemCat的list集合对象
		List<TbItemCat> tbItemCat = tbItemCatMapper.selectByExample(example);
		//创建一个EasyUITreeNode的list集合对象
		List<EasyUITreeNode> listNodes = new ArrayList<>();
		//遍历List<TbItemCat>中的值返回TbItemCat对象并设值
		for (TbItemCat itemCat : tbItemCat) {
			//创建EasyUITreeNode的实例对象
			EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
			//对id、text、state进行设置
			easyUITreeNode.setId(itemCat.getId());
			easyUITreeNode.setText(itemCat.getName());
			easyUITreeNode.setState(itemCat.getIsParent()?"closed":"open");
			//把EasyUITreeNode对象中的值放入List<EasyUITreeNode>集合中并返回其对象
			listNodes.add(easyUITreeNode);
		}			
		return listNodes;
	}
}
