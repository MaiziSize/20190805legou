package com.legou.search.mq;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.legou.common.SearchItem;
import com.legou.search.mapper.ItemMapper;

public class LegouActiveMQListener implements MessageListener {

	@Autowired
	ItemMapper itemMapper;

	@Autowired
	SolrServer solrServer;

	@Override
	public void onMessage(Message message) {

		TextMessage textMessage = (TextMessage) message;
		try {
			String itemid = textMessage.getText();
			// 根据商品id取出商品信息导入索引库
			long itemidLong = Long.parseLong(itemid);

			// itemmap
			SearchItem searchItem = itemMapper.getItemById(itemidLong);
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", searchItem.getId());
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			try {
				solrServer.add(document);
				solrServer.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

}
