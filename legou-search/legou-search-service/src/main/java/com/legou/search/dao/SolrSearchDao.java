package com.legou.search.dao;

import java.util.ArrayList;
import java.util.List;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.legou.common.SearchItem;
import com.legou.common.SearchResult;
//它将DAO 导入 IoC 容器，并使未经检查的异常有资格转换为 Spring DataAccessException
@Repository
public class SolrSearchDao {

	@Autowired
	SolrServer solrServer;

	public SearchResult search(SolrQuery query) {
		//创建SearchResult对象里面存放着集合
		SearchResult searchResult = new SearchResult();
		try {
			//它可以根据 url地址，创建一个可用的示例返回查询对象queryResponse
			QueryResponse queryResponse = solrServer.query(query);
			//从索引库中查询值返回SolrDocumentList类对象
			SolrDocumentList results = queryResponse.getResults();
			//创建SearchResult中的集合List<SearchItem>
			List<SearchItem> itemList = new ArrayList<SearchItem>();
			//循环遍历来取值
			for (SolrDocument solrDocument : results) {
				//每循环一次就创建一次SearchItem
				SearchItem item = new SearchItem();
				item.setId((String) solrDocument.get("id"));
				item.setCategory_name((String) solrDocument.get("item_category_name"));
				item.setImage((String) solrDocument.get("item_image"));
				item.setSell_point((String) solrDocument.get("item_sell_point"));
				item.setPrice((long) solrDocument.get("item_price"));
				item.setTitle((String) solrDocument.get("item_title"));
				//将值都放入到list集合中
				itemList.add(item);
			}
			//设置searchResult中的setItemList属性
			searchResult.setItemList(itemList);

		} catch (SolrServerException e) {
			e.printStackTrace();
		}

		return searchResult;
	}

}