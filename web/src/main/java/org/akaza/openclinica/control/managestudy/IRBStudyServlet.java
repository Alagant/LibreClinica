package org.akaza.openclinica.control.managestudy;

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

        FormProcessor fp = new FormProcessor(request);

        System.out.println("processRequest - fp.isSubmitted()" + fp.isSubmitted());
        if(fp.isSubmitted()) {

            IRBStudyBean irbStudyBean = getIRBStudyDAO().findByStudy(currentStudy);
            if(irbStudyBean == null) irbStudyBean = new IRBStudyBean();
            if(irbStudyBean.getIrbStudyId()<1) irbStudyBean.setStudyId(currentStudy.getId());

            FormDiscrepancyNotes discNotes;

            discNotes = (FormDiscrepancyNotes) session.getAttribute(AddNewSubjectServlet.FORM_DISCREPANCY_NOTES_NAME);
            if (discNotes == null) {
                discNotes = new FormDiscrepancyNotes();
                session.setAttribute(AddNewSubjectServlet.FORM_DISCREPANCY_NOTES_NAME, discNotes);
            }
            DiscrepancyValidator v = new DiscrepancyValidator(request, discNotes);

            v.addValidation(INPUT_CDC_IRB_PROTOCOL_NUMBER, Validator.NO_BLANKS);
            v.addValidation(INPUT_CDC_IRB_PROTOCOL_NUMBER, Validator.LENGTH_NUMERIC_COMPARISON, NumericComparisonOperator.LESS_THAN_OR_EQUAL_TO, 5);
            v.addValidation(INPUT_VERSION1_PROTOCOL_DATE, Validator.NO_BLANKS);
            v.addValidation(INPUT_VERSION1_PROTOCOL_DATE, Validator.IS_A_DATE);
            v.addValidation(INPUT_VERSION1_PROTOCOL_DATE, Validator.DATE_IN_PAST);
            v.addValidation(INPUT_PROTOCOL_OFFICER, Validator.NO_BLANKS);
            v.addValidation(INPUT_PROTOCOL_OFFICER, Validator.LENGTH_NUMERIC_COMPARISON, NumericComparisonOperator.LESS_THAN_OR_EQUAL_TO, 20);
           //  v.addValidation(INPUT_SUBMITTED_CDC_IRB, Validator.NO_BLANKS);
            v.addValidation(INPUT_SUBMITTED_CDC_IRB, Validator.IS_A_DATE);
            v.addValidation(INPUT_SUBMITTED_CDC_IRB, Validator.DATE_IN_PAST);
            // v.addValidation(INPUT_APPROVAL_BY_CDC_IRB, Validator.NO_BLANKS);
            v.addValidation(INPUT_APPROVAL_BY_CDC_IRB, Validator.IS_A_DATE);
            v.addValidation(INPUT_APPROVAL_BY_CDC_IRB, Validator.DATE_IN_PAST);
            // v.addValidation(INPUT_CDC_IRB_EXPIRATION_DATE, Validator.NO_BLANKS);
            v.addValidation(INPUT_CDC_IRB_EXPIRATION_DATE, Validator.IS_A_DATE);
            v.addValidation(INPUT_CDC_IRB_EXPIRATION_DATE, Validator.DATE_IN_PAST);

            HashMap<String, ArrayList<String>> errors = v.validate();

            String label = fp.getString(INPUT_CDC_IRB_PROTOCOL_NUMBER);

            if (!errors.isEmpty()) {
                setInputMessages(errors);
                fp.addPresetValue(INPUT_CDC_IRB_PROTOCOL_NUMBER, label);
                setPresetValues(fp.getPresetValues());

            } else {
                irbStudyBean.setCdcIrbProtocolNumber(request.getParameter("cdc_irb_protocol_number"));
                irbStudyBean.setVersion1ProtocolDate(dateValueOrNull("version1_protocol_date"));
                irbStudyBean.setProtocolOfficer(request.getParameter("protocol_officer"));
                irbStudyBean.setSubmittedCdcIrb(dateValueOrNull("submitted_cdc_irb"));
                irbStudyBean.setApprovalByCdcIrb(dateValueOrNull("approval_by_cdc_irb"));
                irbStudyBean.setCdcIrbExpirationDate(dateValueOrNull("cdc_irb_expiration_date"));

                if(irbStudyBean.getIrbStudyId()<1) {
                    getIRBStudyDAO().create(irbStudyBean);
                } else {
                    getIRBStudyDAO().update(irbStudyBean);
                }
            }

        } else {

            IRBStudyBean irbStudyBean = getIRBStudyDAO().findByStudy(currentStudy);
            request.setAttribute("irbStudyBean", irbStudyBean);

            Date today = new Date(System.currentTimeMillis());
            String todayFormatted = local_df.format(today);
            fp.addPresetValue(INPUT_VERSION1_PROTOCOL_DATE, todayFormatted);

            setPresetValues(fp.getPresetValues());

        }

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
