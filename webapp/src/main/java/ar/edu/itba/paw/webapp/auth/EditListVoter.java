package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class EditListVoter implements AccessDecisionVoter<FilterInvocation> {
    @Autowired
    UserService userService;
    @Autowired
    ListsService listsService;

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
        if (URL.toLowerCase().contains("/lists/edit/")) {
            try {
                int mediaListId = Integer.parseInt(URL.replaceFirst("/lists/edit/", "").replaceFirst("/.*", ""));
                userService.getCurrentUser().ifPresent(user -> {
                    listsService.getMediaListById(mediaListId).ifPresent(mediaList -> {
                        if (URL.contains("/manageMedia") || URL.contains("/addMedia") || URL.contains("/search") || URL.contains("deleteMedia")) {
                            if (listsService.canEditList(user, mediaList)) {
                                vote.set(ACCESS_GRANTED);
                            } else {
                                vote.set(ACCESS_DENIED);
                            }
                        } else {
                            if (user.getUserId() == mediaList.getUser().getUserId()) {
                                vote.set(ACCESS_GRANTED);
                            } else {
                                vote.set(ACCESS_DENIED);
                            }
                        }
                    });
                });
            } catch (NumberFormatException e) {
                vote.set(ACCESS_ABSTAIN);
            }
        }
        return vote.get();
    }
}
