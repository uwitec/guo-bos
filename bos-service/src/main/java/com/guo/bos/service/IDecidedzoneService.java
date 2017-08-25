package com.guo.bos.service;

import com.guo.bos.domain.Decidedzone;
import com.guo.bos.utils.PageBean;

public interface IDecidedzoneService {

	public void save(Decidedzone model, String[] subareaid);

	public void pageQuery(PageBean pageBean);

	public void deleteBatch(String ids);
}