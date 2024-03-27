/*
 * LibreClinica is distributed under the
 * GNU Lesser General Public License (GNU LGPL).

 * For details see: https://libreclinica.org/license
 * LibreClinica, copyright (C) 2020
 */
package org.akaza.openclinica.control.managestudy;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.akaza.openclinica.bean.admin.CRFBean;
import org.akaza.openclinica.bean.core.Role;
import org.akaza.openclinica.bean.core.Status;
import org.akaza.openclinica.bean.managestudy.EventDefinitionCRFBean;
import org.akaza.openclinica.bean.managestudy.LabsForSiteBean;
import org.akaza.openclinica.bean.managestudy.StudyBean;
import org.akaza.openclinica.bean.managestudy.StudyEventDefinitionBean;
import org.akaza.openclinica.bean.service.StudyParameterValueBean;
import org.akaza.openclinica.bean.service.StudyParamsConfig;
import org.akaza.openclinica.bean.submit.CRFVersionBean;
import org.akaza.openclinica.control.SpringServletAccess;
import org.akaza.openclinica.control.core.SecureController;
import org.akaza.openclinica.control.form.FormProcessor;
import org.akaza.openclinica.dao.admin.CRFDAO;
import org.akaza.openclinica.dao.managestudy.*;
import org.akaza.openclinica.dao.service.StudyParameterValueDAO;
import org.akaza.openclinica.dao.submit.CRFVersionDAO;
import org.akaza.openclinica.domain.SourceDataVerification;
import org.akaza.openclinica.service.managestudy.EventDefinitionCrfTagService;
import org.akaza.openclinica.view.Page;
import org.akaza.openclinica.web.InsufficientPermissionException;

import com.sun.research.ws.wadl.Request;

/**
 * @author jxu
 *
 * @version CVS: $Id: InitUpdateSubStudyServlet.java 9834 2007-09-05 22:28:31Z
 *          jxu $
 */
public class InitUpdateSubStudyServlet extends SecureController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1348293062808929660L;
	EventDefinitionCrfTagService eventDefinitionCrfTagService = null;

	/**
	 *
	 */
	@Override
	public void mayProceed() throws InsufficientPermissionException {
		checkStudyLocked(Page.SITE_LIST_SERVLET, respage.getString("current_study_locked"));
		if (ub.isSysAdmin()) {
			return;
		}
		if (currentRole.getRole().equals(Role.STUDYDIRECTOR) || currentRole.getRole().equals(Role.COORDINATOR)) {
			return;
		}

		addPageMessage(respage.getString("no_have_correct_privilege_current_study")
				+ respage.getString("change_study_contact_sysadmin"));
		throw new InsufficientPermissionException(Page.MENU_SERVLET, resexception.getString("not_study_director"), "1");

	}

	@Override
	public void processRequest() throws Exception {
		StudyDAO sdao = new StudyDAO(sm.getDataSource());
		String idString = request.getParameter("id");
		logger.info("study id:" + idString);
		if (idString == null || idString.trim().isEmpty()) {
			addPageMessage(respage.getString("please_choose_a_study_to_edit"));
			forwardPage(Page.STUDY_LIST_SERVLET);
		} else {
			int studyId = Integer.valueOf(idString.trim()).intValue();
			StudyBean study = (StudyBean) sdao.findByPK(studyId);

			checkRoleByUserAndStudy(ub, study.getParentStudyId(), study.getId());

			String parentStudyName = "";
			StudyBean parent = new StudyBean();
			if (study.getParentStudyId() > 0) {
				parent = (StudyBean) sdao.findByPK(study.getParentStudyId());
				parentStudyName = parent.getName();
				// at this time, this feature is only available for site
				createEventDefinitions(parent);
			}

			if (currentStudy.getId() != study.getId()) {
				ArrayList<StudyParamsConfig> parentConfigs = currentStudy.getStudyParameters();
				// logger.info("parentConfigs size:" + parentConfigs.size());
				ArrayList<StudyParamsConfig> configs = new ArrayList<>();
				StudyParameterValueDAO spvdao = new StudyParameterValueDAO(sm.getDataSource());
				for (int i = 0; i < parentConfigs.size(); i++) {
					StudyParamsConfig scg = (StudyParamsConfig) parentConfigs.get(i);
					if (scg != null) {
						// find the one that sub study can change
						if (scg.getValue().getId() > 0 && scg.getParameter().isOverridable()) {
							// logger.info("parameter:" +
							// scg.getParameter().getHandle());
							// logger.info("value:" +
							// scg.getValue().getValue());
							StudyParameterValueBean spvb = spvdao.findByHandleAndStudy(study.getId(),
									scg.getParameter().getHandle());
							if (spvb.getValue().equals("enabled"))
								baseUrl();
							if (spvb.getId() > 0) {
								// the sub study itself has the parameter
								scg.setValue(spvb);
							}
							configs.add(scg);
						}
					}

				}

				study.setStudyParameters(configs);
			}
			request.setAttribute("parentStudy", parent);
			session.setAttribute("parentName", parentStudyName);
			session.setAttribute("newStudy", study);
			request.setAttribute("facRecruitStatusMap", CreateStudyServlet.facRecruitStatusMap);
			request.setAttribute("statuses", Status.toStudyUpdateMembersList());
			LaboratoryDAO laboratoryDAO = new LaboratoryDAO(sm.getDataSource());
			List laboratories = laboratoryDAO.findAll();
			request.setAttribute("laboratories", laboratories);
			CountryDAO countryDAO = new CountryDAO(sm.getDataSource());
			List countries = countryDAO.findAll();
			request.setAttribute("countries", countries);
			LabsForSiteDAO labsForSiteDAO = new LabsForSiteDAO(sm.getDataSource());
			List labsForSite = labsForSiteDAO.findBySiteId(study.getId());
			if (labsForSite.isEmpty()){
				//Does the migration now from the old system
				LabsForSiteBean lab_for_site = labsForSiteDAO.emptyBean();
				lab_for_site.setSite_id(study.getId());
				lab_for_site.setLaboratory_id(study.getLaboratoryId());
				labsForSiteDAO.create(lab_for_site);
			}
			//labsForSite
			request.setAttribute("labsForSite", labsForSite);

			FormProcessor fp = new FormProcessor(request);
			logger.info("start date:" + study.getDatePlannedEnd());
			if (study.getDatePlannedEnd() != null) {
				fp.addPresetValue(UpdateSubStudyServlet.INPUT_END_DATE, local_df.format(study.getDatePlannedEnd()));
			}
			if (study.getDatePlannedStart() != null) {
				fp.addPresetValue(UpdateSubStudyServlet.INPUT_START_DATE, local_df.format(study.getDatePlannedStart()));
			}
			if (study.getProtocolDateVerification() != null) {
				fp.addPresetValue(UpdateSubStudyServlet.INPUT_VER_DATE,
						local_df.format(study.getProtocolDateVerification()));
			}
			if (study.getFwaExpirationDate() != null) {
				fp.addPresetValue(UpdateSubStudyServlet.FWA_EXPIRATION_DATE,
						local_df.format(study.getFwaExpirationDate()));
			}
			setPresetValues(fp.getPresetValues());

			forwardPage(Page.UPDATE_SUB_STUDY);
		}

	}

	private void createEventDefinitions(StudyBean parentStudy) throws MalformedURLException {
		StudyParameterValueDAO spvdao = new StudyParameterValueDAO(sm.getDataSource());

		int siteId = Integer.valueOf(request.getParameter("id").trim());
		ArrayList<StudyEventDefinitionBean> seds = new ArrayList<StudyEventDefinitionBean>();
		StudyEventDefinitionDAO sedDao = new StudyEventDefinitionDAO(sm.getDataSource());
		EventDefinitionCRFDAO edcdao = new EventDefinitionCRFDAO(sm.getDataSource());
		CRFVersionDAO cvdao = new CRFVersionDAO(sm.getDataSource());
		CRFDAO cdao = new CRFDAO(sm.getDataSource());
		seds = sedDao.findAllByStudy(parentStudy);
		for (StudyEventDefinitionBean sed : seds) {
			String participateFormStatus = spvdao.findByHandleAndStudy(sed.getStudyId(), "participantPortal")
					.getValue();
			if (participateFormStatus.equals("enabled"))
				baseUrl();
			request.setAttribute("participateFormStatus", participateFormStatus);

			int defId = sed.getId();
			ArrayList<EventDefinitionCRFBean> edcs = (ArrayList<EventDefinitionCRFBean>) edcdao
					.findAllByDefinitionAndSiteIdAndParentStudyId(defId, siteId, parentStudy.getId());
			ArrayList<EventDefinitionCRFBean> defCrfs = new ArrayList<EventDefinitionCRFBean>();
			// sed.setCrfNum(edcs.size());
			for (EventDefinitionCRFBean edcBean : edcs) {
				CRFBean cBean = (CRFBean) cdao.findByPK(edcBean.getCrfId());
				String crfPath = sed.getOid() + "." + cBean.getOid();
				edcBean.setOffline(getEventDefinitionCrfTagService().getEventDefnCrfOfflineStatus(2, crfPath, true));

				int edcStatusId = edcBean.getStatus().getId();
				CRFBean crf = (CRFBean) cdao.findByPK(edcBean.getCrfId());
				int crfStatusId = crf.getStatusId();
				if (edcStatusId == 5 || edcStatusId == 7 || crfStatusId == 5 || crfStatusId == 7) {
				} else {
					ArrayList<CRFVersionBean> versions = (ArrayList<CRFVersionBean>) cvdao
							.findAllActiveByCRF(edcBean.getCrfId());
					edcBean.setVersions(versions);
					edcBean.setCrfName(crf.getName());

					if (edcBean.getParentId() == 0)
						edcBean.setSubmissionUrl("");

					CRFVersionBean defaultVersion = (CRFVersionBean) cvdao.findByPK(edcBean.getDefaultVersionId());
					edcBean.setDefaultVersionName(defaultVersion.getName());
					String sversionIds = edcBean.getSelectedVersionIds();

					ArrayList<Integer> idList = new ArrayList<Integer>();
					if (sversionIds.length() > 0) {
						String[] ids = sversionIds.split("\\,");
						for (String id : ids) {
							idList.add(Integer.valueOf(id));
						}
					}
					edcBean.setSelectedVersionIdList(idList);
					defCrfs.add(edcBean);
				}
			}
			logger.debug("definitionCrfs size=" + defCrfs.size() + " total size=" + edcs.size());
			sed.setCrfs(defCrfs);
			sed.setCrfNum(defCrfs.size());
		}
		// not sure if request is better, since not sure if there is another
		// process using this.
		session.setAttribute("definitions", seds);
		session.setAttribute("sdvOptions", this.setSDVOptions());
	}

	private ArrayList<String> setSDVOptions() {
		ArrayList<String> sdvOptions = new ArrayList<String>();
		sdvOptions.add(SourceDataVerification.AllREQUIRED.toString());
		sdvOptions.add(SourceDataVerification.PARTIALREQUIRED.toString());
		sdvOptions.add(SourceDataVerification.NOTREQUIRED.toString());
		sdvOptions.add(SourceDataVerification.NOTAPPLICABLE.toString());
		return sdvOptions;
	}

	@Override
	protected String getAdminServlet() {
		if (ub.isSysAdmin()) {
			return SecureController.ADMIN_SERVLET_CODE;
		} else {
			return "";
		}
	}

	public EventDefinitionCrfTagService getEventDefinitionCrfTagService() {
		eventDefinitionCrfTagService = this.eventDefinitionCrfTagService != null ? eventDefinitionCrfTagService
				: (EventDefinitionCrfTagService) SpringServletAccess.getApplicationContext(context)
						.getBean("eventDefinitionCrfTagService");

		return eventDefinitionCrfTagService;
	}

}
