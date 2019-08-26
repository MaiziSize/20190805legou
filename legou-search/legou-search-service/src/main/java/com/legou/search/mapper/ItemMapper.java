package com.legou.search.mapper;

import com.legou.common.SearchItem;
import java.util.List;


public interface ItemMapper {
	
	List<SearchItem> getItemList();

	SearchItem getItemById(long itemidLong);
    
}