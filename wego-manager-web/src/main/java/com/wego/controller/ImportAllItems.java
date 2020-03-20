package com.wego.controller;

import com.wego.common.pojo.WegoResult;
import com.wego.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImportAllItems {
	@Autowired
	private SearchService service;
	/**
	 * 导入所有的商品的数据到索引库中
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/index/importAll")
	@ResponseBody
	public WegoResult importAll() throws Exception{
		//1.引入服务
		//2.注入服务
		//3.调用方法
		return service.importAllSearchItems();
	}
}
