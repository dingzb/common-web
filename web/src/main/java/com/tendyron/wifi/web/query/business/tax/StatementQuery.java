package com.tendyron.wifi.web.query.business.tax;

import com.tendyron.wifi.web.utils.StringTools;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by Neo on 2017/5/11.
 */
public class StatementQuery {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startCreate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endCreate;

    private String agencyIdsStr; //xx,xx,xx
//    private String categoryTypeIdsStr;
//    private String categoryIdsStr;
//    private String issueIdStr;


    public Date getStartCreate() {
        return startCreate;
    }

    public void setStartCreate(Date startCreate) {
        this.startCreate = startCreate;
    }

    public Date getEndCreate() {
        return endCreate;
    }

    public void setEndCreate(Date endCreate) {
        this.endCreate = endCreate;
    }

    public String[] getAgencyIds() {
        return StringTools.isEmpty(agencyIdsStr) ? new String[0] : agencyIdsStr.split(",");
    }

    public String getAgencyIdsStr() {
        return agencyIdsStr;
    }

    public void setAgencyIdsStr(String agencyIdsStr) {
        this.agencyIdsStr = agencyIdsStr;
    }
}
