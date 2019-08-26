package com.legou.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legou.common.SearchResult;
import com.legou.search.dao.SolrSearchDao;
import com.legou.search.service.SolrSearchService;

@Service
public class SolrSearchServiceImpl implements SolrSearchService {

	@Autowired
	SolrSearchDao solrSearchDao;

	@Override
	public SearchResult search(String keyword) throws Exception {
		//往索引库中查询所需对象
		SolrQuery query = new SolrQuery();
		//设置查询
		query.setQuery(keyword);
		query.set("df", "item_title");
		query.setRows(100);
		//将条件传输至持久层
		SearchResult searchResult = solrSearchDao.search(query);
		//返回一个集合类
		return searchResult;
	}

}
