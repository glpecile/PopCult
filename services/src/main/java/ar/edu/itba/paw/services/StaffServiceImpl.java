package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.StaffDao;
import ar.edu.itba.paw.interfaces.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffDao staffDao;

}
