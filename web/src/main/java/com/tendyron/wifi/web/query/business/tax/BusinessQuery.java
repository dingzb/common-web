package com.tendyron.wifi.web.query.business.tax;

import com.tendyron.wifi.web.query.PagingQuery;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by Neo on 2017/5/9.
 */
public class BusinessQuery extends PagingQuery {
    private String content;
    private String categoryId;
    private Boolean hasIssue;
    private String issueId;
    private String agencyId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date busTime;

    private String taxpayerCode;
    private String taxpayerName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTimeStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTimeEnd;

    private Integer status;
    private Integer[] includeStatus;

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

    public String getTaxpayerCode() {
        return taxpayerCode;
    }

    public void setTaxpayerCode(String taxpayerCode) {
        this.taxpayerCode = taxpayerCode;
    }

    public String getTaxpayerName() {
        return taxpayerName;
    }

    public void setTaxpayerName(String taxpayerName) {
        this.taxpayerName = taxpayerName;
    }

    public Date getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(Date createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public Date getBusTime() {
        return busTime;
    }

    public void setBusTime(Date busTime) {
        this.busTime = busTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer[] getIncludeStatus() {
        return includeStatus;
    }

    public void setIncludeStatus(Integer[] includeStatus) {
        this.includeStatus = includeStatus;
    }
}
