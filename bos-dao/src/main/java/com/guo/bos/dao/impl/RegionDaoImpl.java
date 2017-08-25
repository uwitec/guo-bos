package com.guo.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.guo.bos.dao.IRegionDao;
import com.guo.bos.dao.base.impl.BaseDaoImpl;
import com.guo.bos.domain.Region;

@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements IRegionDao {

	/**
	 * 根据页面输入进行模糊查询
	 */
	public List<Region> findLIstByQ(String q) {
		String hql = "FROM Region r WHERE r.shortcode LIKE ? "
				+ "	OR r.citycode LIKE ? OR r.province LIKE ? "
				+ "OR r.city LIKE ? OR r.district LIKE ?";
		List<Region> list = (List<Region>) this.getHibernateTemplate().find(
				hql, "%" + q + "%", "%" + q + "%", "%" + q + "%",
				"%" + q + "%", "%" + q + "%");
		return list;
	}

}
