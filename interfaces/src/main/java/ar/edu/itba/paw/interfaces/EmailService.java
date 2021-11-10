package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.collaborative.Request;
import ar.edu.itba.paw.models.comment.Comment;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.user.User;

import java.util.Map;

public interface EmailService {

    void sendVerificationEmail(User to, String token);

    void sendDeletedUserEmail(User to);

    void sendResetPasswordEmail(User to, String token);

    void sendReportCreatedEmail(User to, String report);

    void sendReportApprovedEmail(User to, String report);

    void sendReportRejectedEmail(User to, String report);

    void sendDeletedCommentEmail(User to, Comment comment, String report);

    void sendDeletedListEmail(User to, MediaList mediaList, String report);

    void sendNewRequestEmail(MediaList list, User requester, User listOwner);

    void sendCollabRequestAcceptedEmail(User to, Request collaboration);

    void sendModRequestApprovedEmail(User to);

    void sendModRoleRemovedEmail(User to);
}
