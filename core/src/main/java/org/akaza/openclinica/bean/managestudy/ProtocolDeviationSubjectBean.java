package org.akaza.openclinica.bean.managestudy;

import org.akaza.openclinica.bean.core.EntityBean;

public class ProtocolDeviationSubjectBean extends EntityBean {
    private static final long serialVersionUID = -8498660903753888474L;
    private int protocolDeviationId;
    private int subjectId;

    public int getProtocolDeviationId() {
        return protocolDeviationId;
    }

    public void setProtocolDeviationId(int protocolDeviationId) {
        this.protocolDeviationId = protocolDeviationId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
}
