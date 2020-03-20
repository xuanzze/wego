package com.wego.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wego.common.pojo.WegoResult;
import com.wego.pojo.TbUser;
import com.wego.sso.service.UserRegisterService;

@Controller
public class UserRegisterController {
	@Autowired
	private UserRegisterService registerservice;
	/**
	 * url：/user/check/{param}/{type}
	 * 
	 * @param param
	 * @param type  1  2 3 
	 * @return
	 */
	@RequestMapping(value="/user/check/{param}/{type}",method=RequestMethod.GET)
	@ResponseBody
	public WegoResult checkData(@PathVariable String param,@PathVariable Integer type){
		//1.引入服务
		//2.注入
		//3.调用
		return registerservice.checkData(param, type);
	}
	/*
	 * url:/user/register
	 *  参数：
	 *  username //用户名
		password //密码
		phone //手机号
		email //邮箱
	请求的方法：post
	返回值：json
	 */
	/**
	 * 注册用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	@ResponseBody
	public WegoResult register(TbUser user){
		//
		WegoResult result = registerservice.register(user);
		return result;
	}
}
