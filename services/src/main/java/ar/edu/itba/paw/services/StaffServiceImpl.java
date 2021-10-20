package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.StaffDao;
import ar.edu.itba.paw.interfaces.StaffService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.staff.Actor;
import ar.edu.itba.paw.models.staff.Director;
import ar.edu.itba.paw.models.staff.StaffMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffDao staffDao;

    @Transactional(readOnly = true)
    @Override
    public Optional<StaffMember> getById(int staffMemberId) {
        return staffDao.getById(staffMemberId);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Media> getMediaByDirector(int staffMemberId, int page, int pageSize) {
        return staffDao.getMediaByDirector(staffMemberId, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Media> getMediaByActor(int staffMemberId, int page, int pageSize) {
        return staffDao.getMediaByActor(staffMemberId,page,pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Media> getMedia(int staffMemberId, int page, int pageSize) {
        return staffDao.getMedia(staffMemberId,page,pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Director> getDirectorsByMedia(int mediaId) {
        return staffDao.getDirectorsByMedia(mediaId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Actor> getActorsByMedia(int mediaId) {
        return staffDao.getActorsByMedia(mediaId);
    }

}
