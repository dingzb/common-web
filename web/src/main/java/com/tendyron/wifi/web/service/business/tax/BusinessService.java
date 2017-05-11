package com.tendyron.wifi.web.service.business.tax;

import com.tendyron.wifi.web.entity.business.tax.BusinessEntity;
import com.tendyron.wifi.web.model.PagingModel;
import com.tendyron.wifi.web.model.business.tax.BusinessModel;
import com.tendyron.wifi.web.model.business.tax.StatementModel;
import com.tendyron.wifi.web.query.business.tax.BusinessQuery;
import com.tendyron.wifi.web.query.business.tax.StatementQuery;
import com.tendyron.wifi.web.service.BaseService;
import com.tendyron.wifi.web.service.ServiceException;

import java.util.List;

/**
 * Created by Neo on 2017/5/9.
 */
public interface BusinessService extends BaseService<BusinessEntity> {

    PagingModel paging(BusinessQuery query) throws ServiceException;

    void add(BusinessModel businessModel) throws ServiceException;

    void edit(BusinessModel business) throws ServiceException;

    Integer del(String[] ids) throws ServiceException;

    List<StatementModel> statement(StatementQuery query) throws ServiceException;
}
