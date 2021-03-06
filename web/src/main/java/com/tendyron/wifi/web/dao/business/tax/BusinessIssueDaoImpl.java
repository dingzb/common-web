package com.tendyron.wifi.web.dao.business.tax;

import com.tendyron.wifi.web.dao.BaseDaoImpl;
import com.tendyron.wifi.web.entity.business.tax.BusIssueEntity;
import com.tendyron.wifi.web.query.BaseQuery;
import com.tendyron.wifi.web.query.business.tax.BusIssueQuery;
import com.tendyron.wifi.web.utils.StringTools;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by Neo on 2017/5/10.
 */
@Repository
public class BusinessIssueDaoImpl extends BaseDaoImpl<BusIssueEntity> implements BusinessIssueDao {
    public BusinessIssueDaoImpl() {
        super(BusIssueEntity.class);
    }

    @Override
    protected StringBuilder getExtensionSql(BaseQuery query, Map<String, Object> params) {
        BusIssueQuery bQuery = (BusIssueQuery) query;

        StringBuilder hqlsb = new StringBuilder("from BusIssueEntity {0} where 1=1");

        if (!StringTools.isEmpty(bQuery.getName())) {
            hqlsb.append(" and {0}.name = :name");
            params.put("name", bQuery.getName());
        }
        return hqlsb;
    }
}
