package com.tendyron.wifi.web.service.business.tax;

import com.tendyron.wifi.web.entity.business.tax.BusCategoryTypeEntity;
import com.tendyron.wifi.web.model.business.tax.BusCategoryTypeModel;
import com.tendyron.wifi.web.service.BaseService;
import com.tendyron.wifi.web.service.ServiceException;

import java.util.List;

/**
 * Created by Neo on 2017/5/10.
 */
public interface BusinessCategoryTypeService extends BaseService<BusCategoryTypeEntity> {
    List<BusCategoryTypeModel> list() throws ServiceException;
}
