package com.tendyron.wifi.web.model.business.tax;

/**
 * Created by Neo on 2017/6/1.
 */
public class BusAttachmentModel {
    private String busId;
    private String url;
    private Integer sort;

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
