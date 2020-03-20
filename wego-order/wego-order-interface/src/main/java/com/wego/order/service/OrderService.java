package com.wego.order.service;

import com.wego.common.pojo.WegoResult;
import com.wego.order.pojo.OrderInfo;

public interface OrderService {
	/**
	 * 创建订单
	 * @param info
	 * @return
	 */
	public WegoResult createOrder(OrderInfo info);
}
