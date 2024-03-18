package org.akaza.openclinica.bean.managestudy;

import org.akaza.openclinica.bean.core.EntityBean;

import java.util.Date;

public class IRBProtocolActionHistoryBean extends EntityBean {
    private static final long serialVersionUID = -5198523403410118479L;

    private int irbProtocolActionHistoryId;
    private int irbProtocolActionTypeId;
    private int irbSiteId;
    private String label;
    private Date versionDate;
    private int versionNumber;
    private Date siteSubmittedToLocalIrb;
    private Date localIrbApproval;
    private Date receivedDocsFromSites;
    private Date packageSentToCdcIrb;
    private Date cdcApproval;

    public int getIrbProtocolActionHistoryId() {
        return irbProtocolActionHistoryId;
    }

    public void setIrbProtocolActionHistoryId(int irbProtocolActionHistoryId) {
        this.irbProtocolActionHistoryId = irbProtocolActionHistoryId;
    }

    public int getIrbProtocolActionTypeId() {
        return irbProtocolActionTypeId;
    }

    public void setIrbProtocolActionTypeId(int irbProtocolActionTypeId) {
        this.irbProtocolActionTypeId = irbProtocolActionTypeId;
    }

<<<<<<< HEAD
    public int getIrbSiteId() {
        return irbSiteId;
    }

    public void setIrbSiteId(int irbSiteId) {
        this.irbSiteId = irbSiteId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

=======
>>>>>>> 667f8b70533dce751e251033a65be3abe3f9cc8c
    public Date getVersionDate() {
        return versionDate;
    }

    public void setVersionDate(Date versionDate) {
        this.versionDate = versionDate;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public Date getSiteSubmittedToLocalIrb() {
        return siteSubmittedToLocalIrb;
    }

    public void setSiteSubmittedToLocalIrb(Date siteSubmittedToLocalIrb) {
        this.siteSubmittedToLocalIrb = siteSubmittedToLocalIrb;
    }

    public Date getLocalIrbApproval() {
        return localIrbApproval;
    }

    public void setLocalIrbApproval(Date localIrbApproval) {
        this.localIrbApproval = localIrbApproval;
    }

    public Date getReceivedDocsFromSites() {
        return receivedDocsFromSites;
    }

    public void setReceivedDocsFromSites(Date receivedDocsFromSites) {
        this.receivedDocsFromSites = receivedDocsFromSites;
    }

    public Date getPackageSentToCdcIrb() {
        return packageSentToCdcIrb;
    }

    public void setPackageSentToCdcIrb(Date packageSentToCdcIrb) {
        this.packageSentToCdcIrb = packageSentToCdcIrb;
    }

    public Date getCdcApproval() {
        return cdcApproval;
    }

    public void setCdcApproval(Date cdcApproval) {
        this.cdcApproval = cdcApproval;
    }
}
