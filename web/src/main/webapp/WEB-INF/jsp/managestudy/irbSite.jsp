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
    <form action="${pageContext.request.contextPath}/IrbStudy" method="post">
        <input type="hidden" name="study_id" value="{siteId}"/>
        <div class="form-item">
            <label>Version number</label>
            <input name="version_number" value="${irbStudyBean.cdcIrbProtocolNumber}"/>
        </div>
        <div class="form-item">
            <label>Sites relies on CDC IRB</label>
            <input name="site_relies_on_cdc_irb" type="checkbox"
                   value="${irbStudyBean.siteReliesOnCdcIrb?'checked':''}"/>
        </div>
        <div class="form-item">
            <label>1572</label>
            <input name="is_1572" type="checkbox"
                   value="${irbStudyBean.is1572?'checked':''}"/>
        </div>
        <div class="form-item">
            <label>CDC IRB protocol version date</label>
            <input name="cdc_irb_protocol_version_date" value="${irbStudyBean.cdcProtocolVersionDate}"/>
        </div>
        <div class="form-item">
            <label>Local IRB approved protocol</label>
            <input name="local_irb_approved_protocol" value="${irbStudyBean.localIrbApprovedProtocol}"/>
        </div>
        <div class="form-item">
            <label>CDC received local documents</label>
            <input name="cdc_received_local_documents" value="${irbStudyBean.cdcReceivedLocalDocuments}"/>
        </div>
        <div class="form-item">
            <label>Site consent package send to CDC IRB</label>
            <input name="site_consent_package_send_to_cdc_irb" value="${irbStudyBean.siteConsentPackageSendToCdcIrb}"/>
        </div>
        <div class="form-item">
            <label>Initial CDC IRB approval</label>
            <input name="initial_cdc_irb_approval" value="${irbStudyBean.initialCdcIrbApproval}"/>
        </div>
        <div class="form-item">
            <label>CRB approval to enroll</label>
            <input name="crb_approval_to_enroll" value="${irbStudyBean.crbApprovalToEnroll}"/>
        </div>
        <div class="form-item">
            <label>IRB approval</label>
            <input name="irb_approval" value="${irbStudyBean.irbApproval}"/>
        </div>
        <div class="form-item">
            <label>Expiration date</label>
            <input name="expiration_date" value="${irbStudyBean.expirationDate}"/>
        </div>
        <div class="form-item">
            <label>Active</label>
            <input name="active" type="checkbox"
                   value="${irbStudyBean.active?'checked':''}"/>
        </div>
        <button class="button" type="submit">Submit</button>
    </form>

<%-- Work here --%>



<jsp:include page="../include/footer.jsp"/>


