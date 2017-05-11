package com.tendyron.wifi.web.dao.business.tax;

import com.tendyron.wifi.web.dao.BaseDao;
import com.tendyron.wifi.web.entity.business.tax.BusCategoryEntity;

import java.util.List;

/**
 * Created by Neo on 2017/5/9.
 */

public interface BusinessCategoryDao extends BaseDao<BusCategoryEntity> {
    List<BusCategoryEntity> list(String typeId);
}
