package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
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

        if (URL.startsWith("/user/") && ((URL.contains("/requests")) || URL.contains("/notifications"))) {
            String username = URL.replaceFirst("/user/", "").replaceFirst("(/requests|/notifications).*", "");
            Optional<User> user = userService.getCurrentUser();
            if (user.isPresent() && user.get().getUsername().equals(username)) {
                vote.set(ACCESS_GRANTED);
            } else {
                vote.set(ACCESS_DENIED);
            }
        }

        return vote.get();
    }
}
