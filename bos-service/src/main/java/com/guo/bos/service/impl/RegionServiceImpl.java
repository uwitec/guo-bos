package com.guo.bos.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guo.bos.dao.IRegionDao;
import com.guo.bos.domain.Region;
import com.guo.bos.domain.Staff;
import com.guo.bos.service.IRegionService;
import com.guo.bos.utils.PageBean;

@Service
@Transactional
public class RegionServiceImpl implements IRegionService {
	@Autowired
	private IRegionDao regionDao;
	
	/**
	 * 新增区域
	 */
	public void save(Region model) {
		regionDao.save(model);
	}

	/**
	 * 区域数据批量保存
	 */
	public void saveBatch(List<Region> regionList) {
		// 集合先遍历
		for (Region region : regionList) {
			regionDao.saveOrUpdate(region);
		}
	}

	// 区域分页查询
	public void pageQuery(PageBean pageBean) {
		regionDao.pageQuery(pageBean);
	}

	/**
	 * 区域通过id
	 */
	public void deleteBatch(String ids) {
		//判断是否为空
				if(StringUtils.isNotBlank(ids)) {
					String[] staffIds = ids.split(",");
					//循环遍历
					for (String id : staffIds) {
						regionDao.deleteBatch("region.delete", id);
					}
				}
	}
	//通过id查询区域
	public Region findByid(String id) {
		return regionDao.findById(id);
	}

	/**
	 * 根据id修还取派员
	 */
	public void update(Region region) {
		regionDao.update(region);
	}

	/**
	 * 查询出所有的区域
	 */
	public List<Region> findAll() {
		return regionDao.findAll();
	}

	//根据页面输入进行模糊查询
	public List<Region> findListByQ(String q) {
		return regionDao.findLIstByQ(q);
	}

	
	

	
	
	
	

}
