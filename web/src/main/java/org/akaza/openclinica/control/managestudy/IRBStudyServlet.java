package org.akaza.openclinica.control.managestudy;

import javassist.Loader;
import org.akaza.openclinica.bean.core.NumericComparisonOperator;
import org.akaza.openclinica.bean.managestudy.IRBProtocolActionHistoryBean;
import org.akaza.openclinica.bean.managestudy.IRBProtocolActionTypeBean;
import org.akaza.openclinica.bean.managestudy.IRBSiteBean;
import org.akaza.openclinica.bean.managestudy.IRBStudyBean;
import org.akaza.openclinica.control.core.SecureController;
import org.akaza.openclinica.dao.managestudy.IRBProtocolActionTypeDAO;
import org.akaza.openclinica.dao.managestudy.IRBSiteDAO;
import org.akaza.openclinica.control.form.DiscrepancyValidator;
import org.akaza.openclinica.control.form.FormDiscrepancyNotes;
import org.akaza.openclinica.control.form.FormProcessor;
import org.akaza.openclinica.control.form.Validator;
import org.akaza.openclinica.control.submit.AddNewSubjectServlet;
import org.akaza.openclinica.dao.managestudy.IRBStudyDAO;
import org.akaza.openclinica.exception.OpenClinicaException;
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
    public static final String INPUT_CDC_IRB_PROTOCOL_NUMBER = "cdc_irb_protocol_number";
    public static final String INPUT_VERSION1_PROTOCOL_DATE = "version1_protocol_date";
    public static final String INPUT_PROTOCOL_OFFICER = "protocol_officer";
    public static final String INPUT_SUBMITTED_CDC_IRB = "submitted_cdc_irb";
    public static final String INPUT_APPROVAL_BY_CDC_IRB = "approval_by_cdc_irb";
    public static final String INPUT_CDC_IRB_EXPIRATION_DATE = "cdc_irb_expiration_date";


    private IRBStudyDAO getIRBStudyDAO() {
        if(irbStudyDAO==null) irbStudyDAO = new IRBStudyDAO(sm.getDataSource());

        return irbStudyDAO;
    }
    @Override
    protected void processRequest() throws Exception {
        IRBStudyBean irbStudyBean = getIRBStudyDAO().findByStudy(currentStudy);
        if(irbStudyBean == null) irbStudyBean = new IRBStudyBean();
        if(irbStudyBean.getIrbStudyId()<1) irbStudyBean.setStudyId(currentStudy.getId());


        FormProcessor fp = new FormProcessor(request);

        System.out.println("processRequest - fp.isSubmitted()" + fp.isSubmitted());
        if(fp.isSubmitted()) {

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
            //v.addValidation(INPUT_APPROVAL_BY_CDC_IRB, Validator.DATE_IN_PAST);
            // v.addValidation(INPUT_CDC_IRB_EXPIRATION_DATE, Validator.NO_BLANKS);
            v.addValidation(INPUT_CDC_IRB_EXPIRATION_DATE, Validator.IS_A_DATE);
            //v.addValidation(INPUT_CDC_IRB_EXPIRATION_DATE, Validator.DATE_IN_PAST);

            HashMap<String, ArrayList<String>> errors = v.validate();

            String label = fp.getString(INPUT_CDC_IRB_PROTOCOL_NUMBER);

            if (!errors.isEmpty()) {
                setInputMessages(errors);
                //fp.addPresetValue(INPUT_CDC_IRB_PROTOCOL_NUMBER, label);
                //setPresetValues(fp.getPresetValues());
                fp.clearPresetValues();
                setPresetValuesFromRequest(fp);
                setPresetValues(fp.getPresetValues());
                //setPresetValues((HashMap<String, Object>)request.getParameterMap());

                addPageMessage("Validation errors were found when saving the IRB Study data");
                forwardPage(Page.IRB_STUDY);


                return;
            }
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
            //At this point the irb Study Bean has been successfully stored
            addPageMessage("IRB Study saved");
            response.sendRedirect(request.getContextPath() + Page.MANAGE_STUDY_MODULE.getFileName());
            return;
        }


        request.setAttribute("irbStudyBean", irbStudyBean);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
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
