package com.sc.td.business.dao.scuppertick;

import java.util.List;
import com.sc.td.business.base.BaseDao;
import com.sc.td.business.entity.scuppertick.ScUpperTick;

public interface ScUpperTickDao extends BaseDao<ScUpperTick> {

	List<ScUpperTick> findByUpperTickCodeOrderByPriceFromAsc(String upperTickCode);
}
