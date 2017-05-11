package com.tendyron.wifi.web.model.business.tax;

import com.tendyron.wifi.web.model.BaseModel;

/**
 * Created by Neo on 2017/5/10.
 */
public class BusCategoryMode extends BaseModel{
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
