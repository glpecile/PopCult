package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    private static final int MULTIPART_MODE = MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED;
    private static final String ENCODING = StandardCharsets.UTF_8.name();
    private static final String FROM = "noreply@popcult.com";

    public void sendEmail(String to, String subject, String template, Map<String, Object> variables) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE, ENCODING);

            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(FROM);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(getHtmlBody(template, variables), true);

            javaMailSender.send(mimeMessage);
        } catch(MessagingException messagingException) {
            //TODO LOG error
        }

    }

    private String getHtmlBody(String template, Map<String, Object> variables) {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(variables);
        return templateEngine.process(template, thymeleafContext);
    }

}
