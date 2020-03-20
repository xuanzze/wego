package com.wego.controller;

import com.wego.common.pojo.WegoResult;
import com.wego.pojo.TbItem;
import com.wego.pojo.TbItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wego.common.pojo.EasyUIDataGridResult;
import com.wego.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemservice;
	//url:/item/list
	//method:get
	//参数：page,rows
	//返回值:json
	@RequestMapping(value="/item/list",method=RequestMethod.GET)
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page,Integer rows){
		//1.引入服务
		//2.注入服务
		//3.调用服务的方法
		return itemservice.getItemList(page, rows);
	}

	//添加商品的方法
	//url:：/item/save
	//参数：tbitem ,desc
	//返回值 json
	//method:post

	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	public WegoResult saveItem(TbItem item, String desc){
//		//1.引入服务
		//2.注入服务
		//3.调用
		return itemservice.saveItem(item, desc);
	}

	@RequestMapping(value="/rest/page/item-edit",method=RequestMethod.GET)
	@ResponseBody
	public TbItem itemEdit(Long _){
		//1.引入服务
		//2.注入服务
		//3.调用服务的方法
		return itemservice.getItemById(_);
	}

	@RequestMapping(value="/rest/item/query/item/desc/",method=RequestMethod.GET)
	@ResponseBody
	public TbItemDesc getItemDescById(Long id){
		//1.引入服务
		//2.注入服务
		//3.调用服务的方法
		return itemservice.getItemDescById(id);
	}
}
