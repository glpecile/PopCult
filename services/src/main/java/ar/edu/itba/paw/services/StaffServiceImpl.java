package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.StaffDao;
import ar.edu.itba.paw.interfaces.StaffService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.staff.StaffMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    public PageContainer<Media> getMediaByDirector(StaffMember staffMember, int page, int pageSize) {
        return staffDao.getMediaByDirector(staffMember, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Media> getMediaByActor(StaffMember staffMember, int page, int pageSize) {
        return staffDao.getMediaByActor(staffMember,page,pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Media> getMedia(StaffMember staffMember, int page, int pageSize) {
        return staffDao.getMedia(staffMember,page,pageSize);
    }

}
