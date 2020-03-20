package com.wego.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.remoting.exchange.Response;
import com.wego.cart.service.CartService;
import com.wego.common.pojo.WegoResult;
import com.wego.common.util.CookieUtils;
import com.wego.common.util.JsonUtils;
import com.wego.pojo.TbItem;
import com.wego.pojo.TbUser;
import com.wego.service.ItemService;
import com.wego.sso.service.UserLoginService;

@Controller
public class CartController {
	@Autowired
	private CartService cartservice;
	@Autowired
	private UserLoginService loginservice;
	@Autowired
	private ItemService itemservice;

	@Value("${TT_TOKEN_KEY}")
	private String TT_TOKEN_KEY;
	@Value("${TT_CART_KEY}")
	private String TT_CART_KEY;

	/**
	 * url:/cart/add/{itemId}?num=2 参数：商品的id 以及num 返回值：jsp页面
	 * 
	 */
	@RequestMapping("/cart/add/{itemId}")
	public String addItemCart(@PathVariable Long itemId, Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		// 1.引入服务
		// 2.注入服务

		// 3.判断用户是否登录
		// 从cookie中获取用户的token信息
		String token = CookieUtils.getCookieValue(request, TT_TOKEN_KEY);
		// 调用SSO的服务查询用户的信息
		WegoResult result = loginservice.getUserByToken(token);

		if (num == null)
			num = 1;
		// 获取商品的数据
		TbItem tbItem = itemservice.getItemById(itemId);
		if (result.getStatus() == 200) {
			// 4.如果已登录，调用service的方法
			TbUser user = (TbUser) result.getData();
			cartservice.addItemCart(tbItem, num, user.getId());
		} else {
			// 5.如果没有登录 调用设置到cookie的方法
			// 先根据cookie获取购物车的列表
			List<TbItem> cartList = getCookieCartList(request);
			boolean flag = false;
			// 判断如果购物车中有包含要添加的商品 商品数量相加
			for (TbItem tbItem2 : cartList) {
				if (tbItem2.getId() == itemId.longValue()) {
					// 找到列表中的商品 更新数量
					tbItem2.setNum(tbItem2.getNum() + num);
					flag = true;
					break;
				}
			}
			if (flag) {
				// 如果找到对应的商品，更新数量后，还需要设置回cookie中
				CookieUtils.setCookie(request, response, TT_CART_KEY, JsonUtils.objectToJson(cartList), 7 * 24 * 3600,
						true);
			} else {
				// 如果没有就直接添加到购物车
				// 调用商品服务
				// 设置数量
				tbItem.setNum(num);
				// 设置图片为一张
				if (tbItem.getImage() != null) {
					tbItem.setImage(tbItem.getImage().split(",")[0]);
				}
				// 添加商品到购物车中
				cartList.add(tbItem);
				// 设置到cookie中
				CookieUtils.setCookie(request, response, TT_CART_KEY, JsonUtils.objectToJson(cartList), 7 * 24 * 3600,
						true);
			}
		}
		return "cartSuccess";
	}

	// 展示购物车的列表
	@RequestMapping("/cart/cart")
	public String getCartList(HttpServletRequest request) {
		// 1.引入服务
		// 2.注入服务

		// 3.判断用户是否登录
		// 从cookie中获取用户的token信息
		String token = CookieUtils.getCookieValue(request, TT_TOKEN_KEY);
		// 调用SSO的服务查询用户的信息
		WegoResult result = loginservice.getUserByToken(token);

		System.out.println(result.getData());
		// 获取商品的数据
		if (result.getStatus() == 200) {
			// 4.如果已登录，调用service的方法
			TbUser user = (TbUser) result.getData();
			List<TbItem> cartList = cartservice.getCartList(user.getId());
			System.out.println(cartList.size());
			request.setAttribute("cartList", cartList);
		} else {
			// 5.如果没有登录 调用cookie的方法 获取商品的列表
			List<TbItem> cartList = getCookieCartList(request);
			// 将数据传递到页面中
			request.setAttribute("cartList", cartList);
		}
		return "cart";
	}

	/**
	 * url:/cart/update/num/{itemId}/{num} 参数：商品的id 和更新后的数量 还有用户的id 返回值：json
	 */
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public WegoResult updateItemCartByItemId(@PathVariable Long itemId, @PathVariable Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		// 1.引入服务
		// 2.注入服务

		// 3.判断用户是否登录
		// 从cookie中获取用户的token信息
		String token = CookieUtils.getCookieValue(request, TT_TOKEN_KEY);
		// 调用SSO的服务查询用户的信息
		WegoResult result = loginservice.getUserByToken(token);

		// 获取商品的数据
		if (result.getStatus() == 200) {
			// 4.如果已登录，调用service的方法
			TbUser user = (TbUser) result.getData();
			// 更新商品的数量
			cartservice.updateItemCartByItemId(user.getId(), itemId, num);
		} else {
			// 5.如果没有登录 调用cookie的方法 更新cookie中的商品的数量的方法
			updateCookieItemCart(itemId, num, request, response);
		}
		return WegoResult.ok();
	}

	/**
	 * url:/cart/delete/{itemId} 参数：用户的ID 还有商品的ID 返回值：string 逻辑视图
	 */
	// 删除购物车中的商品
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteItemCartByItemId(@PathVariable Long itemId, HttpServletRequest request,
			HttpServletResponse response) {
		// 1.引入服务
		// 2.注入服务

		// 3.判断用户是否登录
		// 从cookie中获取用户的token信息
		String token = CookieUtils.getCookieValue(request, TT_TOKEN_KEY);
		// 调用SSO的服务查询用户的信息
		WegoResult result = loginservice.getUserByToken(token);

		// 获取商品的数据
		if (result.getStatus() == 200) {
			// 4.如果已登录，调用service的方法
			TbUser user = (TbUser) result.getData();
			// 删除
			cartservice.deleteItemCartByItemId(user.getId(), itemId);
		} else {
			// 5.如果没有登录 调用cookie的方法 删除cookie中的商品
			deleteCookieItemCartByItemId(itemId, request, response);
		}
		return "redirect:/cart/cart.html";// 重定向
	}

	// -------------------------------------完美分割线------------------------------------------------------------
	// 获取购物车的列表
	public List<TbItem> getCookieCartList(HttpServletRequest request) {
		// 从cookie中获取商品的列表
		String jsonstr = CookieUtils.getCookieValue(request, TT_CART_KEY, true);// 商品的列表的JSON
		// 讲商品的列表的JSON转成 对象
		if (StringUtils.isNotBlank(jsonstr)) {
			List<TbItem> list = JsonUtils.jsonToList(jsonstr, TbItem.class);
			return list;
		}
		return new ArrayList<TbItem>();

	}

	/**
	 * 更新商品的数量
	 * 
	 * @param itemId
	 * @param num
	 */
	private void updateCookieItemCart(Long itemId, Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		// 1.获取cookie中的购物车的商品列表
		List<TbItem> cartList = getCookieCartList(request);
		// 2.判断修改的商品是否在购物车的列表中
		boolean flag = false;
		for (TbItem tbItem : cartList) {
			// 表示找到了要修改的商品
			if (tbItem.getId() == itemId.longValue()) {
				tbItem.setNum(num);
				flag = true;
				break;
			}
		}
		if (flag == true) {
			// 3.如果存在 更新数量
			CookieUtils.setCookie(request, response, TT_CART_KEY, JsonUtils.objectToJson(cartList), 7 * 24 * 3600,
					true);
		} else {
			// 4.如果不存在 不管啦。
		}
	}

	/**
	 * 删除cookie中的购物车的商品
	 * 
	 * @param itemId
	 * @param request
	 * @param response
	 */
	private void deleteCookieItemCartByItemId(Long itemId, HttpServletRequest request, HttpServletResponse response) {
		// 1.从cookie中获取商品的列表
		List<TbItem> cartList = getCookieCartList(request);
		// 2.判断 商品是否存在于商品的列表中
		boolean flag = false;
		for (TbItem tbItem : cartList) {
			if (tbItem.getId() == itemId.longValue()) {
				// 找到要删除的商品
				cartList.remove(tbItem);
				flag = true;
				// break;
			}
		}
		if (flag == true) {
			// 3.如果存在，删除
			CookieUtils.setCookie(request, response, TT_CART_KEY, JsonUtils.objectToJson(cartList), 7 * 24 * 3600,
					true);
		}
		// 4.如果不存在，不管
	}

	public static void main(String[] args) {
		Long a = 129l;
		Long b = 129l;
		System.out.println(a == b);
		List list = new ArrayList<String>();
		list.add("一");
		list.add("二");
		list.add("三");
		list.add("四");
		for (Object o : list) {
			if ("二".equals(o.toString())) {
				list.remove(o);
			}
		}
	}

}
