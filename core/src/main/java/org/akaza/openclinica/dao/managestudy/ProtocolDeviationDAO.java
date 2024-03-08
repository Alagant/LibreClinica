package org.akaza.openclinica.dao.managestudy;

import org.akaza.openclinica.bean.core.EntityBean;
import org.akaza.openclinica.bean.managestudy.ProtocolDeviationBean;
import org.akaza.openclinica.bean.managestudy.StudyBean;
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
        eb.setId((Integer) hm.get("protocol_deviation_id"));
        eb.setProtocolDeviationId((Integer) hm.get("protocol_deviation_id"));
        eb.setLabel((String) hm.get("label"));
        eb.setStudyId((Integer) hm.get("study_id"));
        eb.setItemA1((Short)hm.get("item_a_1"));
        eb.setItemA2((Short)hm.get("item_a_2"));
        eb.setItemA3((Date) hm.get("item_a_3"));
        eb.setItemA4((Date) hm.get("item_a_4"));
        eb.setItemA5((Date) hm.get("item_a_5"));
        eb.setItemA6((Short) hm.get("item_a_6"));
        eb.setItemA7((Short) hm.get("item_a_7"));
        eb.setItemA7_1((Date) hm.get("item_a_7_1"));

        eb.setItemB1((Short) hm.get("item_b_1"));
        eb.setItemB2((Short) hm.get("item_b_2"));
        eb.setItemB3((Short) hm.get("item_b_3"));
        eb.setItemB4((Short) hm.get("item_b_4"));
        eb.setItemB5((Short) hm.get("item_b_5"));
        eb.setItemB6((Short) hm.get("item_b_6"));
        eb.setItemB7((Short) hm.get("item_b_7"));
        eb.setItemB8((Short) hm.get("item_b_8"));
        eb.setItemB9((Short) hm.get("item_b_9"));
        eb.setItemB10((Short) hm.get("item_b_10"));
        eb.setItemB11((Short) hm.get("item_b_11"));
        eb.setItemB12((Short) hm.get("item_b_12"));
        eb.setItemB13((Short) hm.get("item_b_13"));
        eb.setItemB14((Short) hm.get("item_b_14"));
        eb.setItemB15((Short) hm.get("item_b_15"));
        eb.setItemB16((Short) hm.get("item_b_16"));
        eb.setItemB17((Short) hm.get("item_b_17"));
        eb.setItemB18((Short) hm.get("item_b_18"));

        eb.setItemC1_1((Short) hm.get("item_c_1_1"));
        eb.setItemC1_2((Short) hm.get("item_c_1_2"));
        eb.setItemC1_3((Short) hm.get("item_c_1_3"));
        eb.setItemC1_4((Short) hm.get("item_c_1_4"));
        eb.setItemC1_5((Short) hm.get("item_c_1_5"));
        eb.setItemC1_6((Short) hm.get("item_c_1_6"));
        eb.setItemC1_7((Short) hm.get("item_c_1_7"));
        eb.setItemC1_8((Short) hm.get("item_c_1_8"));
        eb.setItemC1_9((Short) hm.get("item_c_1_9"));
        eb.setItemC1_10((String) hm.get("item_c_1_10"));
        eb.setItemC2((String) hm.get("item_c_2"));

        eb.setItemD1_A((Date) hm.get("item_d_1_a"));
        eb.setItemD1_B((String) hm.get("item_d_1_b"));

        eb.setItemE1((String) hm.get("item_e1"));
        eb.setItemE2((String) hm.get("item_e2"));
        eb.setItemE3((String) hm.get("item_e3"));
        eb.setItemE4((String) hm.get("item_e4"));

        eb.setItemF1((String) hm.get("item_f_1"));
        eb.setItemF2((String) hm.get("item_f_2"));
        eb.setItemF3((Date) hm.get("item_f_3"));

        eb.setItemG1((Short) hm.get("item_g_1"));
        eb.setItemG2_1((Short) hm.get("item_g_2_1"));
        eb.setItemG2_2((Short) hm.get("item_g_2_2"));
        eb.setItemG2_3((Short) hm.get("item_g_2_3"));
        eb.setItemG2_4((Short) hm.get("item_g_2_4"));
        eb.setItemG3((Short) hm.get("item_g_3"));
        eb.setItemG4((Short) hm.get("item_g_4"));
        eb.setItemG5((Short) hm.get("item_g_5"));
        eb.setItemG6((Date) hm.get("item_g_6"));
        eb.setItemG6_1_A((String) hm.get("item_g_6_1_a"));
        eb.setItemG6_1_B((Short) hm.get("item_g_6_1_b"));
        eb.setItemG6_1_C((String) hm.get("item_g_6_1_c"));
        eb.setItemG6_2_A((String) hm.get("item_g_6_2_a"));
        eb.setItemG6_2_B((Short) hm.get("item_g_6_2_b"));
        eb.setItemG6_2_C((String) hm.get("item_g_6_2_c"));
        eb.setItemG6_3_A((String) hm.get("item_g_6_3_a"));
        eb.setItemG6_3_B((Short) hm.get("item_g_6_3_b"));
        eb.setItemG6_3_C((String) hm.get("item_g_6_3_c"));
        eb.setItemG6_4_A((String) hm.get("item_g_6_4_a"));
        eb.setItemG6_4_B((Short) hm.get("item_g_6_4_b"));
        eb.setItemG6_4_C((String) hm.get("item_g_6_4_c"));
        eb.setItemG7((String) hm.get("item_g_7"));
        eb.setItemG8((String) hm.get("item_g_8"));
        eb.setItemG9((String) hm.get("item_g_9"));

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

        variables.put(5, null); nullVars.put(5, Types.DATE);  // item_a_3
        variables.put(6, null); nullVars.put(6, Types.DATE); // item_a_4
        variables.put(7, null); nullVars.put(7, Types.DATE); // item_a_5
        variables.put(8, pdb.getItemA6());
        variables.put(9, pdb.getItemA7());
        variables.put(10, null); nullVars.put(10, Types.DATE); // item_a_7_1
        variables.put(11, pdb.getItemA8());
        variables.put(12, pdb.getItemB1());
        variables.put(13, pdb.getItemB2());
        variables.put(14, pdb.getItemB3());
        variables.put(15, pdb.getItemB4());
        variables.put(16, pdb.getItemB5());
        variables.put(17, pdb.getItemB6());
        variables.put(18, pdb.getItemB7());
        variables.put(19, pdb.getItemB8());
        variables.put(20, pdb.getItemB9());
        variables.put(21, pdb.getItemB10());
        variables.put(22, pdb.getItemB11());
        variables.put(23, pdb.getItemB12());
        variables.put(24, pdb.getItemB13());
        variables.put(25, pdb.getItemB14());
        variables.put(26, pdb.getItemB15());
        variables.put(27, pdb.getItemB16());
        variables.put(28, pdb.getItemB17());
        variables.put(29, pdb.getItemB18());
        variables.put(30, pdb.getItemC1_1());
        variables.put(31, pdb.getItemC1_2());
        variables.put(32, pdb.getItemC1_3());
        variables.put(33, pdb.getItemC1_4());
        variables.put(34, pdb.getItemC1_5());
        variables.put(35, pdb.getItemC1_6());
        variables.put(36, pdb.getItemC1_7());
        variables.put(37, pdb.getItemC1_8());
        variables.put(38, pdb.getItemC1_9());
        variables.put(39, pdb.getItemC1_10());
        variables.put(40, pdb.getItemC2());
        variables.put(41, null); nullVars.put(41, Types.DATE); //variables.put(42, pdb.getItemD1_A());
        variables.put(42, pdb.getItemD1_B());
        variables.put(43, pdb.getItemE1());
        variables.put(44, pdb.getItemE2());
        variables.put(45, pdb.getItemE3());
        variables.put(46, pdb.getItemE4());

        variables.put(47, null); nullVars.put(47, Types.DATE); // item_f_1
        variables.put(48, null); nullVars.put(48, Types.DATE); // item_f_2
        variables.put(49, null); nullVars.put(49, Types.DATE); // item_f_3
        variables.put(50, pdb.getItemG1());
        variables.put(51, pdb.getItemG2_1());
        variables.put(52, pdb.getItemG2_2());
        variables.put(53, pdb.getItemG2_3());
        variables.put(54, pdb.getItemG2_4());
        variables.put(55, pdb.getItemG3());
        variables.put(56, pdb.getItemG4());
        variables.put(57, pdb.getItemG5());
        variables.put(58, null); nullVars.put(58, Types.DATE); // item_g_6
        variables.put(59, pdb.getItemG6_1_A());
        variables.put(60, pdb.getItemG6_1_B());
        variables.put(61, pdb.getItemG6_1_C());
        variables.put(62, pdb.getItemG6_2_A());
        variables.put(63, pdb.getItemG6_2_B());
        variables.put(64, pdb.getItemG6_2_C());
        variables.put(65, pdb.getItemG6_3_A());
        variables.put(66, pdb.getItemG6_3_B());
        variables.put(67, pdb.getItemG6_3_C());
        variables.put(68, pdb.getItemG6_4_A());
        variables.put(69, pdb.getItemG6_4_B());
        variables.put(70, pdb.getItemG6_4_C());
        variables.put(71, pdb.getItemG7());
        variables.put(72, pdb.getItemG8());
        variables.put(73, pdb.getItemG9());


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
        setTypeExpected(1, TypeNames.INT);
        setTypeExpected(2, TypeNames.STRING);
        setTypeExpected(3, TypeNames.INT);
        setTypeExpected(4, TypeNames.SHORT);
        setTypeExpected(5, TypeNames.SHORT);
        setTypeExpected(6, TypeNames.DATE);
        setTypeExpected(7, TypeNames.DATE);
        setTypeExpected(8, TypeNames.DATE);
        setTypeExpected(9, TypeNames.SHORT);
        setTypeExpected(10, TypeNames.SHORT);
        setTypeExpected(11, TypeNames.DATE);
        setTypeExpected(12, TypeNames.SHORT);
        setTypeExpected(13, TypeNames.SHORT);
        setTypeExpected(14, TypeNames.SHORT);
        setTypeExpected(15, TypeNames.SHORT);
        setTypeExpected(16, TypeNames.SHORT);
        setTypeExpected(17, TypeNames.SHORT);
        setTypeExpected(18, TypeNames.SHORT);
        setTypeExpected(19, TypeNames.SHORT);
        setTypeExpected(20, TypeNames.SHORT);
        setTypeExpected(21, TypeNames.SHORT);
        setTypeExpected(22, TypeNames.SHORT);
        setTypeExpected(23, TypeNames.SHORT);
        setTypeExpected(24, TypeNames.SHORT);
        setTypeExpected(25, TypeNames.SHORT);
        setTypeExpected(26, TypeNames.SHORT);
        setTypeExpected(27, TypeNames.SHORT);
        setTypeExpected(28, TypeNames.SHORT);
        setTypeExpected(29, TypeNames.SHORT);
        setTypeExpected(30, TypeNames.SHORT);
        setTypeExpected(31, TypeNames.SHORT);
        setTypeExpected(32, TypeNames.SHORT);
        setTypeExpected(33, TypeNames.SHORT);
        setTypeExpected(34, TypeNames.SHORT);
        setTypeExpected(35, TypeNames.SHORT);
        setTypeExpected(36, TypeNames.SHORT);
        setTypeExpected(37, TypeNames.SHORT);
        setTypeExpected(38, TypeNames.SHORT);
        setTypeExpected(39, TypeNames.SHORT);
        setTypeExpected(40, TypeNames.STRING);
        setTypeExpected(41, TypeNames.STRING);
        setTypeExpected(42, TypeNames.DATE);
        setTypeExpected(43, TypeNames.STRING);
        setTypeExpected(44, TypeNames.STRING);
        setTypeExpected(45, TypeNames.STRING);
        setTypeExpected(46, TypeNames.STRING);
        setTypeExpected(47, TypeNames.STRING);
        setTypeExpected(48, TypeNames.STRING);
        setTypeExpected(49, TypeNames.STRING);
        setTypeExpected(50, TypeNames.DATE);
        setTypeExpected(51, TypeNames.SHORT);
        setTypeExpected(52, TypeNames.SHORT);
        setTypeExpected(53, TypeNames.SHORT);
        setTypeExpected(54, TypeNames.SHORT);
        setTypeExpected(55, TypeNames.SHORT);
        setTypeExpected(56, TypeNames.SHORT);
        setTypeExpected(57, TypeNames.SHORT);
        setTypeExpected(58, TypeNames.SHORT);
        setTypeExpected(59, TypeNames.DATE);
        setTypeExpected(60, TypeNames.STRING);
        setTypeExpected(61, TypeNames.SHORT);
        setTypeExpected(62, TypeNames.STRING);
        setTypeExpected(63, TypeNames.STRING);
        setTypeExpected(64, TypeNames.SHORT);
        setTypeExpected(65, TypeNames.STRING);
        setTypeExpected(66, TypeNames.STRING);
        setTypeExpected(67, TypeNames.SHORT);
        setTypeExpected(68, TypeNames.STRING);
        setTypeExpected(69, TypeNames.STRING);
        setTypeExpected(70, TypeNames.SHORT);
        setTypeExpected(71, TypeNames.STRING);
        setTypeExpected(72, TypeNames.STRING);
        setTypeExpected(73, TypeNames.STRING);
        setTypeExpected(74, TypeNames.STRING);
        /*setTypeExpected(75, TypeNames.STRING);
        setTypeExpected(76, TypeNames.STRING);*/



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
