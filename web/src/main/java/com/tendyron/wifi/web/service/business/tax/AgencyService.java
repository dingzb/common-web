package com.tendyron.wifi.web.service.business.tax;

import com.tendyron.wifi.web.entity.business.tax.AgencyEntity;
import com.tendyron.wifi.web.model.business.tax.AgencyModel;
import com.tendyron.wifi.web.service.BaseService;

import javax.sql.rowset.serial.SerialException;
import java.util.List;

/**
 * Created by Neo on 2017/5/12.
 */
public interface AgencyService extends BaseService<AgencyEntity> {
    List<AgencyModel> list(String level) throws SerialException;
}
