package com.guo.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.guo.bos.domain.Decidedzone;
import com.guo.bos.service.IDecidedzoneService;
import com.guo.bos.web.action.base.BaseAction;
import com.guo.crm.Customer;
import com.guo.crm.ICustomerService;


/**
 * 定区管理
 *
 */
@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone>{
	//属性驱动，接收多个分区id
	private String[] subareaid;
	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}
	
	@Autowired
	private IDecidedzoneService decidedzoneService;
	@Autowired
	private ICustomerService proxy;
	
	
	/**
	 * 添加定区
	 */
	public String add(){
		
		decidedzoneService.save(model,subareaid);
		return LIST;
	}
	
	private String ids;
	
	/**
	 * 删除定区
	 */
	public String deleteBatch() {
		decidedzoneService.deleteBatch(getIds());
		return LIST;
	}
	
	/**
	 * 分页查询方法
	 */
	public String pageQuery() throws IOException{
		decidedzoneService.pageQuery(pageBean);
		this.javaToJson(pageBean, new String[]{"currentPage","detachedCriteria",
						"pageSize","subareas","decidedzones"});
		return NONE;
	}
	//查询未关联的客户端
	public String findListNotAssociation() {
		List<Customer> list2 = proxy.findListNotAssociation();
		this.javaToJson(list2, new String[]{});
		return NONE;
	}
		
	/**
	 * 远程调用crm服务,查询已经关联的客户
	 * @return
	 */
	public String findListHasAssociation() {
		String id = model.getId();
		List<Customer> list3 = proxy.findListHasAssociation(id);
		this.javaToJson(list3, new String[]{});
		return NONE;
	}
	
	//属性驱动，接收页面提交的多个客户id
	private List<Integer> customerIds;
			
			
	/**
	 * 远程调用crm服务，将客户关联到定区
	 */
	public String assigncustomerstodecidedzone(){
			proxy.assigncustomerstodecidedzone(model.getId(), customerIds);
			return LIST;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public List<Integer> getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(List<Integer> customerIds) {
		this.customerIds = customerIds;
	}
}
