package com.tendyron.wifi.web.model.business.tax;

import com.tendyron.wifi.web.model.BaseModel;

/**
 * Created by Neo on 2017/5/12.
 */
public class AgencyModel extends BaseModel {
    private String name;
    private Integer level; // 机构级别 0：顶级（目前县局）

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
