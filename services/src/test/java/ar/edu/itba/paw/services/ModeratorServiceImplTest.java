package ar.edu.itba.paw.services;


import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.ModeratorDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.interfaces.exceptions.ModRequestAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.UserAlreadyIsModException;
import ar.edu.itba.paw.models.user.Roles;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ModeratorServiceImplTest {

    private static final int USER_ID = 1;

    @InjectMocks
    private final ModeratorServiceImpl moderatorService = new ModeratorServiceImpl();
    @Mock
    private ModeratorDao moderatorDao;
    @Mock
    private UserService userService;
    @Mock
    private EmailService emailService;
    @Mock
    private RoleHierarchy roleHierarchy;

    @Test(expected = UserAlreadyIsModException.class)
    public void testUserAlreadyIsMod() throws ModRequestAlreadyExistsException, UserAlreadyIsModException {
        //1- Setup
//        when(moderatorService.principalIsMod()).thenReturn(true);
        when(roleHierarchy.getReachableGrantedAuthorities(anyCollection()).contains(new SimpleGrantedAuthority(Roles.MOD.getRoleType()))).thenReturn(false);

        //2
        moderatorService.addModRequest(USER_ID);

        //3
        Assert.fail();

    }


}
