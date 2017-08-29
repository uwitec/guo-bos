package com.guo.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guo.bos.dao.IFunctionDao;
import com.guo.bos.domain.Function;
import com.guo.bos.domain.User;
import com.guo.bos.service.IFunctionService;
import com.guo.bos.utils.BOSUtils;
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

	@Override
	public List<Function> findMenu() {
		List<Function> list = null;
		User user = BOSUtils.getLoginUser();
		if(user.getUsername().equals("admin")){
			//如果是超级管理员内置用户，查询所有菜单
			list = dao.findAllMenu();
		}else{
			//其他用户，根据用户id查询菜单
			list = dao.findMenuByUserId(user.getId());
		}
		return list;
	}
	
}
