<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="org.akaza.openclinica.i18n.words" var="resword"/>
<fmt:setBundle basename="org.akaza.openclinica.i18n.format" var="resformat"/>
<div class="add_new_subject_div" style="display: flex; flex-direction: column; padding: 10px;">
    <form method="post" action="${pageContext.request.contextPath}/ProtocolDeviations">
        <input type="hidden" name="action" value="saveProtocolDeviations"/>
        <div>
            <table>
                <tr>
                    <td class="formlabel"></td>
                    <td><h3>New protocol deviation</h3></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="formlabel">
                        <select class="formfield" id="new-subject">
                            <c:forEach var="p" items="${subjects}">
                                <option value="${p.id}">
                                        ${p.label}
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <button class="button" id="add-subject">Add</button>
                    </td>
                </tr>
            </table>
        </div>
        <div style="flex: 1;">
            <table id="subjects-added" class="table">
                <thead>
                <tr class="header">
                    <th>Subject Id</th>
                    <th>&nbsp;</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="ext" items="${pdsubjects}">
                    <tr>
                        <td>
                            {ext.primaryLabel}
                        </td>
                        <td colspan>

                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="buttons">
            <button class="button" type="submit">
                <fmt:message key="save" bundle="${resword}"/>
            </button>
            <button class="button" id="cancel">
                <fmt:message key="cancel" bundle="${resword}"/>
            </button>
        </div>
    </form>
</div>

<DIV ID="testdiv1" STYLE="position:absolute;z-index:5;visibility:hidden;background-color:white;layer-background-color:white;"></DIV>
