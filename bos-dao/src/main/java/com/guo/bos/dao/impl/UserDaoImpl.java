package com.guo.bos.dao.impl;

import java.io.Serializable;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.guo.bos.dao.IUserDao;
import com.guo.bos.dao.base.impl.BaseDaoImpl;
import com.guo.bos.domain.User;
@Repository   //dao的注解方式
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {
	/**
	 * 根据用户名和密码查询用户
	 */
	public User findUserByUsernameAndPassword(String username, String password) {
		String hql = "FROM User u WHERE u.username = ? AND u.password = ? ";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, username,password);
		if(list != null && list.size() > 0) {     //首先表示里面不为空，
			return list.get(0);
		}
		return null;
	}

	@Override
	public User findUserByUsername(String username) {
		String hql = "FROM User u WHERE u.username = ? ";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, username);
		if(list != null && list.size() > 0) {     //首先表示里面不为空，
			return list.get(0);
		}
		return null;
	}

}
