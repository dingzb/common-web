package com.tendyron.wifi.web.dao.system;

import java.util.List;

import com.tendyron.wifi.web.dao.BaseDao;
import com.tendyron.wifi.web.entity.system.BusinessLogEntity;
import com.tendyron.wifi.web.query.system.BusinessLogQuery;

public interface BusinessLogDao extends BaseDao<BusinessLogEntity> {
	/**
	 * 返回分页查询列表
	 * 
	 * @param query
	 * @return
	 */
	List<BusinessLogEntity> getPaging(BusinessLogQuery query);

	/**逻辑删除多个日志
	 * @param param
	 */
	int logicalDelete(String[] param);
}
