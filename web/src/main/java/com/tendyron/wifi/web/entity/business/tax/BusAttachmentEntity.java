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
    private String uri;
    private Integer sort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id")
    public BusinessEntity getBusiness() {
        return business;
    }

    public void setBusiness(BusinessEntity business) {
        this.business = business;
    }

    @Column(name = "uri", length = 255)
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Column(name = "sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
