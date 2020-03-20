package com.wego.sso.service;

import com.wego.common.pojo.WegoResult;

/**
 * 登录的接口
 * @title UserLoginServcie.java
 * <p>description</p>
 * <p>company: www.itheima.com</p>
 * @author ljh 
 * @version 1.0
 */
public interface UserLoginService {
	/**
	 * 根据用户名和密码登录
	 * @param username
	 * @param password
	 * @return
	 * wegoresult 登录成功 返回200 并且包含一个token数据
	 *登录失败：返回400
	 */
	public WegoResult login(String username, String password);
	/**
	 * 根据token获取用户的信息
	 * @param token
	 * @return  WegoResult 应该包含用户的信息
	 */
	public WegoResult getUserByToken(String token);
}
