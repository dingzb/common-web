package com.tendyron.wifi.web.query.business.tax;

import com.tendyron.wifi.web.query.BaseQuery;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by Neo on 2017/5/26.
 */
public class StatisticsQuery extends BaseQuery {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startCreate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endCreate;

    private String categoryTypeIdsStr;
    private String categoryIdsStr;
    private String issueIdStr;

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

    public String getCategoryTypeIdsStr() {
        return categoryTypeIdsStr;
    }

    public void setCategoryTypeIdsStr(String categoryTypeIdsStr) {
        this.categoryTypeIdsStr = categoryTypeIdsStr;
    }

    public String getCategoryIdsStr() {
        return categoryIdsStr;
    }

    public void setCategoryIdsStr(String categoryIdsStr) {
        this.categoryIdsStr = categoryIdsStr;
    }

    public String getIssueIdStr() {
        return issueIdStr;
    }

    public void setIssueIdStr(String issueIdStr) {
        this.issueIdStr = issueIdStr;
    }
}
