<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="org.akaza.openclinica.bean.managestudy.IRBStudyBean" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.Format" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="org.joda.time.DateTime" %>
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


<h1>IRB Study</h1>
${x}
${irbStudyBean.getApprovalByCdcIrb()}
${irbStudyBean.getApprovalByCdcIrb().getClass()}
${approvalByCdcIrb}
<div class="form-standard">
    <form action="${pageContext.request.contextPath}/IrbStudy" method="post">
        <table>
            <tr>
                <td><label>Protocol number</label></td>
                <td><input name="cdc_irb_protocol_number" value="${irbStudyBean.cdcIrbProtocolNumber}"/></td>
            </tr>
            <tr>
                <td><label>Version 1 protocol date</label></td>
                <td>
                    <input name="version1_protocol_date" id="version1_protocol_date" value="${version1ProtocolDate}">
                    <a href="#">
                        <img src="images/bt_Calendar.gif" title="" border="0" id="version1_protocol_date-trigger" />
                    </a>
                    <script type="text/javascript">
                        Calendar.setup({inputField  : "version1_protocol_date", ifFormat: "%d-%b-%Y",
                            button: "version1_protocol_date-trigger" });
                    </script>
                </td>
            </tr>
            <tr>
                <td><label>Protocol officer</label></td>
                <td><input name="protocol_officer" value="${irbStudyBean.protocolOfficer}"></td>
            </tr>
            <tr>
                <td><label>Submitted CDC IRB</label></td>
                <td>
                    <input name="submitted_cdc_irb" id="submitted_cdc_irb" value="${submittedCdcIrb}">
                    <a href="#">
                        <img src="images/bt_Calendar.gif" title="" border="0" id="submitted_cdc_irb-trigger" />
                    </a>
                    <script type="text/javascript">
                        Calendar.setup({inputField  : "submitted_cdc_irb", ifFormat: "%d-%b-%Y",
                            button: "submitted_cdc_irb-trigger" });
                    </script>
                </td>
            </tr>
            <tr>
                <td><label>Approval by CDC IRB</label></td>
                <td>
                    <input name="approval_by_cdc_irb" id="approval_by_cdc_irb" value="${approvalByCdcIrb}">
                    <a href="#">
                        <img src="images/bt_Calendar.gif" title="" border="0" id="approval_by_cdc_irb-trigger" />
                    </a>
                    <script type="text/javascript">
                        Calendar.setup({inputField  : "approval_by_cdc_irb", ifFormat: "%d-%b-%Y",
                            button: "approval_by_cdc_irb-trigger" });
                    </script>
                </td>
            </tr>
            <tr>
                <td><label>CDC IRB expiration date</label></td>
                <td>
                    <input name="cdc_irb_expiration_date" id="cdc_irb_expiration_date" value="${cdcIrbExpirationDate}">
                    <a href="#">
                        <img src="images/bt_Calendar.gif" title="" border="0" id="cdc_irb_expiration_date-trigger" />
                    </a>
                    <script type="text/javascript">
                        Calendar.setup({inputField  : "cdc_irb_expiration_date", ifFormat: "%d-%b-%Y",
                            button: "cdc_irb_expiration_date-trigger" });
                    </script>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td><button class="button" type="submit">Submit</button></td>
            </tr>
        </table>
    </form>
</div>


<%-- Work here --%>



<jsp:include page="../include/footer.jsp"/>


