package com.wego.service;

import java.util.List;

import com.wego.common.pojo.EasyUITreeNode;

public interface ItemCatService {

	List<EasyUITreeNode> getItemCatListByParentId(Long parentId);
}
