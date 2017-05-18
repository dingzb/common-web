package com.tendyron.wifi.web.model.business.tax;

import java.util.Set;

/**
 * Created by Neo on 2017/5/18.
 */
public class ExamineModel {
    private Boolean hasIssue;
    private Set<BusIssueModel> issues;
    private String description;

    public Boolean getHasIssue() {
        return hasIssue;
    }

    public void setHasIssue(Boolean hasIssue) {
        this.hasIssue = hasIssue;
    }

    public Set<BusIssueModel> getIssues() {
        return issues;
    }

    public void setIssues(Set<BusIssueModel> issues) {
        this.issues = issues;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
