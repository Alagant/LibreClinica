package org.akaza.openclinica.bean.managestudy;

import org.akaza.openclinica.bean.core.EntityBean;

public class IRBProtocolActionTypeBean extends EntityBean {
    private static final long serialVersionUID = -8498550403423118479L;

    public int getIrbProtocolDeviationActionTypeId() {
        return irbProtocolDeviationActionTypeId;
    }

    public void setIrbProtocolDeviationActionTypeId(int irbProtocolDeviationActionTypeId) {
        this.irbProtocolDeviationActionTypeId = irbProtocolDeviationActionTypeId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    private int irbProtocolDeviationActionTypeId;
    private String label;
}
