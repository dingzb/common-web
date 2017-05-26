package com.tendyron.wifi.web.service.business.tax;

import com.tendyron.wifi.web.entity.business.tax.AgencyEntity;
import com.tendyron.wifi.web.model.business.tax.AgencyModel;
import com.tendyron.wifi.web.model.system.UserModel;
import com.tendyron.wifi.web.query.business.tax.AgencyQuery;
import com.tendyron.wifi.web.service.BaseService;
import com.tendyron.wifi.web.service.ServiceException;

import java.util.List;

/**
 * Created by Neo on 2017/5/12.
 */
public interface AgencyService extends BaseService<AgencyEntity> {
    List<AgencyModel> list(String level) throws ServiceException;

    /**
     * 获取当前用户所在机构下所有基层用户
     *
     * @param query
     * @return
     * @throws ServiceException
     */
    List<UserModel> userList(AgencyQuery query) throws ServiceException;

    /**
     * 当前用户所处机构
     * @param query
     * @return
     * @throws ServiceException
     */
    AgencyModel current(AgencyQuery query) throws ServiceException;
}
