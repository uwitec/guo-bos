package com.guo.bos.dao;

import org.hibernate.criterion.DetachedCriteria;

import com.guo.bos.dao.base.IBaseDao;
import com.guo.bos.domain.Staff;

public interface IStaffDao extends IBaseDao<Staff> {

	public void save(Staff model);

}
