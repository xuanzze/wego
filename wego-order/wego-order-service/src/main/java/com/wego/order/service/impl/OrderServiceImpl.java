package com.wego.order.service.impl;

import java.util.Date;
import java.util.List;

import com.wego.order.jedis.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wego.common.pojo.WegoResult;
import com.wego.mapper.TbOrderItemMapper;
import com.wego.mapper.TbOrderMapper;
import com.wego.mapper.TbOrderShippingMapper;
import com.wego.order.pojo.OrderInfo;
import com.wego.order.service.OrderService;
import com.wego.pojo.TbOrderItem;
import com.wego.pojo.TbOrderShipping;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private TbOrderMapper mapper;
	@Autowired
	private TbOrderItemMapper orderitemmapper;
	@Autowired
	private TbOrderShippingMapper shippingmapper;
	@Autowired
	private JedisClient client;
	
	@Value("${GEN_ORDER_ID_KEY}")
	private String GEN_ORDER_ID_KEY;
	
	@Value("${GEN_ORDER_ID_INIT}")
	private String GEN_ORDER_ID_INIT;
	
	@Value("${GEN_ORDER_ITEM_ID_KEY}")
	private String GEN_ORDER_ITEM_ID_KEY;
	
	@Override
	public WegoResult createOrder(OrderInfo info) {
		//1.插入订单表
			//通过redis的incr 生成订单id
		//判断如果key没存在 需要初始化一个key设置一个初始值
		if(!client.exists(GEN_ORDER_ID_KEY)){
				client.set(GEN_ORDER_ID_KEY, GEN_ORDER_ID_INIT);
		}
		String orderId = client.incr(GEN_ORDER_ID_KEY).toString();
			//补全其他的属性
		//info.setBuyerNick(buyerNick);  在controller设置
		info.setCreateTime(new Date());
		info.setOrderId(orderId);
		info.setPostFee("0");
		info.setStatus(1);
		//info.setUserId(userId);由controller设置
		info.setUpdateTime(info.getCreateTime());
		//注入mapper
		mapper.insert(info);
		//2.插入订单项表
			//补全其他的属性
		List<TbOrderItem> orderItems = info.getOrderItems();
		for (TbOrderItem tbOrderItem : orderItems) {
			//设置订单项的id 通过redis的incr 生成订单项的id
			String incr = client.incr(GEN_ORDER_ITEM_ID_KEY).toString();
			tbOrderItem.setId(incr);
			tbOrderItem.setOrderId(orderId);
			//插入订单项表
			orderitemmapper.insert(tbOrderItem);
		}
		//3.插入订单物流表
			//设置订单id
			TbOrderShipping shipping = info.getOrderShipping();
			//补全其他的属性
			shipping.setOrderId(orderId);
			shipping.setCreated(info.getCreateTime());
			shipping.setUpdated(info.getCreateTime());
			//chauru
			shippingmapper.insert(shipping);
			//返回需要包含订单的ID
		return WegoResult.ok(orderId);
	}

}
