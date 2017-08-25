package com.guo.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.guo.bos.domain.Region;
import com.guo.bos.domain.Subarea;
import com.guo.bos.service.IRegionService;
import com.guo.bos.utils.FileUtils;
import com.guo.bos.utils.PageBean;
import com.guo.bos.utils.PinYin4jUtils;
import com.guo.bos.web.action.base.BaseAction;

/**
 * 区域管理
 * 
 * @author guo
 * 
 */
@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region> {
	// 属性驱动，接收上传文件
	private File regionFile;
	
	@Autowired
	private IRegionService regionService;
	
	/**
	 * 添加区域
	 */
	public String add() {
		regionService.save(model);
		return LIST;
	}


	public void setRegionFile(File regionFile) {
		this.regionFile = regionFile;
	}
	
	public String edit(){
		Region region = regionService.findByid(model.getId());
		region.setCity(model.getCity());
		region.setDistrict(model.getDistrict());
		region.setPostcode(model.getPostcode());
		region.setProvince(model.getProvince());
		region.setCitycode(model.getCitycode());
		region.setShortcode(model.getShortcode());
		regionService.update(region);
		return LIST;
	}
	// 属性驱动，接受页面提交的ids
		private String ids;

		public String getIds() {
			return ids;
		}

		public void setIds(String ids) {
			this.ids = ids;
		}


		/**
		 * 取派员批量删除
		 */
		public String deleteBatch() {
			regionService.deleteBatch(getIds());
			return LIST;
		}
		
	/**
	 * 区域导入
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public String importXls() throws FileNotFoundException, IOException {
		// 创建一个集合
		List<Region> regionList = new ArrayList<Region>();
		HSSFWorkbook workbook = new HSSFWorkbook(
				new FileInputStream(regionFile));
		// 根据sheet在第几页
		HSSFSheet hssfSheet = workbook.getSheetAt(0);
		for (Row row : hssfSheet) {
			// 跳过标题
			int rowNum = row.getRowNum();
			if (rowNum == 0) {
				continue;
			}
			String id = row.getCell(0).getStringCellValue();
			String province = row.getCell(1).getStringCellValue();
			String city = row.getCell(2).getStringCellValue();
			String district = row.getCell(3).getStringCellValue();
			String postcode = row.getCell(4).getStringCellValue();
			// 包装一个区域对象
			Region region = new Region(id, province, city, district, postcode,
					null, null, null);
			province = province.substring(0, province.length() - 1);
			city = city.substring(0, city.length() - 1);
			district = district.substring(0, district.length() - 1);

			String info = province + city + district;
			// 城市简码-北京朝阳BJCY
			String[] headByString = PinYin4jUtils.getHeadByString(info);
			String shortcode = StringUtils.join(headByString);
			// 城市编码 /beijingchaoyang
			String citycode = PinYin4jUtils.hanziToPinyin(city, "");

			region.setShortcode(shortcode);
			region.setCitycode(citycode);

			regionList.add(region);
		}
		// 批量保存
		regionService.saveBatch(regionList);
		return LIST;
	}
	
	/**
	 * 分区数据导出
	 * @throws IOException 
	 */
	public String exportXls() throws IOException {
		// 第一步查询所有分区数据
		List<Region> list = regionService.findAll();
		for (Region region : list) {
			
		}

		// 第二步：使用POI将数据写到Excel文件中
		// 相当于在内存中创建一个Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个标签页
		HSSFSheet sheet = workbook.createSheet("区域数据");
		// 创建标题
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("区域编号");
		headRow.createCell(1).setCellValue("省份");
		headRow.createCell(2).setCellValue("城市");
		headRow.createCell(3).setCellValue("区域");
		headRow.createCell(4).setCellValue("邮编");
		headRow.createCell(5).setCellValue("城市简码");
		headRow.createCell(6).setCellValue("城市编码");

		// 遍历循环集合
		
		for (Region region : list) {
			
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
			dataRow.createCell(0).setCellValue(region.getId());
			dataRow.createCell(1).setCellValue(region.getProvince());
			dataRow.createCell(2).setCellValue(region.getCity());
			dataRow.createCell(3).setCellValue(region.getDistrict());
			dataRow.createCell(4).setCellValue(region.getPostcode());
			dataRow.createCell(5).setCellValue(region.getShortcode());
			dataRow.createCell(6).setCellValue(region.getCitycode());
		}

		// 第三步：使用输出流进行文件下载
		//首先得到一个输出流(一个流，两个头)
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
		
		String filename = "区域数据.xls";
		String mimeType = ServletActionContext.getServletContext().getMimeType(filename);
		ServletActionContext.getResponse().setContentType(mimeType);
		String agent = ServletActionContext.getRequest().getHeader("User-agent");
		filename = FileUtils.encodeDownloadFilename(filename, agent);
		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename=" + filename);
		workbook.write(out);
		return NONE;
	}

	/**
	 * 分页查询
	 * @throws Exception 
	 */
	public String pageQuery() throws Exception{
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		// 动态添加过滤条件
		String province = model.getProvince();
		String city = model.getCity();
		String district = model.getDistrict();
		String shortcode = model.getShortcode();
		String citycode = model.getCitycode();
		if (StringUtils.isNotBlank(province)) {
			dc.add(Restrictions.like("province", "%" + province + "%"));
		}
		if (StringUtils.isNotBlank(city)) {
			dc.add(Restrictions.like("city", "%" + city + "%"));
		}
		if (StringUtils.isNotBlank(district)) {
			dc.add(Restrictions.like("district", "%" + district + "%"));
		}
		if (StringUtils.isNotBlank(shortcode)) {
			dc.add(Restrictions.like("shortcode", "%" + shortcode + "%"));
		}
		if (StringUtils.isNotBlank(citycode)) {
			dc.add(Restrictions.like("citycode", "%" + citycode + "%"));
		}
		
		regionService.pageQuery(pageBean);
		this.javaToJson(pageBean, 
					new String[]{"currentPage","detachedCriteria","pageSize","subareas"});
					  //将java对象转换为json的过程中，因为对象之间存在在互相引用，就会发生死循环
					//解决办法，第一种排除，第二种，关闭延迟加载问题。
		return NONE;
	}
		
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
	private String q;
	
	/**
	 * 查询所有区域，写回json
	 * @return
	 */
	public String listajax() {
		List<Region> list = null;  //regionService.findAll();
		if(StringUtils.isNotBlank(q)) {
			list = regionService.findListByQ(q);
		}else {
			list = regionService.findAll();
		}
		this.javaToJson(list, new String[]{"subareas"});
		return NONE;
	}
	public String getQ() {
		return q;
	}
	public void setQ(String q) {
		this.q = q;
	}

}
