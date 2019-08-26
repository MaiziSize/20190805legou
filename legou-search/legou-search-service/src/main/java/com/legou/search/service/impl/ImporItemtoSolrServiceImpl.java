package com.legou.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legou.Utils.utils.LegouResult;
import com.legou.common.SearchItem;
import com.legou.search.mapper.ItemMapper;
import com.legou.search.service.ImporItemtoSolrService;


@Service
public class ImporItemtoSolrServiceImpl implements ImporItemtoSolrService {

	@Autowired
	ItemMapper itemMapper;

	@Autowired
	SolrServer solrServer;

	@Override
	public LegouResult importAllItemToSolr() {

		try {
			//从数据库中查找数据然后返回SearchItem的list集合
			List<SearchItem> itemList = itemMapper.getItemList();
			//遍历集合把值一个一个拿出来
			for (SearchItem searchItem : itemList) {
				//SolrInputDocument的对象，存放索引库必要对象之一
				SolrInputDocument document = new SolrInputDocument();
				//遍历一次放一条记录进去
				document.addField("id", searchItem.getId());
				document.addField("item_title", searchItem.getTitle());
				document.addField("item_sell_point", searchItem.getSell_point());
				document.addField("item_price", searchItem.getPrice());
				document.addField("item_image", searchItem.getImage());
				document.addField("item_category_name", searchItem.getCategory_name());
				solrServer.add(document);
			}
			//提交事务
			solrServer.commit();			
			return LegouResult.ok();
		} catch (Exception e) {
			// log
		 e.printStackTrace();
		 //如果异常则返回导入数据异常
			return LegouResult.build(500, "导入数据异常");
		}	
	}
}
