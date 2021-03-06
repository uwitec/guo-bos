package com.guo.bos.service;

import java.util.List;

import com.guo.bos.domain.Region;
import com.guo.bos.utils.PageBean;

public interface IRegionService {

	public void saveBatch(List<Region> regionList);

	public void pageQuery(PageBean pageBean);

	public void deleteBatch(String ids);

	public Region findByid(String id);

	public void update(Region region);

	public void save(Region model);

	public List<Region> findAll();

	public List<Region> findListByQ(String q);


}
