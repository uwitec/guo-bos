package com.guo.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guo.bos.dao.IFunctionDao;
import com.guo.bos.domain.Function;
import com.guo.bos.service.IFunctionService;
import com.guo.bos.utils.PageBean;

@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService {
	
	@Autowired
	private IFunctionDao dao;
	public List<Function> findAll() {
	return dao.findAll();
	}
	
	//添加权限
	public void save(Function model) {
		Function parentFunction = model.getParentFunction();
		if(parentFunction != null && parentFunction.getId().equals("")){
			model.setParentFunction(null);
		}
		dao.save(model);
	}

	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
	}
}
