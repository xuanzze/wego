package com.wego.order.pojo;

import java.io.Serializable;
import java.util.List;

import com.wego.pojo.TbOrder;
import com.wego.pojo.TbOrderItem;
import com.wego.pojo.TbOrderShipping;

public class OrderInfo extends TbOrder implements Serializable {
	private List<TbOrderItem> orderItems;//订单项
	private TbOrderShipping orderShipping;
	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
	
}
