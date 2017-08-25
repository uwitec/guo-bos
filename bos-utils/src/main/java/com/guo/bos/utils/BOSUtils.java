package com.guo.bos.utils;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.guo.bos.domain.User;

/**
 * BOS项目的工具类
 * @author guo
 *
 */
public class BOSUtils {
	//获取Session的静态方法
	public static HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}
	//获取登录用户的对象
	public static User getLoginUser() {
		return (User) getSession().getAttribute("loginUser");
	}
	
}
