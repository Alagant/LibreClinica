package org.akaza.openclinica.bean.managestudy;

import org.akaza.openclinica.bean.core.EntityBean;
import org.akaza.openclinica.domain.managestudy.ProtocolDeviationSubject;

import java.util.ArrayList;

public class ProtocolDeviationBean extends EntityBean {
    private static final long serialVersionUID = -8498550403753118474L;
    private int protocolDeviationId;
    private String label;
    private int studyId;
    private int severityId;
    private String severityLabel;
    private String description;


    private ArrayList<ProtocolDeviationSubjectBean> subjects = new ArrayList<>();

    public int getProtocolDeviationId() {
        return protocolDeviationId;
    }

    public void setProtocolDeviationId(int protocolDeviationId) {
        this.protocolDeviationId = protocolDeviationId;
    }

    public int getStudyId() {
        return studyId;
    }

    public void setStudyId(int studyId) {
        this.studyId = studyId;
    }

    public int getSeverityId() {
        return severityId;
    }

    public void setSeverityId(int severityId) {
        this.severityId = severityId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSeverityLabel() {
        return severityLabel;
    }

    public void setSeverityLabel(String severityLabel) {
        this.severityLabel = severityLabel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<ProtocolDeviationSubjectBean> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<ProtocolDeviationSubjectBean> subjects) {
        this.subjects = subjects;
    }
}
