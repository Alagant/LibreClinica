package org.akaza.openclinica.control.managestudy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.akaza.openclinica.bean.managestudy.ProtocolDeviationBean;
import org.akaza.openclinica.bean.managestudy.ProtocolDeviationSeverityBean;
import org.akaza.openclinica.bean.managestudy.ProtocolDeviationSubjectBean;
import org.akaza.openclinica.control.core.SecureController;
import org.akaza.openclinica.control.submit.ListStudySubjectTableFactory;
import org.akaza.openclinica.control.submit.ProtocolDeviationTableFactory;
import org.akaza.openclinica.dao.managestudy.*;
import org.akaza.openclinica.domain.managestudy.ProtocolDeviationSubject;
import org.akaza.openclinica.exception.OpenClinicaException;
import org.akaza.openclinica.view.Page;
import org.akaza.openclinica.web.InsufficientPermissionException;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ProtocolDeviationServlet extends SecureController {
    private ProtocolDeviationSeverityDAO protocolDeviationSeverityDAO;
    private ProtocolDeviationDAO protocolDeviationDAO;
    private ProtocolDeviationSubjectDAO protocolDeviationSubjectDAO;
    private ProtocolDeviationSeverityDAO getProtocolDeviationSeverityDAO() {
        if(protocolDeviationSeverityDAO == null)
            protocolDeviationSeverityDAO = new ProtocolDeviationSeverityDAO(sm.getDataSource());

        return protocolDeviationSeverityDAO;
    }

    private ProtocolDeviationDAO getProtocolDeviationDAO() {
        if(protocolDeviationDAO == null)
            protocolDeviationDAO = new ProtocolDeviationDAO(sm.getDataSource());

        return protocolDeviationDAO;
    }

    private ProtocolDeviationSubjectDAO getProtocolDeviationSubjectDAO() {
        if(protocolDeviationSubjectDAO == null)
            protocolDeviationSubjectDAO = new ProtocolDeviationSubjectDAO(sm.getDataSource());

        return protocolDeviationSubjectDAO;
    }

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
            pdb.setDescription(request.getParameter("description"));
            pdb.setSeverityId( Integer.parseInt(request.getParameter("severity")) );
            pdb = protocolDeviationDAO.create(pdb);

            //add subjects to the protocol deviation
            for(String s: subjectsId) {
                int subjectId = Integer.parseInt(s);
                ProtocolDeviationSubjectBean pdsb = protocolDeviationSubjectDAO
                        .findSubjectById(pdb.getProtocolDeviationId(), subjectId);
                if(pdsb == null || pdsb.getId()<1) {
                    pdsb = new ProtocolDeviationSubjectBean();
                    pdsb.setProtocolDeviationId(pdb.getProtocolDeviationId());
                    pdsb.setSubjectId(subjectId);
                    protocolDeviationSubjectDAO.create(pdsb);
                }
            }
        }


    }
    @Override
    protected void processRequest() throws Exception {
        String action = request.getParameter("action");

        if(request.getMethod().compareTo("POST")==0)
            processSaveProtocolDeviation();
        else if(action != null && (action.compareTo("get")==0) &&
                request.getParameter("pdid") != null) {
            String stringProtocolDeviationId = request.getParameter("pdid");
            int protocolDeviationId = Integer.parseInt(stringProtocolDeviationId);
            getProtocolDeviationWithSubjects(protocolDeviationId);
            return;
        }
        ArrayList<ProtocolDeviationSeverityBean> severities =
                getProtocolDeviationSeverityDAO().findAll();

        ArrayList<ProtocolDeviationBean> protocolDeviations =
                getProtocolDeviationDAO().findByStudy(currentStudy.getId());

        request.setAttribute("protocolDeviations", protocolDeviations);
        request.setAttribute("severities", severities);

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

    private void getProtocolDeviationWithSubjects(int id) throws IOException {
        PrintWriter printWriter = response.getWriter();;
        ProtocolDeviationBean protocolDeviationBean =
                getProtocolDeviationDAO().findByPKAndStudy(id, currentStudy);
        protocolDeviationBean.setSubjects(
                getProtocolDeviationSubjectDAO()
                        .findByProtocolDeviation(protocolDeviationBean.getId())
        );

        response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        printWriter.print(objectWriter.writeValueAsString(protocolDeviationBean));
    }

    private void createTable() {
        StudyDAO studyDAO = new StudyDAO(sm.getDataSource());
        StudySubjectDAO studySubjectDAO = new StudySubjectDAO(sm.getDataSource());

        ProtocolDeviationTableFactory  factory = new ProtocolDeviationTableFactory();
        factory.setStudyBean(currentStudy);
        factory.setProtocolDeviationDAO(getProtocolDeviationDAO());

        String findProtocolDeviationsHtml = factory.createTable(request, response).render();
        request.setAttribute("findProtocolDeviationsHtml", findProtocolDeviationsHtml);
        request.setAttribute("subjects", studySubjectDAO.findAllByStudyId(currentStudy.getId()));
    }

    @Override
    protected void mayProceed() throws InsufficientPermissionException {

    }
}
