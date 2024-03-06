package org.akaza.openclinica.dao.managestudy;

import org.akaza.openclinica.bean.core.EntityBean;
import org.akaza.openclinica.bean.managestudy.ProtocolDeviationBean;
import org.akaza.openclinica.bean.managestudy.StudyBean;
import org.akaza.openclinica.bean.submit.SubjectBean;
import org.akaza.openclinica.dao.core.AuditableEntityDAO;
import org.akaza.openclinica.dao.core.SQLFactory;
import org.akaza.openclinica.dao.core.TypeNames;
import org.akaza.openclinica.exception.OpenClinicaException;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ProtocolDeviationDAO extends AuditableEntityDAO<ProtocolDeviationBean> {
    public ProtocolDeviationDAO(DataSource ds) {
        super(ds);
        setDigesterName();
        setQueryNames();
    }

    protected void setQueryNames() {
        findByPKAndStudyName = "findProtocolDeviationByIdAndStudy";
        getCurrentPKName = "getCurrentProtocolDeviationPrimaryKey";
    }

    public Integer getCountWithFilter(ProtocolDeviationFilter filter, StudyBean currentStudy) {
        HashMap<Integer, Object> variables = variables(currentStudy.getId(), currentStudy.getId());
        String query = digester.getQuery("getCountWithStudy");
        query += filter.execute("");
        return getCountByQuery(query, variables);
    }
    @Override
    public ProtocolDeviationBean findByPKAndStudy(int id, StudyBean study) {
        return super.findByPKAndStudy(id, study);
    }

    @Override
    public ProtocolDeviationBean getEntityFromHashMap(HashMap<String, Object> hm) {
        ProtocolDeviationBean eb = new ProtocolDeviationBean();
        //super.setEntityAuditInformation(eb, hm);
        eb.setProtocolDeviationId((Integer) hm.get("protocol_deviation_id"));
        eb.setLabel((String) hm.get("label"));
        //eb.setDescription((String) hm.get("description"));
        //eb.setSeverityId((Integer) hm.get("protocol_deviation_severity_id"));
        //eb.setSeverityLabel((String) hm.get("protocol_deviation_severity_label"));
        eb.setStudyId((Integer) hm.get("study_id"));

        return eb;
    }



    @Override
    public ArrayList<ProtocolDeviationBean> findAll(String strOrderByColumn, boolean blnAscendingSort,
                                                    String strSearchPhrase) throws OpenClinicaException {
        return null;
    }

    @Override
    public ArrayList<ProtocolDeviationBean> findAll() throws OpenClinicaException {
        return null;
    }

    @Override
    public EntityBean findByPK(int id) throws OpenClinicaException {
        return null;
    }

    public ProtocolDeviationBean create(ProtocolDeviationBean pdb) {
        HashMap<Integer, Object> variables = new HashMap<>();
        HashMap<Integer, Integer> nullVars = new HashMap<>();

        LocalDateTime ldt = LocalDateTime.now();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String label = ldt.format(dtf);
        pdb.setLabel(label);

        variables.put(1, label);
        //variables.put(2, pdb.getDescription());
        //variables.put(3, pdb.getSeverityId());
        //variables.put(4, pdb.getStudyId());
        executeUpdateWithPK(digester.getQuery("createProtocolDeviation"), variables, nullVars);
        if (isQuerySuccessful()) {
            pdb.setProtocolDeviationId(getLatestPK());
        }
            return pdb;
    }

    @Override
    public ProtocolDeviationBean update(ProtocolDeviationBean eb) throws OpenClinicaException {
        return null;
    }

    @Override
    public ArrayList<ProtocolDeviationBean> findAllByPermission(Object objCurrentUser, int intActionType, String strOrderByColumn, boolean blnAscendingSort, String strSearchPhrase) throws OpenClinicaException {
        return null;
    }

    @Override
    public ArrayList<ProtocolDeviationBean> findAllByPermission(Object objCurrentUser, int intActionType) throws OpenClinicaException {
        return null;
    }

    @Override
    public void setTypesExpected() {
        this.unsetTypeExpected();
        this.setTypeExpected(1, TypeNames.INT);
        this.setTypeExpected(2, TypeNames.STRING);
        /*this.setTypeExpected(3, TypeNames.STRING);
        this.setTypeExpected(4, TypeNames.INT);
        this.setTypeExpected(5, TypeNames.STRING);
        this.setTypeExpected(6, TypeNames.INT);*/
    }

    public ArrayList<ProtocolDeviationBean> findByStudy(int studyId) {
        HashMap<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, studyId);

        return this.executeFindAllQuery("findAllProtocolDeviationsByStudyId", parameters);
    }

    @Override
    protected void setDigesterName() {
        digesterName = SQLFactory.getInstance().DAO_SUBJECT;
    }


    @Override
    public ProtocolDeviationBean emptyBean() {
        return new ProtocolDeviationBean();
    }

}
