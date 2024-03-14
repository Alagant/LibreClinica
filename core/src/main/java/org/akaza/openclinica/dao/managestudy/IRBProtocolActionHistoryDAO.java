package org.akaza.openclinica.dao.managestudy;

import org.akaza.openclinica.bean.core.EntityBean;
import org.akaza.openclinica.bean.managestudy.IRBProtocolActionHistoryBean;
import org.akaza.openclinica.bean.managestudy.IRBProtocolActionTypeBean;
import org.akaza.openclinica.dao.core.AuditableEntityDAO;
import org.akaza.openclinica.exception.OpenClinicaException;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;

public class IRBProtocolActionHistoryDAO extends AuditableEntityDAO<IRBProtocolActionHistoryBean> {
    public IRBProtocolActionHistoryDAO(DataSource ds) {
        super(ds);
        setDigesterName();
        setQueryNames();
    }

    private void setQueryNames() {

    }

    @Override
    public IRBProtocolActionHistoryBean getEntityFromHashMap(HashMap<String, Object> hm) {
        return null;
    }

    @Override
    public ArrayList<IRBProtocolActionHistoryBean> findAll(String strOrderByColumn, boolean blnAscendingSort, String strSearchPhrase) throws OpenClinicaException {
        return null;
    }

    @Override
    public ArrayList<IRBProtocolActionHistoryBean> findAll() throws OpenClinicaException {
        return null;
    }

    @Override
    public EntityBean findByPK(int id) throws OpenClinicaException {
        return null;
    }

    @Override
    public IRBProtocolActionHistoryBean create(IRBProtocolActionHistoryBean eb) throws OpenClinicaException {
        return null;
    }

    @Override
    public IRBProtocolActionHistoryBean update(IRBProtocolActionHistoryBean eb) throws OpenClinicaException {
        return null;
    }

    @Override
    public ArrayList<IRBProtocolActionHistoryBean> findAllByPermission(Object objCurrentUser, int intActionType, String strOrderByColumn, boolean blnAscendingSort, String strSearchPhrase) throws OpenClinicaException {
        return null;
    }

    @Override
    public ArrayList<IRBProtocolActionHistoryBean> findAllByPermission(Object objCurrentUser, int intActionType) throws OpenClinicaException {
        return null;
    }

    @Override
    public void setTypesExpected() {

    }

    @Override
    protected void setDigesterName() {

    }

    @Override
    public IRBProtocolActionHistoryBean emptyBean() {
        return null;
    }
}
