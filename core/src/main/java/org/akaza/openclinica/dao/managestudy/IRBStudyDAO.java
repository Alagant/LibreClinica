package org.akaza.openclinica.dao.managestudy;

import org.akaza.openclinica.bean.core.EntityBean;
import org.akaza.openclinica.bean.managestudy.IRBStudyBean;
import org.akaza.openclinica.dao.core.AuditableEntityDAO;
import org.akaza.openclinica.exception.OpenClinicaException;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;

public class IRBStudyDAO extends AuditableEntityDAO<IRBStudyBean> {
    @Override
    protected void setDigesterName() {

    }

    public IRBStudyDAO(DataSource ds) {
        super(ds);
        setDigesterName();
        setQueryNames();
    }

    private void setQueryNames() {

    }

    @Override
    public IRBStudyBean emptyBean() {
        return null;
    }

    @Override
    public IRBStudyBean getEntityFromHashMap(HashMap<String, Object> hm) {
        return null;
    }

    @Override
    public ArrayList<IRBStudyBean> findAll(String strOrderByColumn, boolean blnAscendingSort, String strSearchPhrase) throws OpenClinicaException {
        return null;
    }

    @Override
    public ArrayList<IRBStudyBean> findAll() throws OpenClinicaException {
        return null;
    }

    @Override
    public EntityBean findByPK(int id) throws OpenClinicaException {
        return null;
    }

    @Override
    public IRBStudyBean create(IRBStudyBean eb) throws OpenClinicaException {
        return null;
    }

    @Override
    public IRBStudyBean update(IRBStudyBean eb) throws OpenClinicaException {
        return null;
    }

    @Override
    public ArrayList<IRBStudyBean> findAllByPermission(Object objCurrentUser, int intActionType, String strOrderByColumn, boolean blnAscendingSort, String strSearchPhrase) throws OpenClinicaException {
        return null;
    }

    @Override
    public ArrayList<IRBStudyBean> findAllByPermission(Object objCurrentUser, int intActionType) throws OpenClinicaException {
        return null;
    }

    @Override
    public void setTypesExpected() {

    }
}
