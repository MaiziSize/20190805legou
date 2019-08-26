package com.sy.sorl;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class Sorl {
	@Test
	public void sorlTest() throws SolrServerException, IOException {
		SolrServer server= new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id","6");
		document.addField("item_title", "中国电信移动网");
		document.addField("item_sell_point", "清仓！仅北京，武汉仓有货！");
		document.addField("item_price", 1000);
		server.add(document);
		server.commit();
	}
	
	@Test
	public void delTest() throws SolrServerException, IOException {
		SolrServer server = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
		server.deleteById("005");
		server.commit();
	}
	@Test
	public void seleTest() throws SolrServerException {
		SolrServer server = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");	
		SolrQuery query = new SolrQuery();
		query.set("q", "*:*");
		QueryResponse response = server.query(query);
		SolrDocumentList documentList = response.getResults();
		for (SolrDocument document : documentList) {
			System.out.println(document.get("id"));
			System.out.println(document.get("item_title"));
			System.out.println(document.get("item_sell_point"));
			System.out.println(document.get("item_price"));
		}
		
	}
}
