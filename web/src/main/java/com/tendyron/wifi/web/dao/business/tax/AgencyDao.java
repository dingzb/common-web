package com.tendyron.wifi.web.dao.business.tax;

import com.tendyron.wifi.web.dao.BaseDao;
import com.tendyron.wifi.web.entity.business.tax.AgencyEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Neo on 2017/5/9.
 */

public interface AgencyDao extends BaseDao<AgencyEntity> {

    /**
     * 获取机构列表
     *
     * @param level
     * @return
     */
    List<AgencyEntity> getList(String level);
}
