package com.wego.service;

import com.wego.common.pojo.EasyUIDataGridResult;
import com.wego.common.pojo.WegoResult;
import com.wego.pojo.TbItem;
import com.wego.pojo.TbItemDesc;

public interface ItemService {
	
	/**
	 *  根据当前的页码和每页 的行数进行分页查询
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUIDataGridResult getItemList(Integer page, Integer rows);

	/**
	 * 添加商品基本数据和描述数据
	 * @param item
	 * @param desc
	 * @return
	 */
	public WegoResult saveItem(TbItem item, String desc);

	/**
	 * 根据商品的id查询商品的数据
	 * @param itemId
	 * @return
	 */
	public TbItem  getItemById(Long itemId);

	//根据商品的id查询商品的描述
	public TbItemDesc getItemDescById(Long itemId);

//	public TbItem itemEdit(String itemId);
}
