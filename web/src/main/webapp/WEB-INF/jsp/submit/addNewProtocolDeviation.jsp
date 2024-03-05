<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="org.akaza.openclinica.i18n.words" var="resword"/>
<fmt:setBundle basename="org.akaza.openclinica.i18n.format" var="resformat"/>
<div class="add_new_subject_div form-standard" style="display: flex; flex-direction: column; padding: 10px; row-gap: 10px;">
    <style type="text/css" scoped>
        h1 {
            display: block;
            color: var(--lightblue-d20);
            font-family: Tahoma, Arial, Helvetica, sans-serif;
        }
        h2 {
            display: block;
            color: white;
            font-family: Tahoma, Arial, Helvetica, sans-serif;
            font-size: 1rem;
            padding: 4px;
            background: var(--lightblue-d20);
        }
        li {
            list-style-image: none;
            margin-bottom: 8px;
        }
        li:nth-child(even) {
            background: #4D4D4D;
        }
    </style>
    <h1 id="title-editor">New Protocol Deviation</h1>
    <form method="post" action="${pageContext.request.contextPath}/ProtocolDeviations">
        <c:import url="../submit/protocolDeviationEditorSectionA.jsp">
        </c:import>
        <c:import url="../submit/protocolDeviationEditorSectionB.jsp">
        </c:import>
        <c:import url="../submit/protocolDeviationEditorSectionC.jsp">
        </c:import>
        <c:import url="../submit/protocolDeviationEditorSectionD.jsp">
        </c:import>
        <c:import url="../submit/protocolDeviationEditorSectionE.jsp">
        </c:import>
        <c:import url="../submit/protocolDeviationEditorSectionF.jsp">
        </c:import>
        <c:import url="../submit/protocolDeviationEditorSectionG.jsp">
        </c:import>
    <div style="display: flex;">

        <div class="formlabel" style="width: 120px;">
            Severity:
        </div>
        <div class="formlabel" style="">
            <select class="formfield" id="severity" name="severity">
                <c:forEach var="p" items="${severities}">
                    <option value="${p.protocolDeviationSeverityId}">
                            ${p.label}
                    </option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div style="display: flex;">
        <div class="formlabel" style="width: 120px;">
            Description:
        </div>
        <div class="formlabel" style="">
            <textarea id="description" name="description" rows="3"></textarea>
        </div>
    </div>


    <h3>Subjects added</h3>
        <div style="display: flex;">
            <div class="formlabel" style="width: 120px;">
                Subject Id:
            </div>
            <div class="formlabel" style="">
                <select class="formfield" id="new-subject">
                    <c:forEach var="p" items="${subjects}">
                        <option value="${p.id}">
                                ${p.label}
                        </option>
                    </c:forEach>
                </select>
                <button type="button" class="button" id="add-subject">Add</button>
            </div>
        </div>
        <div style="display: flex;">
            <div class="formlabel" style="width: 120px;">Text to search</div>
            <div><input type="text"></div>
        </div>

        <div style="height: 240px;" id="subjects-added" class="protocol-deviation-subject-container">

        </div>
        <div class="buttons">
            <input type="hidden" name="action" value="saveProtocolDeviations"/>
            <
            <button class="button" type="submit">
                <fmt:message key="save" bundle="${resword}"/>
            </button>
            <button type="button" class="button" id="cancel">
                <fmt:message key="cancel" bundle="${resword}"/>
            </button>
        </div>
    </form>

</div>

<DIV ID="testdiv1" STYLE="position:absolute;z-index:5;visibility:hidden;background-color:white;layer-background-color:white;"></DIV>
