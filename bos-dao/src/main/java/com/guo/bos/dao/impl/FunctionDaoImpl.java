package com.guo.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.guo.bos.dao.IFunctionDao;
import com.guo.bos.dao.base.impl.BaseDaoImpl;
import com.guo.bos.domain.Function;


@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements IFunctionDao {
	public List<Function> findAll() {
		String hql = "FROM Function f WHERE f.parentFunction IS NULL";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql);
		return list;
	}

	//根据用户id查询对应的权限
	public List<Function> findFunctionListByUserId(String userid) {
		String hql = "SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles"
				+ " r LEFT OUTER JOIN r.users u WHERE u.id = ?";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql, userid);
		return list;
	}

	// 查询所有菜单
		public List<Function> findAllMenu() {
			String hql = "FROM Function f WHERE f.generatemenu = '1' ORDER BY f.zindex DESC";
			List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql);
			return list;
		}
		
		//根据用户id查询菜单
		public List<Function> findMenuByUserId(String userId) {
			String hql = "SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles"
					+ " r LEFT OUTER JOIN r.users u WHERE u.id = ? AND f.generatemenu = '1' "
					+ "ORDER BY f.zindex DESC";
			List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql, userId);
			return list;
		}
}
