package com.guo.bos.service;

import com.guo.bos.domain.User;
import com.guo.bos.utils.PageBean;

public interface IUserService {

	public User login(User model);

	public void editPassword(String id, String password);

	public void pageQuery(PageBean pageBean);

	public void save(User model, String[] roleIds);

}
