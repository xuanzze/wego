package com.wego.content.service;

import com.wego.common.pojo.EasyUITreeNode;
import com.wego.common.pojo.WegoResult;

import java.util.List;

public interface ContentCategoryService {
	//通过节点的id查询该节点的哦子节点列表
	public List<EasyUITreeNode> getContentCategoryList(Long parentId);
	//添加内容分类
	/**
	 * @param parentId 父节点的id
	 * @param name 新增节点的名称
	 * @return
	 */
	public WegoResult createContentCategory(Long parentId, String name);
}
