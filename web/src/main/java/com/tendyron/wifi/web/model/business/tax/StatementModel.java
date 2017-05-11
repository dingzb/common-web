package com.tendyron.wifi.web.model.business.tax;

import java.util.List;
import java.util.Set;

/**
 * Created by Neo on 2017/5/11.
 */
public class StatementModel {

    private String agencyName;
    private String agencyId;
    private List<StatementCategoryTypeModel> recs;

    private int detailCount;

    public int getDetailCount() {
        return detailCount;
    }

    public void setDetailCount(int detailCount) {
        this.detailCount = detailCount;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public List<StatementCategoryTypeModel> getRecs() {
        return recs;
    }

    public void setRecs(List<StatementCategoryTypeModel> recs) {
        this.recs = recs;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }
}



