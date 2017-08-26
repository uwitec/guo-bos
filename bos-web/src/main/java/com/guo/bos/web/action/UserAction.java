package com.guo.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.guo.bos.domain.User;
import com.guo.bos.service.IUserService;
import com.guo.bos.utils.BOSUtils;
import com.guo.bos.web.action.base.BaseAction;
import com.guo.crm.Customer;
import com.guo.crm.ICustomerService;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {

	private static final long serialVersionUID = 1L;
	// 属性驱动，用户接受用户的验证码
	private String checkcode;

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	@Autowired
	// 自动注入
	private IUserService userService;

	/**
	 * 用户登录
	 */
	public String login() {
		// 从Session中获取生成的验证码
		String validatecode = (String) ServletActionContext.getRequest()
				.getSession().getAttribute("key");
		// 校验验证码是否输入正确
		if (StringUtils.isNotBlank(checkcode) && checkcode.equals(validatecode)) {
			// 输入的验证码正确
			User user = userService.login(model);
			if (user != null) {
				// 表示登录成功
				// 将User对象放入Seession域中，然后在跳转到首页
				ServletActionContext.getRequest().getSession()
						.setAttribute("loginUser", user);
				return HOME;
			} else {
				// 登录失败，,设置提示信息，跳转到登录页面
				// 输入的验证码错误,设置提示信息，跳转到登录页面
				this.addActionError("用户名或者密码输入错误！");
				return LOGIN;
			}
		} else {
			// 登录失败，,设置提示信息，跳转到登录页面
			// 输入的验证码错误,设置提示信息，跳转到登录页面
			this.addActionError("输入的验证码错误");
			return LOGIN;
		}
	}
	/**
	 * 注册方法
	 */
	public String logout() {
		ServletActionContext.getRequest().getSession().invalidate();   //直接销毁session
		return LOGIN;
	}
	/**
	 * 修改当前用户密码
	 * @throws IOException 
	 */
	public String editPassword() throws IOException{
		String f = "1";
		//获取当前登录用户
		User user = BOSUtils.getLoginUser();
		try{
			userService.editPassword(user.getId(),model.getPassword());
		}catch(Exception e){
			f = "0";
			e.printStackTrace();
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(f);
		return NONE;
	}
}
