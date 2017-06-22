package com.tendyron.wifi.web.service.business.tax;

import com.tendyron.wifi.web.entity.business.tax.BusCategoryEntity;
import com.tendyron.wifi.web.model.business.tax.BusCategoryMode;
import com.tendyron.wifi.web.model.business.tax.BusCategoryTypeModel;
import com.tendyron.wifi.web.service.BaseService;
import com.tendyron.wifi.web.service.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * Created by Neo on 2017/5/10.
 */
public interface BusinessCategoryService extends BaseService<BusCategoryEntity> {
    List<BusCategoryMode> list(String typeId) throws ServiceException;
}
