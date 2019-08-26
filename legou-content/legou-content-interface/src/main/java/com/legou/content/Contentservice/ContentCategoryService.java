package com.legou.content.Contentservice;

import java.util.List;

import com.legou.Utils.utils.LegouResult;
import com.legou.common.EasyUITreeNode;


public interface ContentCategoryService {
	List<EasyUITreeNode> getContentList(long id);
	LegouResult addContentCategoryNode(Long parentId, String name);
}
