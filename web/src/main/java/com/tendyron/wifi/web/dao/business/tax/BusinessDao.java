package com.tendyron.wifi.web.dao.business.tax;

import com.tendyron.wifi.web.dao.BaseDao;
import com.tendyron.wifi.web.entity.business.tax.BusinessEntity;
import com.tendyron.wifi.web.query.business.tax.BusinessQuery;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Neo on 2017/5/9.
 */

public interface BusinessDao extends BaseDao<BusinessEntity> {

    List<BusinessEntity> paging(BusinessQuery query);
}
