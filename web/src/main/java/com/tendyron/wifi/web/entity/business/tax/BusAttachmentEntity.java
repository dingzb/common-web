package com.tendyron.wifi.web.entity.business.tax;

import com.tendyron.wifi.web.entity.BaseEntity;

import javax.persistence.*;

/**
 * Created by Neo on 2017/5/31.
 */
@Entity
@Table(name = "bus_tax_bus_attachment")
public class BusAttachmentEntity extends BaseEntity {
    private BusinessEntity business;
    private String url;
    private Integer sort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id")
    public BusinessEntity getBusiness() {
        return business;
    }

    public void setBusiness(BusinessEntity business) {
        this.business = business;
    }

    @Column(name = "url", length = 255)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
