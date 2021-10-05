package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.ModeratorDao;
import ar.edu.itba.paw.interfaces.ModeratorService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.interfaces.exceptions.ModRequestAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.UserAlreadyIsModException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.user.Roles;
import ar.edu.itba.paw.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ModeratorServiceImpl implements ModeratorService {
    @Autowired
    private ModeratorDao moderatorDao;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private RoleHierarchy roleHierarchy;

    @Override
    public PageContainer<User> getModerators(int page, int pageSize) {
        return moderatorDao.getModerators(page, pageSize);
    }

    @Override
    public void promoteToMod(int userId) {
        moderatorDao.promoteToMod(userId);
        removeRequest(userId);
        userService.getById(userId).ifPresent(user -> {
            emailService.sendModRequestApprovedEmail(user);
        });

    }

    @Override
    public void removeMod(int userId) {
        moderatorDao.removeMod(userId);
        userService.getById(userId).ifPresent(user -> {
            emailService.sendModRoleRemovedEmail(user);
        });
    }

    @Override
    public PageContainer<User> getModRequesters(int page, int pageSize) {
        return moderatorDao.getModRequesters(page, pageSize);
    }

    @Override
    public void addModRequest(int userId) throws UserAlreadyIsModException, ModRequestAlreadyExistsException {
        if(principalIsMod()) {
            throw new UserAlreadyIsModException();
        }
        moderatorDao.addModRequest(userId);
    }

    @Override
    public void removeRequest(int userId) {
        moderatorDao.removeRequest(userId);
    }

    @Override
    public boolean principalIsMod() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return roleHierarchy.getReachableGrantedAuthorities(principal.getAuthorities()).contains(new SimpleGrantedAuthority(Roles.MOD.getRoleType()));
    }
}
