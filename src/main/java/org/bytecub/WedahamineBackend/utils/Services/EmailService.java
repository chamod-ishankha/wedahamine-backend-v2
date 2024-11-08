package org.bytecub.WedahamineBackend.utils.Services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    @Autowired
    private JavaMailSender mailSender;

    // Inject spring.mail.username from application.properties
    @Value("${spring.mail.username}")
    private String mailUsername;

    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(mailUsername);

            helper.setText(body, true); // Set to true to indicate HTML content
            mailSender.send(message);
            log.info("Email sent successfully");
        } catch (Exception e) {
            log.error("Error sending email: {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
