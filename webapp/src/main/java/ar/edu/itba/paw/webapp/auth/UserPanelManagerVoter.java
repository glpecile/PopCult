package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.CollaborativeListService;
import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class UserPanelManagerVoter implements AccessDecisionVoter<FilterInvocation> {
    @Autowired
    private UserService userService;

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
        String URL = filterInvocation.getRequestUrl();
        try {
            if (URL.startsWith("/user/") && (URL.contains("/requests"))) {
                String username = URL.substring(URL.indexOf("/user/") + ("/user/").length(), URL.indexOf("/requests"));
                userService.getCurrentUser().ifPresent(user -> {
                    if ((user.getUsername().equals(username))) {
                        vote.set(ACCESS_GRANTED);
                    } else {
                        vote.set(ACCESS_DENIED);
                    }
                });
            } else if (URL.startsWith("/user/") && URL.contains("/notifications")) {
                String username = URL.substring(URL.indexOf("/user/") + ("/user/").length(), URL.indexOf("/notifications"));
                userService.getCurrentUser().ifPresent(user -> {
                    if ((user.getUsername().equals(username))) {
                        vote.set(ACCESS_GRANTED);
                    } else {
                        vote.set(ACCESS_DENIED);
                    }
                });
            }
        } catch (NumberFormatException e) {
            vote.set(ACCESS_ABSTAIN);
        }
        return vote.get();
    }
}
