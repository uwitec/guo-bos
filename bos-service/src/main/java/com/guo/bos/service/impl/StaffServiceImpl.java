package com.guo.bos.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guo.bos.dao.IStaffDao;
import com.guo.bos.domain.Staff;
import com.guo.bos.service.IStaffService;
import com.guo.bos.utils.PageBean;

@Service
@Transactional
public class StaffServiceImpl implements IStaffService {
	@Autowired
	private IStaffDao staffDao;

	/**
	 * 新增取派员
	 */
	public void save(Staff model) {
		staffDao.save(model);
	}

	public void pageQuery(PageBean pageBean) { // 注意参数的大小写。不然传不过去。！！！
		staffDao.pageQuery(pageBean);
	}

	/**
	 * 取派员批量删除 需要说明：是逻辑删除，将deltag改为1
	 */
	public void deleteBatch(String ids) {
		// 判断是否为空
		if (StringUtils.isNotBlank(ids)) {
			String[] staffIds = ids.split(",");
			// 循环遍历
			for (String id : staffIds) {
				staffDao.executeUpdate("staff.delete", id);
			}
		}

	}

	/**
	 * 根据Id查询取派员
	 */
	public Staff findById(String id) {
		return staffDao.findById(id);
	}

	/**
	 * 根据id修还取派员
	 */
	public void update(Staff staff) {
		staffDao.update(staff);
	}

	/**
	 * 批量还原
	 */
	public void restoreBatch(String ids) {
		// 判断是否为空
		if (StringUtils.isNotBlank(ids)) {
			String[] staffIds = ids.split(",");
			// 循环遍历
			for (String id : staffIds) {
				staffDao.executeUpdate("staff.restore", id);
			}
		}
	}

	/**
	 * 返回未被删除的取派员
	 */
	public List<Staff> findListNotDelete() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
		//添加过滤条件deltag=0的
		detachedCriteria.add(Restrictions.eq("deltag", "0"));
		//detachedCriteria.add(Restrictions.ne("deltag", "1"));
		return staffDao.findByCriteria(detachedCriteria );
	}
}
