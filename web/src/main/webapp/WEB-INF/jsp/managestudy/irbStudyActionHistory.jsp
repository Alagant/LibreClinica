<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="studyActionHistory" scope="request" type="java.util.List"/>
<div>
    <style type="text/css" scoped>
        #table-history tbody tr:nth-child(odd) td {
            background: var(--gray-l40);
        }
    </style>
<script>
    jQuery(document).ready(function () {


        jQuery('#study-action-open').click(function () {
            jQuery("#studyActionHistoryParameterSelection").val('');
            jQuery('#study-action-editor input[type="text"]').val('');
            enableFieldsByActionParameter();

            jQuery.blockUI({
                message: jQuery('#study-action-editor'),
                css:{left: "300px", top:"10px", width: "400px" }
            });
        });
        jQuery('a.update-history').click(function (e) {
            const tr = jQuery(e.currentTarget).closest('tr');
            jQuery("#studyActionHistoryParameterSelection").val(jQuery(tr).data('action-type'));
            jQuery('input[name="study_action_history_id"]').val(jQuery(tr).data('id'));


            jQuery('input[name="effective_date"]').val(jQuery(tr).find('td:nth-child(2)').text());
            jQuery('input[name="hrpo_action"]').val(jQuery(tr).find('td:nth-child(3)').text());
            jQuery('input[name="version_number"]').val(jQuery(tr).find('td:nth-child(4)').text());
            jQuery('input[name="version_date"]').val(jQuery(tr).find('td:nth-child(5)').text());
            jQuery('input[name="submission_to_cdc_irb"]').val(jQuery(tr).find('td:nth-child(6)').text());
            jQuery('input[name="cdc_irb_approval"]').val(jQuery(tr).find('td:nth-child(7)').text());
            jQuery('input[name="notification_sent_to_sites"]').val(jQuery(tr).find('td:nth-child(8)').text());
            enableFieldsByActionParameter();

            jQuery.blockUI({
                message: jQuery('#study-action-editor'),
                css:{left: "300px", top:"10px", width: "400px" }
            });
            //jQuery('input[name="studyActionHistoryParameterSelection"]').val(jQuery(tr).data('id'));

        });

        jQuery('.close-editor').click(function() {
            jQuery.unblockUI();
            return false;
        });
    });
</script>

<div id="study-action-editor" style="display:none;">
    <jsp:include page="./irbStudyActionEditor.jsp"/>
</div>
<h2>Study action history</h2>
<table id="table-history">
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
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>

        <c:forEach var="h" items="${studyActionHistory}">
            <tr data-id="${h['id']}" data-action-type="${h['actionTypeId']}">
                <td>${h['actionLabel']}</td>
                <td>${h['effectiveDate']}</td>
                <td>${h['hrpoAction']}</td>
                <td>${h['versionNumber']}</td>
                <td>${h['versionDate']}</td>
                <td>${h['submissionToCdcIrb']}</td>
                <td>${h['cdcIrbApproval']}</td>
                <td>${h['notificationSentToSites']}</td>
                <td>
                    <a href="#" class="update-history">Edit</a>
                </td>
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
</div>