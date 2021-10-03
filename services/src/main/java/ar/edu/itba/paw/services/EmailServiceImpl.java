package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.collaborative.Request;
import ar.edu.itba.paw.models.comment.Comment;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserDao userDao;

    private static final int MULTIPART_MODE = MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED;
    private static final String ENCODING = StandardCharsets.UTF_8.name();
    private static final String FROM = "noreply@popcult.com";

    @Override
    public void sendEmail(String to, String subject, String template, Map<String, Object> variables) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE, ENCODING);

            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(FROM);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(getHtmlBody(template, variables), true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException messagingException) {
            //TODO LOG error
        }

    }

    private String getHtmlBody(String template, Map<String, Object> variables) {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(variables);
        return templateEngine.process(template, thymeleafContext);
    }

    @Override
    public void sendResetPasswordEmail(String to, String username, String token) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("username", username);
        mailMap.put("token", token);
        final String subject = messageSource.getMessage("email.resetPassword", null, Locale.getDefault());
        sendEmail(to, subject, "resetPassword.html", mailMap);
    }

    @Override
    public void sendReportCreatedEmail(String to, String report) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("report", report);
        final String subject = messageSource.getMessage("email.report.created.subject", null, Locale.getDefault());
        sendEmail(to, subject, "reportCreated.html", mailMap);
    }

    @Override
    public void sendReportApprovedEmail(String to, String report) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("report", report);
        final String subject = messageSource.getMessage("email.report.approved.subject", null, Locale.getDefault());
        sendEmail(to, subject, "reportApproved.html", mailMap);
    }

    @Override
    public void sendReportRejectedEmail(String to, String report) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("report", report);
        final String subject = messageSource.getMessage("email.report.rejected.subject", null, Locale.getDefault());
        sendEmail(to, subject, "reportRejected.html", mailMap);
    }

    @Override
    public void sendDeletedCommentEmail(String to, String comment, String report) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("comment", comment);
        mailMap.put("report", report);
        final String subject = messageSource.getMessage("email.deleted.comment.subject", null, Locale.getDefault());
        sendEmail(to, subject, "deletedComment.html", mailMap);
    }

    @Override
    public void sendDeletedListEmail(String to, MediaList mediaList, String report) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("list", mediaList.getListName());
        mailMap.put("listDescription", mediaList.getDescription());
        mailMap.put("report", report);
        final String subject = messageSource.getMessage("email.deleted.list.subject", null, Locale.getDefault());
        sendEmail(to, subject, "deletedList.html", mailMap);
    }

    public void sendNewRequestEmail(MediaList list, User user) {
        User to = userDao.getById(list.getUserId()).orElseThrow(RuntimeException::new);
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("listname", list.getListName());
        mailMap.put("username", user.getUsername());
        final String subject = messageSource.getMessage("collabEmail.subject", null, Locale.getDefault());
        sendEmail(to.getEmail(), subject, "collaborationRequest.html", mailMap);
    }

    public void sendCollabRequestAccepted(User to, Request collaboration) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("listname", collaboration.getListname());
        mailMap.put("collabUsername", collaboration.getCollaboratorUsername());
        mailMap.put("listId", collaboration.getListId());
        final String subject = messageSource.getMessage("collabConfirmEmail.subject", null, Locale.getDefault());
        sendEmail(to.getEmail(), subject, "collaborationConfirmed.html", mailMap);
    }
}
