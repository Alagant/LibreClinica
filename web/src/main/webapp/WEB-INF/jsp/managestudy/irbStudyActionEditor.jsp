<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="org.akaza.openclinica.i18n.words" var="resword"/>
<fmt:setBundle basename="org.akaza.openclinica.i18n.notes" var="restext"/>
<fmt:setBundle basename="org.akaza.openclinica.i18n.format" var="resformat"/>


<h1 id="title-editor">New Study Action</h1>
<form method="post" onsubmit="return validateForm()"
      action="${pageContext.request.contextPath}/IrbStudy">
  <style type="text/css" scoped>
    table tbody tr td:nth-child(2) {
      text-align: left;
    }
    table tfoot tr td:nth-child(2) {
      text-align: left;
    }
    div.calendar {
      z-index: 2000;
    }

    input:disabled {
      background: gray;
    }
  </style>
  <input type="hidden" name="studyId" value="${studyId}"/>
  <input type="hidden" name="studyActionHistoryId">
  <input type="hidden" name="action" value="saveStudyActionEditor">
  <table>
    <tbody>
    <tr>
      <td align="right"><label>Action:</label></td>
      <td>
        <select name="study_action_type_id" id="studyActionHistoryParameterSelection" class="formfield">
          <option value="">(Select an action)</option>
          <c:forEach var="c" items="${protocolActionHistoryParameter}">
            <option value="${c.irbStudyActionHistoryParameterId}"
                    data-effective-date="${c.effectiveDate}"
                    data-hrpo-action="${c.hrpoAction}"
                    data-version-date="${c.versionDate}"
                    data-version-number="${c.versionNumber}"
                    data-submission-to-cdc-irb="${c.submissionToCdcIrb}"
                    data-cdc-irb-approval="${c.cdcIrbApproval}"
                    data-notification-sent-to-sites="${c.notificationSentToSites}"
                    data-enrollment-pause-date="${c.enrollmentPauseDate}"
                    data-enrollment-re-started-date="${c.enrollmentReStartedDate}"
                    data-reason-for-enrollment-pause="${c.reasonForEnrollmentPause}"
            >
                ${c.action}
            </option>
          </c:forEach>
        </select>
      </td>
    </tr>
    <tr>
      <td align="right">
        <label>Effective date:</label>
      </td>
      <td>
        <input type="text" id="effective_date" name="effective_date" class="formfield"/>
        <img src="images/bt_Calendar.gif" alt="<fmt:message key="show_calendar" bundle="${resword}"/>"
             title="<fmt:message key="show_calendar" bundle="${resword}"/>"
             border="0" id="effective_date-trigger" /> *
        <script type="text/javascript">
          Calendar.setup({inputField: "effective_date",
            ifFormat: "<fmt:message key="date_format_calender" bundle="${resformat}"/>",
            button: "effective_date-trigger", customPX: 300, customPY: 10 });
        </script>
      </td>
    </tr>
    <tr>
      <td align="right">
        <label>HRPO Action:</label>
      </td>
      <td>
        <input type="text" name="hrpo_action" id="hrpo_action" class="formfield"/>
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
        <label>Version date:</label>
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
        <label>Submission to CDC IRB:</label>
      </td>
      <td>
        <input type="text" id="submission_to_cdc_irb" name="submission_to_cdc_irb" class="formfield"/>
        <img src="images/bt_Calendar.gif" alt="<fmt:message key="show_calendar" bundle="${resword}"/>"
             title="<fmt:message key="show_calendar" bundle="${resword}"/>"
             border="0" id="submission_to_cdc_irb-trigger" /> *
        <script type="text/javascript">
          Calendar.setup({inputField: "submission_to_cdc_irb",
            ifFormat: "<fmt:message key="date_format_calender" bundle="${resformat}"/>",
            button: "submission_to_cdc_irb-trigger", customPX: 300, customPY: 10 });
        </script>
      </td>
    </tr>
    <tr>
      <td align="right">
        <label>CDC IRB Approval/memo date:</label>
      </td>
      <td>
        <input type="text" id="cdc_irb_approval" name="cdc_irb_approval" class="formfield"/>
        <img src="images/bt_Calendar.gif" alt="<fmt:message key="show_calendar" bundle="${resword}"/>"
             title="<fmt:message key="show_calendar" bundle="${resword}"/>"
             border="0" id="cdc_irb_approval-trigger" /> *
        <script type="text/javascript">
          Calendar.setup({inputField: "cdc_irb_approval",
            ifFormat: "<fmt:message key="date_format_calender" bundle="${resformat}"/>",
            button: "cdc_irb_approval-trigger", customPX: 300, customPY: 10 });
        </script>
      </td>
    </tr>
    <tr>
      <td align="right">
        <label>Memo/notification sent to sites:</label>
      </td>
      <td>
        <input type="text" id="notification_sent_to_sites" name="notification_sent_to_sites" class="formfield"/>
        <img src="images/bt_Calendar.gif" alt="<fmt:message key="show_calendar" bundle="${resword}"/>"
             title="<fmt:message key="show_calendar" bundle="${resword}"/>"
             border="0" id="notification_sent_to_sites-trigger" /> *
        <script type="text/javascript">
          Calendar.setup({inputField: "notification_sent_to_sites",
            ifFormat: "<fmt:message key="date_format_calender" bundle="${resformat}"/>",
            button: "notification_sent_to_sites-trigger", customPX: 300, customPY: 10 });
        </script>
      </td>
    </tr>
    </tbody>
    <tfoot>
    <tr>
      <td></td>
      <td>
        <button type="submit" class="button">Submit</button>
        <button type="button" class="button close-editor">Cancel</button>
      </td>
    </tr>
    </tfoot>
  </table>
</form>
<script>
  jQuery("#studyActionHistoryParameterSelection").change(e => {
    /*
     data-effective-date="${c.effectiveDate}"
                    data-hrpo-action="${c.hrpoAction}"
                    data-version-date="${c.versionDate}"
                    data-submission-to-cdc-irb="${c.submissionToCdcIrb}"
                    data-cdc-irb-approval="${c.cdcIrbApproval}"
                    data-notification-sent-to-sites="${c.notificationSentToSites}"
    * */
    const option = jQuery('#studyActionHistoryParameterSelection option[value="'+
                      jQuery("#studyActionHistoryParameterSelection").val() + '"]'
                  );

    console.log(option);
    jQuery('#effective_date').attr('disabled', 'disabled');
    jQuery('#hrpo_action').attr('disabled', 'disabled');
    jQuery('#version_date').attr('disabled', 'disabled');
    jQuery('#version_number').attr('disabled', 'disabled');
    jQuery('#submission_to_cdc_irb').attr('disabled', 'disabled');
    jQuery('#cdc_irb_approval').attr('disabled', 'disabled');
    jQuery('#notification_sent_to_sites').attr('disabled', 'disabled');
    jQuery('#notification_sent_to_sites').attr('disabled', 'disabled');

    console.log(jQuery(option).data('effective-date'));

    if(jQuery(option).data('effective-date'))
        jQuery('#effective_date').removeAttr('disabled');

    if(jQuery(option).data('hrpo-action'))
      jQuery('#hrpo_action').removeAttr('disabled');

    if(jQuery(option).data('version-date'))
      jQuery('#version_date').removeAttr('disabled');

    if(jQuery(option).data('version-number'))
      jQuery('#version_number').removeAttr('disabled');

    if(jQuery(option).data('submission-to-cdc-irb'))
      jQuery('#submission_to_cdc_irb').removeAttr('disabled');

    if(jQuery(option).data('cdc-irb-approval'))
      jQuery('#cdc_irb_approval').removeAttr('disabled');

    if(jQuery(option).data('notification-sent-to-sites'))
      jQuery('#notification_sent_to_sites').removeAttr('disabled');


  });
</script>