package com.guo.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.guo.bos.domain.Function;
import com.guo.bos.service.IFunctionService;
import com.guo.bos.web.action.base.BaseAction;


/**
 * 权限管理
 * @author guo
 *
 */
@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function>{
	@Autowired
	private IFunctionService service;
	/**
	 * 查询所有权限，返回json数据
	 */
	public String listajax(){
		List<Function> list = service.findAll();
		this.javaToJson(list, new String[]{"parentFunction","roles"});
		return NONE;
	}
	
	/**
	 * 添加权限 
	 */
	public String add(){
		service.save(model);
		return LIST;
	}
	
	public String pageQuery(){
		String page = model.getPage();
		pageBean.setCurrentPage(Integer.parseInt(page));
		service.pageQuery(pageBean);
		this.javaToJson(pageBean, new String[]{"parentFunction","roles","children"});
		return NONE;
	}
}
