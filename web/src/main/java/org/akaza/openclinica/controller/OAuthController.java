package org.akaza.openclinica.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.akaza.openclinica.bean.core.Status;
import org.akaza.openclinica.bean.login.UserAccountBean;
import org.akaza.openclinica.control.core.SecureController;
import org.akaza.openclinica.core.CRFLocker;
import org.akaza.openclinica.core.SecurityManager;
import org.akaza.openclinica.dao.hibernate.AuthoritiesDao;
import org.akaza.openclinica.dao.login.UserAccountDAO;
import org.akaza.openclinica.domain.user.AuthoritiesBean;
import org.akaza.openclinica.web.filter.OpenClinicaUsernamePasswordAuthenticationFilter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.sql.DataSource;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collection;

import static org.akaza.openclinica.web.filter.OpenClinicaUsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY;
import static org.akaza.openclinica.web.filter.OpenClinicaUsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;


@Controller
public class OAuthController {
    private ApplicationContext context;
    private DataSource dataSource;
    private SecurityManager securityManager;

    final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass().getName());
    @Autowired
    public OAuthController(ApplicationContext context, DataSource dataSource, SecurityManager securityManager) {
        this.context = context;
        this.dataSource = dataSource;
        this.securityManager = securityManager;
    }
    @RequestMapping("/oauth")
    public String oauth(HttpServletRequest request/*ModelMap modelMap*/) {
        String oauth_server = "https://cdcoauthmockup.azurewebsites.net";
        String oauth_base_url = oauth_server + "/auth/oauth/v2";
        String openid_base_url = oauth_server + "/openid/connect/v1";
        String self_url = request.getRequestURL().toString().replace(request.getRequestURI(),"");
        String oauth_redirect_uri = self_url + "/pages/login/login";

        String oauth_code = request.getParameter("code");
        String oauth_client_id = "19216801";
        String oauth_client_secret = "3EEnASDAS6pmAASDyPzviWQSDPufTcIpg";

        if(oauth_code == null || oauth_code.isEmpty()) {
            return "redirect:/";
        }

        try {
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


            connection = (HttpURLConnection) new URL(openid_base_url + "/userinfo").openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + access_token);
            connection.setDoOutput(true);

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

            UserAccountDAO userAccountDAO = new UserAccountDAO(dataSource);
            //find the Bean by username,
            UserAccountBean oauthAccount = userAccountDAO.findByUserName(a3rd_email);
            // the useraccount does not exist create {{{
            if(oauthAccount == null || oauthAccount.getId()<1) {
                oauthAccount = new UserAccountBean();
                oauthAccount.setName(a3rd_email);
                oauthAccount.setFirstName(a3rd_name);
                oauthAccount.setLastName(a3rd_family_name);
                oauthAccount.setEmail(a3rd_email);

                oauthAccount.setPasswd(securityManager.encryptPassword(
                        a3rd_email, oauthAccount.getRunWebservices()));
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
                //reload the user account bean
                oauthAccount = userAccountDAO.findByUserName(a3rd_email);

                AuthoritiesDao authoritiesDao = (AuthoritiesDao) context.getBean("authoritiesDao");
                authoritiesDao.saveOrUpdate(new AuthoritiesBean(oauthAccount.getName()));
                OpenClinicaUsernamePasswordAuthenticationFilter filter = new OpenClinicaUsernamePasswordAuthenticationFilter();

            }
            //}}}

            //SecurityContextHolder.getContext().setAuthentication(new PreAuthenticatedAuthenticationToken());
            request.getSession().setAttribute(SecureController.USER_BEAN_NAME, oauthAccount);
            return "redirect:/MainMenu";
        }
        catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
            return "forward:/pages/login/login";
        }
        catch(NoSuchAlgorithmException ex) {
            logger.error(ex.getMessage(), ex);
            return "forward:/pages/login/login";
        }
    }
}