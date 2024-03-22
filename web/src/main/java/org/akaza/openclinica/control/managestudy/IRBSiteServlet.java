package org.akaza.openclinica.control.managestudy;

import org.akaza.openclinica.bean.core.NumericComparisonOperator;
import org.akaza.openclinica.bean.managestudy.IRBProtocolActionHistoryBean;
import org.akaza.openclinica.bean.managestudy.IRBProtocolActionTypeBean;
import org.akaza.openclinica.bean.managestudy.IRBProtocolActionHistoryParameterBean;
import org.akaza.openclinica.bean.managestudy.IRBSiteBean;
import org.akaza.openclinica.control.core.SecureController;
import org.akaza.openclinica.control.form.DiscrepancyValidator;
import org.akaza.openclinica.control.form.FormDiscrepancyNotes;
import org.akaza.openclinica.control.form.FormProcessor;
import org.akaza.openclinica.control.form.Validator;
import org.akaza.openclinica.control.submit.AddNewSubjectServlet;
import org.akaza.openclinica.dao.managestudy.IRBProtocolActionHistoryDAO;
import org.akaza.openclinica.dao.managestudy.IRBProtocolActionHistoryParameterDAO;
import org.akaza.openclinica.dao.managestudy.IRBProtocolActionTypeDAO;
import org.akaza.openclinica.dao.managestudy.IRBSiteDAO;
import org.akaza.openclinica.exception.OpenClinicaException;
import org.akaza.openclinica.i18n.core.LocaleResolver;
import org.akaza.openclinica.view.Page;
import org.akaza.openclinica.web.InsufficientPermissionException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.print.DocFlavor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class IRBSiteServlet extends SecureController {
    private IRBSiteDAO irbSiteDAO;
    private static final String INPUT_VERSION_NUMBER = "version_number";
    private static final String INPUT_SITE_RELIES_ON_CDC_IRB = "site_relies_on_cdc_irb";
    private static final String INPUT_IS_1572  = "is_1572";
    private static final String INPUT_PROTOCOL_VERSION_DATE  = "cdc_irb_protocol_version_date";
    private static final String INPUT_LOCAL_IRB_APPROVED_PROTOCOL  = "local_irb_approved_protocol";
    private static final String INPUT_CDC_RECEIVED_LOCAL_DOCUMENTS  = "cdc_received_local_documents";
    private static final String INPUT_SITE_CONSENT_PACKAGE_SEND_TO_CDC_IRB  = "site_consent_package_send_to_cdc_irb";
    private static final String INPUT_INITIAL_CDC_IRB_APPROVAL  = "initial_cdc_irb_approval";
    private static final String INPUT_CRB_APPROVAL_TO_ENROLL  = "crb_approval_to_enroll";
    private static final String INPUT_IRB_APPROVAL  = "irb_approval";
    private static final String INPUT_EXPIRATION_DATE  = "expiration_date";
    private static final String INPUT_ACTIVE  = "active";
    private static final String INPUT_COMMENTS  = "comments";


    private IRBProtocolActionHistoryDAO irbProtocolActionHistoryDAO;

    private IRBProtocolActionHistoryParameterDAO irbProtocolActionHistoryParameterDAO;

    private IRBSiteDAO getIRBSiteDAO() {
        if(irbSiteDAO==null) irbSiteDAO = new IRBSiteDAO(sm.getDataSource());

        return irbSiteDAO;
    }

    private IRBProtocolActionHistoryParameterDAO getIrbProtocolActionHistoryParameter() {
        if(irbProtocolActionHistoryParameterDAO == null)
            irbProtocolActionHistoryParameterDAO = new IRBProtocolActionHistoryParameterDAO(sm.getDataSource());

        return irbProtocolActionHistoryParameterDAO;
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
        irbProtocolActionHistoryBean.setEnrollmentPauseDate(
                dateValueOrNull("enrollment_pause_date"));
        irbProtocolActionHistoryBean.setEnrollmentRestartedDate(
                dateValueOrNull("enrollment_restarted_date"));
        irbProtocolActionHistoryBean.setReasonForEnrollmentPaused(
                request.getParameter("reason_for_enrollment_paused"));

        if(irbProtocolActionHistoryBean.getIrbProtocolActionHistoryId()<1)
            getIRBProtocolActionHistoryDAO()
                    .create(irbProtocolActionHistoryBean);
        else
            getIRBProtocolActionHistoryDAO()
                    .update(irbProtocolActionHistoryBean);
    }

    private HashMap<String, ArrayList<String>> validateIrbSiteRequest() {
        FormDiscrepancyNotes discNotes;

        discNotes = (FormDiscrepancyNotes) session.getAttribute(AddNewSubjectServlet.FORM_DISCREPANCY_NOTES_NAME);
        if (discNotes == null) {
            discNotes = new FormDiscrepancyNotes();
            session.setAttribute(AddNewSubjectServlet.FORM_DISCREPANCY_NOTES_NAME, discNotes);
        }
        DiscrepancyValidator v = new DiscrepancyValidator(request, discNotes);

        v.addValidation(INPUT_VERSION_NUMBER, Validator.IS_A_NUMBER);
        v.addValidation(INPUT_LOCAL_IRB_APPROVED_PROTOCOL, Validator.IS_A_DATE);
        v.addValidation(INPUT_PROTOCOL_VERSION_DATE, Validator.IS_A_DATE);
        v.addValidation(INPUT_CDC_RECEIVED_LOCAL_DOCUMENTS, Validator.IS_A_DATE);
        v.addValidation(INPUT_SITE_CONSENT_PACKAGE_SEND_TO_CDC_IRB, Validator.IS_A_DATE);
        v.addValidation(INPUT_INITIAL_CDC_IRB_APPROVAL, Validator.IS_A_DATE);
        v.addValidation(INPUT_CRB_APPROVAL_TO_ENROLL, Validator.IS_A_DATE);
        v.addValidation(INPUT_IRB_APPROVAL, Validator.IS_A_DATE);
        v.addValidation(INPUT_EXPIRATION_DATE, Validator.IS_A_DATE);

        return v.validate();
    }
    @Override
    protected void processRequest() throws Exception {
        FormProcessor fp = new FormProcessor(request);
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
        request.setAttribute("protocolActionTypes", protocolActionsTypes);
            request.setAttribute("protocolActionHistoryParameter", getIrbProtocolActionHistoryParameter().findAll());
            request.setAttribute("protocolActionHistory", getIRBProtocolActionHistoryDAO().findBySiteId(siteId));
        request.setAttribute("siteId", siteId);

        IRBSiteBean irbSiteBean;

        if(request.getMethod().compareToIgnoreCase("POST")==0) {
            if(request.getParameter("action") != null &&
                (request.getParameter("action").compareToIgnoreCase("saveProtocolActionEditor")==0)) {
                createOrUpdateActionHistory();
                forwardPage(Page.VIEW_STUDY);
                return;
            }

            HashMap<String, ArrayList<String>> errors = validateIrbSiteRequest();
            if(!errors.isEmpty()) {
                setInputMessages(errors);
                fp.clearPresetValues();
                setPresetValuesFromRequest(fp);
                setPresetValues(fp.getPresetValues());

                addPageMessage("Validation errors were found when saving the IRB Study data");
                forwardPage(Page.IRB_SITE);
                return;
            }
            irbSiteBean = createOrUpdateIRBSiteBean();
            forwardPage(Page.STUDY_LIST_SERVLET);
            return;
        }

        irbSiteBean = getIRBSiteDAO().findBySiteId(siteId);
        Locale locale = LocaleResolver.getLocale(request);
        SimpleDateFormat sdf= new SimpleDateFormat("dd-MMM-yyyy", locale);
        fp.addPresetValue("siteId", siteId);
        fp.addPresetValue(INPUT_VERSION_NUMBER, irbSiteBean.getVersionNumber());
        fp.addPresetValue(INPUT_SITE_RELIES_ON_CDC_IRB, irbSiteBean.isSiteReliesOnCdcIrb()?"1":"0");
        fp.addPresetValue(INPUT_IS_1572, irbSiteBean.isIs1572()?"1":"0");
        fp.addPresetValue(INPUT_PROTOCOL_VERSION_DATE, irbSiteBean.getCdcIrbProtocolVersionDate()!=null?
            sdf.format(irbSiteBean.getCdcIrbProtocolVersionDate()): "");
        fp.addPresetValue(INPUT_LOCAL_IRB_APPROVED_PROTOCOL, irbSiteBean.getLocalIrbApprovedProtocol()!=null?
                sdf.format(irbSiteBean.getLocalIrbApprovedProtocol()): "");
        fp.addPresetValue(INPUT_CDC_RECEIVED_LOCAL_DOCUMENTS, irbSiteBean.getCdcReceivedLocalDocuments()!=null?
                sdf.format(irbSiteBean.getCdcReceivedLocalDocuments()): "");
        fp.addPresetValue(INPUT_SITE_CONSENT_PACKAGE_SEND_TO_CDC_IRB, irbSiteBean.getSiteConsentPackageSendToCdcIrb()!=null?
                sdf.format(irbSiteBean.getSiteConsentPackageSendToCdcIrb()): "");
        fp.addPresetValue(INPUT_INITIAL_CDC_IRB_APPROVAL, irbSiteBean.getInitialCdcIrbApproval()!=null?
                sdf.format(irbSiteBean.getInitialCdcIrbApproval()): "");
        fp.addPresetValue(INPUT_CRB_APPROVAL_TO_ENROLL, irbSiteBean.getCrbApprovalToEnroll()!=null?
                sdf.format(irbSiteBean.getCrbApprovalToEnroll()): "");
        fp.addPresetValue(INPUT_IRB_APPROVAL, irbSiteBean.getIrbApproval()!=null?
                sdf.format(irbSiteBean.getIrbApproval()): "");
        fp.addPresetValue(INPUT_EXPIRATION_DATE, irbSiteBean.getExpirationDate()!=null?
                sdf.format(irbSiteBean.getExpirationDate()): "");
        fp.addPresetValue(INPUT_ACTIVE, irbSiteBean.isActive()?"1":"0");
        fp.addPresetValue(INPUT_COMMENTS, irbSiteBean.getComments());

        setPresetValues(fp.getPresetValues());

        forwardPage(Page.IRB_SITE);
    }
    private IRBSiteBean createOrUpdateIRBSiteBean() throws NumberFormatException, OpenClinicaException {
        String stringSiteId = request.getParameter("siteId");
        int siteId = Integer.parseInt(stringSiteId);

        IRBSiteBean irbSiteBean = getIRBSiteDAO().findBySiteId(siteId);
        if(irbSiteBean == null) irbSiteBean = new IRBSiteBean();
        if(irbSiteBean.getIrbSiteId()<1) irbSiteBean.setSiteId(siteId);

        irbSiteBean.setVersionNumber(intValueOrZero(INPUT_VERSION_NUMBER));
        irbSiteBean.setSiteReliesOnCdcIrb(intValueOrZero(INPUT_SITE_RELIES_ON_CDC_IRB)==1);
        irbSiteBean.setIs1572(intValueOrZero(INPUT_IS_1572)==1);
        irbSiteBean.setCdcIrbProtocolVersionDate(dateValueOrNull(INPUT_PROTOCOL_VERSION_DATE));
        irbSiteBean.setLocalIrbApprovedProtocol(dateValueOrNull(INPUT_LOCAL_IRB_APPROVED_PROTOCOL));
        irbSiteBean.setCdcReceivedLocalDocuments(dateValueOrNull(INPUT_CDC_RECEIVED_LOCAL_DOCUMENTS));
        irbSiteBean.setSiteConsentPackageSendToCdcIrb(dateValueOrNull(INPUT_SITE_CONSENT_PACKAGE_SEND_TO_CDC_IRB));
        irbSiteBean.setInitialCdcIrbApproval(dateValueOrNull(INPUT_INITIAL_CDC_IRB_APPROVAL));
        irbSiteBean.setCrbApprovalToEnroll(dateValueOrNull(INPUT_CRB_APPROVAL_TO_ENROLL));
        irbSiteBean.setIrbApproval(dateValueOrNull(INPUT_IRB_APPROVAL));
        irbSiteBean.setExpirationDate(dateValueOrNull(INPUT_EXPIRATION_DATE));
        irbSiteBean.setActive(intValueOrZero(INPUT_ACTIVE)==1);
        irbSiteBean.setComments(request.getParameter(INPUT_COMMENTS));

        if(irbSiteBean.getIrbSiteId()<1)
            irbSiteBean = getIRBSiteDAO().create(irbSiteBean);
        else
            irbSiteBean = getIRBSiteDAO().update(irbSiteBean);
        addPageMessage("IRB Site saved");

        return irbSiteBean;
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
