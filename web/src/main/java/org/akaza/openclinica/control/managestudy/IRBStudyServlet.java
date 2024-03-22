package org.akaza.openclinica.control.managestudy;

import javassist.Loader;
import org.akaza.openclinica.bean.core.NumericComparisonOperator;
import org.akaza.openclinica.bean.managestudy.*;
import org.akaza.openclinica.control.core.SecureController;
import org.akaza.openclinica.dao.managestudy.*;
import org.akaza.openclinica.control.form.DiscrepancyValidator;
import org.akaza.openclinica.control.form.FormDiscrepancyNotes;
import org.akaza.openclinica.control.form.FormProcessor;
import org.akaza.openclinica.control.form.Validator;
import org.akaza.openclinica.control.submit.AddNewSubjectServlet;
import org.akaza.openclinica.exception.OpenClinicaException;
import org.akaza.openclinica.i18n.core.LocaleResolver;
import org.akaza.openclinica.view.Page;
import org.akaza.openclinica.web.InsufficientPermissionException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;
import java.util.HashMap;

public class IRBStudyServlet extends SecureController {
    private IRBStudyDAO irbStudyDAO;
    private IRBStudyActionHistoryDAO irbStudyActionHistoryDAO;
    private IRBStudyActionHistoryParameterDAO irbStudyActionHistoryParameterDAO;
    public static final String INPUT_CDC_IRB_PROTOCOL_NUMBER = "cdc_irb_protocol_number";
    public static final String INPUT_VERSION1_PROTOCOL_DATE = "version1_protocol_date";
    public static final String INPUT_PROTOCOL_OFFICER = "protocol_officer";
    public static final String INPUT_SUBMITTED_CDC_IRB = "submitted_cdc_irb";
    public static final String INPUT_APPROVAL_BY_CDC_IRB = "approval_by_cdc_irb";
    public static final String INPUT_CDC_IRB_EXPIRATION_DATE = "cdc_irb_expiration_date";


    public static final String INPUT_H_STUDY_ACTION_HISTORY_ID = "study_action_history_id";
    public static final String INPUT_H_PROTOCOL_ACTION_TYPE = "study_action_type_id";
    public static final String INPUT_H_EFFECTIVE_DATE = "effective_date";
    public static final String INPUT_H_HRPO_ACTION = "hrpo_action";
    public static final String INPUT_H_VERSION_NUMBER = "version_number";
    public static final String INPUT_H_VERSION_DATE = "version_date";
    public static final String INPUT_H_SUBMISSION_TO_CDC_IRB = "submission_to_cdc_irb";
    public static final String INPUT_H_CDC_IRB_APPROVAL = "cdc_irb_approval";
    public static final String INPUT_H_NOTIFICATION_SENT_TO_SITES = "notification_sent_to_sites";


    private IRBStudyDAO getIRBStudyDAO() {
        if(irbStudyDAO==null) irbStudyDAO = new IRBStudyDAO(sm.getDataSource());

        return irbStudyDAO;
    }

    private IRBStudyActionHistoryDAO getIRBStudyActionHistoryDAO() {
        if(irbStudyActionHistoryDAO==null)
            irbStudyActionHistoryDAO = new IRBStudyActionHistoryDAO(sm.getDataSource());

        return irbStudyActionHistoryDAO;
    }

    private IRBStudyActionHistoryParameterDAO getIrbStudyActionHistoryParameterDAO() {
        if(irbStudyActionHistoryParameterDAO==null)
            irbStudyActionHistoryParameterDAO = new IRBStudyActionHistoryParameterDAO(sm.getDataSource());

        return irbStudyActionHistoryParameterDAO;
    }


    private IRBStudyActionHistoryBean createOrUpdateActionHistory() throws NumberFormatException, OpenClinicaException {

        String stringStudyActionHistoryId =
                request.getParameter(INPUT_H_STUDY_ACTION_HISTORY_ID);
        int studyActionHistoryId = -1;
        try {
            studyActionHistoryId = Integer.parseInt(stringStudyActionHistoryId);
        }
        catch (NumberFormatException ex) {
            //Don't do anything.
        }
        IRBStudyActionHistoryBean  irbStudyActionHistoryBean = null;
        if(studyActionHistoryId>0) {
            irbStudyActionHistoryBean = (IRBStudyActionHistoryBean) getIRBStudyActionHistoryDAO()
                            .findByPK(studyActionHistoryId);
        }
        if(irbStudyActionHistoryBean == null)
            irbStudyActionHistoryBean = new IRBStudyActionHistoryBean();
        //if(irbProtocolActionHistoryBean.getir)

        irbStudyActionHistoryBean.setStudyId(currentStudy.getId());
        irbStudyActionHistoryBean.setIrbProtocolActionTypeId(
                intValueOrZero( INPUT_H_PROTOCOL_ACTION_TYPE));
        irbStudyActionHistoryBean.setEffectiveDate(dateValueOrNull(INPUT_H_EFFECTIVE_DATE));
        irbStudyActionHistoryBean.setHrpoAction(intValueOrZero(INPUT_H_HRPO_ACTION));
        irbStudyActionHistoryBean.setVersionNumber(intValueOrZero(INPUT_H_VERSION_NUMBER));
        irbStudyActionHistoryBean.setVersionDate(dateValueOrNull(INPUT_H_VERSION_DATE));
        irbStudyActionHistoryBean.setSubmissionToCdcIrb(dateValueOrNull(INPUT_H_SUBMISSION_TO_CDC_IRB));
        irbStudyActionHistoryBean.setCdcIrbApproval(dateValueOrNull(INPUT_H_CDC_IRB_APPROVAL));
        irbStudyActionHistoryBean.setNotificationSentToSites(dateValueOrNull(INPUT_H_NOTIFICATION_SENT_TO_SITES));

        if(irbStudyActionHistoryBean.getIrbStudyActionHistoryId()<1)
            getIRBStudyActionHistoryDAO()
                    .create(irbStudyActionHistoryBean);
        else
            getIRBStudyActionHistoryDAO()
                    .update(irbStudyActionHistoryBean);

        return irbStudyActionHistoryBean;
    }

    private HashMap<String, ArrayList<String>> validateIrbStudyRequest() {
        FormDiscrepancyNotes discNotes;

        discNotes = (FormDiscrepancyNotes) session.getAttribute(AddNewSubjectServlet.FORM_DISCREPANCY_NOTES_NAME);
        if (discNotes == null) {
            discNotes = new FormDiscrepancyNotes();
            session.setAttribute(AddNewSubjectServlet.FORM_DISCREPANCY_NOTES_NAME, discNotes);
        }
        DiscrepancyValidator v = new DiscrepancyValidator(request, discNotes);

        v.addValidation(INPUT_CDC_IRB_PROTOCOL_NUMBER, Validator.NO_BLANKS);
        v.addValidation(INPUT_CDC_IRB_PROTOCOL_NUMBER, Validator.LENGTH_NUMERIC_COMPARISON,
                NumericComparisonOperator.LESS_THAN_OR_EQUAL_TO, 5);
        v.addValidation(INPUT_VERSION1_PROTOCOL_DATE, Validator.NO_BLANKS);
        v.addValidation(INPUT_VERSION1_PROTOCOL_DATE, Validator.IS_A_DATE);
        //v.addValidation(INPUT_VERSION1_PROTOCOL_DATE, Validator.DATE_IN_PAST);
        v.addValidation(INPUT_PROTOCOL_OFFICER, Validator.NO_BLANKS);
        v.addValidation(INPUT_PROTOCOL_OFFICER, Validator.LENGTH_NUMERIC_COMPARISON,
                NumericComparisonOperator.LESS_THAN_OR_EQUAL_TO, 20);
        //  v.addValidation(INPUT_SUBMITTED_CDC_IRB, Validator.NO_BLANKS);
        v.addValidation(INPUT_SUBMITTED_CDC_IRB, Validator.IS_A_DATE);
        v.addValidation(INPUT_SUBMITTED_CDC_IRB, Validator.DATE_IN_PAST);
        // v.addValidation(INPUT_APPROVAL_BY_CDC_IRB, Validator.NO_BLANKS);
        v.addValidation(INPUT_APPROVAL_BY_CDC_IRB, Validator.IS_A_DATE);
        v.addValidation(INPUT_APPROVAL_BY_CDC_IRB, Validator.DATE_IN_PAST);
        // v.addValidation(INPUT_CDC_IRB_EXPIRATION_DATE, Validator.NO_BLANKS);
        v.addValidation(INPUT_CDC_IRB_EXPIRATION_DATE, Validator.IS_A_DATE);
        v.addValidation(INPUT_CDC_IRB_EXPIRATION_DATE, Validator.DATE_IN_PAST);

        return v.validate();
    }

    private ArrayList<HashMap<String, String>> getStudyActionHistory() {
        Locale locale = LocaleResolver.getLocale(request);
        SimpleDateFormat sdf= new SimpleDateFormat("dd-MMM-yyyy", locale);

        ArrayList<IRBStudyActionHistoryBean> studyActionHistory =
                getIRBStudyActionHistoryDAO().findByStudyId(currentStudy.getId());
        ArrayList<HashMap<String, String>> studyActionHistoryFormatted = new ArrayList<>();

        for(IRBStudyActionHistoryBean sb: studyActionHistory) {
            HashMap<String, String> row = new HashMap<>();
            row.put("id", Integer.toString(sb.getIrbStudyActionHistoryId()));
            row.put("actionTypeId", Integer.toString(sb.getIrbProtocolActionTypeId()));
            row.put("actionLabel", sb.getActionLabel());
            row.put("effectiveDate", sb.getEffectiveDate()!=null? sdf.format(sb.getEffectiveDate()):"");
            row.put("hrpoAction", sb.getHrpoAction()>0? Integer.toString(sb.getHrpoAction()):"");
            row.put("versionNumber", sb.getVersionNumber()>0? Integer.toString(sb.getVersionNumber()):"");
            row.put("versionDate", sb.getVersionDate()!=null? sdf.format(sb.getVersionDate()):"");
            row.put("submissionToCdcIrb", sb.getSubmissionToCdcIrb()!=null? sdf.format(sb.getSubmissionToCdcIrb()):"");
            row.put("cdcIrbApproval", sb.getCdcIrbApproval()!=null? sdf.format(sb.getCdcIrbApproval()):"");
            row.put("notificationSentToSites", sb.getNotificationSentToSites()!=null? sdf.format(sb.getNotificationSentToSites()):"");
            studyActionHistoryFormatted.add(row);
        }

        return studyActionHistoryFormatted;
    }


    @Override
    protected void processRequest() throws Exception {
        FormProcessor fp = new FormProcessor(request);
        Locale locale = LocaleResolver.getLocale(request);
        SimpleDateFormat sdf= new SimpleDateFormat("dd-MMM-yyyy", locale);

        ArrayList<IRBStudyActionHistoryParameterBean> protocolActionsTypes =
                getIrbStudyActionHistoryParameterDAO().findAll();
        /*
        ArrayList<IRBStudyActionHistoryBean> studyActionHistory =
                getIRBStudyActionHistoryDAO().findByStudyId(currentStudy.getId());

        ArrayList<HashMap<String, String>> studyActionHistoryFormatted = new ArrayList<>();
         */

        request.setAttribute("protocolActionHistoryParameter", protocolActionsTypes);
        //request.setAttribute("studyActionHistory", studyActionHistory);
        request.setAttribute("studyActionHistory", getStudyActionHistory());

        request.setAttribute("studyId", currentStudy.getId());

        IRBStudyBean irbStudyBean;

        System.out.println("processRequest - fp.isSubmitted()" + fp.isSubmitted());
        if(request.getMethod().compareToIgnoreCase("POST")==0) {
            if(request.getParameter("action") != null &&
                    (request.getParameter("action").compareToIgnoreCase("saveStudyActionEditor")==0)) {
                createOrUpdateActionHistory();
                request.setAttribute("studyActionHistory", getStudyActionHistory());
                forwardPage(Page.IRB_STUDY);
                return;
            }
        }


        if(fp.isSubmitted()) {
            HashMap<String, ArrayList<String>> errors = validateIrbStudyRequest();

            if (!errors.isEmpty()) {
                setInputMessages(errors);
                fp.clearPresetValues();
                setPresetValuesFromRequest(fp);
                setPresetValues(fp.getPresetValues());

                addPageMessage("Validation errors were found when saving the IRB Study data");
                forwardPage(Page.IRB_STUDY);
                return;
            }
            irbStudyBean = createOrUpdateIRBStudyBean();
            //forwardPage(Page.MANAGE_STUDY_MODULE);
            addPageMessage("IRB Study saved");
            response.sendRedirect(request.getContextPath() + Page.MANAGE_STUDY_MODULE.getFileName());
            return;
        }

        irbStudyBean = getIRBStudyDAO().findByStudy(currentStudy);
        /*Locale locale = LocaleResolver.getLocale(request);
        SimpleDateFormat sdf= new SimpleDateFormat("dd-MMM-yyyy", locale);*/
        fp.addPresetValue("studyId", currentStudy.getId());
        request.setAttribute("irbStudyBean", irbStudyBean);

        fp.addPresetValue(INPUT_CDC_IRB_PROTOCOL_NUMBER, irbStudyBean.getCdcIrbProtocolNumber());
        fp.addPresetValue(INPUT_VERSION1_PROTOCOL_DATE,
                irbStudyBean.getVersion1ProtocolDate()!= null?
                        sdf.format(irbStudyBean.getVersion1ProtocolDate()): "");
        fp.addPresetValue(INPUT_PROTOCOL_OFFICER, irbStudyBean.getProtocolOfficer());
        fp.addPresetValue(INPUT_SUBMITTED_CDC_IRB,
                irbStudyBean.getSubmittedCdcIrb()!= null?
                        sdf.format(irbStudyBean.getSubmittedCdcIrb()): "");
        fp.addPresetValue(INPUT_APPROVAL_BY_CDC_IRB,
                irbStudyBean.getApprovalByCdcIrb()!= null?
                        sdf.format(irbStudyBean.getApprovalByCdcIrb()): "");
        fp.addPresetValue(INPUT_CDC_IRB_EXPIRATION_DATE,
                irbStudyBean.getApprovalByCdcIrb()!= null?
                        sdf.format(irbStudyBean.getCdcIrbExpirationDate()): "");

        setPresetValues(fp.getPresetValues());

        forwardPage(Page.IRB_STUDY);
    }

    private IRBStudyBean createOrUpdateIRBStudyBean() throws NumberFormatException, OpenClinicaException {
        IRBStudyBean irbStudyBean = getIRBStudyDAO().findByStudy(currentStudy);
        irbStudyBean.setStudyId(currentStudy.getId());
        irbStudyBean.setCdcIrbProtocolNumber(request.getParameter(INPUT_CDC_IRB_PROTOCOL_NUMBER));
        irbStudyBean.setVersion1ProtocolDate(dateValueOrNull(INPUT_VERSION1_PROTOCOL_DATE));
        irbStudyBean.setProtocolOfficer(request.getParameter(INPUT_PROTOCOL_OFFICER));
        irbStudyBean.setSubmittedCdcIrb(dateValueOrNull(INPUT_SUBMITTED_CDC_IRB));
        irbStudyBean.setApprovalByCdcIrb(dateValueOrNull(INPUT_APPROVAL_BY_CDC_IRB));
        irbStudyBean.setCdcIrbExpirationDate(dateValueOrNull(INPUT_CDC_IRB_EXPIRATION_DATE));

        if(irbStudyBean.getIrbStudyId()<1) {
            irbStudyBean = getIRBStudyDAO().create(irbStudyBean);
        } else {
            irbStudyBean = getIRBStudyDAO().update(irbStudyBean);
        }

        addPageMessage("IRB Study saved");

        return irbStudyBean;
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
        if(request.getParameter(field)!= null && !request.getParameter(field).isEmpty()) {
            retval = Integer.parseInt(request.getParameter(field));
        }

        return retval;
    }
}
