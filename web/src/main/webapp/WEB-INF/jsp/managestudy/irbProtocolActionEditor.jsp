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
                            <option value="${c.irbProtocolActionHistoryParameterId}">
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
                    <input type="text" name="version_date" class="formfield"/>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>Version No:</label>
                </td>
                <td>
                    <input type="text" name="version_number" class="formfield" />
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>Site submitted to local IRB:</label>
                </td>
                <td>
                    <input type="text" name="site_submitted_to_local_irb" class="formfield" />
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>Local IRB approval:</label>
                </td>
                <td>
                    <input type="text" name="local_irb_approval" class="formfield"/>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>Site sends docs to IRB:</label>
                </td>
                <td>
                    <input type="text" name="site_sends_docs_to_irb" class="formfield"/>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>Package sent to CDC IRB:</label>
                </td>
                <td>
                    <input type="text" name="package_sent_to_cdc_irb" class="formfield"/>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>CDC Approval/Acknowledgement:</label>
                </td>
                <td>
                    <input type="text" name="cdc_approval" class="formfield"/>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>Enrollment pause date:</label>
                </td>
                <td>
                    <input type="text" name="enrollment_pause_date" class="formfield"/>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>Enrollment restarted date:</label>
                </td>
                <td>
                    <input type="text" name="enrollment_restarted_date" class="formfield"/>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>Reason for enrollment paused:</label>
                </td>
                <td>
                    <input type="text" name="reason_for_enrollment_paused" class="formfield"/>
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