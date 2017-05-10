package com.tendyron.wifi.web.query.business.tax;

import com.tendyron.wifi.web.query.PagingQuery;

/**
 * Created by Neo on 2017/5/9.
 */
public class BusinessQuery extends PagingQuery {
    private String content;
    private String categoryId;
    private Boolean hasIssue;
    private String issueId;
    private String agencyId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getHasIssue() {
        return hasIssue;
    }

    public void setHasIssue(Boolean hasIssue) {
        this.hasIssue = hasIssue;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }
}
