package org.akaza.openclinica.control.managestudy;

import org.akaza.openclinica.bean.managestudy.IRBProtocolActionHistoryBean;
import org.akaza.openclinica.bean.managestudy.IRBProtocolActionTypeBean;
import org.akaza.openclinica.bean.managestudy.IRBSiteBean;
import org.akaza.openclinica.control.core.SecureController;
import org.akaza.openclinica.dao.managestudy.IRBProtocolActionHistoryDAO;
import org.akaza.openclinica.dao.managestudy.IRBProtocolActionTypeDAO;
import org.akaza.openclinica.dao.managestudy.IRBSiteDAO;
import org.akaza.openclinica.exception.OpenClinicaException;
import org.akaza.openclinica.view.Page;
import org.akaza.openclinica.web.InsufficientPermissionException;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class IRBSiteServlet extends SecureController {
    private IRBSiteDAO irbSiteDAO;
    private IRBProtocolActionHistoryDAO irbProtocolActionHistoryDAO;

    private IRBSiteDAO getIRBSiteDAO() {
        if(irbSiteDAO==null) irbSiteDAO = new IRBSiteDAO(sm.getDataSource());

        return irbSiteDAO;
    }

    private IRBProtocolActionHistoryDAO getIRBProtocolActionHistoryDAO() {
        if(irbProtocolActionHistoryDAO==null)
            irbProtocolActionHistoryDAO = new IRBProtocolActionHistoryDAO(sm.getDataSource());

        return irbProtocolActionHistoryDAO;
    }

    private void createOrUpdateActionHistory() throws NumberFormatException, OpenClinicaException {
        String stringSiteId = request.getParameter("siteId");
        int siteId = Integer.parseInt(stringSiteId);
        IRBSiteBean irbSiteBean = getIRBSiteDAO().findBySiteId(siteId);
        if(irbSiteBean.getIrbSiteId()<1) {
            irbSiteBean.setSiteId(siteId);
            irbSiteBean.setIs1572(false);
            irbSiteBean.setActive(true);
            irbSiteBean = getIRBSiteDAO().create(irbSiteBean);
        }
        String stringProtocolActionHistoryId =
                request.getParameter("protocolActionHistoryId");
        int protocolActionHistoryId = -1;
        try {
            protocolActionHistoryId = Integer.parseInt(stringProtocolActionHistoryId);
        }
        catch (NumberFormatException ex) {
            //Don't do anything.
        }
        IRBProtocolActionHistoryBean irbProtocolActionHistoryBean = null;
        if(protocolActionHistoryId>0) {
            irbProtocolActionHistoryBean =
                    (IRBProtocolActionHistoryBean) getIRBProtocolActionHistoryDAO()
                    .findByPK(protocolActionHistoryId);
        }
        if(irbProtocolActionHistoryBean == null)
            irbProtocolActionHistoryBean = new IRBProtocolActionHistoryBean();
        //if(irbProtocolActionHistoryBean.getir)

        irbProtocolActionHistoryBean.setIrbSiteId(siteId);
        irbProtocolActionHistoryBean.setIrbProtocolActionTypeId(
                intValueOrZero("protocol_action_type_id"));
        irbProtocolActionHistoryBean.setVersionDate(dateValueOrNull("version_date"));
        irbProtocolActionHistoryBean.setVersionNumber(intValueOrZero("version_number"));
        irbProtocolActionHistoryBean.setSiteSubmittedToLocalIrb(
                dateValueOrNull("site_submitted_to_local_irb"));
        irbProtocolActionHistoryBean.setLocalIrbApproval(
                dateValueOrNull("local_irb_approval"));
        irbProtocolActionHistoryBean.setReceivedDocsFromSites(
                dateValueOrNull("received_docs_from_sites"));
        irbProtocolActionHistoryBean.setPackageSentToCdcIrb(
                dateValueOrNull("package_sent_to_cdc_irb"));
        irbProtocolActionHistoryBean.setCdcApproval(
                dateValueOrNull("cdc_approval"));

        if(irbProtocolActionHistoryBean.getIrbProtocolActionHistoryId()<1)
            getIRBProtocolActionHistoryDAO()
                    .create(irbProtocolActionHistoryBean);
        else
            getIRBProtocolActionHistoryDAO()
                    .update(irbProtocolActionHistoryBean);
    }

    @Override
    protected void processRequest() throws Exception {
        if(request.getMethod().compareToIgnoreCase("POST")==0) {
            if(request.getParameter("action") != null &&
                (request.getParameter("action").compareToIgnoreCase("saveProtocolActionEditor")==0)) {
                createOrUpdateActionHistory();
                forwardPage(Page.VIEW_STUDY);
                return;
            }

            createOrUpdateIRBSiteBean();
            forwardPage(Page.VIEW_STUDY);
            return;
        }

        String stringSiteId = request.getParameter("siteId");
        if(stringSiteId==null || stringSiteId.isEmpty()) {
            addPageMessage("Invalid site id");
            forwardPage(Page.VIEW_STUDY);
            return;
        }
        int siteId = Integer.parseInt(stringSiteId);
        IRBProtocolActionTypeDAO protocolActionTypeDAO =
                new IRBProtocolActionTypeDAO(sm.getDataSource());
        ArrayList<IRBProtocolActionTypeBean> protocolActionsTypes =
                protocolActionTypeDAO.findAll();
        ArrayList<IRBProtocolActionHistoryBean> protocolActionHistory =
                getIRBProtocolActionHistoryDAO().findBySiteId(siteId);

        IRBSiteBean irbSiteBean = getIRBSiteDAO().findBySiteId(siteId);
        request.setAttribute("siteId", siteId);
        request.setAttribute("irbSiteBean", irbSiteBean);
        request.setAttribute("protocolActionTypes", protocolActionsTypes);
        request.setAttribute("protocolActionHistory", protocolActionHistory);
        forwardPage(Page.IRB_SITE);
    }
    private void createOrUpdateIRBSiteBean() throws NumberFormatException, OpenClinicaException {
        String stringSiteId = request.getParameter("siteId");
        int siteId = Integer.parseInt(stringSiteId);

        IRBSiteBean irbSiteBean = getIRBSiteDAO().findBySiteId(siteId);
        if(irbSiteBean == null) irbSiteBean = new IRBSiteBean();
        if(irbSiteBean.getIrbSiteId()<1) irbSiteBean.setSiteId(siteId);

        irbSiteBean.setVersionNumber(intValueOrZero("version_number"));
        irbSiteBean.setSiteReliesOnCdcIrb(intValueOrZero("version_number")==1);
        irbSiteBean.setIs1572(intValueOrZero("is_1572")==1);
        irbSiteBean.setCdcIrbProtocolVersionDate(dateValueOrNull("cdc_irb_protocol_version_date"));
        irbSiteBean.setLocalIrbApprovedProtocol(dateValueOrNull("local_irb_approved_protocol"));
        irbSiteBean.setCdcReceivedLocalDocuments(dateValueOrNull("cdc_received_local_documents"));
        irbSiteBean.setSiteConsentPackageSendToCdcIrb(dateValueOrNull("site_consent_package_send_to_cdc_irb"));
        irbSiteBean.setInitialCdcIrbApproval(dateValueOrNull("initial_cdc_irb_approval"));
        irbSiteBean.setCrbApprovalToEnroll(dateValueOrNull("crb_approval_to_enroll"));
        irbSiteBean.setIrbApproval(dateValueOrNull("irb_approval"));
        irbSiteBean.setExpirationDate(dateValueOrNull("expiration_date"));
        irbSiteBean.setActive(intValueOrZero("active")==1);
        irbSiteBean.setComments(request.getParameter("comments"));

        if(irbSiteBean.getIrbSiteId()<1)
            getIRBSiteDAO().create(irbSiteBean);
        else
            getIRBSiteDAO().update(irbSiteBean);
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
        try {
            if(request.getParameter(field)!= null) {
                retval = Integer.parseInt(request.getParameter(field));
            }
        } catch(NumberFormatException ex) {
            //
        }


        return retval;
    }
}
