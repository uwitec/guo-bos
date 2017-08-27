package com.guo.bos.dao;

import com.guo.bos.dao.base.IBaseDao;
import com.guo.bos.domain.User;

public interface IUserDao extends IBaseDao<User> {

	public User findUserByUsernameAndPassword(String username, String password);

	public User findUserByUsername(String username);



}
