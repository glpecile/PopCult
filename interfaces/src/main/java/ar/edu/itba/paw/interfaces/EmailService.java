package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.collaborative.Request;
import ar.edu.itba.paw.models.comment.Comment;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.user.User;

import java.util.Locale;

public interface EmailService {

    void sendVerificationEmail(User to, String token, Locale locale);

    void sendDeletedUserEmail(User to, Locale locale);

    void sendResetPasswordEmail(User to, String token, Locale locale);

    void sendReportCreatedEmail(User to, String report, Locale locale);

    void sendReportApprovedEmail(User to, String report, Locale locale);

    void sendReportRejectedEmail(User to, String report, Locale locale);

    void sendDeletedCommentEmail(User to, Comment comment, String report, Locale locale);

    void sendDeletedListEmail(User to, MediaList mediaList, String report, Locale locale);

    void sendNewRequestEmail(MediaList list, User requester, User listOwner, Locale locale);

    void sendCollabRequestAcceptedEmail(User to, Request collaboration, Locale locale);

    void sendModRequestApprovedEmail(User to, Locale locale);

    void sendModRoleRemovedEmail(User to, Locale locale);

    void sendBannedUserEmail(User to, Locale locale);

    void sendUnbannedUserEmail(User to, Locale locale);

    void sendDeletedUserByStrikesEmail(User to, Locale locale);
}
