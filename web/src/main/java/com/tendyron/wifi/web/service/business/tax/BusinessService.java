package com.tendyron.wifi.web.service.business.tax;

import com.tendyron.wifi.web.entity.business.tax.BusinessEntity;
import com.tendyron.wifi.web.model.PagingModel;
import com.tendyron.wifi.web.query.business.tax.BusinessQuery;
import com.tendyron.wifi.web.service.BaseService;
import com.tendyron.wifi.web.service.ServiceException;

/**
 * Created by Neo on 2017/5/9.
 */
public interface BusinessService extends BaseService<BusinessEntity> {

    PagingModel paging(BusinessQuery query) throws ServiceException;
}
