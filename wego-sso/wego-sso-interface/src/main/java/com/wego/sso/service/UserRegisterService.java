package com.wego.sso.service;

import com.wego.common.pojo.WegoResult;
import com.wego.pojo.TbUser;

/**
 * 用户注册的接口
 * @title UserRegisterService.java
 * <p>description</p>
 * <p>company: www.itheima.com</p>
 * @author ljh 
 * @version 1.0
 */
public interface UserRegisterService {
	/**
	 * 根据参数和类型来校验数据
	 * @param param  要校验的数据
	 * @param type  1  2  3  分别代表 username,phone,email
	 * @return
	 */
	public WegoResult checkData(String param, Integer type);
	/**
	 * 注册用户
	 * @param user
	 * @return
	 */
	public WegoResult register(TbUser user);
}
