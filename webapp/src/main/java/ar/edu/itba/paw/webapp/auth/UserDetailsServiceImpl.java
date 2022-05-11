package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.webapp.exceptions.UnregisteredUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    private static final boolean ACCOUNT_NON_EXPIRED = true;
    private static final boolean CREDENTIALS_NON_EXPIRED = true;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final ar.edu.itba.paw.models.user.User user = userService.getByUsername(username).orElseThrow(UnregisteredUserException::new);
        final Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getRoleType()));
        return new User(username, user.getPassword(), user.isEnabled(), ACCOUNT_NON_EXPIRED, CREDENTIALS_NON_EXPIRED, user.isNonLocked(), authorities);
    }
}
