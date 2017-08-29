package com.guo.bos.dao;

import java.util.List;

import com.guo.bos.dao.base.IBaseDao;
import com.guo.bos.domain.Subarea;


public interface ISubareaDao extends IBaseDao<Subarea>{

	public List<Object> findSubareasGroupByProvince();

	
}
