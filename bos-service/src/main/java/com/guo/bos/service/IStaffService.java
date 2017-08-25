package com.guo.bos.service;

import java.util.List;

import com.guo.bos.domain.Staff;
import com.guo.bos.utils.PageBean;

public interface IStaffService {


	public void save(Staff model);

	public void pageQuery(PageBean pageBean);

	public void deleteBatch(String ids);

	public Staff findById(String id);

	public void update(Staff staff);

	public void restoreBatch(String ids);

	public List<Staff> findListNotDelete();


}
