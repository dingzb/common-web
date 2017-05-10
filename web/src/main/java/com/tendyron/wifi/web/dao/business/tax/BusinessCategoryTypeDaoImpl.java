package com.tendyron.wifi.web.dao.business.tax;

import com.tendyron.wifi.web.dao.BaseDaoImpl;
import com.tendyron.wifi.web.entity.business.tax.BusCategoryTypeEntity;
import com.tendyron.wifi.web.query.BaseQuery;
import com.tendyron.wifi.web.query.business.tax.BusCategoryTypeQuery;
import com.tendyron.wifi.web.utils.StringTools;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by Neo on 2017/5/10.
 */

@Repository
public class BusinessCategoryTypeDaoImpl extends BaseDaoImpl<BusCategoryTypeEntity> implements BusinessCategoryTypeDao {
    public BusinessCategoryTypeDaoImpl() {
        super(BusCategoryTypeEntity.class);
    }

    @Override
    protected StringBuilder getExtensionSql(BaseQuery query, Map<String, Object> params) {
        BusCategoryTypeQuery bQuery = (BusCategoryTypeQuery) query;

        StringBuilder hqlsb = new StringBuilder("from BusCategoryTypeEntity {0} where 1=1");

        if (!StringTools.isEmpty(bQuery.getName())) {
            hqlsb.append(" and {0}.name = :name");
            params.put("name", bQuery.getName());
        }
        return hqlsb;
    }
}
