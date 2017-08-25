package com.guo.bos.service.impl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guo.bos.dao.IDecidedzoneDao;
import com.guo.bos.dao.ISubareaDao;
import com.guo.bos.domain.Decidedzone;
import com.guo.bos.domain.Subarea;
import com.guo.bos.service.IDecidedzoneService;
import com.guo.bos.utils.PageBean;


@Service
@Transactional
public class DecidedzoneServiceImpl implements IDecidedzoneService {
	@Autowired
	private IDecidedzoneDao decidedzoneDao;
	@Autowired
	private ISubareaDao subareaDao;
	
	/**
	 * 添加定区，同时关联分区
	 */
	public void save(Decidedzone model, String[] subareaid) {
		decidedzoneDao.save(model);
		for (String id : subareaid) {
			Subarea subarea = subareaDao.findById(id);
			//model.getSubareas().add(subarea);一方（定区）已经放弃维护外键权利，只能由多方（分区）负责维护
			subarea.setDecidedzone(model);//分区关联定区
		}
	}

	public void pageQuery(PageBean pageBean) {
		decidedzoneDao.pageQuery(pageBean);
	}

	/**
	 * 通过id删除定区
	 */
	public void deleteBatch(String ids) {
		//判断是否为空
				if(StringUtils.isNotBlank(ids)) {
					String[] staffIds = ids.split(",");
					//循环遍历
					for (String id : staffIds) {
						decidedzoneDao.deleteBatch("decidedzone.delete", id);
					}
				}
	}
}
