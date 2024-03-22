<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="studyActionHistory" scope="request" type="java.util.List"/>
<script>
    jQuery(document).ready(function () {
        jQuery('#study-action-open').click(function () {
            jQuery.blockUI({
                message: jQuery('#study-action-editor'),
                css:{left: "300px", top:"10px", width: "400px" }
            });
        });
        jQuery('button.close-editor').click(function() {
            jQuery.unblockUI();
            return false;
        });
    });
</script>

<div id="study-action-editor" style="display:none;">
    <jsp:include page="./irbStudyActionEditor.jsp"/>
</div>
<h2>Study action history</h2>
<table>
    <thead>
    <tr>
        <th>Action</th>
        <th>Efective Date</th>
        <th>HRPO Action #</th>
        <th>Version No</th>
        <th>Version Date</th>
        <th>Package sent to CDC IRB</th>
        <th>CDC IRB Approval/Memo</th>
        <th>Memo notification sent to sites</th>
    </tr>
    </thead>
    <tbody>

        <c:forEach var="h" items="${studyActionHistory}">
            <tr>
                <td>${h['actionLabel']}</td>
                <td>${h['effectiveDate']}</td>
                <td>${h['hrpoAction']}</td>
                <td>${h['versionNumber']}</td>
                <td>${h['versionDate']}</td>
                <td>${h['submissionToCdcIrb']}</td>
                <td>${h['cdcIrbApproval']}</td>
                <td>${h['notificationSentToSites']}</td>
            </tr>
        </c:forEach>
    </tbody>
    <tfoot style="background: #f7f7f7;">
    <tr>
        <td colspan="10" style="padding: 5px;">
            <button type="button" class="button" id="study-action-open">Add</button>
        </td>
    </tr>
    </tfoot>
</table>