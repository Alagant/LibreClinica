/*
 * LibreClinica is distributed under the
 * GNU Lesser General Public License (GNU LGPL).

 * For details see: https://libreclinica.org/license
 * LibreClinica, copyright (C) 2020
 */
package org.akaza.openclinica.control.submit;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;

import org.akaza.openclinica.bean.core.Utils;
import org.akaza.openclinica.bean.managestudy.StudyBean;
import org.akaza.openclinica.control.core.SecureController;
import org.akaza.openclinica.control.form.FormProcessor;
import org.akaza.openclinica.dao.managestudy.StudyDAO;
import org.akaza.openclinica.view.Page;
import org.akaza.openclinica.web.InsufficientPermissionException;

/**
 * @author ywang (Dec., 2008)
 */
public class DownloadAttachedFileServlet extends SecureController {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3098103596566845378L;

	/**
     * Checks whether the user has the correct privilege
     */
    @Override
    public void mayProceed() throws InsufficientPermissionException {
        if (ub.isSysAdmin()) {
            return;
        }
        if (SubmitDataServlet.mayViewData(ub, currentRole)) {
            return;
        }

        request.setAttribute("downloadStatus", "false");
        addPageMessage(respage.getString("you_not_have_permission_download_attached_file"));
        throw new InsufficientPermissionException(Page.DOWNLOAD_ATTACHED_FILE, resexception.getString("no_permission"), "1");
    }

    @Override
    public void processRequest() throws Exception {
        FormProcessor fp = new FormProcessor(request);
        String filePathName = "";
        String fileName = fp.getString("fileName");
        File f = new File(fileName);
        if (fileName != null && fileName.length() > 0) {
            int parentStudyId = currentStudy.getParentStudyId();
            String testPath = Utils.getAttachedFileRootPath();
            String tail = File.separator + f.getName();
            String testName = testPath + currentStudy.getOid() + tail;
            File temp = new File(testName);
            if (temp.exists()) {
                filePathName = testName;
                logger.info(currentStudy.getName() + " existing filePathName=" + filePathName);
            } else {
                if (currentStudy.isSite(parentStudyId)) {
                    testName = testPath + ((StudyBean) new StudyDAO(sm.getDataSource()).findByPK(parentStudyId)).getOid() + tail;
                    temp = new File(testName);
                    if (temp.exists()) {
                        filePathName = testName;
                        logger.info("parent existing filePathName=" + filePathName);
                    }
                } else {
                    ArrayList<StudyBean> sites = (ArrayList<StudyBean>) new StudyDAO(sm.getDataSource()).findAllByParent(currentStudy.getId());
                    for (StudyBean s : sites) {
                        testPath = Utils.getAttachedFilePath(s);
                        testName = testPath + tail;//+ s.getIdentifier() + tail;
                        File test = new File(testName);
                        if (test.exists()) {
                            filePathName = testName;
                            logger.info("site of currentStudy existing filePathName=" + filePathName);
                            break;
                        }
                    }
                }
            }
        }
        logger.info("filePathName=" + filePathName + " fileName=" + fileName);
        File file = new File(filePathName);
        if (!file.exists() || file.length() <= 0) {
            addPageMessage("File " + filePathName + " " + respage.getString("not_exist"));
        } else {
//            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\";");
            response.setHeader("Pragma", "public");

            ServletOutputStream outStream = response.getOutputStream();
            DataInputStream inStream = null;
            try {
                response.setContentType("application/download");
                response.setHeader("Cache-Control", "max-age=0");
                response.setContentLength((int) file.length());

                byte[] bbuf = new byte[(int) file.length()];
                inStream = new DataInputStream(new FileInputStream(file));
                int length;
                while (inStream != null && (length = inStream.read(bbuf)) != -1) {
                    outStream.write(bbuf, 0, length);
                }

                inStream.close();
                outStream.flush();
                outStream.close();
            } catch (Exception ee) {
                logger.error("InputStream is not working properly: ", ee);
            } finally {
                if (inStream != null) {
                    inStream.close();
                }
                if (outStream != null) {
                    outStream.close();
                }
            }
        }
    }

}
