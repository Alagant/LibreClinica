package org.akaza.openclinica.control.submit;

import org.akaza.openclinica.bean.managestudy.StudyEventDefinitionBean;
import org.akaza.openclinica.bean.managestudy.StudyGroupClassBean;
import org.akaza.openclinica.control.AbstractTableFactory;
import org.akaza.openclinica.control.ListStudyView;
import org.akaza.openclinica.dao.managestudy.FindSubjectsFilter;
import org.akaza.openclinica.i18n.util.ResourceBundleProvider;
import org.jmesa.facade.TableFacade;
import org.jmesa.limit.Limit;
import org.jmesa.view.component.Row;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class ProtocolDeviationTableFactory extends AbstractTableFactory {
    private String[] columnNames = new String[] {};
    private ResourceBundle resword;
    private ResourceBundle resformat;
    @Override
    protected String getTableName() {
        return "protocolDeviations";
    }

    @Override
    public void configureTableFacade(HttpServletResponse response, TableFacade tableFacade) {
        super.configureTableFacade(response, tableFacade);
        getColumnNamesMap();
        ArrayList<String> columnNamesList = new ArrayList<String>();
        columnNamesList.add("protocolDeviation.id");
    }

    public void configureTableFacadeCustomView(TableFacade tableFacade, HttpServletRequest request) {
        tableFacade.setView(new ProtocolDeviationView(getLocale(), request));
    }

    private void getColumnNamesMap() {
        ArrayList<String> columnNamesList = new ArrayList<String>();
        columnNamesList.add("protocolDeviation.id");
        columnNamesList.add("actions");
        columnNames = columnNamesList.toArray(columnNames);
    }

    @Override
    protected void configureColumns(TableFacade tableFacade, Locale locale) {
        /*resword = ResourceBundleProvider.getWordsBundle(locale);
        resformat = ResourceBundleProvider.getFormatBundle(locale);*/
        tableFacade.setColumnProperties(columnNames);
        Row row = tableFacade.getTable().getRow();
        int index = 0;
        configureColumn(row.getColumn(columnNames[index]), "PDID", null, null);
        ++index;
    }

    @Override
    // To avoid showing title in other pages, the request element is used to determine where the request came from.
    public TableFacade createTable(HttpServletRequest request, HttpServletResponse response) {
        TableFacade tableFacade = getTableFacadeImpl(request, response);
        tableFacade.setStateAttr("restore");
        setDataAndLimitVariables(tableFacade);
        configureTableFacade(response, tableFacade);
        if (!tableFacade.getLimit().isExported()) {
            configureColumns(tableFacade, locale);
            tableFacade.setMaxRowsIncrements(getMaxRowIncrements());
            configureTableFacadePostColumnConfiguration(tableFacade);
            configureTableFacadeCustomView(tableFacade, request);
            configureUnexportedTable(tableFacade, locale);
        } else {
            configureExportColumns(tableFacade, locale);
        }
        return tableFacade;
    }

    /*
    public void configureTableFacadeCustomView(TableFacade tableFacade, HttpServletRequest request) {
        tableFacade.setView(new ListStudyView(getLocale(), request));
    }*/

    @Override
    public void setDataAndLimitVariables(TableFacade tableFacade) {
        Limit limit = tableFacade.getLimit();

        //FindSubjectsFilter subjectFilter = getSubjectFilter(limit);

        if (!limit.isComplete()) {
            //int totalRows = getStudySubjectDAO().getCountWithFilter(subjectFilter, getStudyBean());
            tableFacade.setTotalRows(0);
        }
        Collection<HashMap<Object, Object>> theItems = new ArrayList<HashMap<Object, Object>>();
        HashMap<Object, Object> theItem = new HashMap<Object, Object>();
        theItem.put("protocolDeviation.id", "0001-001");
        theItems.add(theItem);
        tableFacade.setItems(theItems);
        tableFacade.setTotalRows(theItems.size());
    }
}
