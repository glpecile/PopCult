package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.StaffDao;
import ar.edu.itba.paw.interfaces.StaffService;
import ar.edu.itba.paw.models.staff.Actor;
import ar.edu.itba.paw.models.staff.Director;
import ar.edu.itba.paw.models.staff.StaffMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffDao staffDao;

    @Override
    public Optional<StaffMember> getById(int staffMemberId) {
        return staffDao.getById(staffMemberId);
    }

    @Override
    public List<StaffMember> getPersonList() {
        return staffDao.getPersonList();
    }

    @Override
    public List<Integer> getMediaByDirector(int staffMemberId, int page, int pageSize) {
        return staffDao.getMediaByDirector(staffMemberId, page, pageSize);
    }

    @Override
    public List<Integer> getMediaByActor(int staffMemberId, int page, int pageSize) {
        return staffDao.getMediaByActor(staffMemberId, page, pageSize);
    }

    @Override
    public List<Integer> getMediaByRoleType(int staffMemberId, int page, int pageSize, int roleType) {
        return staffDao.getMediaByRoleType(staffMemberId,page,pageSize,roleType);
    }

    @Override
    public List<Director> getDirectorsByMedia(int mediaId) {
        return staffDao.getDirectorsByMedia(mediaId);
    }

    @Override
    public List<Actor> getActorsByMedia(int mediaId) {
        return staffDao.getActorsByMedia(mediaId);
    }
}
