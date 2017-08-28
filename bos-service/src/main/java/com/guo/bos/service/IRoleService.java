package com.guo.bos.service;

import java.util.List;

import com.guo.bos.domain.Role;
import com.guo.bos.utils.PageBean;


public interface IRoleService {

	public void save(Role role, String functionIds);

	public void pageQuery(PageBean pageBean);

	public List<Role> findAll();

}
