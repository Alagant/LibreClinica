package org.akaza.openclinica.dao.managestudy;

import org.akaza.openclinica.bean.core.EntityBean;
import org.akaza.openclinica.bean.managestudy.ProtocolDeviationSubjectBean;
import org.akaza.openclinica.dao.core.AuditableEntityDAO;
import org.akaza.openclinica.dao.core.SQLFactory;
import org.akaza.openclinica.exception.OpenClinicaException;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;

public class ProtocolDeviationSubjectDAO extends AuditableEntityDAO<ProtocolDeviationSubjectBean> {
    public ProtocolDeviationSubjectDAO(DataSource ds) {
        super(ds);
        setDigesterName();
    }

    protected void setQueryNames() {
        getCurrentPKName = "getCurrentProtocolDeviationSubjectPrimaryKey";
    }

    @Override
    public ProtocolDeviationSubjectBean emptyBean() {
        return null;
    }


    public ProtocolDeviationSubjectBean findSubjectById(int protocolDeviationId, int subjectId) {
        HashMap<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, protocolDeviationId);
        parameters.put(2, subjectId);

        return this.executeFindByPKQuery("findProtocolDeviationSubjectById", parameters);
    }


    @Override
    protected void setDigesterName() {
        digesterName = SQLFactory.getInstance().DAO_SUBJECT;
    }

    @Override
    public ProtocolDeviationSubjectBean getEntityFromHashMap(HashMap<String, Object> hm) {
        return null;
    }

    @Override
    public ArrayList<ProtocolDeviationSubjectBean> findAll(String strOrderByColumn, boolean blnAscendingSort, String strSearchPhrase) throws OpenClinicaException {
        return null;
    }

    @Override
    public ArrayList<ProtocolDeviationSubjectBean> findAll() throws OpenClinicaException {
        return null;
    }

    @Override
    public EntityBean findByPK(int id) throws OpenClinicaException {
        return null;
    }

    @Override
    public ProtocolDeviationSubjectBean create(ProtocolDeviationSubjectBean pdb) {
        HashMap<Integer, Object> variables = new HashMap<>();
        HashMap<Integer, Integer> nullVars = new HashMap<>();


        variables.put(1, pdb.getProtocolDeviationId());
        variables.put(2, pdb.getSubjectId());
        executeUpdateWithPK(digester.getQuery("createProtocolDeviationSubject"), variables, nullVars);
        if (isQuerySuccessful()) {
            pdb.setId(getLatestPK());
        }
        return pdb;
    }

    @Override
    public ProtocolDeviationSubjectBean update(ProtocolDeviationSubjectBean eb) throws OpenClinicaException {
        return null;
    }

    @Override
    public ArrayList<ProtocolDeviationSubjectBean> findAllByPermission(Object objCurrentUser, int intActionType, String strOrderByColumn, boolean blnAscendingSort, String strSearchPhrase) throws OpenClinicaException {
        return null;
    }

    @Override
    public ArrayList<ProtocolDeviationSubjectBean> findAllByPermission(Object objCurrentUser, int intActionType) throws OpenClinicaException {
        return null;
    }

    @Override
    public void setTypesExpected() {

    }
}
