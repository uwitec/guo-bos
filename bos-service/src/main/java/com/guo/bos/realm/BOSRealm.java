package com.guo.bos.realm;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.guo.bos.dao.IFunctionDao;
import com.guo.bos.dao.IUserDao;
import com.guo.bos.domain.Function;
import com.guo.bos.domain.User;
import com.guo.bos.service.IFunctionService;


public class BOSRealm extends AuthorizingRealm{
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IFunctionDao dao;
	
	//认证方法
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken passwordToken = (UsernamePasswordToken)token;
		//获得页面输入的用户名
		String username = passwordToken.getUsername();
		//根据用户名查询数据库中的密码
		User user = userDao.findUserByUsername(username);
		if(user == null){
			//页面输入的用户名不存在
			return null;
		}
		//简单认证信息对象
		AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
		//框架负责比对数据库中的密码和页面输入的密码是否一致
		return info;
	}

	//授权方法
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//为用户授权
		info.addStringPermission("staff-list");
		
		//获取当前登录用户
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		//User user2 = (User) principals.getPrimaryPrincipal();
		//System.out.println(user1 == user2);
		//根据当前用户查询对应的角色，获取实际的权限
		List<Function> list = null;
		if(user.getUsername().equals("admin")){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Function.class);
			list = dao.findByCriteria(detachedCriteria );
		}else {
			list = dao.findFunctionListByUserId(user.getId());
		}
		for (Function function : list) {
			info.addStringPermission(function.getId());
		}
		return info;
	}
}
