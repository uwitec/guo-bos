package com.guo.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.guo.bos.domain.Region;
import com.guo.bos.domain.Subarea;
import com.guo.bos.service.ISubareaService;
import com.guo.bos.utils.FileUtils;
import com.guo.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea> {
	@Resource
	private ISubareaService subareaService;

	/**
	 * 添加分区
	 */
	public String add() {
		subareaService.save(model);
		return LIST;
	}

	/**
	 * 修改分区信息
	 */
	public String edit() {
		// 先查询数据库，根据ID查询原始数据
		Subarea subarea = subareaService.findById(model.getId());
		// 使用页面提交的数据进行覆盖
		/*
		 * private String addresskey; private String startnum; private String
		 * endnum; private String single; private String position;
		 */
		subarea.setAddresskey(model.getAddresskey());
		subarea.setStartnum(model.getStartnum());
		subarea.setStartnum(model.getEndnum());
		subarea.setSingle(model.getSingle());
		subarea.setPosition(model.getPosition());
		subarea.setRegion(model.getRegion());
		subareaService.update(subarea);
		return LIST;
	}

	// 属性驱动，接受页面提交的ids
	private String ids;

	/**
	 * 取派员批量删除
	 */
	public String deleteBatch() {
		subareaService.deleteBatch(getIds());
		return LIST;
	}

	// 属性驱动，接收上传文件
	private File subareaFile;

	/**
	 * 区域导入
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public String importXls() {
		// 创建一个集合
		List<Subarea> subareaList = new ArrayList<Subarea>();
		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook(new FileInputStream(subareaFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 根据sheet在第几页
		HSSFSheet hssfSheet = workbook.getSheetAt(0);
		for (Row row : hssfSheet) {
			// 跳过标题
			int rowNum = row.getRowNum();
			if (rowNum == 0) {
				continue;
			}

			String id = row.getCell(0).getStringCellValue();
			String decidedzone = row.getCell(1).getStringCellValue();
			String region = row.getCell(2).getStringCellValue();
			String addresskey = row.getCell(3).getStringCellValue();
			String startnum = row.getCell(4).getStringCellValue();
			String endnum = row.getCell(5).getStringCellValue();
			String single = row.getCell(6).getStringCellValue();
			String position = row.getCell(7).getStringCellValue();
			// 包装一个区域对象
			Subarea subarea = new Subarea();
			// Subarea subarea = new
			// Subarea(id,null,null,addresskey,startnum,endnum,single,position);
			subareaList.add(subarea);
		}
		// 批量保存
		subareaService.saveBatch1(subareaList);
		return NONE;
	}

	/**
	 * 分区数据导出
	 * @throws IOException 
	 */
	public String exportXls() throws IOException {
		// 第一步查询所有分区数据
		List<Subarea> list = subareaService.findAll();

		// 第二步：使用POI将数据写到Excel文件中
		// 相当于在内存中创建一个Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个标签页
		HSSFSheet sheet = workbook.createSheet("分区数据");
		// 创建标题
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("分区编号");
		headRow.createCell(1).setCellValue("关键字");
		headRow.createCell(2).setCellValue("起始号");
		headRow.createCell(3).setCellValue("结束号");
		headRow.createCell(5).setCellValue("位置信息");
		headRow.createCell(6).setCellValue("省市区");

		// 遍历循环集合
		for (Subarea subarea : list) {
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getAddresskey());
			dataRow.createCell(2).setCellValue(subarea.getStartnum());
			dataRow.createCell(3).setCellValue(subarea.getEndnum());
			dataRow.createCell(5).setCellValue(subarea.getPosition());
			dataRow.createCell(6).setCellValue(subarea.getRegion().getName());
		}

		// 第三步：使用输出流进行文件下载
		//首先得到一个输出流(一个流，两个头)
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
		
		String filename = "分区数据.xls";
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
	 */
	public String pageQuery() {
		// 调用查询条件
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		// 动态添加过滤条件
		String addresskey = model.getAddresskey();
		if (StringUtils.isNotBlank(addresskey)) {
			// 添加过滤条件，根据地址关键字模糊查询
			dc.add(Restrictions.like("addresskey", "%" + addresskey + "%"));
		}

		Region region = model.getRegion();
		if (region != null) {
			String province = region.getProvince();
			String city = region.getCity();
			String district = region.getDistrict();
			dc.createAlias("region", "r");
			if (StringUtils.isNotBlank(province)) {
				// 添加过滤条件，根据省份模糊查询-----多表关联查询，使用别名方式实现
				// 参数一：分区对象中关联的区域对象属性名称
				// 参数二：别名，可以任意
				dc.add(Restrictions.like("r.province", "%" + province + "%"));
			}
			if (StringUtils.isNotBlank(city)) {
				dc.add(Restrictions.like("r.city", "%" + city + "%"));
			}
			if (StringUtils.isNotBlank(district)) {
				dc.add(Restrictions.like("r.district", "%" + district + "%"));
			}
		}
		subareaService.pageQuery(pageBean);
		this.javaToJson(pageBean, new String[] { "currentPage",
				"detachedCriteria", "pageSize", "decidedzone", "subareas" });
		return NONE;

	}
	/**
	 * 查询所有未关联到定区的分区，返回json
	 * @return
	 */
	public String listajax() {
		List<Subarea> list = subareaService.findListNotAssciation();
		this.javaToJson(list, new String[]{"decidedzone","region"});
		return NONE;
	}
	
	//属性驱动，接收定区id
		private String decidedzoneId;
		
		/**
		 * 根据定区id查询关联的分区
		 */
		public String findListByDecidedzoneId(){
			List<Subarea> list = subareaService.findListByDecidedzoneId(getDecidedzoneId());
			this.javaToJson(list, new String[]{"decidedzone","subareas"});
			return NONE;
		}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public File getSubareaFile() {
		return subareaFile;
	}

	public void setSubareaFile(File subareaFile) {
		this.subareaFile = subareaFile;
	}

	public String getDecidedzoneId() {
		return decidedzoneId;
	}

	public void setDecidedzoneId(String decidedzoneId) {
		this.decidedzoneId = decidedzoneId;
	}

}
