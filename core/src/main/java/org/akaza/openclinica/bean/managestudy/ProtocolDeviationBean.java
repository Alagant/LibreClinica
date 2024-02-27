package org.akaza.openclinica.bean.managestudy;

import org.akaza.openclinica.bean.core.EntityBean;

public class ProtocolDeviationBean extends EntityBean {
    private static final long serialVersionUID = -8498550403753118474L;
    private long protocolDeviationId;
    private int studyId;

    public long getProtocolDeviationId() {
        return protocolDeviationId;
    }

    public void setProtocolDeviationId(long protocolDeviationId) {
        this.protocolDeviationId = protocolDeviationId;
    }

    public int getStudyId() {
        return studyId;
    }

    public void setStudyId(int studyId) {
        this.studyId = studyId;
    }
}
