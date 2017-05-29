package sagai.dmytro.car.seller;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 19.04.2017
 */
public class Test {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        PropertiesFactoryBean propertiesFactoryBean = context.getBean(PropertiesFactoryBean.class);

        Properties properties = propertiesFactoryBean.getObject();
        properties.load(Test.class.getClassLoader().getResourceAsStream("mail_config/email.config.properties"));


        String from = properties.getProperty("mail.address");
        String login = properties.getProperty("mail.login");
        String password = properties.getProperty("mail.password");
        String to = "accn1@yandex.ru";

        Session session = Session.getDefaultInstance(properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(login,
                                password);
                    }
                });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("Test message");
            message.setContent("<h1>Hello</h1><h2>world</h2>", "text/html");

            Transport.send(message);
            System.out.println("complete!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
