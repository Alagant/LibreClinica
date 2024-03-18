<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: iceman
  Date: 16/03/2024
  Time: 09:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    jQuery(document).ready(function () {
        jQuery("#protocol-action-open").click(() => {
            jQuery.blockUI({ message: jQuery('#protocol-action-editor'), css:{left: "300px", top:"10px", width: "400px" } });
        });
        jQuery('button.close-editor').click(function() {
            jQuery.unblockUI();
            return false;
        });
    });
</script>

<div id="protocol-action-editor" style="display:none;">
    <jsp:include page="./irbProtocolActionEditor.jsp"/>
</div>
<h2>Protocol action history</h2>
<table>
    <thead>
        <tr>
            <th>Action</th>
            <th>CDC IRB Protocol Version Date</th>
            <th>Version No</th>
            <th>Site submitted to local IRB</th>
            <th>Local IRB Approval</th>
            <th>Received docs from sites</th>
            <th>Package sent to CDC IRB</th>
            <th>CDC Approval/ Acknowledgment</th>
            <th>Enrollment Restarted Date</th>
            <th>Reason for enrollment paused</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="c" items="${protocolActionHistory}">
            <tr>
                <td>${c.label}</td>
                <td>${c.versionDate}</td>
                <td>${c.versionNumber}</td>
                <td>${c.siteSubmittedToLocalIrb}</td>
                <td>${c.localIrbApproval}</td>
                <td>${c.receivedDocsFromSites}</td>
                <td>${c.packageSentToCdcIrb}</td>
                <td>${c.cdcApproval}</td>
                <td></td>
                <td></td>
            </tr>
        </c:forEach>
    </tbody>
    <tfoot style="background: #f7f7f7;">
        <tr>
            <td colspan="10" style="padding: 5px;">
                <button type="button" class="button" id="protocol-action-open">Add</button>
            </td>
        </tr>
    </tfoot>
</table>