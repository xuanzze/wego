package com.wego.cart.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wego.cart.jedis.JedisClient;
import com.wego.cart.service.CartService;
import com.wego.common.pojo.WegoResult;
import com.wego.common.util.JsonUtils;
import com.wego.pojo.TbItem;

@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private JedisClient client;
	
	@Value("${TT_CART_REDIS_PRE_KEY}")
	private String TT_CART_REDIS_PRE_KEY;
	

	// 登录状态下的添加购物车  hash
	@Override
	public WegoResult addItemCart(TbItem item, Integer num, Long userId) {
		// 1.查询  可以根据 key 和field获取某一个商品
		TbItem itemtem = queryItemByItemIdAndUserId(item.getId(), userId);
		// 2.判断要添加的商品是否存在于列表中
		if(itemtem!=null){
			// 3.如果存在，直接数量相加
			itemtem.setNum(itemtem.getNum()+num);
			//图片只取一张
			//设置到redis
			client.hset(TT_CART_REDIS_PRE_KEY+":"+userId+"", itemtem.getId()+"", JsonUtils.objectToJson(itemtem));
		}else{
			// 4.如果不存在，直接添加到redis中
				//查询商品的数据 （商品的名称商品的价格，商品的图片。。） 调用商品的服务  直接从controller中传递
				//.设置商品的数量
			item.setNum(num);
				//.设置商品的图片为一张
			if(item.getImage()!=null){
				item.setImage(item.getImage().split(",")[0]);
			}
			//.设置到redis中
			client.hset(TT_CART_REDIS_PRE_KEY+":"+userId+"", item.getId()+"", JsonUtils.objectToJson(item));
		}
		return WegoResult.ok();
	}

	private TbItem queryItemByItemIdAndUserId(Long itemId, Long userId) {
		String string = client.hget(TT_CART_REDIS_PRE_KEY+":"+userId+"", itemId+"");
		if(StringUtils.isNoneBlank(string)){
			TbItem tbItem = JsonUtils.jsonToPojo(string, TbItem.class);
			return tbItem;
		}
		return null;
	}

	// 获取购物车的商品的列表
	public List<TbItem> getCartList(Long userId) {
		Map<String, String> map = client.hgetAll(TT_CART_REDIS_PRE_KEY+":"+userId + "");
		//
		List<TbItem> list = new ArrayList<TbItem>();
		if (map != null) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				String value = entry.getValue();// 商品的jSON数据
				// 转成POJO
				TbItem item = JsonUtils.jsonToPojo(value, TbItem.class);
				list.add(item);
			}
		}
		return list;
	}

	@Override
	public WegoResult updateItemCartByItemId(Long userId, Long itemId, Integer num) {
		//1.根据用户id和商品的id获取商品的对象
		TbItem tbItem = queryItemByItemIdAndUserId(itemId,userId);
		//判断是否存在
		if(tbItem!=null){
			//2.更新数量
			tbItem.setNum(num);
			//设置回redis中
			client.hset(TT_CART_REDIS_PRE_KEY+":"+userId, itemId+"", JsonUtils.objectToJson(tbItem));
		}else{
			//不管啦
		}
		return WegoResult.ok();
	}

	@Override
	public WegoResult deleteItemCartByItemId(Long userId, Long itemId) {
		client.hdel(TT_CART_REDIS_PRE_KEY+":"+userId, itemId+"");
		return WegoResult.ok();
	}

}
