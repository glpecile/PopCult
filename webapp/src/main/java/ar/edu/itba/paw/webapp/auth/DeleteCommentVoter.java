package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class DeleteCommentVoter implements AccessDecisionVoter<FilterInvocation> {
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation filterInvocation, Collection<ConfigAttribute> attributes) {
        AtomicInteger vote = new AtomicInteger();
        vote.set(ACCESS_ABSTAIN);
        String URL = filterInvocation.getRequestUrl().toLowerCase();

        if (URL.contains("/deletecomment")) {
            try {
                int commentId = Integer.parseInt(URL.replaceFirst("/deletecomment/", "").replaceFirst("/.*", ""));
                userService.getCurrentUser().ifPresent(user -> {
                    if (URL.contains("/lists/")) {
                        commentService.getListCommentById(commentId).ifPresent((comment -> {
                            if (user.getUserId() == comment.getUser().getUserId()) {
                                vote.set(ACCESS_GRANTED);
                            } else {
                                vote.set(ACCESS_DENIED);
                            }
                        }));
                    }else if (URL.contains("/media/")) {
                        commentService.getMediaCommentById(commentId).ifPresent((comment -> {
                            if (user.getUserId() == comment.getUser().getUserId()) {
                                vote.set(ACCESS_GRANTED);
                            } else {
                                vote.set(ACCESS_DENIED);
                            }
                        }));
                    }

                });
            } catch (NumberFormatException e) {
                vote.set(ACCESS_ABSTAIN);
            }
        }
        return vote.get();
    }
}
