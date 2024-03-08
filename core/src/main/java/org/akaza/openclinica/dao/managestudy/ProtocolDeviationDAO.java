package org.akaza.openclinica.dao.managestudy;

import org.akaza.openclinica.bean.core.EntityBean;
import org.akaza.openclinica.bean.managestudy.ProtocolDeviationBean;
import org.akaza.openclinica.bean.managestudy.StudyBean;
import org.akaza.openclinica.bean.submit.SubjectBean;
import org.akaza.openclinica.dao.core.AuditableEntityDAO;
import org.akaza.openclinica.dao.core.SQLFactory;
import org.akaza.openclinica.dao.core.TypeNames;
import org.akaza.openclinica.exception.OpenClinicaException;

import javax.sql.DataSource;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ProtocolDeviationDAO extends AuditableEntityDAO<ProtocolDeviationBean> {
    public ProtocolDeviationDAO(DataSource ds) {
        super(ds);
        setDigesterName();
        setQueryNames();
    }

    protected void setQueryNames() {
        findByPKAndStudyName = "findProtocolDeviationByIdAndStudy";
        getCurrentPKName = "getCurrentProtocolDeviationPrimaryKey";
    }

    public Integer getCountWithFilter(ProtocolDeviationFilter filter, StudyBean currentStudy) {
        HashMap<Integer, Object> variables = variables(currentStudy.getId(), currentStudy.getId());
        String query = digester.getQuery("getCountWithStudy");
        query += filter.execute("");
        return getCountByQuery(query, variables);
    }
    @Override
    public ProtocolDeviationBean findByPKAndStudy(int id, StudyBean study) {
        return super.findByPKAndStudy(id, study);
    }

    @Override
    public ProtocolDeviationBean getEntityFromHashMap(HashMap<String, Object> hm) {
        ProtocolDeviationBean eb = new ProtocolDeviationBean();
        //super.setEntityAuditInformation(eb, hm);
        eb.setProtocolDeviationId((Integer) hm.get("protocol_deviation_id"));
        eb.setLabel((String) hm.get("label"));
        //eb.setDescription((String) hm.get("description"));
        //eb.setSeverityId((Integer) hm.get("protocol_deviation_severity_id"));
        //eb.setSeverityLabel((String) hm.get("protocol_deviation_severity_label"));
        eb.setStudyId((Integer) hm.get("study_id"));

        return eb;
    }



    @Override
    public ArrayList<ProtocolDeviationBean> findAll(String strOrderByColumn, boolean blnAscendingSort,
                                                    String strSearchPhrase) throws OpenClinicaException {
        return null;
    }

    @Override
    public ArrayList<ProtocolDeviationBean> findAll() throws OpenClinicaException {
        return null;
    }

    @Override
    public EntityBean findByPK(int id) throws OpenClinicaException {
        return null;
    }

    public ProtocolDeviationBean create(ProtocolDeviationBean pdb) {
        HashMap<Integer, Object> variables = new HashMap<>();
        HashMap<Integer, Integer> nullVars = new HashMap<>();

        LocalDateTime ldt = LocalDateTime.now();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String label = ldt.format(dtf);
        pdb.setLabel(label);

        variables.put(1, label);
        variables.put(2, pdb.getStudyId());
        variables.put(3, pdb.getItemA1());
        variables.put(4, pdb.getItemA2());
        /*
        variables.put(5, pdb.getItemA3());
        variables.put(6, pdb.getItemA4());
        variables.put(7, pdb.getItemA5());
         */
        nullVars.put(5, Types.DATE);
        nullVars.put(6, Types.DATE);
        nullVars.put(7, Types.DATE);
        variables.put(5, null);
        variables.put(6, null);
        variables.put(7, null);
        variables.put(8, pdb.getItemA6());
        variables.put(9, pdb.getItemA7());
        //variables.put(10, pdb.getItemA7_1());
        nullVars.put(10, Types.DATE);
        variables.put(10, null);
        //variables.put(11, pdb.getItemA7_1());
        nullVars.put(11, Types.DATE);
        variables.put(11, null);
        variables.put(12, pdb.getItemA8());
        variables.put(13, pdb.getItemB1());
        variables.put(14, pdb.getItemB2());
        variables.put(15, pdb.getItemB3());
        variables.put(16, pdb.getItemB4());
        variables.put(17, pdb.getItemB5());
        variables.put(18, pdb.getItemB6());
        variables.put(19, pdb.getItemB7());
        variables.put(20, pdb.getItemB8());
        variables.put(21, pdb.getItemB9());
        variables.put(22, pdb.getItemB10());
        variables.put(23, pdb.getItemB11());
        variables.put(24, pdb.getItemB12());
        variables.put(25, pdb.getItemB13());
        variables.put(26, pdb.getItemB14());
        variables.put(27, pdb.getItemB15());
        variables.put(28, pdb.getItemB16());
        variables.put(29, pdb.getItemB17());
        variables.put(30, pdb.getItemB18());
        variables.put(31, pdb.getItemB1());
        variables.put(32, pdb.getItemC1_1());
        variables.put(33, pdb.getItemC1_2());
        variables.put(34, pdb.getItemC1_3());
        variables.put(35, pdb.getItemC1_4());
        variables.put(36, pdb.getItemC1_5());
        variables.put(37, pdb.getItemC1_6());
        variables.put(38, pdb.getItemC1_7());
        variables.put(39, pdb.getItemC1_8());
        variables.put(40, pdb.getItemC1_9());
        variables.put(41, pdb.getItemC1_10());
        variables.put(42, pdb.getItemC2());
        nullVars.put(43, Types.DATE);
        variables.put(43, null);
        //variables.put(42, pdb.getItemD1_A());
        variables.put(44, pdb.getItemD1_B());
        variables.put(45, pdb.getItemE1_A());
        variables.put(46, pdb.getItemE1_B());
        variables.put(47, pdb.getItemE1_C());
        variables.put(48, pdb.getItemE1_D());
        variables.put(49, pdb.getItemD1_A());
        variables.put(50, pdb.getItemD1_B());
        variables.put(51, pdb.getItemE1_A());
        variables.put(52, pdb.getItemE1_B());
        variables.put(53, pdb.getItemE1_C());
        variables.put(55, pdb.getItemE1_D());
        variables.put(56, pdb.getItemF1());
        variables.put(57, pdb.getItemF2());
        nullVars.put(58, Types.DATE);
        variables.put(58, null);
        //variables.put(56, pdb.getItemF3());
        variables.put(59, pdb.getItemG1());
        variables.put(60, pdb.getItemG2_1());
        variables.put(61, pdb.getItemG2_2());
        variables.put(62, pdb.getItemG2_3());
        variables.put(63, pdb.getItemG2_4());
        variables.put(64, pdb.getItemG3());
        variables.put(65, pdb.getItemG4());
        variables.put(66, pdb.getItemG5());
        nullVars.put(67, Types.DATE);
        variables.put(67, null);
        //variables.put(65, pdb.getItemG6());
        variables.put(68, pdb.getItemG6_1_A());
        variables.put(69, pdb.getItemG6_1_B());
        variables.put(70, pdb.getItemG6_1_C());
        variables.put(71, pdb.getItemG6_2_A());
        variables.put(72, pdb.getItemG6_2_B());
        variables.put(73, pdb.getItemG6_2_C());
        variables.put(74, pdb.getItemG6_3_A());
        variables.put(75, pdb.getItemG6_3_B());
        variables.put(76, pdb.getItemG6_3_C());
        variables.put(77, pdb.getItemG6_4_A());
        variables.put(78, pdb.getItemG6_4_B());
        variables.put(79, pdb.getItemG6_4_C());
        variables.put(80, pdb.getItemG7());
        variables.put(81, pdb.getItemG8());
        variables.put(82, pdb.getItemG9());


        executeUpdateWithPK(digester.getQuery("createProtocolDeviation"), variables, nullVars);
        if (isQuerySuccessful()) {
            pdb.setProtocolDeviationId(getLatestPK());
        }
            return pdb;
    }

    @Override
    public ProtocolDeviationBean update(ProtocolDeviationBean eb) throws OpenClinicaException {
        return null;
    }

    @Override
    public ArrayList<ProtocolDeviationBean> findAllByPermission(Object objCurrentUser, int intActionType, String strOrderByColumn, boolean blnAscendingSort, String strSearchPhrase) throws OpenClinicaException {
        return null;
    }

    @Override
    public ArrayList<ProtocolDeviationBean> findAllByPermission(Object objCurrentUser, int intActionType) throws OpenClinicaException {
        return null;
    }

    @Override
    public void setTypesExpected() {
        this.unsetTypeExpected();
        this.setTypeExpected(1, TypeNames.INT);
        this.setTypeExpected(2, TypeNames.STRING);
        this.setTypeExpected(3, TypeNames.SHORT);
        this.setTypeExpected(4, TypeNames.DATE);
        this.setTypeExpected(5, TypeNames.DATE);
        this.setTypeExpected(6, TypeNames.DATE);
        this.setTypeExpected(7, TypeNames.SHORT);
        this.setTypeExpected(8, TypeNames.SHORT);
        this.setTypeExpected(9, TypeNames.SHORT);
        this.setTypeExpected(10, TypeNames.SHORT);
        this.setTypeExpected(11, TypeNames.DATE);
        this.setTypeExpected(12, TypeNames.SHORT);
        this.setTypeExpected(13, TypeNames.SHORT);
        this.setTypeExpected(14, TypeNames.SHORT);
        this.setTypeExpected(15, TypeNames.SHORT);
        this.setTypeExpected(16, TypeNames.SHORT);
        this.setTypeExpected(17, TypeNames.SHORT);
        this.setTypeExpected(18, TypeNames.SHORT);
        this.setTypeExpected(19, TypeNames.SHORT);
        this.setTypeExpected(20, TypeNames.SHORT);
        this.setTypeExpected(21, TypeNames.SHORT);
        this.setTypeExpected(22, TypeNames.SHORT);
        this.setTypeExpected(23, TypeNames.SHORT);
        this.setTypeExpected(24, TypeNames.SHORT);
        this.setTypeExpected(25, TypeNames.SHORT);
        this.setTypeExpected(26, TypeNames.SHORT);
        this.setTypeExpected(27, TypeNames.SHORT);
        this.setTypeExpected(28, TypeNames.SHORT);
        this.setTypeExpected(29, TypeNames.SHORT);
        this.setTypeExpected(30, TypeNames.SHORT);
        this.setTypeExpected(31, TypeNames.SHORT);
        this.setTypeExpected(32, TypeNames.SHORT);
        this.setTypeExpected(33, TypeNames.SHORT);
        this.setTypeExpected(34, TypeNames.SHORT);
        this.setTypeExpected(35, TypeNames.SHORT);
        this.setTypeExpected(36, TypeNames.SHORT);
        this.setTypeExpected(37, TypeNames.SHORT);
        this.setTypeExpected(38, TypeNames.SHORT);
        this.setTypeExpected(39, TypeNames.SHORT);
        this.setTypeExpected(40, TypeNames.SHORT);
        this.setTypeExpected(41, TypeNames.STRING);
        this.setTypeExpected(42, TypeNames.STRING);
        this.setTypeExpected(43, TypeNames.DATE);
        this.setTypeExpected(44, TypeNames.STRING);
        this.setTypeExpected(45, TypeNames.STRING);
        this.setTypeExpected(46, TypeNames.STRING);
        this.setTypeExpected(47, TypeNames.STRING);
        this.setTypeExpected(48, TypeNames.STRING);
        this.setTypeExpected(49, TypeNames.STRING);
        this.setTypeExpected(50, TypeNames.STRING);
        this.setTypeExpected(51, TypeNames.DATE);
        this.setTypeExpected(52, TypeNames.SHORT);
        this.setTypeExpected(53, TypeNames.SHORT);
        this.setTypeExpected(54, TypeNames.SHORT);
        this.setTypeExpected(55, TypeNames.SHORT);
        this.setTypeExpected(56, TypeNames.SHORT);
        this.setTypeExpected(57, TypeNames.SHORT);
        this.setTypeExpected(58, TypeNames.SHORT);
        this.setTypeExpected(59, TypeNames.SHORT);
        this.setTypeExpected(60, TypeNames.SHORT);
        this.setTypeExpected(61, TypeNames.SHORT);
        this.setTypeExpected(62, TypeNames.SHORT);
        this.setTypeExpected(63, TypeNames.SHORT);
        this.setTypeExpected(64, TypeNames.SHORT);
        this.setTypeExpected(65, TypeNames.SHORT);
        this.setTypeExpected(66, TypeNames.DATE);
        this.setTypeExpected(67, TypeNames.STRING);
        this.setTypeExpected(68, TypeNames.SHORT);
        this.setTypeExpected(69, TypeNames.STRING);
        this.setTypeExpected(70, TypeNames.STRING);
        this.setTypeExpected(71, TypeNames.SHORT);
        this.setTypeExpected(72, TypeNames.STRING);
        this.setTypeExpected(73, TypeNames.STRING);
        this.setTypeExpected(74, TypeNames.SHORT);
        this.setTypeExpected(75, TypeNames.STRING);
        this.setTypeExpected(76, TypeNames.STRING);
        this.setTypeExpected(77, TypeNames.SHORT);
        this.setTypeExpected(78, TypeNames.STRING);
        this.setTypeExpected(79, TypeNames.STRING);
        this.setTypeExpected(80, TypeNames.STRING);
        this.setTypeExpected(81, TypeNames.STRING);



        /*this.setTypeExpected(3, TypeNames.STRING);
        this.setTypeExpected(4, TypeNames.INT);
        this.setTypeExpected(5, TypeNames.STRING);
        this.setTypeExpected(6, TypeNames.INT);*/
    }

    public ArrayList<ProtocolDeviationBean> findByStudy(int studyId) {
        HashMap<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, studyId);

        return this.executeFindAllQuery("findAllProtocolDeviationsByStudyId", parameters);
    }

    @Override
    protected void setDigesterName() {
        digesterName = SQLFactory.getInstance().DAO_SUBJECT;
    }


    @Override
    public ProtocolDeviationBean emptyBean() {
        return new ProtocolDeviationBean();
    }

}
