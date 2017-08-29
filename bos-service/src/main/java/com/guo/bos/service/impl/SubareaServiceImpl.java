package com.guo.bos.service.impl;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guo.bos.dao.ISubareaDao;
import com.guo.bos.domain.Subarea;
import com.guo.bos.service.ISubareaService;
import com.guo.bos.utils.PageBean;
@Service
@Transactional
public class SubareaServiceImpl implements ISubareaService {
	@Autowired
	private ISubareaDao subareaDao;
	public void save(Subarea model) {
		subareaDao.save(model);
	}
	
	public void pageQuery(PageBean pageBean) {
		subareaDao.pageQuery(pageBean);
	}

	
	public Subarea findById(String id) {
		return subareaDao.findById(id);
	}

	
	public void update(Subarea subarea) {
		subareaDao.update(subarea);
	}

	public void deleteBatch(String ids) {
		// 判断是否为空
		if (StringUtils.isNotBlank(ids)) {
			String[] staffIds = ids.split(",");
			// 循环遍历
			for (String id : staffIds) {
				subareaDao.deleteBatch("subarea.delete", id);
			}
		}

	}


	/**
	 * 文件导入
	 */
	public void saveBatch1(List<Subarea> subareaList) {
		for (Subarea subarea : subareaList) {
			subareaDao.saveOrUpdate(subarea);
		}
	}
	/**
	 * 	查询出所有
	 */
	public List<Subarea> findAll() {
		return subareaDao.findAll();
	}

	/**
	 * 查询所有未关联到定区的分区
	 */
	public List<Subarea> findListNotAssciation() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		//添加过滤条件:decidedzone的属性值为null；
		detachedCriteria.add(Restrictions.isNull("decidedzone"));
		return subareaDao.findByCriteria(detachedCriteria );
	}

	/**
	 * 根据定区id查询关联的分区
	 */
	public List<Subarea> findListByDecidedzoneId(String decidedzoneId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		//添加过滤条件
		detachedCriteria.add(Restrictions.eq("decidedzone.id", decidedzoneId));
		return subareaDao.findByCriteria(detachedCriteria );
	}

	public List<Object> findSubareasGroupByProvince() {
		return subareaDao.findSubareasGroupByProvince();
	}
	
}
