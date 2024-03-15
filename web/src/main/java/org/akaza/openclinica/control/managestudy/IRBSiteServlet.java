package org.akaza.openclinica.control.managestudy;

import org.akaza.openclinica.control.core.SecureController;
import org.akaza.openclinica.view.Page;
import org.akaza.openclinica.web.InsufficientPermissionException;

public class IRBSiteServlet extends SecureController {
    @Override
    protected void processRequest() throws Exception {
        forwardPage(Page.IRB_SITE);
    }

    @Override
    protected void mayProceed() throws InsufficientPermissionException {

    }
}
