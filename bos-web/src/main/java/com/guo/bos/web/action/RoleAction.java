package com.guo.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.guo.bos.domain.Role;
import com.guo.bos.service.IRoleService;
import com.guo.bos.web.action.base.BaseAction;


/**
 * 角色管理
 * @author guo
 *
 */
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{
	//属性驱动，接收权限的id
	private String functionIds;
	
	@Autowired
	private IRoleService service;
	
	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}
	
	/**
	 * 添加角色
	 */
	public String add(){
		service.save(model,functionIds);
		return LIST;
	}
	
	/**
	 * 分页查询
	 */
	public String pageQuery(){
		service.pageQuery(pageBean);
		this.javaToJson(pageBean, new String[]{"functions","users"});
		return NONE;
	}
	
	/**
	 * 查询所有的角色数据，返回json
	 */
	public String listajax(){
		List<Role> list = service.findAll();
		this.javaToJson(list, new String[]{"functions","users"});
		return NONE;
	}
}
