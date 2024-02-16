package org.akaza.openclinica.control.managestudy;

import org.akaza.openclinica.bean.managestudy.ProtocolDeviationBean;
import org.akaza.openclinica.control.core.SecureController;
import org.akaza.openclinica.control.submit.ListStudySubjectTableFactory;
import org.akaza.openclinica.control.submit.ProtocolDeviationTableFactory;
import org.akaza.openclinica.dao.managestudy.ProtocolDeviationDAO;
import org.akaza.openclinica.view.Page;
import org.akaza.openclinica.web.InsufficientPermissionException;

import java.util.ArrayList;

public class ProtocolDeviationServlet extends SecureController {
    @Override
    protected void processRequest() throws Exception {

        ProtocolDeviationDAO protocolDeviationDAO = new ProtocolDeviationDAO(sm.getDataSource());
        ArrayList<ProtocolDeviationBean> protocolDeviations =
                protocolDeviationDAO.findByStudy(currentStudy.getId());

        ProtocolDeviationTableFactory  factory = new ProtocolDeviationTableFactory();

        String findProtocolDeviationsHtml = factory.createTable(request, response).render();
        request.setAttribute("findProtocolDeviationsHtml", findProtocolDeviationsHtml);

        forwardPage(Page.PROTOCOL_DEVIATIONS);
        /*
        ListStudySubjectTableFactory factory = new ListStudySubjectTableFactory(showMoreLink);
        factory.setStudyEventDefinitionDao(getStudyEventDefinitionDao());
        factory.setSubjectDAO(getSubjectDAO());
        factory.setStudySubjectDAO(getStudySubjectDAO());
        factory.setStudyEventDAO(getStudyEventDAO());
        factory.setStudyBean(currentStudy);
        factory.setStudyGroupClassDAO(getStudyGroupClassDAO());
        factory.setSubjectGroupMapDAO(getSubjectGroupMapDAO());
        factory.setStudyDAO(getStudyDAO());
        factory.setCurrentRole(currentRole);
        factory.setCurrentUser(ub);
        factory.setEventCRFDAO(getEventCRFDAO());
        factory.setEventDefintionCRFDAO(getEventDefinitionCRFDAO());
        factory.setStudyGroupDAO(getStudyGroupDAO());
        factory.setStudyParameterValueDAO(getStudyParameterValueDAO());
        String findSubjectsHtml = factory.createTable(request, response).render();

        request.setAttribute("findSubjectsHtml", findSubjectsHtml);
        * */
    }

    @Override
    protected void mayProceed() throws InsufficientPermissionException {

    }
}
