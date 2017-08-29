package com.guo.bos.dao;

import java.util.List;

import com.guo.bos.dao.base.IBaseDao;
import com.guo.bos.domain.Function;


public interface IFunctionDao extends IBaseDao<Function> {
	public List<Function> findAll();

	public List<Function> findFunctionListByUserId(String id);

	public List<Function> findAllMenu();

	public List<Function> findMenuByUserId(String id);
}
