<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1 id="title-editor">New Protocol Action</h1>
<form method="post" onsubmit="return validateForm()"
      action="${pageContext.request.contextPath}/IrbSite">
    <style type="text/css" scoped>
        table tbody tr td:nth-child(2) {
            text-align: left;
        }
        table tfoot tr td:nth-child(2) {
            text-align: left;
        }
    </style>
    <input type="hidden" name="siteId" value="${siteId}"/>
    <input type="hidden" name="protocolActionHistoryId">
    <input type="hidden" name="action" value="saveProtocolActionEditor">
    <table>
        <tbody>
            <tr>
                <td align="right"><label>Action:</label></td>
                <td>
                    <select name="protocol_action_type_id" id="protocolActionHistoryParameterSelection" class="formfield">
                        <c:forEach var="c" items="${protocolActionHistoryParameter}">
                            <option value="${c.irbProtocolActionHistoryParameterId}"
                                    data-cdc-irb-protocol-version-date="${c.cdcIrbProtocolVersionDate}"
                                    data-version="${c.version}"
                                    data-site-submitted-to-local-irb="${c.siteSubmittedToLocalIrb}"
                                    data-local-irb-approval="${c.localIrbApproval}"
                                    data-site-sends-docs-to-crb="${c.siteSendsDocsToCrb}"
                                    data-package-sent-to-cdc-irb="${c.packageSentToCdcIrb}"
                                    data-cdc-approval-acknowledgment="${c.cdcApprovalAcknowledgment}"
                                    data-enrollment-pause-date="${c.enrollmentPauseDate}"
                                    data-enrollment-re-started-date="${c.enrollmentReStartedDate}"
                                    data-reason-for-enrollment-pause="${c.enrollmentReStartedDate}"
                            >
                                ${c.action}
                            </option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>CDC IRB protocol version date:</label>
                </td>
                <td>
                    <input type="text" id="version_date" name="version_date" class="formfield"/>
                    <img src="images/bt_Calendar.gif" alt="<fmt:message key="show_calendar" bundle="${resword}"/>"
                         title="<fmt:message key="show_calendar" bundle="${resword}"/>"
                         border="0" id="version_date-trigger" /> *
                    <script type="text/javascript">
                        Calendar.setup({inputField: "version_date",
                            ifFormat: "<fmt:message key="date_format_calender" bundle="${resformat}"/>",
                            button: "version_date-trigger", customPX: 300, customPY: 10 });
                    </script>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>Version No:</label>
                </td>
                <td>
                    <input type="text" id="version_number" name="version_number" class="formfield" />
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>Site submitted to local IRB:</label>
                </td>
                <td>
                    <input type="text" id="site_submitted_to_local_irb" name="site_submitted_to_local_irb" class="formfield"/>
                    <img src="images/bt_Calendar.gif" alt="<fmt:message key="show_calendar" bundle="${resword}"/>"
                         title="<fmt:message key="show_calendar" bundle="${resword}"/>"
                         border="0" id="site_submitted_to_local_irb-trigger" /> *
                    <script type="text/javascript">
                        Calendar.setup({inputField: "site_submitted_to_local_irb",
                            ifFormat: "<fmt:message key="date_format_calender" bundle="${resformat}"/>",
                            button: "site_submitted_to_local_irb-trigger", customPX: 300, customPY: 10 });
                    </script>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>Local IRB approval:</label>
                </td>
                <td>
                    <input type="text" id="local_irb_approval" name="local_irb_approval" class="formfield"/>
                    <img src="images/bt_Calendar.gif" alt="<fmt:message key="show_calendar" bundle="${resword}"/>"
                         title="<fmt:message key="show_calendar" bundle="${resword}"/>"
                         border="0" id="local_irb_approval-trigger" /> *
                    <script type="text/javascript">
                        Calendar.setup({inputField: "local_irb_approval",
                            ifFormat: "<fmt:message key="date_format_calender" bundle="${resformat}"/>",
                            button: "local_irb_approval-trigger", customPX: 300, customPY: 10 });
                    </script>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>Site sends docs to IRB:</label>
                </td>
                <td>
                    <input type="text" id="site_sends_docs_to_irb" name="site_sends_docs_to_irb" class="formfield"/>
                    <img src="images/bt_Calendar.gif" alt="<fmt:message key="show_calendar" bundle="${resword}"/>"
                         title="<fmt:message key="show_calendar" bundle="${resword}"/>"
                         border="0" id="site_sends_docs_to_irb-trigger" /> *
                    <script type="text/javascript">
                        Calendar.setup({inputField: "site_sends_docs_to_irb",
                            ifFormat: "<fmt:message key="date_format_calender" bundle="${resformat}"/>",
                            button: "site_sends_docs_to_irb-trigger", customPX: 300, customPY: 10 });
                    </script>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>Package sent to CDC IRB:</label>
                </td>
                <td>
                    <input type="text" id="package_sent_to_cdc_irb" name="package_sent_to_cdc_irb" class="formfield"/>
                    <img src="images/bt_Calendar.gif" alt="<fmt:message key="show_calendar" bundle="${resword}"/>"
                         title="<fmt:message key="show_calendar" bundle="${resword}"/>"
                         border="0" id="package_sent_to_cdc_irb-trigger" /> *
                    <script type="text/javascript">
                        Calendar.setup({inputField: "package_sent_to_cdc_irb",
                            ifFormat: "<fmt:message key="date_format_calender" bundle="${resformat}"/>",
                            button: "package_sent_to_cdc_irb-trigger", customPX: 300, customPY: 10 });
                    </script>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>CDC Approval/Acknowledgement:</label>
                </td>
                <td>
                    <input type="text" id="cdc_approval" name="cdc_approval" class="formfield"/>
                    <img src="images/bt_Calendar.gif" alt="<fmt:message key="show_calendar" bundle="${resword}"/>"
                         title="<fmt:message key="show_calendar" bundle="${resword}"/>"
                         border="0" id="cdc_approval-trigger" /> *
                    <script type="text/javascript">
                        Calendar.setup({inputField: "cdc_approval",
                            ifFormat: "<fmt:message key="date_format_calender" bundle="${resformat}"/>",
                            button: "cdc_approval-trigger", customPX: 300, customPY: 10 });
                    </script>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>Enrollment pause date:</label>
                </td>
                <td>
                    <input type="text" id="enrollment_pause_date" name="enrollment_pause_date" class="formfield"/>
                    <img src="images/bt_Calendar.gif" alt="<fmt:message key="show_calendar" bundle="${resword}"/>"
                         title="<fmt:message key="show_calendar" bundle="${resword}"/>"
                         border="0" id="enrollment_pause_date-trigger" /> *
                    <script type="text/javascript">
                        Calendar.setup({inputField: "enrollment_pause_date",
                            ifFormat: "<fmt:message key="date_format_calender" bundle="${resformat}"/>",
                            button: "enrollment_pause_date-trigger", customPX: 300, customPY: 10 });
                    </script>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>Enrollment restarted date:</label>
                </td>
                <td>
                    <input type="text" id="enrollment_restarted_date" name="enrollment_restarted_date" class="formfield"/>
                    <img src="images/bt_Calendar.gif" alt="<fmt:message key="show_calendar" bundle="${resword}"/>"
                         title="<fmt:message key="show_calendar" bundle="${resword}"/>"
                         border="0" id="enrollment_restarted_date-trigger" /> *
                    <script type="text/javascript">
                        Calendar.setup({inputField: "enrollment_restarted_date",
                            ifFormat: "<fmt:message key="date_format_calender" bundle="${resformat}"/>",
                            button: "enrollment_restarted_date-trigger", customPX: 300, customPY: 10 });
                    </script>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>Reason for enrollment paused:</label>
                </td>
                <td>
                    <input type="text" id="reason_for_enrollment_paused" name="reason_for_enrollment_paused" class="formfield"/>
                </td>
            </tr>
        </tbody>
        <tfoot>
            <tr>
                <td></td>
                <td>
                    <button type="submit" class="button">Submit</button>
                    <button type="button" class="button close-edition">Cancel</button>
                </td>
            </tr>
        </tfoot>
    </table>
</form>
<script>
    function enableFieldsByActionParameter() {
        const option = jQuery('#protocolActionHistoryParameterSelection option[value="' +
            jQuery("#protocolActionHistoryParameterSelection").val() + '"]'
        );

        jQuery('#protocol-action-editor .table-history-editor img').css('display', 'none');
        jQuery('#version_date').attr('disabled', 'disabled');
        jQuery('#version_number').attr('disabled', 'disabled');
        jQuery('#site_submitted_to_local_irb').attr('disabled', 'disabled');
        jQuery('#local_irb_approval').attr('disabled', 'disabled');
        jQuery('#site_sends_docs_to_irb').attr('disabled', 'disabled');
        jQuery('#package_sent_to_cdc_irb').attr('disabled', 'disabled');
        jQuery('#cdc_approval').attr('disabled', 'disabled');
        jQuery('#enrollment_pause_date').attr('disabled', 'disabled');
        jQuery('#enrollment_restarted_date').attr('disabled', 'disabled');
        jQuery('#reason_for_enrollment_paused').attr('disabled', 'disabled');

        if (jQuery(option).data('cdc-irb-protocol-version-date')) {
            jQuery('#version_date').removeAttr('disabled');
            jQuery('#version_date-trigger').removeAttr('disabled');
        }

        if (jQuery(option).data('version')) {
            jQuery('#version_number').removeAttr('disabled');
        }

        if (jQuery(option).data('site-submitted-to-local-irb')) {
            jQuery('#site_submitted_to_local_irb').removeAttr('disabled');
            jQuery('#site_submitted_to_local_irb-trigger').removeAttr('disabled');
        }

        if (jQuery(option).data('local-irb-approval')) {
            jQuery('#local_irb_approval').removeAttr('disabled');
            jQuery('#local_irb_approval-trigger').removeAttr('disabled');
        }

        if (jQuery(option).data('site-sends-docs-to-crb')) {
            jQuery('#site_sends_docs_to_irb').removeAttr('disabled');
            jQuery('#site_sends_docs_to_irb-trigger').removeAttr('disabled');
        }

        if (jQuery(option).data('package-sent-to-cdc-irb')) {
            jQuery('#package_sent_to_cdc_irb').removeAttr('disabled');
            jQuery('#package_sent_to_cdc_irb-trigger').removeAttr('disabled');
        }

        if (jQuery(option).data('cdc-approval-acknowledgment')) {
            jQuery('#cdc_approval').removeAttr('disabled');
            jQuery('#cdc_approval-trigger').removeAttr('disabled');
        }

        if (jQuery(option).data('enrollment-pause-date')) {
            jQuery('#enrollment_pause_date').removeAttr('disabled');
            jQuery('#enrollment_pause_date-trigger').removeAttr('disabled');
        }

        if (jQuery(option).data('enrollment-re-started-date')) {
            jQuery('#enrollment_restarted_date').removeAttr('disabled');
            jQuery('#enrollment_restarted_date-trigger').removeAttr('disabled');
        }

        if (jQuery(option).data('reason-for-enrollment-pause')) {
            jQuery('#reason_for_enrollment_paused').removeAttr('disabled');
        }
    }

    jQuery("#protocolActionHistoryParameterSelection").change(e => {
        enableFieldsByActionParameter();
    });
</script>