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
    public void sendVerificationEmail(User to, String token) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("username", to.getUsername());
        mailMap.put("token", token);
        final String subject = messageSource.getMessage("email.confirmation.subject", null, Locale.getDefault());
        sendEmail(to.getEmail(), subject, "registerConfirmation.html", mailMap);
    }

    @Override
    public void sendResetPasswordEmail(User to, String token) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("username", to.getUsername());
        mailMap.put("token", token);
        final String subject = messageSource.getMessage("email.resetPassword", null, Locale.getDefault());
        sendEmail(to.getEmail(), subject, "resetPassword.html", mailMap);
    }

    @Override
    public void sendReportCreatedEmail(User to, String report) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("report", report);
        final String subject = messageSource.getMessage("email.report.created.subject", null, Locale.getDefault());
        sendEmail(to.getEmail(), subject, "reportCreated.html", mailMap);
    }

    @Override
    public void sendReportApprovedEmail(User to, String report) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("report", report);
        final String subject = messageSource.getMessage("email.report.approved.subject", null, Locale.getDefault());
        sendEmail(to.getEmail(), subject, "reportApproved.html", mailMap);
    }

    @Override
    public void sendReportRejectedEmail(User to, String report) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("report", report);
        final String subject = messageSource.getMessage("email.report.rejected.subject", null, Locale.getDefault());
        sendEmail(to.getEmail(), subject, "reportRejected.html", mailMap);
    }

    @Override
    public void sendDeletedCommentEmail(User to, Comment comment, String report) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("comment", comment.getCommentBody());
        mailMap.put("report", report);
        final String subject = messageSource.getMessage("email.deleted.comment.subject", null, Locale.getDefault());
        sendEmail(to.getEmail(), subject, "deletedComment.html", mailMap);
    }

    @Override
    public void sendDeletedListEmail(User to, MediaList mediaList, String report) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("list", mediaList.getListName());
        mailMap.put("listDescription", mediaList.getDescription());
        mailMap.put("report", report);
        final String subject = messageSource.getMessage("email.deleted.list.subject", null, Locale.getDefault());
        sendEmail(to.getEmail(), subject, "deletedList.html", mailMap);
    }

    public void sendNewRequestEmail(MediaList list, User requester) {
        User to = userDao.getById(list.getUserId()).orElseThrow(RuntimeException::new);
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("listname", list.getListName());
        mailMap.put("username", requester.getUsername());
        final String subject = messageSource.getMessage("collabEmail.subject", null, Locale.getDefault());
        sendEmail(to.getEmail(), subject, "collaborationRequest.html", mailMap);
    }

    public void sendCollabRequestAcceptedEmail(User to, Request collaboration) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("listname", collaboration.getListname());
        mailMap.put("collabUsername", collaboration.getCollaboratorUsername());
        mailMap.put("listId", collaboration.getListId());
        final String subject = messageSource.getMessage("collabConfirmEmail.subject", null, Locale.getDefault());
        sendEmail(to.getEmail(), subject, "collaborationConfirmed.html", mailMap);
    }

    @Override
    public void sendModRequestApprovedEmail(User to) {
        final String subject = messageSource.getMessage("email.mod.approved.subject", null, Locale.getDefault());
        sendEmail(to.getEmail(), subject, "ModRequestApproved.html", null);
    }

    @Override
    public void sendModRoleRemovedEmail(User to) {
        final String subject = messageSource.getMessage("email.mod.removed.subject", null, Locale.getDefault());
        sendEmail(to.getEmail(), subject, "ModRoleRemoved.html", null);
    }
}
