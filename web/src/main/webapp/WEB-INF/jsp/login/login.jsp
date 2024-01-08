<%
    String oauth_server = "https://cdcoauthmockup.azurewebsites.net";
    String oauth_base_url = oauth_server + "/auth/oauth/v2";
    String openid_base_url = oauth_server + "/openid/connect/v1";
    String self_url = ServletUriComponentsBuilder.fromCurrentContextPath().build().toString();
    String query_string = request.getQueryString();
    String oauth_code = request.getParameter("code");
    String oauth_client_id = "19216801";
    String oauth_client_secret = "3EEnASDAS6pmAASDyPzviWQSDPufTcIpg";
    String oauth_redirect_uri = self_url + "/pages/login/login";
    final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass().getName());

    if(oauth_code != null && !oauth_code.isEmpty()) {
        String oauth_token_url = oauth_base_url + "/token";
        URL url = new URL(oauth_token_url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Authorization", "Basic " +
                Base64.getEncoder().encodeToString( (oauth_client_id+":"+oauth_client_secret).getBytes() )
        );
        StringBuilder sbRequest = new StringBuilder("grant_type=authorization_code");
        sbRequest.append("&code="+oauth_code);
        sbRequest.append("&client_id="+oauth_client_id);
        sbRequest.append("&client_secret="+oauth_client_secret);
        sbRequest.append("&redirect_uri="+ URLEncoder.encode(oauth_redirect_uri, "utf-8"));

        String access_token = "";
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(connection.getOutputStream()));
            bufferedWriter.write(sbRequest.toString());
            bufferedWriter.flush();
            bufferedWriter.close();

            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK) {
                JsonNode jsonNode = objectMapper.readTree(
                    new InputStreamReader(connection.getInputStream(), "utf-8")
                );

                access_token = jsonNode.get("access_token").asText();
                connection.disconnect();
            }
        }
        catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }

        connection = (HttpURLConnection) new URL(openid_base_url + "/userinfo").openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + access_token);
        connection.setDoOutput(true);

        try {
            JsonNode jsonNode = objectMapper.readTree(
                    new InputStreamReader(connection.getInputStream(), "utf-8")
            );
            JsonNode profileNode = jsonNode.get("profile");
            String a3rd_sub = jsonNode.get("sub").asText();
            String a3rd_account_id = profileNode.get("account_id").asText();
            String a3rd_name = profileNode.get("name").asText();
            String a3rd_family_name = profileNode.get("family_name").asText();
            String a3rd_middle_name = profileNode.get("middle_name").asText();
            String a3rd_email = jsonNode.get("email").asText();

            UserAccountDAO userAccountDAO = (UserAccountDAO) RequestContextUtils
                        .findWebApplicationContext(request)
                        .getBean("userAccountDAO");

            //find the Bean by username,
            UserAccountBean oauthAccount = userAccountDAO.findByUserName(a3rd_sub);
            // the useraccount does not exist create {{{
            if(oauthAccount != null) {
                oauthAccount = new UserAccountBean();
                oauthAccount.setName(a3rd_sub);
                oauthAccount.setFirstName(a3rd_name);
                oauthAccount.setLastName(a3rd_family_name);
                oauthAccount.setEmail(a3rd_email);

                oauthAccount.setPasswd("**");
                oauthAccount.setPasswdTimestamp(null);
                oauthAccount.setLastVisitDate(null);
                oauthAccount.setStatus(Status.AVAILABLE);
                oauthAccount.setPasswdChallengeQuestion("");
                oauthAccount.setPasswdChallengeAnswer("");
                oauthAccount.setPhone("");
                //createdUserAccountBean.setOwner(createdUserAccountBean.getOwner());
                oauthAccount.setRunWebservices(null);
                oauthAccount.setAccessCode("null");
                oauthAccount.setEnableApiKey(true);
                oauthAccount.setRunWebservices(false);
                userAccountDAO.create(oauthAccount);

                AuthoritiesDao authoritiesDao = (AuthoritiesDao) RequestContextUtils
                        .findWebApplicationContext(request).getBean("authoritiesDao");
                authoritiesDao.saveOrUpdate(new AuthoritiesBean(oauthAccount.getName()));
            }
            //}}}
            // create session and redirect to main page
            request.getSession().setAttribute(SecureController.USER_BEAN_NAME, oauthAccount);
            response.sendRedirect("/MainMenu");

                    /*
                    StudyUserRoleBean surb = new StudyUserRoleBean();
                    surb.setRole(Role.MONITOR);
                    createdUserAccountBean.addRole(surb);
                    createdUserAccountBean.add
                     */

            //String newDigestPass = sm.encryptPassword("*", createdUserAccountBean.getRunWebservices());


        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        /*






        connection.setRequestProperty("client_id", username);
        connection.setRequestProperty("client_secret", password);
        */
    }
%>

<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.akaza.openclinica.service.otp.TwoFactorService" %>
<%@ page import="org.springframework.web.servlet.support.ServletUriComponentsBuilder" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.util.Base64" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ page import="java.io.*" %>
<%@ page import="com.fasterxml.jackson.core.JsonParser" %>
<%@ page import="com.fasterxml.jackson.databind.JsonNode" %>
<%@ page import="java.util.logging.Logger" %>
<%@ page import="org.slf4j.LoggerFactory" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="org.akaza.openclinica.bean.login.UserAccountBean" %>
<%@ page import="org.akaza.openclinica.core.SecurityManager" %>
<%@ page import="org.akaza.openclinica.control.SpringServletAccess" %>
<%@ page import="org.akaza.openclinica.bean.core.Status" %>
<%@ page import="org.akaza.openclinica.dao.hibernate.AuthoritiesDao" %>
<%@ page import="org.akaza.openclinica.domain.user.AuthoritiesBean" %>
<%@ page import="org.akaza.openclinica.dao.hibernate.UserAccountDao" %>
<%@ page import="org.akaza.openclinica.dao.login.UserAccountDAO" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="org.akaza.openclinica.control.core.SecureController" %>


<!-- For Mantis Issue 6099 -->
<jsp:useBean scope='session' id='userBean' class='org.akaza.openclinica.bean.login.UserAccountBean'/>

<c:if test="${userBean.name!=''}">
	<c:redirect url="/MainMenu"/>
</c:if>
<!-- End of 6099-->

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="org.akaza.openclinica.i18n.notes" var="restext"/>
<fmt:setBundle basename="org.akaza.openclinica.i18n.workflow" var="resworkflow"/>
<fmt:setBundle basename="org.akaza.openclinica.i18n.words" var="resword"/>
<fmt:setBundle basename="org.akaza.openclinica.i18n.format" var="resformat"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><fmt:message key="openclinica" bundle="${resword}"/></title>	
	<meta http-equiv="Content-type" content="text/html; charset=UTF-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
    
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value='/images/favicon.ico'/>">
	<link rel="stylesheet" href="<c:url value='/includes/styles.css'/>" type="text/css"/>
	
	<script type="text/JavaScript" language="JavaScript" src="<c:url value='/includes/jmesa/jquery.min.js'/>"></script>
	<script type="text/JavaScript" language="JavaScript" src="<c:url value='/includes/jmesa/jquery-migrate-1.1.1.js'/>"></script>
	<script type="text/javascript" language="JavaScript" src="<c:url value='/includes/jmesa/jquery.blockUI.js'/>"></script>
	<script type="text/JavaScript" language="JavaScript" src="<c:url value='/includes/global_functions_javascript.js'/>"></script>
	<script type="text/JavaScript" language="JavaScript" src="<c:url value='/includes/ua-parser.min.js'/>"></script>
</head>

<%
ApplicationContext appContext = RequestContextUtils.findWebApplicationContext(request);
TwoFactorService factorService = (TwoFactorService) appContext.getBean("factorService");
String oauth_authorize_url = oauth_base_url + "/authorize?response_type=code&client_id="+oauth_client_id+"&"+
    "redirect_uri="+oauth_redirect_uri+"&scope=openid%20profile";

request.setAttribute("factorService", factorService);
session.setAttribute("factorService", factorService);
%>

<body  onload="document.getElementById('username').focus();">
<!-- start of login/login.jsp -->
 
    <div class="login_BG">

    <!-- LibreClinica logo -->
	<%String ua = request.getHeader( "User-Agent" );
	String temp = "";
	String iev = "";
	if( ua != null && ua.indexOf( "MSIE" ) != -1 ) {
		temp = ua.substring(ua.indexOf( "MSIE" ),ua.length());
		iev = temp.substring(4, temp.indexOf(";"));
		iev = iev.trim();
	}
	if(iev.length() > 1 && Double.valueOf(iev)<7) {%>
	<div ID="logoIE6">&nbsp;</div>
	<%} else {%>
    <div ID="logo">&nbsp;</div>
  	<%}%>
    <!-- end LibreClinica logo -->
        <table>

    <script type="text/javascript">
        var parser = new UAParser();
        var showMessage = false;

        if (parser.getBrowser().name != 'Firefox' && parser.getBrowser().name !='Chrome') {
            showMessage = true;
        }

        if (showMessage) {
            document.write(
                "<tr><td align='center'><h4>" +
                " <fmt:message key="choose_browser" bundle="${restext}"/>" +
                "</h4></td></tr>"
            );
        }
    </script>
            </table>
        
	<table class="loginBoxes">
    	<tr>
        	<td class="loginBox_T">&nbsp;</td>
		</tr>
		<tr>
            <td class="loginBox">
            <div id="loginBox">
            <!-- Login box contents -->
                <div id="login">
                    <form action="<c:url value='/j_spring_security_check'/>" method="post">
                        <h1><fmt:message key="login" bundle="${resword}"/></h1>
                        <b><fmt:message key="user_name" bundle="${resword}"/></b>
                        <div class="formfieldM_BG">
                            <input type="text" id="username" name="j_username" class="formfieldM" />
                        </div>

                        <b><fmt:message key="password" bundle="${resword}"/></b>
                        <div class="formfieldM_BG">
                            <input type="password" id="j_password" name="j_password"  class="formfieldM" autocomplete="off" />
                        </div>

                        <c:if test="${factorService.twoFactorActivated}">
                            <b><fmt:message key="factor" bundle="${resword}"/></b>
                            <div class="formfieldM_BG">
                                <input type="text" id="j_factor" name="j_factor"  class="formfieldM" autocomplete="off" />
                            </div>
                        </c:if>

                        <input type="submit" name="submit" value="<fmt:message key='login' bundle='${resword}'/>" class="loginbutton" />
                        <a href="#" id="requestPassword"> <fmt:message key="forgot_password" bundle="${resword}"/></a>
                   </form>
                    <a href="<%= oauth_authorize_url%>">OAuth Login</a>
                   <br/><jsp:include page="../login-include/login-alertbox.jsp"/>
                   <%-- <a href="<c:url value="/RequestPassword"/>"> <fmt:message key="forgot_password" bundle="${resword}"/></a> --%>
               	</div>
            </div>
		</td>
      </tr>
    </table>

    <script type="text/javascript">
        document.getElementById('username').setAttribute( 'autocomplete', 'off' );
        document.getElementById('j_password').setAttribute( 'autocomplete', 'off' );

        jQuery(document).ready(function() {
            jQuery('#requestPassword').click(function() {
                jQuery.blockUI({ message: jQuery('#requestPasswordForm'), css:{left: "200px", top:"180px", textAlign:"left" } });
            });

            jQuery('#cancel').click(function() {
                jQuery.unblockUI();
                return false;
            });
        });

    </script>

    <div id="requestPasswordForm" style="display:none;"><c:import url="requestPasswordPop.jsp" /></div> <!-- this is in fact the reset-password-dialog -->
    
</div>

<!-- end of login/login.jsp -->
<jsp:include page="../login-include/login-footer.jsp"/>
