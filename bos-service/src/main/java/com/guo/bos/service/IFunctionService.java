package com.guo.bos.service;

import java.util.List;

import com.guo.bos.domain.Function;
import com.guo.bos.utils.PageBean;

public interface IFunctionService {

	public List<Function> findAll();

	public void save(Function model);

	public void pageQuery(PageBean pageBean);

}
