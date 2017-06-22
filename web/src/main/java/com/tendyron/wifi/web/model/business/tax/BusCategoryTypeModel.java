package com.tendyron.wifi.web.model.business.tax;

import com.tendyron.wifi.web.entity.BaseEntity;
import com.tendyron.wifi.web.entity.business.tax.BusCategoryEntity;
import com.tendyron.wifi.web.model.BaseModel;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by Neo on 2017/5/9.
 * 税务业务类型分组
 */

public class BusCategoryTypeModel extends BaseModel {
    private String name;
    private List<BusCategoryMode> categories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BusCategoryMode> getCategories() {
        return categories;
    }

    public void setCategories(List<BusCategoryMode> categories) {
        this.categories = categories;
    }
}
