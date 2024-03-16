package org.akaza.openclinica.control.managestudy;

import org.akaza.openclinica.bean.managestudy.IRBSiteBean;
import org.akaza.openclinica.control.core.SecureController;
import org.akaza.openclinica.dao.managestudy.IRBSiteDAO;
import org.akaza.openclinica.exception.OpenClinicaException;
import org.akaza.openclinica.view.Page;
import org.akaza.openclinica.web.InsufficientPermissionException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class IRBSiteServlet extends SecureController {
    private IRBSiteDAO irbSiteDAO;

    private IRBSiteDAO getIRBSiteDAO() {
        if(irbSiteDAO==null) irbSiteDAO = new IRBSiteDAO(sm.getDataSource());

        return irbSiteDAO;
    }
    @Override
    protected void processRequest() throws Exception {
        if(request.getMethod().compareToIgnoreCase("POST")==0) {
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


        IRBSiteBean irbSiteBean = getIRBSiteDAO().findBySiteId(siteId);
        request.setAttribute("siteId", siteId);
        request.setAttribute("irbSiteBean", irbSiteBean);
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
        if(request.getParameter(field)!= null) {
            retval = Integer.parseInt(request.getParameter(field));
        }

        return retval;
    }
}
