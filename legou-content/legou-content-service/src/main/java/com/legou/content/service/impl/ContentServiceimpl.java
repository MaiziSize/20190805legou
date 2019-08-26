package com.legou.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.legou.Utils.utils.JsonUtils;
import com.legou.Utils.utils.LegouResult;
import com.legou.content.Contentservice.ContentService;
import com.legou.mapper.TbContentMapper;
import com.legou.pojo.TbContent;
import com.legou.pojo.TbContentExample;
import com.legou.redis.JedisClientPool;

@Service
public class ContentServiceimpl implements ContentService{
	
	@Autowired
	TbContentMapper tbContentMapper;
	@Autowired
	JedisClientPool jedisClientPool;
	
	@Override
	public LegouResult saveContent(TbContent tbContent) {
		//向bean中添加数据
		tbContent.setCreated(new Date());
		tbContent.setUpdated(new Date());
		//向数据库添加数据
		tbContentMapper.insert(tbContent);
		//返回状态如果成功则回200
		jedisClientPool.hdel("legouImg", tbContent.getCategoryId().toString());
		return LegouResult.ok();
	}
	
	@Override
	public List<TbContent> getContentById(long catgoryid) {	
		//从缓存中去除值并返回String类型
		String clientPool = jedisClientPool.hget("legouImg", catgoryid+"");
		//判断返回的值是否为空如果不为空则从缓存中获取
		if(!StringUtils.isEmpty(clientPool)) {
			System.out.println("从缓存中获取");
			//返回缓存数据
			return JsonUtils.jsonToList(clientPool, TbContent.class);
		}		
		System.out.println("这是从数据库中查找");
		//创建TbContentExample的对象
		TbContentExample example = new TbContentExample();
		//向sql语句中添加id条件
		example.createCriteria().andCategoryIdEqualTo(catgoryid);
		//向数据库中查找并返回list集合
		List<TbContent> ContentList = tbContentMapper.selectByExample(example);
		//创建redis的数据库名称以及Key和value值
		jedisClientPool.hset("legouImg", catgoryid+"", JsonUtils.objectToJson(ContentList));		
		return ContentList;
	}

}
