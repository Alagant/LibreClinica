<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<fmt:setBundle basename="org.akaza.openclinica.i18n.words" var="resword"/>
<fmt:setBundle basename="org.akaza.openclinica.i18n.notes" var="restext"/>


<jsp:include page="../include/submit-header.jsp"/>
<!-- move the alert message to the sidebar-->
<jsp:include page="../include/sideAlert.jsp"/>

<link rel="stylesheet" href="includes/jmesa/jmesa.css" type="text/css">

<script type="text/JavaScript" language="JavaScript" src="includes/jmesa/jquery.min.js"></script>
<script type="text/JavaScript" language="JavaScript" src="includes/jmesa/jquery.jmesa.js"></script>
<script type="text/JavaScript" language="JavaScript" src="includes/jmesa/jmesa.js"></script>
<%-- <script type="text/JavaScript" language="JavaScript" src="includes/jmesa/jmesa-original.js"></script> --%>
<script type="text/javascript" language="JavaScript" src="includes/jmesa/jquery.blockUI.js"></script>

<script type="text/javascript" language="JavaScript" src="includes/jmesa/jquery-migrate-1.1.1.js"></script>

<tr id="sidebar_Instructions_open" style="display: none">
    <td class="sidebar_tab">

        <a href="javascript:leftnavExpand('sidebar_Instructions_open'); leftnavExpand('sidebar_Instructions_closed');"><img src="images/sidebar_collapse.gif" border="0" align="right" hspace="10"></a>

        <b><fmt:message key="instructions" bundle="${resword}"/></b>

        <div class="sidebar_tab_content">

        </div>

    </td>

</tr>
<tr id="sidebar_Instructions_closed" style="display: all">
    <td class="sidebar_tab">

        <a href="javascript:leftnavExpand('sidebar_Instructions_open'); leftnavExpand('sidebar_Instructions_closed');"><img src="images/sidebar_expand.gif" border="0" align="right" hspace="10"></a>

        <b><fmt:message key="instructions" bundle="${resword}"/></b>

    </td>
</tr>
<jsp:include page="../include/sideInfo.jsp"/>

<jsp:useBean scope='session' id='userBean' class='org.akaza.openclinica.bean.login.UserAccountBean'/>
<jsp:useBean scope='request' id='crf' class='org.akaza.openclinica.bean.admin.CRFBean'/>


<h1><span class="title_manage">IRB Site Definition</span></h1>
<div class="form-standard">
    <style type="text/css" scoped>
        table tbody tr td:nth-child(2) {
        text-align: left;
        }
        table tfoot tr td:nth-child(2) {
        text-align: left;
        }
    </style>
    <form action="${pageContext.request.contextPath}/IrbSite" method="post">
        <input type="hidden" name="siteId" value="${siteId}"/>
        <table>
            <tr>
                <td align="right"><label>Version number</label></td>
                <td><input name="version_number" value="${irbSiteBean.versionNumber}"/></td>
            </tr>
            <tr>
                <td align="right"><input name="site_relies_on_cdc_irb" type="checkbox" value="1"
                           ${irbSiteBean.siteReliesOnCdcIrb?'checked':''}/>
                </td>
                <td><label>Sites relies on CDC IRB</label></td>
            </tr>
            <tr>
                <td align="right">
                    <input name="is_1572" type="checkbox" value="1"
                           ${irbSiteBean.is1572?'checked':''} />
                </td>
                <td><label>1572</label></td>
            </tr>
            <tr>
                <td align="right">
                    <label>CDC IRB protocol version date</label>
                </td>
                <td>
                    <input name="cdc_irb_protocol_version_date" value="${irbSiteBean.cdcIrbProtocolVersionDate}"/>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>Local IRB approved protocol</label>
                </td>
                <td>
                    <input name="local_irb_approved_protocol" value="${irbSiteBean.localIrbApprovedProtocol}"/>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>CDC received local documents</label>
                </td>
                <td>
                    <input name="cdc_received_local_documents" value="${irbSiteBean.cdcReceivedLocalDocuments}"/>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>Site consent package send to CDC IRB</label>
                </td>
                <td>
                    <input name="site_consent_package_send_to_cdc_irb" value="${irbSiteBean.siteConsentPackageSendToCdcIrb}"/>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>Initial CDC IRB approval</label>
                </td>
                <td>
                    <input name="initial_cdc_irb_approval" value="${irbSiteBean.initialCdcIrbApproval}"/>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>CRB approval to enroll</label>
                </td>
                <td>
                    <input name="crb_approval_to_enroll" value="${irbSiteBean.crbApprovalToEnroll}"/>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>IRB approval</label>
                </td>
                <td>
                    <input name="irb_approval" value="${irbSiteBean.irbApproval}"/>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>Expiration date</label>
                </td>
                <td>
                    <input name="expiration_date" value="${irbSiteBean.expirationDate}"/>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <input name="active" type="checkbox" value="1"
                           ${irbSiteBean.active?'checked':''}/>
                </td>
                <td>
                    <label>Active</label>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label>Comments</label>
                </td>
                <td>
                    <textarea>
                        ${irbSiteBean.comments}
                    </textarea>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td><button class="button" type="submit">Submit</button></td>
            </tr>
        </table>
    </form>
    <jsp:include page="./irbProtocolActions.jsp"/>


<jsp:include page="../include/footer.jsp"/>


