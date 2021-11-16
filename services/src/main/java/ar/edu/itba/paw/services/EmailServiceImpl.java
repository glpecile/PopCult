package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.models.collaborative.Request;
import ar.edu.itba.paw.models.comment.Comment;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
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
    @Qualifier("basePath")
    private String basePath;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;
    @Autowired
    private MessageSource messageSource;

    private static final int MULTIPART_MODE = MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED;
    private static final String ENCODING = StandardCharsets.UTF_8.name();
    private static final String FROM = "noreply@popcult.com";

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    private void sendEmail(String to, String subject, String template, Map<String, Object> variables, Locale locale) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE, ENCODING);

            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(FROM);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(getHtmlBody(template, variables, locale), true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException messagingException) {
            LOGGER.error("Sending email failed");
        }
    }

    private String getHtmlBody(String template, Map<String, Object> variables, Locale locale) {
        Context thymeleafContext = new Context(locale);
        if(variables == null) {
            variables = new HashMap<>();
        }
        variables.put("basePath", basePath);
        thymeleafContext.setVariables(variables);
        return templateEngine.process(template, thymeleafContext);
    }

    @Async
    @Override
    public void sendVerificationEmail(User to, String token, Locale locale) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("username", to.getUsername());
        mailMap.put("token", token);
        final String subject = messageSource.getMessage("email.confirmation.subject", null, locale);
        sendEmail(to.getEmail(), subject, "registerConfirmation.html", mailMap, locale);
    }

    @Async
    @Override
    public void sendDeletedUserEmail(User to, Locale locale) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("username", to.getUsername());
        final String subject = messageSource.getMessage("email.deleteUser.subject", null, locale);
        sendEmail(to.getEmail(), subject, "userDeleted.html", mailMap, locale);
    }

    @Async
    @Override
    public void sendResetPasswordEmail(User to, String token, Locale locale) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("username", to.getUsername());
        mailMap.put("token", token);
        final String subject = messageSource.getMessage("email.resetPassword", null, locale);
        sendEmail(to.getEmail(), subject, "resetPassword.html", mailMap, locale);
    }

    @Async
    @Override
    public void sendReportCreatedEmail(User to, String report, Locale locale) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("report", report);
        final String subject = messageSource.getMessage("email.report.created.subject", null, locale);
        sendEmail(to.getEmail(), subject, "reportCreated.html", mailMap, locale);
    }

    @Async
    @Override
    public void sendReportApprovedEmail(User to, String report, Locale locale) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("report", report);
        final String subject = messageSource.getMessage("email.report.approved.subject", null, locale);
        sendEmail(to.getEmail(), subject, "reportApproved.html", mailMap, locale);
    }

    @Async
    @Override
    public void sendReportRejectedEmail(User to, String report, Locale locale) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("report", report);
        final String subject = messageSource.getMessage("email.report.rejected.subject", null, locale);
        sendEmail(to.getEmail(), subject, "reportRejected.html", mailMap, locale);
    }

    @Async
    @Override
    public void sendDeletedCommentEmail(User to, Comment comment, String report, Locale locale) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("comment", comment.getCommentBody());
        mailMap.put("report", report);
        mailMap.put("strikes", to.getStrikes());
        final String subject = messageSource.getMessage("email.deleted.comment.subject", null, locale);
        sendEmail(to.getEmail(), subject, "deletedComment.html", mailMap, locale);
    }

    @Async
    @Override
    public void sendDeletedListEmail(User to, MediaList mediaList, String report, Locale locale) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("list", mediaList.getListName());
        mailMap.put("listDescription", mediaList.getDescription());
        mailMap.put("report", report);
        mailMap.put("strikes", to.getStrikes());
        final String subject = messageSource.getMessage("email.deleted.list.subject", null, locale);
        sendEmail(to.getEmail(), subject, "deletedList.html", mailMap, locale);
    }

    @Async
    @Override
    public void sendNewRequestEmail(MediaList list, User requester, User listOwner, Locale locale) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("listname", list.getListName());
        mailMap.put("username", requester.getUsername());
        mailMap.put("toUsername", listOwner.getUsername());
        final String subject = messageSource.getMessage("collabEmail.subject", null, locale);
        sendEmail(listOwner.getEmail(), subject, "collaborationRequest.html", mailMap, locale);
    }

    @Async
    @Override
    public void sendCollabRequestAcceptedEmail(User to, Request collaboration, Locale locale) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("listname", collaboration.getMediaList().getListName());
        mailMap.put("collabUsername", collaboration.getCollaborator().getUsername());
        mailMap.put("listId", collaboration.getMediaList().getMediaListId());
        final String subject = messageSource.getMessage("collabConfirmEmail.subject", null, locale);
        sendEmail(to.getEmail(), subject, "collaborationConfirmed.html", mailMap, locale);
    }

    @Async
    @Override
    public void sendModRequestApprovedEmail(User to, Locale locale) {
        final String subject = messageSource.getMessage("email.mod.approved.subject", null, locale);
        sendEmail(to.getEmail(), subject, "modRequestApproved.html", null, locale);
    }

    @Async
    @Override
    public void sendModRoleRemovedEmail(User to, Locale locale) {
        final String subject = messageSource.getMessage("email.mod.removed.subject", null, locale);
        sendEmail(to.getEmail(), subject, "modRoleRemoved.html", null, locale);
    }

    @Async
    @Override
    public void sendBannedUserEmail(User to, Locale locale) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("username", to.getUsername());
        mailMap.put("strikes", to.getStrikes());
        final String subject = messageSource.getMessage("email.bans.ban.subject", null, locale);
        sendEmail(to.getEmail(), subject, "userBanned.html", mailMap, locale);
    }

    @Async
    @Override
    public void sendUnbannedUserEmail(User to, Locale locale) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("username", to.getUsername());
        mailMap.put("strikes", to.getStrikes());
        final String subject = messageSource.getMessage("email.bans.unban.subject", null, locale);
        sendEmail(to.getEmail(), subject, "userUnbanned.html", mailMap, locale);
    }

    @Async
    @Override
    public void sendDeletedUserByStrikesEmail(User to, Locale locale) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("username", to.getUsername());
        final String subject = messageSource.getMessage("email.bans.deleted.subject", null, locale);
        sendEmail(to.getEmail(), subject, "userDeletedByStrikes.html", mailMap, locale);
    }
}
