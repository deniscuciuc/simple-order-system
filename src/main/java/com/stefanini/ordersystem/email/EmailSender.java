package com.stefanini.ordersystem.email;

import com.stefanini.ordersystem.util.PropertiesReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static javax.mail.Message.RecipientType.TO;

public class EmailSender {


    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);
    private static final PropertiesReader propertiesReader = new PropertiesReader("email.properties");

    public static void sendMail(String text, String subject) {
        Session session = Session.getInstance(propertiesReader.getProperties());
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(propertiesReader.readProperty("mail.smtp.user")));
            message.addRecipients(TO, String.valueOf(new InternetAddress(propertiesReader.readProperty("to"))));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message, propertiesReader.readProperty("mail.smtp.user"),
                    propertiesReader.readProperty("mail.smtp.password"));
            logger.info("Email sent");
        } catch (MessagingException exception) {
            exception.printStackTrace();
        }
    }
}
