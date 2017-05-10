package com.tendyron.wifi.web.dao.business.tax;

import com.tendyron.wifi.web.dao.BaseDaoImpl;
import com.tendyron.wifi.web.entity.business.tax.AgencyEntity;
import com.tendyron.wifi.web.query.BaseQuery;
import com.tendyron.wifi.web.query.business.tax.AgencyQuery;
import com.tendyron.wifi.web.utils.StringTools;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by Neo on 2017/5/10.
 */
@Repository
public class AgencyDaoImpl extends BaseDaoImpl<AgencyEntity> implements AgencyDao {
    public AgencyDaoImpl() {
        super(AgencyEntity.class);
    }

    @Override
    protected StringBuilder getExtensionSql(BaseQuery query, Map<String, Object> params) {
        AgencyQuery aQuery = (AgencyQuery) query;

        StringBuilder hqlsb = new StringBuilder("from AgencyEntity {0} where 1=1");

        if (!StringTools.isEmpty(aQuery.getName())) {
            hqlsb.append(" and {0}.name = :name");
            params.put("name", aQuery.getName());
        }
        if (!StringTools.isEmpty(aQuery.getParentId())) {
            hqlsb.append(" and {0}.parent.id = :parentId");
            params.put("parentId", aQuery.getParentId());
        }
        return hqlsb;
    }
}
