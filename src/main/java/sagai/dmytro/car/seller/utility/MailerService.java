package sagai.dmytro.car.seller.utility;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sagai.dmytro.car.seller.model.authentication.User;

import javax.inject.Named;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Service for sending email messages.
 *
 * @author dsagai
 * @version 1.00
 * @since 15.05.2017
 */
@Component("mailerService")
public class MailerService {
    private static final Logger LOGGER = Logger.getLogger(MailerService.class);
    private static final String ACTIVATION_MAIL_BODY = "<a href=\"http://%s\">Click on this link to activate your login.</a>";

    @Value("${mail.login}")
    private String login;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.address}")
    private String fromAddress;

    private Session session;


    public MailerService() {

    }

    @Autowired
    @Named("emailConfig")
    private void initSession(Properties mailConfig) {
        this.session = Session.getDefaultInstance(mailConfig, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(getLogin(), getPassword());
            }
        });
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }


    /**
     * method sends to the new registered user an email containing activation link
     * @param user User.
     * @param contextPath String.
     * @throws UtilityServiceException
     */
    public void sendActivationMessage(User user, String absolutePath, int port, String contextPath)
            throws UtilityServiceException {
        try {
            MimeMessage message = new MimeMessage(this.session);
            message.setSubject("Car seller login activation");
            message.setFrom(new InternetAddress(this.fromAddress));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));

            String link = String.format("%s:%d%s/activate?uuid=%s", absolutePath, port, contextPath, user.getUuid());
            String messageBody = String.format(ACTIVATION_MAIL_BODY, link);
            message.setContent(messageBody, "text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            LOGGER.log(Level.WARN, e);
            throw new UtilityServiceException("exception during sending message occurs", e);
        }
    }
}
