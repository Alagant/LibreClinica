<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <style type="text/css" scoped>
        #table-history tbody tr:nth-child(odd) td {
            background: var(--gray-l40);
        }
    </style>
<script>
    function loadActionHistoryEditor(payload) {
        jQuery("#studyActionHistoryParameterSelection").val(payload.actionType);
        jQuery('input[name="h_study_action_history_id"]').val(payload.id);


        jQuery('input[name="h_effective_date"]').val(payload.effectiveDate);
        jQuery('input[name="h_hrpo_action"]').val(payload.hrpoAction);
        jQuery('input[name="h_version_number"]').val(payload.versionNumber);
        jQuery('input[name="h_version_date"]').val(payload.versionDate);
        jQuery('input[name="h_submission_to_cdc_irb"]').val(payload.submissionToCdcIrb);
        jQuery('input[name="h_cdc_irb_approval"]').val(payload.cdcIrbApproval);
        jQuery('input[name="h_notification_sent_to_sites"]').val(payload.notificationSentToSites);
        jQuery('input[name="h_enrollment_pause_date"]').val(payload.enrollmentPauseDate);
        jQuery('input[name="h_enrollment_re_started_date"]').val(payload.enrollmentReStartedDate);
        jQuery('input[name="h_reason_for_enrollment_pause"]').val(payload.reasonForEnrollmentPause);
        enableFieldsByActionParameter();

        jQuery.blockUI({
            message: jQuery('#study-action-editor'),
            css:{left: "300px", top:"10px", width: "400px" }
        });
    }


    jQuery(document).ready(function () {


        jQuery('#protocol-action-open').click(function () {
            jQuery('input[name="h_study_action_history_id"]').val('');
            jQuery("#studyActionHistoryParameterSelection").val('');
            jQuery('#study-action-editor input[type="text"]').val('');
            enableFieldsByActionParameter();

            jQuery.blockUI({
                message: jQuery('#protocol-action-editor'),
                css:{left: "300px", top:"10px", width: "400px" }
            });
        });


        jQuery('a.update-history').click(function (e) {
            const tr = jQuery(e.currentTarget).closest('tr');
            loadActionHistoryEditor({
                actionType: jQuery(tr).data('action-type'),
                id: jQuery(tr).data('id'),
                effectiveDate: jQuery(tr).find('td:nth-child(2)').text(),
                hrpoAction: jQuery(tr).find('td:nth-child(3)').text(),
                versionNumber: jQuery(tr).find('td:nth-child(4)').text(),
                versionDate: jQuery(tr).find('td:nth-child(5)').text(),
                submissionToCdcIrb: jQuery(tr).find('td:nth-child(6)').text(),
                cdcIrbApproval: jQuery(tr).find('td:nth-child(7)').text(),
                notificationSentToSites: jQuery(tr).find('td:nth-child(8)').text(),
                enrollmentPauseDate: jQuery(tr).find('td:nth-child(9)').text(),
                enrollmentReStartedDate: jQuery(tr).find('td:nth-child(10)').text(),
                reasonForEnrollmentPause: jQuery(tr).find('td:nth-child(11)').text(),
            });


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



    jQuery(document).ready(function () {
        jQuery('#protocol-action-open').click(function () {
            jQuery('input[name="h_study_action_history_id"]').val('');
            jQuery("#studyActionHistoryParameterSelection").val('');
            jQuery('#study-action-editor input[type="text"]').val('');
            enableFieldsByActionParameter();

            jQuery.blockUI({
                message: jQuery('#protocol-action-editor'),
                css:{left: "300px", top:"10px", width: "400px" }
            });
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
<table id="table-history-editor">
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
            <th>Enrollment Pause Date</th>
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
                <td>${c.enrollmentPauseDate}</td>
                <td>${c.enrollmentRestartedDate}</td>
                <td>${c.reasonForEnrollmentPaused}</td>
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
</div>