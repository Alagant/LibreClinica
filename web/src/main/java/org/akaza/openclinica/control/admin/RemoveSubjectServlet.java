/*
 * LibreClinica is distributed under the
 * GNU Lesser General Public License (GNU LGPL).

 * For details see: https://libreclinica.org/license
 * LibreClinica, copyright (C) 2020
 */
package org.akaza.openclinica.control.admin;

import java.util.ArrayList;
import java.util.Date;

import org.akaza.openclinica.bean.core.Status;
import org.akaza.openclinica.bean.managestudy.StudyEventBean;
import org.akaza.openclinica.bean.managestudy.StudySubjectBean;
import org.akaza.openclinica.bean.submit.EventCRFBean;
import org.akaza.openclinica.bean.submit.ItemDataBean;
import org.akaza.openclinica.bean.submit.SubjectBean;
import org.akaza.openclinica.control.core.SecureController;
import org.akaza.openclinica.control.form.FormProcessor;
import org.akaza.openclinica.dao.managestudy.StudyEventDAO;
import org.akaza.openclinica.dao.managestudy.StudySubjectDAO;
import org.akaza.openclinica.dao.submit.EventCRFDAO;
import org.akaza.openclinica.dao.submit.ItemDataDAO;
import org.akaza.openclinica.dao.submit.SubjectDAO;
import org.akaza.openclinica.view.Page;
import org.akaza.openclinica.web.InsufficientPermissionException;

/**
 * @author jxu
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RemoveSubjectServlet extends SecureController {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1934219170299408831L;

	/**
     *
     */
    @Override
    public void mayProceed() throws InsufficientPermissionException {
        checkStudyLocked(Page.LIST_STUDY_SUBJECTS, respage.getString("current_study_locked"));

        if (ub.isSysAdmin()) {
            return;
        }

        addPageMessage(respage.getString("no_have_correct_privilege_current_study") + respage.getString("change_study_contact_sysadmin"));
        throw new InsufficientPermissionException(Page.SUBJECT_LIST_SERVLET, resexception.getString("not_admin"), "1");

    }

    @Override
    public void processRequest() throws Exception {

        SubjectDAO sdao = new SubjectDAO(sm.getDataSource());
        FormProcessor fp = new FormProcessor(request);
        int subjectId = fp.getInt("id");

        String action = fp.getString("action");
        if (subjectId == 0 || action == null || action.trim().isEmpty()) {
            addPageMessage(respage.getString("please_choose_a_subject_to_remove"));
            forwardPage(Page.SUBJECT_LIST_SERVLET);
        } else {

            SubjectBean subject = (SubjectBean) sdao.findByPK(subjectId);

            // find all study subjects
            StudySubjectDAO ssdao = new StudySubjectDAO(sm.getDataSource());
            ArrayList<StudySubjectBean> studySubs = ssdao.findAllBySubjectId(subjectId);

            // find study events
            StudyEventDAO sedao = new StudyEventDAO(sm.getDataSource());
            ArrayList<StudyEventBean> events = sedao.findAllBySubjectId(subjectId);
            if ("confirm".equalsIgnoreCase(action)) {
                request.setAttribute("subjectToRemove", subject);
                request.setAttribute("studySubs", studySubs);
                request.setAttribute("events", events);
                forwardPage(Page.REMOVE_SUBJECT);
            } else {
                logger.info("submit to remove the subject");
                // change all statuses to deleted
                subject.setStatus(Status.DELETED);
                subject.setUpdater(ub);
                subject.setUpdatedDate(new Date());
                sdao.update(subject);

                // remove subject references from study
                for (int i = 0; i < studySubs.size(); i++) {
                    StudySubjectBean studySub = (StudySubjectBean) studySubs.get(i);
                    if (!studySub.getStatus().equals(Status.DELETED)) {
                        studySub.setStatus(Status.AUTO_DELETED);
                        studySub.setUpdater(ub);
                        studySub.setUpdatedDate(new Date());
                        ssdao.update(studySub);
                    }
                }

                EventCRFDAO ecdao = new EventCRFDAO(sm.getDataSource());

                for (int j = 0; j < events.size(); j++) {
                    StudyEventBean event = (StudyEventBean) events.get(j);
                    if (!event.getStatus().equals(Status.DELETED)) {
                        event.setStatus(Status.AUTO_DELETED);
                        event.setUpdater(ub);
                        event.setUpdatedDate(new Date());
                        sedao.update(event);

                        ArrayList<EventCRFBean> eventCRFs = ecdao.findAllByStudyEvent(event);

                        ItemDataDAO iddao = new ItemDataDAO(sm.getDataSource());
                        for (int k = 0; k < eventCRFs.size(); k++) {
                            EventCRFBean eventCRF = (EventCRFBean) eventCRFs.get(k);
                            if (!eventCRF.getStatus().equals(Status.DELETED)) {
                                eventCRF.setStatus(Status.AUTO_DELETED);
                                eventCRF.setUpdater(ub);
                                eventCRF.setUpdatedDate(new Date());
                                ecdao.update(eventCRF);
                                // remove all the item data
                                ArrayList<ItemDataBean> itemDatas = iddao.findAllByEventCRFId(eventCRF.getId());
                                for (int a = 0; a < itemDatas.size(); a++) {
                                    ItemDataBean item = (ItemDataBean) itemDatas.get(a);
                                    if (!item.getStatus().equals(Status.DELETED)) {
                                        item.setStatus(Status.AUTO_DELETED);
                                        item.setUpdater(ub);
                                        item.setUpdatedDate(new Date());
                                        iddao.update(item);
                                    }
                                }
                            }
                        }
                    }
                }

                String emailBody = respage.getString("the_subject") + " "+ subject.getUniqueIdentifier() + " " + respage.getString("has_been_removed_succesfully");

                addPageMessage(emailBody);
//                sendEmail(emailBody);

                forwardPage(Page.SUBJECT_LIST_SERVLET);

            }
        }

    }

    @Override
    protected String getAdminServlet() {
        return SecureController.ADMIN_SERVLET_CODE;
    }

}
