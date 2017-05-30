package com.tendyron.wifi.web.dao.business.tax;

import com.tendyron.wifi.web.dao.BaseDaoImpl;
import com.tendyron.wifi.web.entity.business.tax.BusinessEntity;
import com.tendyron.wifi.web.query.BaseQuery;
import com.tendyron.wifi.web.query.business.tax.BusinessQuery;
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
        StringBuilder hqlsb = new StringBuilder();

        hqlsb.append(" from BusinessEntity {0}");

        if (bQuery.getHasIssue() != null) {
            hqlsb.append(" left join {0}.firstExamine first left join {0}.secondExamine second left join {0}.thirdExamine third ");
        }

        hqlsb.append(" where 1=1");

        if (bQuery.getHasIssue() != null) {
            hqlsb.append(" and (first.hasIssue = true or second.hasIssue = true or third.hasIssue = true)");
        }

        if (bQuery.getHasIssue() == null && bQuery.getFirstHasIssue() != null){
            hqlsb.append(" and {0}.firstExamine.hasIssue = true");
        }
        if (bQuery.getHasIssue() == null && bQuery.getSecondHasIssue() != null){
            hqlsb.append(" and {0}.secondExamine.hasIssue = true");
        }
        if (bQuery.getHasIssue() == null && bQuery.getThirdHasIssue() != null){
            hqlsb.append(" and {0}.thirdExamine.hasIssue = true");
        }

        if (!StringTools.isEmpty(bQuery.getTaxpayerName())) {
            hqlsb.append(" and {0}.taxpayerName like :taxpayerName");
            params.put("taxpayerName", "%" + bQuery.getTaxpayerName() + "%");
        }
        if (!StringTools.isEmpty(bQuery.getAgencyId())) {
            hqlsb.append(" and {0}.agency.id = :agencyId");
            params.put("agencyId", bQuery.getAgencyId());
        }
        if (!StringTools.isEmpty(bQuery.getCategoryId())){
            hqlsb.append(" and {0}.category.id = :categoryId");
            params.put("categoryId", bQuery.getCategoryId());
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

        if (bQuery.getBusTimeStart() != null) {
            hqlsb.append(" and {0}.busTime >= :busTimeStart");
            params.put("busTimeStart", bQuery.getBusTimeStart());
        }
        if (bQuery.getBusTimeEnd() != null) {
            hqlsb.append(" and {0}.busTime <= :busTimeEnd");
            params.put("busTimeEnd", bQuery.getBusTimeEnd());
        }

        if (bQuery.getStatus() != null){
            hqlsb.append(" and {0}.status = :status");
            params.put("status", bQuery.getStatus());
        }
        if(bQuery.getIncludeStatus() != null){
            hqlsb.append(" and {0}.status in (:statuses)");
            params.put("statuses", bQuery.getIncludeStatus());
        }
        if (!StringTools.isEmpty(bQuery.getCreateUserId())){
            hqlsb.append(" and {0}.create.id = :create");
            params.put("create", bQuery.getCreateUserId());
        }
        if (bQuery.getCreateUserIds() != null) {
            hqlsb.append(" and {0}.create.id in (:creates)");
            params.put("creates", bQuery.getCreateUserIds());
        }

        return hqlsb;
    }

    @Override
    public List<BusinessEntity> paging(BusinessQuery query) {
        Map<String, Object> params = new HashMap<>();
        String hql = getHql(query, "business", params) + " order by business.busTime desc";
        return getByHqlPaging(hql, params, query.getPage(), query.getSize());
    }

    @Override
    public List<BusinessEntity> pagingError(BusinessQuery query) {
        Map<String, Object> params = new HashMap<>();
        String hql = "select new BusinessEntity(business.id,business.taxpayerCode,business.taxpayerName,business.description,business.busTime,first.hasIssue,second.hasIssue,third.hasIssue,business.amendment,business.category,business.agency,business.create,business.createTime,business.modifyTime,business.status)"
                + getHql(query, "business", params) + " order by business.busTime desc";
        return getByHqlPaging(hql, params, query.getPage(), query.getSize());
    }
}
