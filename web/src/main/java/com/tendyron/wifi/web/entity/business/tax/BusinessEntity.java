package com.tendyron.wifi.web.entity.business.tax;

import com.tendyron.wifi.web.entity.BaseEntity;
import com.tendyron.wifi.web.entity.system.UserEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Neo on 2017/5/9.
 */
@Entity
@Table(name = "bus_tax_bus")
public class BusinessEntity extends BaseEntity {

    private String taxpayerCode;
    private String taxpayerName;
    private String content;
    private String description;

    private Boolean hasIssue;
    private BusIssueEntity issue;

    private BusCategoryEntity category;

    private AgencyEntity agency;

    private UserEntity create;
    private UserEntity check;
    private UserEntity finalCheck;

    private Date createTime;
    private Date modifyTime;

    @Column(name = "taxpayer_code", length = 50)
    public String getTaxpayerCode() {
        return taxpayerCode;
    }

    public void setTaxpayerCode(String taxpayerCode) {
        this.taxpayerCode = taxpayerCode;
    }

    @Column(name = "taxpayer_name", length = 50)
    public String getTaxpayerName() {
        return taxpayerName;
    }

    public void setTaxpayerName(String taxpayerName) {
        this.taxpayerName = taxpayerName;
    }

    @Column(name = "content", length = 4096)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "description", length = 4096)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    public BusCategoryEntity getCategory() {
        return category;
    }

    public void setCategory(BusCategoryEntity category) {
        this.category = category;
    }

    @Column(name = "has_issue")
    public Boolean getHasIssue() {
        return hasIssue;
    }

    public void setHasIssue(Boolean hasIssue) {
        this.hasIssue = hasIssue;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    public BusIssueEntity getIssue() {
        return issue;
    }

    public void setIssue(BusIssueEntity issue) {
        this.issue = issue;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_id")
    public AgencyEntity getAgency() {
        return agency;
    }

    public void setAgency(AgencyEntity agency) {
        this.agency = agency;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_user_id")
    public UserEntity getCreate() {
        return create;
    }

    public void setCreate(UserEntity create) {
        this.create = create;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "check_user_id")
    public UserEntity getCheck() {
        return check;
    }

    public void setCheck(UserEntity check) {
        this.check = check;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "final_user_id")
    public UserEntity getFinalCheck() {
        return finalCheck;
    }

    public void setFinalCheck(UserEntity finalCheck) {
        this.finalCheck = finalCheck;
    }

    @Column(name = "create_time", length = 19)
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "modify_time", length = 19)
    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
