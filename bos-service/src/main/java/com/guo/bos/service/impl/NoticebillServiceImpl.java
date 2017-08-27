package com.guo.bos.service.impl;


import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guo.bos.dao.IDecidedzoneDao;
import com.guo.bos.dao.INoticebillDao;
import com.guo.bos.dao.IWorkbillDao;
import com.guo.bos.domain.Decidedzone;
import com.guo.bos.domain.Noticebill;
import com.guo.bos.domain.Staff;
import com.guo.bos.domain.User;
import com.guo.bos.domain.Workbill;
import com.guo.bos.service.INoticebillService;
import com.guo.bos.utils.BOSUtils;
import com.guo.crm.ICustomerService;
@Service
@Transactional
public class NoticebillServiceImpl implements INoticebillService {
	
	@Autowired
	private INoticebillDao noticebillDao;
	@Autowired
	private IDecidedzoneDao decidedzoneDao; 
	@Autowired
	private IWorkbillDao workbillDao;
	@Autowired
	private ICustomerService customerService;
	
	/**
	 * 保存业务通知单，尝试自动分单
	 */
	public void save(Noticebill model) {
		//获得登录的用户
		User user = BOSUtils.getLoginUser();
		model.setUser(user);  //设置当前登录用户
		noticebillDao.save(model);
		
		//远程调用CRM服务根据地址查询区域ID
		String address = model.getPickaddress();
		String decidedzoneId = customerService.findDecidedzoneIdByAddress(address);
		if(decidedzoneId != null){
			//查询到了定区id，可以完成自动分单
			Decidedzone decidedzone = decidedzoneDao.findById(decidedzoneId);
			Staff staff = decidedzone.getStaff();
			model.setStaff(staff);//业务通知单关联取派员对象
			//设置分单类型为：自动分单
			model.setOrdertype(Noticebill.ORDERTYPE_AUTO);
			//为取派员产生一个工单
			Workbill workbill = new Workbill();
			workbill.setAttachbilltimes(0);//追单次数
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//创建时间，当前系统时间
			workbill.setNoticebill(model);//工单关联页面通知单
			workbill.setPickstate(Workbill.PICKSTATE_NO);//取件状态
			workbill.setRemark(model.getRemark());//备注信息
			workbill.setStaff(staff);//工单关联取派员
			workbill.setType(Workbill.TYPE_1);//工单类型
			workbillDao.save(workbill);
			//调用短信平台，发送短信
		}else{
			//没有查询到定区id，不能完成自动分单
			model.setOrdertype(Noticebill.ORDERTYPE_MAN);
		}
	}

}
