<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1 id="title-editor">New Protocol Deviation</h1>
<form method="post" onsubmit="return validateForm()"
      action="${pageContext.request.contextPath}/IrbSite">
    <input type="hidden" name="action" value="saveProtocolActionEditor">
    <table>
        <tbody>
            <tr>
                <th align="right">Action:</th>
                <td>
                    <select name="protocol_action_type_id">
                        <c:forEach var="c" items="protocolActionTypes">
                            <option value="${c.protocolActionTypeId}">
                                ${c.label}
                            </option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <th align="right">CDC IRB protocol version date:</th>
                <td>
                    <input type="text" name="version_date"/>
                </td>
            </tr>
            <tr>
                <th align="right">Version No:</th>
                <td>
                    <input type="text" name="version_number"/>
                </td>
            </tr>
            <tr>
                <th align="right">Site submitted to local IRB:</th>
                <td>
                    <input type="text" name="site_submitted_to_local_irb"/>
                </td>
            </tr>
            <tr>
                <th align="right">Local IRB approval:</th>
                <td>
                    <input type="text" name="local_irb_approval"/>
                </td>
            </tr>
            <tr>
                <th align="right">Site sends docs to IRB:</th>
                <td>
                    <input type="text" name="site_sends_docs_to_irb"/>
                </td>
            </tr>
            <tr>
                <th align="right">Package sent to CDC IRB:</th>
                <td>
                    <input type="text" name="package_sent_to_cdc_irb"/>
                </td>
            </tr>
            <tr>
                <th align="right">CDC Approval/Acknowledgement:</th>
                <td>
                    <input type="text" name="cdc_approval"/>
                </td>
            </tr>
            <tr>
                <th align="right">Enrollment pause date:</th>
                <td>
                    <input type="text" name="enrollment_pause_date"/>
                </td>
            </tr>
            <tr>
                <th align="right">Enrollment restarted date:</th>
                <td>
                    <input type="text" name="enrollment_restarted_date"/>
                </td>
            </tr>
            <tr>
                <th align="right">Reason for enrollment paused:</th>
                <td>
                    <input type="text" name="reason_for_enrollment_paused"/>
                </td>
            </tr>
        </tbody>
        <tfoot>
            <tr>
                <td colspan="2">
                    <button type="submit" class="button">Submit</button>
                    <button type="button" class="button close-edition">Cancel</button>
                </td>
            </tr>
        </tfoot>
    </table>
</form>