package com.tendyron.wifi.web.dao.business.tax;

import com.tendyron.wifi.web.dao.BaseDaoImpl;
import com.tendyron.wifi.web.entity.business.tax.BusCategoryEntity;
import com.tendyron.wifi.web.query.BaseQuery;
import com.tendyron.wifi.web.query.business.tax.BusCategoryQuery;
import com.tendyron.wifi.web.utils.StringTools;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Neo on 2017/5/10.
 */

@Repository
public class BusinessCategoryDaoImpl extends BaseDaoImpl<BusCategoryEntity> implements BusinessCategoryDao {

    public BusinessCategoryDaoImpl() {
        super(BusCategoryEntity.class);
    }

    @Override
    protected StringBuilder getExtensionSql(BaseQuery query, Map<String, Object> params) {
        BusCategoryQuery bQuery = (BusCategoryQuery) query;

        StringBuilder hqlsb = new StringBuilder("from AgencyEntity {0} where 1=1");

        if (!StringTools.isEmpty(bQuery.getName())) {
            hqlsb.append(" and {0}.name = :name");
            params.put("name", bQuery.getName());
        }
        if (!StringTools.isEmpty(bQuery.getTypeId())) {
            hqlsb.append(" and {0}.type.id = :typeId");
            params.put("typeId", bQuery.getTypeId());
        }
        return hqlsb;
    }

    @Override
    public List<BusCategoryEntity> list(String typeId) {
        String hql = "from BusCategoryEntity bc";

        Map<String, Object> params = new HashMap<>();

        if (!StringTools.isEmpty(typeId)){
            hql += " where bc.type.id = :typeId";
            params.put("typeId", typeId);
        }

        return getByHql(hql, params);
    }
}
