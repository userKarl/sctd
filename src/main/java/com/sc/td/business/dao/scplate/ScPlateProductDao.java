package com.sc.td.business.dao.scplate;

import java.util.List;

import com.sc.td.business.base.BaseDao;
import com.sc.td.business.entity.scplate.ScPlateProduct;

public interface ScPlateProductDao extends BaseDao<ScPlateProduct> {

	List<ScPlateProduct> findById(String id);
}
