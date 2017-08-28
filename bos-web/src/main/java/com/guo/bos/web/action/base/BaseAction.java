package com.guo.bos.web.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.guo.bos.utils.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 表现层通用实现
 * @author zhaoqx
 *
 * @param <T>
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	/**
	 * 分页方法抽取
	 */
	protected PageBean pageBean = new PageBean();
	
	DetachedCriteria detachedCriteria = null;	
	public void setPage(int page) {
		pageBean.setCurrentPage(page);
	}

	public void setRows(int rows) {
		pageBean.setPageSize(rows);
	}
	/**
	 * 将指定Java转化为Json，并响应到客户端页面
	 * @param o
	 * @param excludes
	 */
	public void javaToJson(Object o,String[] excludes) {
		JsonConfig jsonConfig = new JsonConfig();
		//指定哪些属性不需要转json
		jsonConfig.setExcludes(excludes);
		String json = JSONObject.fromObject(o,jsonConfig).toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 将指定Java对象转为json，并响应到客户端页面
	 * @param o
	 * @param exclueds
	 */
	public void javaToJson(List o ,String[] exclueds){
		JsonConfig jsonConfig = new JsonConfig();
		//指定哪些属性不需要转json
		jsonConfig.setExcludes(exclueds);
		String json = JSONArray.fromObject(o,jsonConfig).toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static final String HOME = "home";
	public static final String LIST = "list";
	
	//模型对象
	protected T model;
	public T getModel() {
		return model;
	}
	
	//在构造方法中动态获取实体类型，通过反射创建model对象
	public BaseAction() {
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		//获得BaseAction上声明的泛型数组
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		Class<T> entityClass = (Class<T>) actualTypeArguments[0];
		//我勒个去，还可以这样做？？？
		detachedCriteria = DetachedCriteria.forClass(entityClass);
		pageBean.setDetachedCriteria(detachedCriteria);
		//通过反射创建对象
		try {
			model = entityClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
