package com.tendyron.wifi.web.entity.business.tax;

import com.tendyron.wifi.web.entity.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Neo on 2017/5/9.
 */
@Entity
@Table(name = "bus_tax_bus_issue")
public class BusIssueEntity extends BaseEntity {

    private String name;
    private Set<BusinessEntity> business;

    @Column(name = "name", length = 50, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "issue")
    public Set<BusinessEntity> getBusiness() {
        return business;
    }

    public void setBusiness(Set<BusinessEntity> business) {
        this.business = business;
    }
}
