package com.wego.content.service;

import java.util.List;

import com.wego.common.pojo.WegoResult;
import com.wego.pojo.TbContent;

/**
 * 内容处理的接口
 * @title ContentService.java
 * <p>description</p>
 * <p>company: www.itheima.com</p>
 * @author ljh 
 * @version 1.0
 */
public interface ContentService {
	/**
	 * 插入内容表
	 * @param content
	 * @return
	 */
	public WegoResult saveContent(TbContent content);
	/**
	 * 根据内容分类的ID 查询其下的内容列表
	 * @param categoryId
	 * @return
	 */
	public List<TbContent> getContentListByCatId(Long categoryId);
}
