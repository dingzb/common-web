package com.tendyron.wifi.web.dao.business.tax;

import com.tendyron.wifi.web.dao.BaseDaoImpl;
import com.tendyron.wifi.web.entity.business.tax.BusAttachmentEntity;
import com.tendyron.wifi.web.query.BaseQuery;
import com.tendyron.wifi.web.service.ServiceException;
import com.tendyron.wifi.web.service.business.tax.BusinessAttachmentService;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by Neo on 2017/6/1.
 */

@Repository
public class BusinessAttachmentDaoImpl extends BaseDaoImpl<BusAttachmentEntity> implements BusinessAttachmentDao {

    public BusinessAttachmentDaoImpl() {
        super(BusAttachmentEntity.class);
    }

    @Override
    protected StringBuilder getExtensionSql(BaseQuery query, Map<String, Object> params) {
        return null;
    }
}
