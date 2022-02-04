package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.ModeratorDao;
import ar.edu.itba.paw.interfaces.ModeratorService;
import ar.edu.itba.paw.interfaces.exceptions.ModRequestAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.UserAlreadyIsModException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.user.ModRequest;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ModeratorServiceImpl implements ModeratorService {

    @Autowired
    private ModeratorDao moderatorDao;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RoleHierarchy roleHierarchy;

    @Transactional(readOnly = true)
    @Override
    public PageContainer<User> getModerators(int page, int pageSize) {
        return moderatorDao.getModerators(page, pageSize);
    }

    @Transactional
    @Override
    public void promoteToMod(User user) {
        user.setRole(UserRole.MOD);
        removeRequest(user);
        emailService.sendModRequestApprovedEmail(user, LocaleContextHolder.getLocale());
    }

    @Transactional
    @Override
    public void removeMod(User user) {
        user.setRole(UserRole.USER);
        emailService.sendModRoleRemovedEmail(user, LocaleContextHolder.getLocale());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ModRequest> getModRequest(int modRequestId) {
        return moderatorDao.getModRequest(modRequestId);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<ModRequest> getModRequests(int page, int pageSize) {
        return moderatorDao.getModRequests(page, pageSize);
    }

    @Transactional
    @Override
    public ModRequest addModRequest(User user) throws UserAlreadyIsModException, ModRequestAlreadyExistsException {
        if (principalIsMod()) {
            throw new UserAlreadyIsModException();
        }
        return moderatorDao.addModRequest(user);
    }

    @Transactional
    @Override
    public void removeModRequest(ModRequest modRequest) {
        moderatorDao.removeModRequest(modRequest);
    }

    @Transactional
    @Override
    public void removeRequest(User user) {
        moderatorDao.removeRequest(user);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean principalIsMod() {
        //TODO migrate to JWT compatibility
//        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return roleHierarchy.getReachableGrantedAuthorities(principal.getAuthorities()).contains(new SimpleGrantedAuthority(UserRole.MOD.getRoleType()));
        return false;
    }
}
