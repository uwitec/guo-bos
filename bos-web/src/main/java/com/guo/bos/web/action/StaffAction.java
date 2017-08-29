package com.guo.bos.web.action;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.guo.bos.domain.Staff;
import com.guo.bos.service.IStaffService;
import com.guo.bos.utils.PageBean;
import com.guo.bos.web.action.base.BaseAction;

/**
 * 取派员管理
 * 
 * @author guo
 * 
 */
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff> {
	@Autowired
	private IStaffService staffService;

	/**
	 * 添加取派员
	 */
	public String add() {
		staffService.save(model);
		return LIST;
	}

	/**
	 * 分页查询
	 */
	public String pageQuery() {
		staffService.pageQuery(pageBean);   
		/**
		 * 页面不需要加载时
		 * 解决办法：将关联对象排除
		 * 页面需要加载时：
		 * 将关联对象改为立即加载，
		 */
		this.javaToJson(pageBean, new String[]{"decidedzones","currentPage","detachedCriteria","pageSize"});
		return NONE;
		
		/********************对立面的代码重构，向BaseAction中提取********************
		 //属性驱动，接收页面提交的分页参数
		private int page;
		private int rows;
		PageBean pageBean = new PageBean();
		pageBean.setCurrentPage(page);
		pageBean.setPageSize(rows);
		//创建离线提交查询对象
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
		pageBean.setDetachedCriteria(detachedCriteria);
		staffService.pageQuery(pageBean);
		
		//使用json-lib将PageBean对象转为json，通过输出流写回页面中
		//JSONObject---将单一对象转为json
		//JSONArray----将数组或者集合对象转为json
		JsonConfig jsonConfig = new JsonConfig();
		//指定哪些属性不需要转json
		jsonConfig.setExcludes(new String[]{"currentPage","detachedCriteria","pageSize"});
		String json = JSONObject.fromObject(pageBean,jsonConfig).toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(json);
		===============================================================================*/
	}

	// 属性驱动，接受页面提交的ids
	private String ids;

	/**
	 * 取派员批量删除
	 */
	@RequiresPermissions("staff-delete")  //执行这个方法，需要当前用户具有这个权限
	public String deleteBatch() {
		staffService.deleteBatch(ids);
		return LIST;
	}
	/**
	 * 批量还原
	 */
	public String restoreBatch() {
		staffService.restoreBatch(ids);
		return LIST;
	}

	/**
	 * 修改取派员信息
	 */
	@RequiresPermissions("staff-edit")
	public String edit() {
		// 先查询数据库，根据ID查询原始数据
		Staff staff = staffService.findById(model.getId());
		// 使用页面提交的数据进行覆盖
		staff.setDeltag(model.getDeltag());
		staff.setName(model.getName());
		staff.setHaspda(model.getHaspda());
		staff.setTelephone(model.getTelephone());
		staff.setStation(model.getStation());
		staffService.update(staff);
		return LIST;
	}
	/**
	 * 查询未被删除的取派员，并JSon返回数据
	 * @return
	 */
	public String listajax() {
		List<Staff> list = staffService.findListNotDelete();
		this.javaToJson(list, new String[]{"decidedzones"});
		return NONE;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
}
