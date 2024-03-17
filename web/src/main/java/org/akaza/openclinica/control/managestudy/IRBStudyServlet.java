package org.akaza.openclinica.control.managestudy;

import org.akaza.openclinica.bean.managestudy.IRBProtocolActionHistoryBean;
import org.akaza.openclinica.bean.managestudy.IRBProtocolActionTypeBean;
import org.akaza.openclinica.bean.managestudy.IRBSiteBean;
import org.akaza.openclinica.bean.managestudy.IRBStudyBean;
import org.akaza.openclinica.control.core.SecureController;
import org.akaza.openclinica.dao.managestudy.IRBProtocolActionTypeDAO;
import org.akaza.openclinica.dao.managestudy.IRBSiteDAO;
import org.akaza.openclinica.dao.managestudy.IRBStudyDAO;
import org.akaza.openclinica.exception.OpenClinicaException;
import org.akaza.openclinica.view.Page;
import org.akaza.openclinica.web.InsufficientPermissionException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class IRBStudyServlet extends SecureController {
    private IRBStudyDAO irbStudyDAO;

    private IRBStudyDAO getIRBStudyDAO() {
        if(irbStudyDAO==null) irbStudyDAO = new IRBStudyDAO(sm.getDataSource());

        return irbStudyDAO;
    }
    @Override
    protected void processRequest() throws Exception {
        if(request.getMethod().compareToIgnoreCase("POST")==0) {
            createOrUpdateIRBStudyBean();
            response.sendRedirect(request.getContextPath() + Page.MANAGE_STUDY_MODULE.getFileName());
            return;
        }


        IRBStudyBean irbStudyBean = getIRBStudyDAO().findByStudy(currentStudy);
        request.setAttribute("irbStudyBean", irbStudyBean);
        forwardPage(Page.IRB_STUDY);
    }

    private void createOrUpdateIRBStudyBean() throws OpenClinicaException {
        IRBStudyBean irbStudyBean = getIRBStudyDAO().findByStudy(currentStudy);
        if(irbStudyBean == null) irbStudyBean = new IRBStudyBean();
        if(irbStudyBean.getIrbStudyId()<1) irbStudyBean.setStudyId(currentStudy.getId());

        irbStudyBean.setCdcIrbProtocolNumber(request.getParameter("cdc_irb_protocol_number"));
        irbStudyBean.setVersion1ProtocolDate(dateValueOrNull("version1_protocol_date"));
        irbStudyBean.setProtocolOfficer(request.getParameter("protocol_officer"));
        irbStudyBean.setSubmittedCdcIrb(dateValueOrNull("submitted_cdc_irb"));
        irbStudyBean.setApprovalByCdcIrb(dateValueOrNull("approval_by_cdc_irb"));
        irbStudyBean.setCdcIrbExpirationDate(dateValueOrNull("cdc_irb_expiration_date"));

        if(irbStudyBean.getIrbStudyId()<1)
            getIRBStudyDAO().create(irbStudyBean);
        else
            getIRBStudyDAO().update(irbStudyBean);
    }

    @Override
    protected void mayProceed() throws InsufficientPermissionException {

    }

    private Date dateValueOrNull(String field) {
        Date retval = null;
        if(request.getParameter(field) == null) return null;

        SimpleDateFormat dateTimeFormatter =
                new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        try {
            retval = dateTimeFormatter.parse(request.getParameter(field));
        }
        catch (ParseException ex) {
            //ignore the error, we'll return null at the next statement
        }

        return retval;
    }

    private int intValueOrZero(String field) {
        int retval = 0;
        if(request.getParameter(field)!= null) {
            retval = Integer.parseInt(request.getParameter(field));
        }

        return retval;
    }
}
