package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.edu.itba.paw.persistence.hibernate.MediaHibernateDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static ar.edu.itba.paw.persistence.InstanceProvider.ALREADY_EXISTS_MEDIA_ID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class MediaHibernateDaoTest {

    @Autowired
    private MediaHibernateDao mediaHibernateDao;

    @Rollback
    @Test
    public void testGetById() {
        Optional<Media> media = mediaHibernateDao.getById(ALREADY_EXISTS_MEDIA_ID);

        Assert.assertTrue(media.isPresent());
        Assert.assertEquals(ALREADY_EXISTS_MEDIA_ID, media.get().getMediaId().intValue());
    }
}
