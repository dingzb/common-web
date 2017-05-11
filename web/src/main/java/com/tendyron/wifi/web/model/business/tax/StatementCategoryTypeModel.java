package com.tendyron.wifi.web.model.business.tax;

import java.util.List;

public class StatementCategoryTypeModel {
    private String name;
    private String id;
    private List<StatementCategoryModel> recs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<StatementCategoryModel> getRecs() {
        return recs;
    }

    public void setRecs(List<StatementCategoryModel> recs) {
        this.recs = recs;
    }
}