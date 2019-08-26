package com.legou.search.service;

import com.legou.common.SearchResult;

public interface SolrSearchService {

	SearchResult search(String keyword) throws Exception;

}
