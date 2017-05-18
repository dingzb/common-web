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
//    private String content;
    private String description;
    private Date busTime;   //业务发生时间

    private ExamineEntity firstExamine;
    private ExamineEntity secondExamine;
    private ExamineEntity thirdExamine;

    private Boolean amendment; //是否已经整改

    private BusCategoryEntity category;

    private AgencyEntity agency;

    private UserEntity create;
    private UserEntity check;
    private UserEntity finalCheck;

    private Date createTime;
    private Date modifyTime;

    private Integer status; // 0: 创建, 1: 提交, 2: 自查, 3: 审查, 4: 核查, 5: 整改

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

//    @Column(name = "content", length = 4096)
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }

    @Column(name = "description", length = 4096)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "bus_time", length = 19)
    public Date getBusTime() {
        return busTime;
    }

    public void setBusTime(Date busTime) {
        this.busTime = busTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    public BusCategoryEntity getCategory() {
        return category;
    }

    public void setCategory(BusCategoryEntity category) {
        this.category = category;
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

    @OneToOne
    @JoinColumn(name = "examine_first_id")
    public ExamineEntity getFirstExamine() {
        return firstExamine;
    }

    public void setFirstExamine(ExamineEntity firstExamine) {
        this.firstExamine = firstExamine;
    }

    @OneToOne
    @JoinColumn(name = "examine_second_id")
    public ExamineEntity getSecondExamine() {
        return secondExamine;
    }

    public void setSecondExamine(ExamineEntity secondExamine) {
        this.secondExamine = secondExamine;
    }

    @OneToOne
    @JoinColumn(name = "examine_third_id")
    public ExamineEntity getThirdExamine() {
        return thirdExamine;
    }

    public void setThirdExamine(ExamineEntity thirdExamine) {
        this.thirdExamine = thirdExamine;
    }

    @Column(name = "amendment")
    public Boolean getAmendment() {
        return amendment;
    }

    public void setAmendment(Boolean amendment) {
        this.amendment = amendment;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
