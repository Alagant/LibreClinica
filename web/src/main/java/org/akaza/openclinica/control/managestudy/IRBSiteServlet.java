package org.akaza.openclinica.control.managestudy;

import org.akaza.openclinica.bean.managestudy.IRBSiteBean;
import org.akaza.openclinica.bean.managestudy.IRBStudyBean;
import org.akaza.openclinica.control.core.SecureController;
import org.akaza.openclinica.dao.managestudy.IRBSiteDAO;
import org.akaza.openclinica.dao.managestudy.IRBStudyDAO;
import org.akaza.openclinica.view.Page;
import org.akaza.openclinica.web.InsufficientPermissionException;

public class IRBSiteServlet extends SecureController {
    @Override
    protected void processRequest() throws Exception {
        IRBSiteDAO irbSiteDAO = new IRBSiteDAO(sm.getDataSource());
        String siteId = request.getParameter("id");
        if(siteId==null || siteId.isEmpty()) {
            addPageMessage("Invalid site id");
            forwardPage(Page.VIEW_STUDY);
            return;
        }

        IRBSiteBean irbSiteBean = irbSiteDAO.findBySiteId(siteId);
        request.setAttribute("irbSiteBean", irbSiteBean);
        forwardPage(Page.IRB_SITE);
    }

    @Override
    protected void mayProceed() throws InsufficientPermissionException {

    }
}
