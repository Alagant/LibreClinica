package org.akaza.openclinica.control.managestudy;

import org.akaza.openclinica.bean.managestudy.IRBStudyBean;
import org.akaza.openclinica.control.core.SecureController;
import org.akaza.openclinica.dao.managestudy.IRBStudyDAO;
import org.akaza.openclinica.view.Page;
import org.akaza.openclinica.web.InsufficientPermissionException;

public class IRBStudyServlet extends SecureController {
    @Override
    protected void processRequest() throws Exception {
        IRBStudyDAO irbStudyDAO = new IRBStudyDAO(sm.getDataSource());

        IRBStudyBean irbStudyBean = irbStudyDAO.findByStudy(currentStudy);
        request.setAttribute("irbStudyBean", irbStudyBean);
        forwardPage(Page.IRB_STUDY);
    }

    @Override
    protected void mayProceed() throws InsufficientPermissionException {

    }
}
