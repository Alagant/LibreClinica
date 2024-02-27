package org.akaza.openclinica.dao.managestudy;

import org.akaza.openclinica.bean.core.EntityBean;
import org.akaza.openclinica.bean.managestudy.DiscrepancyNoteBean;
import org.akaza.openclinica.bean.managestudy.ProtocolDeviationBean;
import org.akaza.openclinica.bean.managestudy.StudyBean;
import org.akaza.openclinica.dao.core.AuditableEntityDAO;
import org.akaza.openclinica.dao.core.DAODigester;
import org.akaza.openclinica.dao.core.SQLFactory;
import org.akaza.openclinica.exception.OpenClinicaException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class ProtocolDeviationDAO extends AuditableEntityDAO<ProtocolDeviationBean> {
    public ProtocolDeviationDAO(DataSource ds) {
        super(ds);
        setDigesterName();
    }

    public ProtocolDeviationDAO(DataSource ds, DAODigester digester) {
        super(ds);
        this.digester = digester;
    }

    @Override
    public ProtocolDeviationBean findByPKAndStudy(int id, StudyBean study) {
        return super.findByPKAndStudy(id, study);
    }

    @Override
    public ProtocolDeviationBean getEntityFromHashMap(HashMap<String, Object> hm) {
        return null;
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
        return null;
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
