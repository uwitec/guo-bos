package com.guo.bos.web.interceptor;

import org.apache.struts2.ServletActionContext;

import com.guo.bos.domain.User;
import com.guo.bos.utils.BOSUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 自定义拦截器，实现未登录自动跳转到登录页面MethodFilterInterceptor
 */
public class BOSLoginInterceptor extends MethodFilterInterceptor {
	//拦截方法
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		/*//获取拦截的路径
		ActionProxy proxy = invocation.getProxy();
		String actionName = proxy.getActionName();
		String namespace = proxy.getNamespace();
		String url = namespace + actionName;
		System.out.println(url);*/
		//从session域中取出用户对象
		User user = BOSUtils.getLoginUser();
		if(user == null) {
			//说明没有登录，跳转到登录页面
			return "login";   //因为没有继承BaseAction，
		}
		//放行
		return invocation.invoke();
	}

}
