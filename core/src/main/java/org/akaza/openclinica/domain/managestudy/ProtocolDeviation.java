package org.akaza.openclinica.domain.managestudy;

import org.akaza.openclinica.domain.AbstractAuditableMutableDomainObject;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "protocol_deviation")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence_name", value = "protocol_deviation_id_seq") })
public class ProtocolDeviation extends AbstractAuditableMutableDomainObject {
    private static final long serialVersionUID = -3852612749282796891L;
    private long protocolDeviationId;

    public long getProtocolDeviationId() {
        return protocolDeviationId;
    }

    public void setProtocolDeviationId(long protocolDeviationId) {
        this.protocolDeviationId = protocolDeviationId;
    }
}
