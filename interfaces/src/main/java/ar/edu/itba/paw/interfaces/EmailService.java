package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.comment.Comment;
import ar.edu.itba.paw.models.lists.MediaList;

import java.util.Map;

public interface EmailService {
    void sendEmail(String to, String subject, String template, Map<String, Object> variables);

    void sendResetPasswordEmail(String to, String username, String token);

    void sendReportCreatedEmail(String to, String report);

    void sendReportApprovedEmail(String to, String report);

    void sendReportRejectedEmail(String to, String report);

    void sendDeletedCommentEmail(String to, Comment comment);

    void sendDeletedListEmail(String to, MediaList mediaList);
}
