package org.akaza.openclinica.control.managestudy;

import org.akaza.openclinica.bean.managestudy.ProtocolDeviationBean;
import org.akaza.openclinica.bean.managestudy.ProtocolDeviationSubjectBean;
import org.akaza.openclinica.control.core.SecureController;
import org.akaza.openclinica.control.submit.ListStudySubjectTableFactory;
import org.akaza.openclinica.control.submit.ProtocolDeviationTableFactory;
import org.akaza.openclinica.dao.managestudy.ProtocolDeviationDAO;
import org.akaza.openclinica.dao.managestudy.ProtocolDeviationSubjectDAO;
import org.akaza.openclinica.dao.managestudy.StudyDAO;
import org.akaza.openclinica.dao.managestudy.StudySubjectDAO;
import org.akaza.openclinica.domain.managestudy.ProtocolDeviationSubject;
import org.akaza.openclinica.view.Page;
import org.akaza.openclinica.web.InsufficientPermissionException;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;

public class ProtocolDeviationServlet extends SecureController {
    private void processSaveProtocolDeviation() {
        ProtocolDeviationDAO protocolDeviationDAO = new ProtocolDeviationDAO(sm.getDataSource());

        int protocolDeviationId = -1;
        try {
            if(request.getParameter("protocolDeviationId") != null)
                protocolDeviationId = Integer.parseInt(
                        request.getParameter("protocolDeviationId")
                );
        }
        catch(NumberFormatException ex) {
            addPageMessage("Protocol deviation id not found or it has an incorrect format");
            return;
        }

        String[] subjectsId = request.getParameterValues("subjects[]");
        ProtocolDeviationBean pdb = protocolDeviationDAO.findByPKAndStudy(protocolDeviationId, currentStudy);

        ProtocolDeviationSubjectDAO protocolDeviationSubjectDAO =
                new ProtocolDeviationSubjectDAO(sm.getDataSource());

        if(pdb == null || pdb.getId()<1) {
            pdb.setStudyId(currentStudy.getId());
            pdb = protocolDeviationDAO.create(pdb);

            //add subjects to the protocol deviation
            for(String s: subjectsId) {
                int subjectId = Integer.parseInt(s);
                ProtocolDeviationSubjectBean pdsb = protocolDeviationSubjectDAO
                        .findSubjectById(pdb.getId(), subjectId);
                if(pdsb == null || pdsb.getId()<1) {
                    pdsb = new ProtocolDeviationSubjectBean();
                    pdsb.setProtocolDeviationId(pdb.getId());
                    pdsb.setSubjectId(subjectId);
                    protocolDeviationSubjectDAO.create(pdsb);
                }
            }
        }


    }
    @Override
    protected void processRequest() throws Exception {
        ProtocolDeviationDAO protocolDeviationDAO = new ProtocolDeviationDAO(sm.getDataSource());

        if(request.getMethod().compareTo("POST")==0)
            processSaveProtocolDeviation();
        else if(request.getParameter("action")=="get" && request.getParameter("pdid")!= null) {
            getProtocolDeviationWithSubjects(request.getParameter("pdid"));
            return;
        }

        ArrayList<ProtocolDeviationBean> protocolDeviations =
                protocolDeviationDAO.findByStudy(currentStudy.getId());

        request.setAttribute("protocolDeviations", protocolDeviations);

        createTable();


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

    private void getProtocolDeviationWithSubjects(String pdid) {
    }

    private void createTable() {
        ProtocolDeviationDAO protocolDeviationDAO = new ProtocolDeviationDAO(sm.getDataSource());
        StudyDAO studyDAO = new StudyDAO(sm.getDataSource());
        StudySubjectDAO studySubjectDAO = new StudySubjectDAO(sm.getDataSource());

        ProtocolDeviationTableFactory  factory = new ProtocolDeviationTableFactory();
        factory.setStudyBean(currentStudy);
        factory.setProtocolDeviationDAO(protocolDeviationDAO);

        String findProtocolDeviationsHtml = factory.createTable(request, response).render();
        request.setAttribute("findProtocolDeviationsHtml", findProtocolDeviationsHtml);
        request.setAttribute("subjects", studySubjectDAO.findAllByStudyId(currentStudy.getId()));
    }

    @Override
    protected void mayProceed() throws InsufficientPermissionException {

    }
}
