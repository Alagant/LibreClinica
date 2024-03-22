package org.akaza.openclinica.bean.managestudy;

import org.akaza.openclinica.bean.core.EntityBean;

import java.util.Date;

public class IRBStudyActionHistoryBean extends EntityBean {
    private int irbStudyActionHistoryId;
    private int studyId;
    private int irbProtocolActionTypeId;
    private Date effectiveDate;
    private Date hrpoAction;
    private Date versionNumber;
    private Date versionDate;
    private Date submissionToCdcIrb;
    private Date cdcIrbApproval;
    private Date notificationSentToSites;

    public int getIrbStudyActionHistoryId() {
        return irbStudyActionHistoryId;
    }

    public void setIrbStudyActionHistoryId(int irbStudyActionHistoryId) {
        this.irbStudyActionHistoryId = irbStudyActionHistoryId;
    }

    public int getStudyId() {
        return studyId;
    }

    public void setStudyId(int studyId) {
        this.studyId = studyId;
    }

    public int getIrbProtocolActionTypeId() {
        return irbProtocolActionTypeId;
    }

    public void setIrbProtocolActionTypeId(int irbProtocolActionTypeId) {
        this.irbProtocolActionTypeId = irbProtocolActionTypeId;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getHrpoAction() {
        return hrpoAction;
    }

    public void setHrpoAction(Date hrpoAction) {
        this.hrpoAction = hrpoAction;
    }

    public Date getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Date versionNumber) {
        this.versionNumber = versionNumber;
    }

    public Date getVersionDate() {
        return versionDate;
    }

    public void setVersionDate(Date versionDate) {
        this.versionDate = versionDate;
    }

    public Date getSubmissionToCdcIrb() {
        return submissionToCdcIrb;
    }

    public void setSubmissionToCdcIrb(Date submissionToCdcIrb) {
        this.submissionToCdcIrb = submissionToCdcIrb;
    }

    public Date getCdcIrbApproval() {
        return cdcIrbApproval;
    }

    public void setCdcIrbApproval(Date cdcIrbApproval) {
        this.cdcIrbApproval = cdcIrbApproval;
    }

    public Date getNotificationSentToSites() {
        return notificationSentToSites;
    }

    public void setNotificationSentToSites(Date notificationSentToSites) {
        this.notificationSentToSites = notificationSentToSites;
    }
}
