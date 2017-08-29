package com.guo.bos.service;

import java.util.List;

import com.guo.bos.domain.Subarea;
import com.guo.bos.utils.PageBean;


public interface ISubareaService {

	public void save(Subarea model);

	public void pageQuery(PageBean pageBean);

	public Subarea findById(String id);

	public void update(Subarea subarea);

	public void deleteBatch(String ids);

	public void saveBatch1(List<Subarea> subareaList);

	public List<Subarea> findAll();

	public List<Subarea> findListNotAssciation();

	public List<Subarea> findListByDecidedzoneId(String decidedzoneId);

	public List<Object> findSubareasGroupByProvince();

}
