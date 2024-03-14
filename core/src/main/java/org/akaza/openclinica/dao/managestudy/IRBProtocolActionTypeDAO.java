package org.akaza.openclinica.dao.managestudy;

import org.akaza.openclinica.bean.core.EntityBean;
import org.akaza.openclinica.bean.managestudy.IRBProtocolActionTypeBean;
import org.akaza.openclinica.bean.managestudy.ProtocolDeviationBean;
import org.akaza.openclinica.dao.core.AuditableEntityDAO;
import org.akaza.openclinica.exception.OpenClinicaException;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;

public class IRBProtocolActionTypeDAO extends AuditableEntityDAO<IRBProtocolActionTypeBean> {
    public IRBProtocolActionTypeDAO(DataSource ds) {
        super(ds);
        setDigesterName();
        setQueryNames();
    }

    private void setQueryNames() {
    }

    @Override
    public IRBProtocolActionTypeBean getEntityFromHashMap(HashMap<String, Object> hm) {
        return null;
    }

    @Override
    public ArrayList<IRBProtocolActionTypeBean> findAll(String strOrderByColumn, boolean blnAscendingSort, String strSearchPhrase) throws OpenClinicaException {
        return null;
    }

    @Override
    public ArrayList<IRBProtocolActionTypeBean> findAll() throws OpenClinicaException {
        return null;
    }

    @Override
    public EntityBean findByPK(int id) throws OpenClinicaException {
        return null;
    }

    @Override
    public IRBProtocolActionTypeBean create(IRBProtocolActionTypeBean eb) throws OpenClinicaException {
        return null;
    }

    @Override
    public IRBProtocolActionTypeBean update(IRBProtocolActionTypeBean eb) throws OpenClinicaException {
        return null;
    }

    @Override
    public ArrayList<IRBProtocolActionTypeBean> findAllByPermission(Object objCurrentUser, int intActionType, String strOrderByColumn, boolean blnAscendingSort, String strSearchPhrase) throws OpenClinicaException {
        return null;
    }

    @Override
    public ArrayList<IRBProtocolActionTypeBean> findAllByPermission(Object objCurrentUser, int intActionType) throws OpenClinicaException {
        return null;
    }

    @Override
    public void setTypesExpected() {

    }

    @Override
    protected void setDigesterName() {

    }

    @Override
    public IRBProtocolActionTypeBean emptyBean() {
        return null;
    }
}
