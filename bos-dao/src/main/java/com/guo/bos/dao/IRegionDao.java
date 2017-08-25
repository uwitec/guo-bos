package com.guo.bos.dao;

import java.util.List;

import com.guo.bos.dao.base.IBaseDao;
import com.guo.bos.domain.Region;

public interface IRegionDao extends IBaseDao<Region> {

	public List<Region> findLIstByQ(String q);





}
