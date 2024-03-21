/*
 * LibreClinica is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: https://libreclinica.org/license
 * LibreClinica, copyright (C) 2020
 */
package org.akaza.openclinica.dao.managestudy;

import org.akaza.openclinica.bean.core.Status;
import org.akaza.openclinica.bean.managestudy.LaboratoryBean;
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

public class LaboratoryDAO extends AuditableEntityDAO<LaboratoryBean> {

    public LaboratoryDAO(DataSource ds) {
        super(ds);
        setQueryNames();
    }

    public LaboratoryDAO(DataSource ds, DAODigester digester) {
        super(ds);
        this.digester = digester;
        setQueryNames();
    }

    // This constructor sets up the Locale for JUnit tests; see the locale
    // member variable in EntityDAO, and its initializeI18nStrings() method
    public LaboratoryDAO(DataSource ds, DAODigester digester, Locale locale) {
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
        // 1 study_id serial NOT NULL,
        // 2 parent_study_id numeric,
        // 3 unique_identifier varchar(30),
        // 4 secondary_identifier varchar(255),
        // 5 name varchar(60),
        // 6 summary varchar(255),
        // 7 date_planned_start date,
        // 8 date_planned_end date,
        // 9 date_created date,
        // 10 date_updated date,
        // 11 owner_id numeric,
        // 12 update_id numeric,
        // 13 type_id numeric,
        // 14 status_id numeric,
        // 15 principal_investigator varchar(255),
        // 16 facility_name varchar(255),
        // 17 facility_city varchar(255),
        // 18 facility_state varchar(20),
        // 19 facility_zip varchar(64),
        // 20 facility_country varchar(64),
        // 21 facility_recruitment_status varchar(60),
        // 22 facility_contact_name varchar(255),
        // 23 facility_contact_degree varchar(255),
        // 24 facility_contact_phone varchar(255),
        // 25 facility_contact_email varchar(255),
        // 26 protocol_type varchar(30),
        // 27 protocol_description varchar(1000),
        // 28 protocol_date_verification date,
        // 29 phase varchar(30),
        // 30 expected_total_enrollment numeric,
        // 31 sponsor varchar(255),
        // 32 collaborators varchar(1000),
        // 33 medline_identifier varchar(255),
        // 34 url varchar(255),
        // 35 url_description varchar(255),
        // 36 conditions varchar(500),
        // 37 keywords varchar(255),
        // 38 eligibility varchar(500),
        // 39 gender varchar(30),
        // 40 age_max varchar(3),
        // 41 age_min varchar(3),
        // 42 healthy_volunteer_accepted bool,
        // 43 purpose varchar(64),
        // 44 allocation varchar(64),
        // 45 masking varchar(30),
        // 46 control varchar(30),
        // 47 "assignment" varchar(30),
        // 48 endpoint varchar(64),
        // 49 interventions varchar(1000),
        // 50 duration varchar(30),
        // 51 selection varchar(30),
        // 52 timing varchar(30),
        // 53 official_title varchar(50)
        // 54 results_reference boolean

        this.unsetTypeExpected();
        this.setTypeExpected(1, TypeNames.INT);// sid
        this.setTypeExpected(2, TypeNames.INT);// parent id
        this.setTypeExpected(3, TypeNames.STRING);// ident
        this.setTypeExpected(4, TypeNames.STRING);// second ident
        this.setTypeExpected(5, TypeNames.STRING);// name
        this.setTypeExpected(6, TypeNames.STRING);// summary
        this.setTypeExpected(7, TypeNames.DATE);// start
   }

    /**
     * <b>update </b>, the method that returns an updated study bean after it
     * updates the database. Note that we can use the three stages from our
     * creation use case.
     * 
     * @return sb an updated study bean.
     */
    @Override
    public LaboratoryBean update(LaboratoryBean sb) {
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
    public LaboratoryBean updateStepOne(LaboratoryBean sb) {
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
        variables.put(6, sb.getSummary());
        variables.put(7, sb.getPrincipalInvestigator());
        if (sb.getDatePlannedStart() == null) {
            nullVars.put(8, Types.DATE);
            variables.put(8, null);
        } else {
            variables.put(8, sb.getDatePlannedStart());
        }

        if (sb.getDatePlannedEnd() == null) {
            nullVars.put(9, Types.DATE);
            variables.put(9, null);
        } else {
            variables.put(9, sb.getDatePlannedEnd());
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
    public LaboratoryBean create(LaboratoryBean sb) {
        sb = this.createStepOne(sb);


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
    public LaboratoryBean createStepOne(LaboratoryBean sb) {
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
        variables.put(6, sb.getSecondaryIdentifier());
        variables.put(7, sb.getSummary());
        variables.put(8, sb.getPrincipalInvestigator());


        // replace this with the owner id
        this.executeUpdate(digester.getQuery("createStepOne"), variables, nullVars);
        return sb;
    }

    // we are generating and creating the valid oid at step one, tbh
    private String getOid(LaboratoryBean sb) {
        String oid;
        try {
            oid = sb.getOid() != null ? sb.getOid() : sb.getOidGenerator().generateOid(sb.getIdentifier());
            return oid;
        } catch (Exception e) {
            throw new RuntimeException("CANNOT GENERATE OID");
        }
    }

    private String getValidOid(LaboratoryBean sb) {
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
        LaboratoryBean foundStudy = findByOid(oid);
        return foundStudy != null && oid.equals(foundStudy.getOid());
    }

    public LaboratoryBean findByOid(String oid) {
        String queryName = "findByOid";
        HashMap<Integer, Object> variables = variables(oid);
        return executeFindByPKQuery(queryName, variables);
    }

    public LaboratoryBean findByUniqueIdentifier(String oid) {
        String queryName = "findByUniqueIdentifier";
        HashMap<Integer, Object> variables = variables(oid);
        return executeFindByPKQuery(queryName, variables);
    }

    public LaboratoryBean findSiteByUniqueIdentifier(String parentUniqueIdentifier, String siteUniqueIdentifier) {
        String queryName = "findSiteByUniqueIdentifier";
        HashMap<Integer, Object> variables = variables(parentUniqueIdentifier, siteUniqueIdentifier);
        return executeFindByPKQuery(queryName, variables);
    }


    /**
     * <p>
     * getEntityFromHashMap, the method that gets the object from the database query.
     */
    @Override
    public LaboratoryBean getEntityFromHashMap(HashMap<String, Object> hm) {
        LaboratoryBean eb = new LaboratoryBean();

        // first set all the strings
        eb.setIdentifier((String) hm.get("unique_identifier"));
        eb.setName((String) hm.get("name"));
        eb.setSummary((String) hm.get("summary"));
        eb.setSecondaryIdentifier((String) hm.get("secondary_identifier"));
        eb.setPrincipalInvestigator((String) hm.get("principal_investigator"));
        return eb;
    }

    public ArrayList<LaboratoryBean> findAllByUser(String username) {
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
    public ArrayList<LaboratoryBean> findAllByUserNotRemoved(String username) {
        String queryName = "findAllByUserNotRemoved";
        HashMap<Integer, Object> variables = variables(username);
        return executeFindAllQuery(queryName, variables);
    }

    public ArrayList<LaboratoryBean> findAllByStatus(Status status) {
        String queryName = "findAllByStatus";
        HashMap<Integer, Object> variables = variables(status.getId());
        return executeFindAllQuery(queryName, variables);
    }

    @Override
    public ArrayList<LaboratoryBean> findAll() {
        return findAllByLimit(false);
    }

    public ArrayList<LaboratoryBean> findAllByLimit(boolean isLimited) {
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


    public ArrayList<LaboratoryBean> findAll(int studyId) {
        String queryName = "findAllByStudyId";
        HashMap<Integer, Object> variables = variables(studyId, studyId);
        return executeFindAllQuery(queryName, variables);
    }

    /**
     * TODO: NOT IMPLEMENTED
     */
    @Override
    public ArrayList<LaboratoryBean> findAll(String strOrderByColumn, boolean blnAscendingSort, String strSearchPhrase) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public LaboratoryBean findByPK(int ID) {
        String queryName = "findByPK";
        HashMap<Integer, Object> variables = variables(ID);
        return executeFindByPKQuery(queryName, variables);
    }

    public LaboratoryBean findByName(String name) {
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
    public ArrayList<LaboratoryBean> findAllByPermission(Object objCurrentUser, int intActionType, String strOrderByColumn, boolean blnAscendingSort,
            String strSearchPhrase) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * TODO: NOT IMPLEMENTED
     */
    @Override
    public ArrayList<LaboratoryBean> findAllByPermission(Object objCurrentUser, int intActionType) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * @param allStudies The result of findAll().
     * @return A HashMap where the keys are Integers whose intValue are studyIds
     *         and the values are ArrayLists; each element of the ArrayList is a
     *         LaboratoryBean representing a child of the study whose id is the key
     *         <p>
     *         e.g., if A has children B and C, then this will return a HashMap
     *         h where h.get(A.getId()) returns an ArrayList whose elements are
     *         B and C
     */

    public ArrayList<Integer> findAllSiteIdsByStudy(LaboratoryBean study) {
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

    public ArrayList<Integer> findOlnySiteIdsByStudy(LaboratoryBean study) {
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


    public LaboratoryBean findByStudySubjectId(int studySubjectId) {
        String queryName = "findByStudySubjectId";
        HashMap<Integer, Object> variables = variables(studySubjectId);
        return executeFindByPKQuery(queryName, variables);
    }

    public ArrayList<LaboratoryBean> findAllByParentStudyIdOrderedByIdAsc(int parentStudyId) {
        String queryName = "findAllByParentStudyIdOrderedByIdAsc";
        HashMap<Integer, Object> variables = variables(parentStudyId, parentStudyId);
        return executeFindAllQuery(queryName, variables);
    }

    @Override
    public LaboratoryBean emptyBean() {
        return new LaboratoryBean();
    }

}
