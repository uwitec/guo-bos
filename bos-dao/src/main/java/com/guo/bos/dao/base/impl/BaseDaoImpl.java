package com.guo.bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import com.guo.bos.dao.base.IBaseDao;
import com.guo.bos.utils.PageBean;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {
	// 代表的是某个实体的类型
	private Class<T> entityClass;

	@Resource
	// 根据类型注入Sptring工厂中的会话工厂对象
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory); // 直接找父类！！！！
	}

	// 在父类(BaseDaoImpl)构造方法中动态获取entityClass
	public BaseDaoImpl() {
		ParameterizedType superClass = (ParameterizedType) this.getClass()
				.getGenericSuperclass(); // 获得父类的类型
		// 获得父类上声明 的泛型数组
		Type[] actualTypeArguments = superClass.getActualTypeArguments();
		entityClass = (Class<T>) actualTypeArguments[0];
	}

	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}

	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	public T findById(Serializable id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	public List<T> findAll() {
		String hql = "FROM " + entityClass.getSimpleName();
		return (List<T>) this.getHibernateTemplate().find(hql);
	}

	/**
	 * 通用执行更新
	 * 这个方法很重要
	 */
	public void executeUpdate(String queryName, Object... objects) {
		//获取当前Session
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.getNamedQuery(queryName);
		int i = 0;
		for (Object object : objects) {
			// 为HQL语句中的？赋值
			query.setParameter(i++, object);
		}
		// 执行更新
		query.executeUpdate();
	}

	/**
	 * 通用的分页查询
	 */
	public void pageQuery(PageBean pageBean) {
		//在pagebean中已经封装好了三个属性
		int currentPage = pageBean.getCurrentPage();
		int pageSize = pageBean.getPageSize();
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		
		//查询total，总的数据量
		detachedCriteria.setProjection(Projections.rowCount());     //指定Hibernate查询方式
		//指定HIbernate框架封装对象的方式
		detachedCriteria.setResultTransformer(detachedCriteria.ROOT_ENTITY);
		//返回的是Long
		List<Long> countList = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);  //select Count(*) from bc_staff;
		//循环遍历取第一个
		Long count = countList.get(0);
		//查询出来之后赋值给pagebean
		pageBean.setTotal(count.intValue());
		
		//查询rows，当前页需要展示的数据页
		detachedCriteria.setProjection(null);     //在这里是常规的查询方式，select * from bc_staff;
		int firstResult = (currentPage - 1) * pageSize;
		int maxResults = pageSize;
		//返回当前页的数据
		List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		//放到pagebean中
		pageBean.setRows(rows);
		
	}

	/**
	 * 保存或更新
	 */
	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	@Override
	public void deleteBatch(String queryName, Object... objects) {
		      //获取当前Session
				Session session = this.getSessionFactory().getCurrentSession();
				Query query = session.getNamedQuery(queryName);
				int i = 0;
				for (Object object : objects) {
					// 为HQL语句中的？赋值
					query.setParameter(i++, object);
				}
				// 执行更新
				query.executeUpdate();
	}

	@Override
	public void restoreBatch(String queryName, Object... objects) {
		//获取当前Session
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.getNamedQuery(queryName);
		int i = 0;
		for (Object object : objects) {
			// 为HQL语句中的？赋值
			query.setParameter(i++, object);
		}
		// 执行更新
		query.executeUpdate();
	}

	public List<T> findByCriteria(DetachedCriteria detachedCriteria) {
		return (List<T>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
	}

}
