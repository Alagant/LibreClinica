package org.akaza.openclinica.bean.managestudy;

import org.akaza.openclinica.bean.core.EntityBean;

public class ProtocolDeviationBean extends EntityBean {
    private static final long serialVersionUID = -8498550403753118474L;
    private String protocolDeviationId;

    public String getProtocolDeviationId() {
        return protocolDeviationId;
    }

    public void setProtocolDeviationId(String protocolDeviationId) {
        this.protocolDeviationId = protocolDeviationId;
    }
}
