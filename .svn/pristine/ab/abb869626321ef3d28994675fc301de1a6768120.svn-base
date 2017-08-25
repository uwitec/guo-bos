package com.guo.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guo.bos.dao.IUserDao;
import com.guo.bos.domain.User;
import com.guo.bos.service.IUserService;
import com.guo.bos.utils.MD5Utils;
@Service
@Transactional   //添加注解和事务
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserDao userDao;
	/**
	 * 用户登录
	 */
	public User login(User user) {
		//使用MD5进行加密
		String password = MD5Utils.md5(user.getPassword());
		return userDao.findUserByUsernameAndPassword(user.getUsername(),password);
	}
	/**
	 * 根据用户ID修改密码
	 */
	public void editPassword(String id, String password) {
		//使用MD5加密密码
		password = MD5Utils.md5(password);
		userDao.executeUpdate("user.editpassword", password,id);
	}

}
