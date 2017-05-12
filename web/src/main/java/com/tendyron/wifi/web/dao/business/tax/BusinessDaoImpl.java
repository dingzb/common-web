package com.tendyron.wifi.web.dao.business.tax;

import com.tendyron.wifi.web.dao.BaseDaoImpl;
import com.tendyron.wifi.web.entity.business.tax.BusinessEntity;
import com.tendyron.wifi.web.query.BaseQuery;
import com.tendyron.wifi.web.query.business.tax.BusinessQuery;
import com.tendyron.wifi.web.query.business.tax.StatementQuery;
import com.tendyron.wifi.web.utils.StringTools;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Neo on 2017/5/9.
 */
@Repository
public class BusinessDaoImpl extends BaseDaoImpl<BusinessEntity> implements BusinessDao {
    public BusinessDaoImpl() {
        super(BusinessEntity.class);
    }

    @Override
    protected StringBuilder getExtensionSql(BaseQuery query, Map<String, Object> params) {

        BusinessQuery bQuery = (BusinessQuery) query;

        StringBuilder hqlsb = new StringBuilder("from BusinessEntity {0} where 1=1");

        if (!StringTools.isEmpty(bQuery.getTaxpayerName())) {
            hqlsb.append(" and {0}.taxpayerName like :taxpayerName");
            params.put("taxpayerName", "%" + bQuery.getTaxpayerName() + "%");
        }
        if (!StringTools.isEmpty(bQuery.getAgencyId())) {
            hqlsb.append(" and {0}.agency.id = :agencyId");
            params.put("agencyId", bQuery.getAgencyId());
        }
        if (bQuery.getHasIssue() != null) {
            hqlsb.append(" and {0}.hasIssue = :hasIssue");
            params.put("hasIssue", bQuery.getHasIssue());
        }

        if (!StringTools.isEmpty(bQuery.getIssueId())) {
            hqlsb.append(" and {0}.issue.id = :issueId");
            params.put("issueId", bQuery.getIssueId());
        }

        if (bQuery.getCreateTimeStart() != null) {
            hqlsb.append(" and {0}.createTime >= :createTimeStart");
            params.put("createTimeStart", bQuery.getCreateTimeStart());
        }
        if (bQuery.getCreateTimeEnd() != null) {
            hqlsb.append(" and {0}.createTime <= :createTimeEnd");
            params.put("createTimeEnd", bQuery.getCreateTimeEnd());
        }

        return hqlsb;
    }

    @Override
    public List<BusinessEntity> paging(BusinessQuery query) {
        Map<String, Object> params = new HashMap<>();
        String hql = getHql(query, "business", params) + "order by business.createTime desc";
        return getByHqlPaging(hql, params, query.getPage(), query.getSize());
    }
}
