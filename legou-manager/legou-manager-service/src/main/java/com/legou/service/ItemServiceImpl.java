package com.legou.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.legou.Service.ItemService;
import com.legou.Utils.utils.IDUtils;
import com.legou.Utils.utils.JsonUtils;
import com.legou.Utils.utils.LegouResult;
import com.legou.common.EasyUIDataGridResult;
import com.legou.mapper.TbItemDescMapper;
import com.legou.mapper.TbItemMapper;
import com.legou.pojo.TbContent;
import com.legou.pojo.TbItem;
import com.legou.pojo.TbItemDesc;
import com.legou.pojo.TbItemExample;
import com.legou.redis.JedisClient;

@Service
public class ItemServiceImpl implements ItemService{
	
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Autowired
	private  JmsTemplate jmsTemplate;
	
	//resource 默认按照名字注入
    @Resource
	private Destination activeMQTopic;
    @Autowired
    private JedisClient jedisClient;
    
	@Override
	public TbItem getItem(long itemId) {
		String TbItems = jedisClient.get(itemId+"detail");
		if(!StringUtils.isEmpty(TbItems)) {
			System.out.println("从缓存中获取");
			//返回缓存数据
			TbItem tbitem = JsonUtils.jsonToPojo(TbItems, TbItem.class);
			return tbitem;
		}
		System.out.println("这是从数据库中查找");
		//向数据库中查找并返回tbItem對象
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
		//创建redis的数据库名称以及Key和value值
		jedisClient.set(itemId+"detail",JsonUtils.objectToJson(tbItem));
		//緩存過期
		jedisClient.expire(itemId+"detail",3600);
		return tbItem;
	}

	@Override
	public EasyUIDataGridResult selectList(Integer page, Integer rows) {
		PageHelper.startPage(page,rows);
		TbItemExample example = new TbItemExample();
		List<TbItem> tbItem = tbItemMapper.selectByExample(example);
		PageInfo<TbItem> pageInfo =  new PageInfo<TbItem>(tbItem);
		EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
		easyUIDataGridResult.setRows(tbItem);
		easyUIDataGridResult.setTotal(pageInfo.getTotal());
		return easyUIDataGridResult;
	}

	@Override
	public LegouResult save(TbItem tbItem, String desc) {		
		long itemId = IDUtils.genItemId();
		tbItem.setId(itemId);
		tbItem.setStatus((byte)1);
		tbItem.setCreated(new Date());
		tbItem.setUpdated(new Date());
		tbItemMapper.insertSelective(tbItem);
		
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemId(itemId);
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(new Date());
		tbItemDesc.setUpdated(new Date());
		tbItemDescMapper.insertSelective(tbItemDesc);
		
		//每次添加一条商品就需要向mq里面发送一条消息		
		jmsTemplate.send(activeMQTopic, new MessageCreator() {			
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(itemId+"");
			}
		});
		return LegouResult.ok();
	}

	@Override
	public LegouResult tbItemUpdata(TbItem tbItem) {
//		tbItem.setId(tbItem.getId());
//		tbItem.setTitle(tbItem.getTitle());
//		tbItem.setSellPoint(tbItem.getSellPoint());
//		tbItem.setPrice(tbItem.getPrice());
//		tbItem.setNum(tbItem.getNum());
//		tbItem.setBarcode(tbItem.getBarcode());
//		tbItem.setImage(tbItem.getImage());
//		tbItem.setCid(tbItem.getCid());
//		tbItem.setCreated(new Date());
//		tbItem.setUpdated(new Date());
		tbItem.setStatus((byte)3);
		tbItemMapper.updateByPrimaryKeySelective(tbItem);
		return LegouResult.ok();
	}

	@Override
	public TbItemDesc getDescById(long itemid) {
		String TbItemDescs = jedisClient.get(itemid+"desc");
		if(!StringUtils.isEmpty(TbItemDescs)) {
			System.out.println("从缓存中获取");
			//返回缓存数据
			 TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(TbItemDescs, TbItemDesc.class);
			return tbItemDesc;
		}				
		System.out.println("这是从数据库中查找");
		//向数据库中查找并返回tbItem對象
		TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemid);
		//创建redis的数据库名称以及Key和value值
		jedisClient.set(itemid+"desc",JsonUtils.objectToJson(tbItemDesc));
		jedisClient.expire(itemid+"desc",3600);
		return tbItemDesc;
	}

}
