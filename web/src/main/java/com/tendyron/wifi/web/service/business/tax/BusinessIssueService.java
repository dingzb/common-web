package com.tendyron.wifi.web.service.business.tax;

import com.tendyron.wifi.web.entity.business.tax.BusIssueEntity;
import com.tendyron.wifi.web.model.business.tax.BusIssueModel;
import com.tendyron.wifi.web.service.BaseService;
import com.tendyron.wifi.web.service.ServiceException;

import java.util.List;

/**
 * Created by Neo on 2017/5/11.
 */
public interface BusinessIssueService extends BaseService<BusIssueEntity> {
    List<BusIssueModel> list() throws ServiceException;
}
