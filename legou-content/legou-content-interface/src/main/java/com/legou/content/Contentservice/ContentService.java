package com.legou.content.Contentservice;

import java.util.List;

import com.legou.Utils.utils.LegouResult;
import com.legou.pojo.TbContent;

public interface ContentService {
	LegouResult saveContent(TbContent tbContent);
	List<TbContent> getContentById(long catgoryid);

}
