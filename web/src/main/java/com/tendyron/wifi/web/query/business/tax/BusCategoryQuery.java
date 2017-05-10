package com.tendyron.wifi.web.query.business.tax;

import com.tendyron.wifi.web.query.BaseQuery;

/**
 * Created by Neo on 2017/5/9.
 */

public class BusCategoryQuery extends BaseQuery {
    private String name;
    private String typeId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
}
