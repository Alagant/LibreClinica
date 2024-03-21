/*
 * LibreClinica is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: https://libreclinica.org/license
 * LibreClinica, copyright (C) 2020
 */
package org.akaza.openclinica.dao.managestudy;

import org.akaza.openclinica.bean.core.Status;
import org.akaza.openclinica.bean.managestudy.StudyBean;
import org.akaza.openclinica.bean.managestudy.StudyType;
import org.akaza.openclinica.dao.core.AuditableEntityDAO;
import org.akaza.openclinica.dao.core.DAODigester;
import org.akaza.openclinica.dao.core.SQLFactory;
import org.akaza.openclinica.dao.core.TypeNames;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;

public class CountryDAO extends AuditableEntityDAO<StudyBean> {

    public CountryDAO(DataSource ds) {
        super(ds);
        setQueryNames();
    }

    public CountryDAO(DataSource ds, DAODigester digester) {
        super(ds);
        this.digester = digester;
        setQueryNames();
    }

    // This constructor sets up the Locale for JUnit tests; see the locale
    // member variable in EntityDAO, and its initializeI18nStrings() method
    public CountryDAO(DataSource ds, DAODigester digester, Locale locale) {
        this(ds, digester);
        this.locale = locale;
    }

    private void setQueryNames() {
        getNextPKName = "findNextKey";
    }

    @Override
    protected void setDigesterName() {
        digesterName = SQLFactory.getInstance().DAO_STUDY;
    }

    @Override
    public void setTypesExpected() {
        // 1 sysid serial NOT NULL,
        // 2 code varchar(3),
        // 3 displayname varchar(50),
        // 4 sortorder INT,

        this.unsetTypeExpected();
        this.setTypeExpected(1, TypeNames.INT);// sysid
        this.setTypeExpected(2, TypeNames.STRING);// code
        this.setTypeExpected(3, TypeNames.STRING);// displayname
        this.setTypeExpected(4, TypeNames.INT);// sortorder
    }

    /**
     * <b>update </b>, the method that returns an updated study bean after it
     * updates the database. Note that we can use the three stages from our
     * creation use case.
     * 
     * @return sb an updated study bean.
     */
    @Override
    public StudyBean update(StudyBean sb) {
        sb = this.updateStepOne(sb);
        return sb;
    }

    /**
     * <P>
     * updateStepOne, the update method for the database. This method takes the
     * place of createStepOne, since it runs an update and assumes you already
     * have a primary key in the study bean object.
     *
     * @param sb the study bean which will be updated.
     * @return sb the study bean after it is updated with this phase.
     */
    public StudyBean updateStepOne(StudyBean sb) {
        HashMap<Integer, Object> variables = new HashMap<>();
        HashMap<Integer, Integer> nullVars = new HashMap<>();

        if (sb.getParentStudyId() == 0) {
            nullVars.put(1, Types.INTEGER);
            variables.put(1, null);
        } else {
            variables.put(1, sb.getParentStudyId());
        }
        variables.put(2, sb.getName());
        variables.put(3, sb.getOfficialTitle());
        variables.put(4, sb.getIdentifier());
        variables.put(5, sb.getSecondaryIdentifier());
        if (sb.getDatePlannedStart() == null) {
            nullVars.put(8, Types.DATE);
            variables.put(8, null);
        } else {
            variables.put(8, sb.getDatePlannedStart());
        }


        this.executeUpdate(digester.getQuery("updateStepOne"), variables, nullVars);
        return sb;
    }

    /**
     * <b>create </b>, the method that creates a study in the database.
     * <P>
     * note: create is split up into four custom functions, per the use case; we
     * are creating the standard create function here which calls all four
     * functions at once, but the seperate functions may be required in the
     * control servlets.
     * 
     * @return eb the created entity bean.
     */
    @Override
    public StudyBean create(StudyBean sb) {
        sb = this.createStepOne(sb);
        // in the above step, we will have created a primary key,
        // and in the next steps, we update the study bean
        // in phases


        return sb;
    }

    /**
     * <P>
     * findNextKey, a method to return a simple int from the database.
     * 
     * @return int, which is the next primary key for creating a study.
     */
    public int findNextKey() {
        return getNextPK();
    }

    /**
     * <P>
     * createStepOne, per the 'Detailed use case for administer system document
     * v1.0rc1' document. We insert the study in this method, and then update
     * the same study in the next three steps.
     * <P>
     * The next three steps, by the way, can then be used to update studies as well.
     *
     * @param sb Study bean about to be created.
     * @return same study bean with a primary key in the ID field.
     */
    public StudyBean createStepOne(StudyBean sb) {
        HashMap<Integer, Object> variables = new HashMap<>();
        HashMap<Integer, Integer> nullVars = new HashMap<>();
        sb.setId(this.findNextKey());
        variables.put(1, sb.getId());
        if (sb.getParentStudyId() == 0) {
            nullVars.put(2, Types.INTEGER);
            variables.put(2, null);
        } else {
            variables.put(2, sb.getParentStudyId());
        }

        variables.put(3, sb.getName());
        variables.put(4, sb.getOfficialTitle());
        variables.put(5, sb.getIdentifier());

        // replace this with the owner id
        this.executeUpdate(digester.getQuery("createStepOne"), variables, nullVars);
        return sb;
    }

    // we are generating and creating the valid oid at step one, tbh
    private String getOid(StudyBean sb) {
        String oid;
        try {
            oid = sb.getOid() != null ? sb.getOid() : sb.getOidGenerator().generateOid(sb.getIdentifier());
            return oid;
        } catch (Exception e) {
            throw new RuntimeException("CANNOT GENERATE OID");
        }
    }

    private String getValidOid(StudyBean sb) {
        String oid = getOid(sb);
        logger.info("*** " + oid);
        String oidPreRandomization = oid;
        while (existStudyWithOid(oid)) {
            oid = sb.getOidGenerator().randomizeOid(oidPreRandomization);
        }
        logger.info("returning the following oid: " + oid);
        return oid;
    }

    /**
     * Checks whether a study with the given OID already exist.
     * 
     * @param oid the study OID
     * @return true if a study with the given OID exists, false otherwise
     */
    public boolean existStudyWithOid(String oid) {
        StudyBean foundStudy = findByOid(oid);
        return foundStudy != null && oid.equals(foundStudy.getOid());
    }

    public StudyBean findByOid(String oid) {
        String queryName = "findByOid";
        HashMap<Integer, Object> variables = variables(oid);
        return executeFindByPKQuery(queryName, variables);
    }

    public StudyBean findByUniqueIdentifier(String oid) {
        String queryName = "findByUniqueIdentifier";
        HashMap<Integer, Object> variables = variables(oid);
        return executeFindByPKQuery(queryName, variables);
    }

    public StudyBean findSiteByUniqueIdentifier(String parentUniqueIdentifier, String siteUniqueIdentifier) {
        String queryName = "findSiteByUniqueIdentifier";
        HashMap<Integer, Object> variables = variables(parentUniqueIdentifier, siteUniqueIdentifier);
        return executeFindByPKQuery(queryName, variables);
    }


    /**
     * <p>
     * getEntityFromHashMap, the method that gets the object from the database query.
     */
    @Override
    public StudyBean getEntityFromHashMap(HashMap<String, Object> hm) {
        StudyBean eb = new StudyBean();

        // first set all the strings
        eb.setIdentifier((String) hm.get("unique_identifier"));
        eb.setName((String) hm.get("name"));
        eb.setSummary((String) hm.get("summary"));
        eb.setSecondaryIdentifier((String) hm.get("secondary_identifier"));
        eb.setPrincipalInvestigator((String) hm.get("principal_investigator"));

        // next set all the ints/dates
        Integer studyId = (Integer) hm.get("study_id");
        eb.setId(studyId);
        Integer parentStudyId = (Integer) hm.get("parent_study_id");
        if (parentStudyId == null) {
            eb.setParentStudyId(0);
        } else {
            eb.setParentStudyId(parentStudyId);
        }
        Integer ownerId = (Integer) hm.get("owner_id");
        // Even thou it is deprecated it creates more performant lazy loading behaviour
        eb.setOwnerId(ownerId);
        // Disabled because it generates additional SQL query
        //UserAccountBean owner = getUserAccountDAO().findByPK(ownerId);
        //eb.setOwner(owner);
        Integer updateId = (Integer) hm.get("update_id");
        // Even thou it is deprecated it creates more performant lazy loading behaviour
        eb.setUpdaterId(updateId);
        // Disabled because it generates additional SQL query
        //UserAccountBean updater = getUserAccountDAO().findByPK(updateId);
        //eb.setUpdater(updater);
        Integer typeId = (Integer) hm.get("type_id");
        eb.setType(StudyType.get(typeId));
        Integer statusId = (Integer) hm.get("status_id");
        eb.setStatus(Status.get(statusId));
        Integer expectedTotalEnrollment = (Integer) hm.get("expected_total_enrollment");
        eb.setExpectedTotalEnrollment(expectedTotalEnrollment);
        Date dateCreated = (Date) hm.get("date_created");
        Date dateUpdated = (Date) hm.get("date_updated");
        Date datePlannedStart = (Date) hm.get("date_planned_start");
        Date datePlannedEnd = (Date) hm.get("date_planned_end");
        Date dateProtocolVerification = (Date) hm.get("protocol_date_verification");

        return eb;
    }

    public ArrayList<StudyBean> findAllByUser(String username) {
        String queryName = "findAllByUser";
        HashMap<Integer, Object> variables = variables(username);
        return executeFindAllQuery(queryName, variables);
    }

    public ArrayList<Integer> getStudyIdsByCRF(int crfId) {
        this.unsetTypeExpected();
        this.setTypeExpected(1, TypeNames.INT);
        HashMap<Integer, Object> variables = variables(crfId);
        ArrayList<HashMap<String, Object>> alist = this.select(digester.getQuery("getStudyIdsByCRF"), variables);
        ArrayList<Integer> al = new ArrayList<>();
        for (HashMap<String, Object> hm : alist) {
            al.add((Integer) hm.get("study_id"));
        }
        return al;
    }

    // YW 10-18-2007
    public ArrayList<StudyBean> findAllByUserNotRemoved(String username) {
        String queryName = "findAllByUserNotRemoved";
        HashMap<Integer, Object> variables = variables(username);
        return executeFindAllQuery(queryName, variables);
    }

    public ArrayList<StudyBean> findAllByStatus(Status status) {
        String queryName = "findAllByStatus";
        HashMap<Integer, Object> variables = variables(status.getId());
        return executeFindAllQuery(queryName, variables);
    }

    @Override
    public ArrayList<StudyBean> findAll() {
        return findAllByLimit(false);
    }

    public ArrayList<StudyBean> findAllByLimit(boolean isLimited) {
        this.setTypesExpected();
        String sql;
        if (isLimited) {
            sql = digester.getQuery("findAll") + " limit 5";
        } else {
            sql = digester.getQuery("findAll");
        }
        ArrayList<HashMap<String, Object>> alist = this.select(sql);
        return alist.stream().map(hm -> this.getEntityFromHashMap(hm)).collect(toCollection(ArrayList::new));
    }

    public ArrayList<StudyBean> findAllParents() {
        String queryName = "findAllParents";
        return executeFindAllQuery(queryName);
    }


    public ArrayList<StudyBean> findAll(int studyId) {
        String queryName = "findAllByStudyId";
        HashMap<Integer, Object> variables = variables(studyId, studyId);
        return executeFindAllQuery(queryName, variables);
    }

    /**
     * TODO: NOT IMPLEMENTED
     */
    @Override
    public ArrayList<StudyBean> findAll(String strOrderByColumn, boolean blnAscendingSort, String strSearchPhrase) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public StudyBean findByPK(int ID) {
        String queryName = "findByPK";
        HashMap<Integer, Object> variables = variables(ID);
        return executeFindByPKQuery(queryName, variables);
    }

    public StudyBean findByName(String name) {
        String queryName = "findByName";
        HashMap<Integer, Object> variables = variables(name);
        return executeFindByPKQuery(queryName, variables);
    }

    /**
     * deleteTestOnly, used only to clean up after unit testing
     * 
     * @param name name
     */
    public void deleteTestOnly(String name) {
        HashMap<Integer, Object> variables = new HashMap<>();
        variables.put(1, name);
        this.executeUpdate(digester.getQuery("deleteTestOnly"), variables);
    }

    /**
     * TODO: NOT IMPLEMENTED
     */
    @Override
    public ArrayList<StudyBean> findAllByPermission(Object objCurrentUser, int intActionType, String strOrderByColumn, boolean blnAscendingSort,
            String strSearchPhrase) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * TODO: NOT IMPLEMENTED
     */
    @Override
    public ArrayList<StudyBean> findAllByPermission(Object objCurrentUser, int intActionType) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * @param allStudies The result of findAll().
     * @return A HashMap where the keys are Integers whose intValue are studyIds
     *         and the values are ArrayLists; each element of the ArrayList is a
     *         StudyBean representing a child of the study whose id is the key
     *         <p>
     *         e.g., if A has children B and C, then this will return a HashMap
     *         h where h.get(A.getId()) returns an ArrayList whose elements are
     *         B and C
     */
    public HashMap<Integer, ArrayList<StudyBean>> getChildrenByParentIds(ArrayList<StudyBean> allStudies) {
        if (allStudies == null) {
            return new HashMap<>();
        }

        Stream<StudyBean> stream = allStudies.stream();
        // filter for child studies (studies with a parent study id)
        stream = stream.filter(s -> s.getParentStudyId() > 0);
        // group by parent study id
        return stream.collect(groupingBy(StudyBean::getParentStudyId, HashMap::new, toCollection(ArrayList::new)));
    }

    public ArrayList<Integer> findAllSiteIdsByStudy(StudyBean study) {
        this.unsetTypeExpected();
        this.setTypeExpected(1, TypeNames.INT);// sid
        HashMap<Integer, Object> variables = variables(study.getId(), study.getId());
        ArrayList<HashMap<String, Object>> alist = this.select(digester.getQuery("findAllSiteIdsByStudy"), variables);
        ArrayList<Integer> al = new ArrayList<>();
        for (HashMap<String, Object> hm : alist) {
            al.add((Integer) hm.get("study_id"));
        }
        return al;
    }

    public ArrayList<Integer> findOlnySiteIdsByStudy(StudyBean study) {
        this.unsetTypeExpected();
        this.setTypeExpected(1, TypeNames.INT);// sid
        HashMap<Integer, Object> variables = variables(study.getId());
        ArrayList<HashMap<String, Object>> alist = this.select(digester.getQuery("findOlnySiteIdsByStudy"), variables);
        ArrayList<Integer> al = new ArrayList<>();
        for (HashMap<String, Object> hm : alist) {
            al.add((Integer) hm.get("study_id"));
        }
        return al;
    }

    public StudyBean updateSitesStatus(StudyBean sb) {
        HashMap<Integer, Object> variables = new HashMap<>();
        HashMap<Integer, Integer> nullVars = new HashMap<>();

        variables.put(1, sb.getStatus().getId());
        variables.put(2, sb.getOldStatus().getId());
        variables.put(3, sb.getId());

        this.executeUpdate(digester.getQuery("updateSitesStatus"), variables, nullVars);
        return sb;
    }

    public StudyBean updateStudyStatus(StudyBean sb) {
        HashMap<Integer, Object> variables = new HashMap<>();
        HashMap<Integer, Integer> nullVars = new HashMap<>();

        variables.put(1, sb.getStatus().getId());
        variables.put(2, sb.getOldStatus().getId());
        variables.put(3, sb.getId());

        this.executeUpdate(digester.getQuery("updateStudyStatus"), variables, nullVars);
        return sb;
    }

    public StudyBean findByStudySubjectId(int studySubjectId) {
        String queryName = "findByStudySubjectId";
        HashMap<Integer, Object> variables = variables(studySubjectId);
        return executeFindByPKQuery(queryName, variables);
    }

    public ArrayList<StudyBean> findAllByParentStudyIdOrderedByIdAsc(int parentStudyId) {
        String queryName = "findAllByParentStudyIdOrderedByIdAsc";
        HashMap<Integer, Object> variables = variables(parentStudyId, parentStudyId);
        return executeFindAllQuery(queryName, variables);
    }

    public StudyBean getDefaultStudy() {
        return executeFindByPKQuery("findDefaultStudy");
    }

    @Override
    public StudyBean emptyBean() {
        return new StudyBean();
    }

}
