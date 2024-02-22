package org.akaza.openclinica.control.managestudy;

import org.akaza.openclinica.bean.managestudy.ProtocolDeviationBean;
import org.akaza.openclinica.control.core.SecureController;
import org.akaza.openclinica.control.submit.ListStudySubjectTableFactory;
import org.akaza.openclinica.control.submit.ProtocolDeviationTableFactory;
import org.akaza.openclinica.dao.managestudy.ProtocolDeviationDAO;
import org.akaza.openclinica.dao.managestudy.StudyDAO;
import org.akaza.openclinica.dao.managestudy.StudySubjectDAO;
import org.akaza.openclinica.view.Page;
import org.akaza.openclinica.web.InsufficientPermissionException;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;

public class ProtocolDeviationServlet extends SecureController {
    @Override
    protected void processRequest() throws Exception {
        ProtocolDeviationDAO protocolDeviationDAO = new ProtocolDeviationDAO(sm.getDataSource());
        if(request.getMethod().compareTo("POST")==0) {
            int protocolDeviationId = -1;
            try {
                protocolDeviationId = Integer.parseInt(
                        request.getParameter("protocolDeviationId")
                );
            }
            catch(NumberFormatException ex) {
                addPageMessage("Protocol deviation id has an incorrect format");
            }

            String[] subjectsId = request.getParameterValues("subjects[]");
            ProtocolDeviationBean pdb = protocolDeviationDAO.findByPKAndStudy(protocolDeviationId, currentStudy);


            if(pdb.getId()<1) {
                pdb = protocolDeviationDAO.create(pdb);
            }
            else {
                protocolDeviationDAO.update(pdb);
            }
        }


        ArrayList<ProtocolDeviationBean> protocolDeviations =
                protocolDeviationDAO.findByStudy(currentStudy.getId());
        StudyDAO studyDAO = new StudyDAO(sm.getDataSource());
        StudySubjectDAO studySubjectDAO = new StudySubjectDAO(sm.getDataSource());

        ProtocolDeviationTableFactory  factory = new ProtocolDeviationTableFactory();
        request.setAttribute("protocolDeviations", protocolDeviations);

        String findProtocolDeviationsHtml = factory.createTable(request, response).render();
        request.setAttribute("findProtocolDeviationsHtml", findProtocolDeviationsHtml);
        request.setAttribute("subjects", studySubjectDAO.findAllByStudyId(currentStudy.getId()));


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
