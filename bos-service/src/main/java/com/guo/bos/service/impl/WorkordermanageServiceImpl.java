package com.guo.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guo.bos.dao.IWorkordermanageDao;
import com.guo.bos.domain.Workordermanage;
import com.guo.bos.service.IWorkordermanageService;

@Service
@Transactional
public class WorkordermanageServiceImpl implements IWorkordermanageService {
	@Autowired
	private IWorkordermanageDao workordermanageDao;
	public void save(Workordermanage model) {
		workordermanageDao.save(model);
	}
}
