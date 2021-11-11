package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.edu.itba.paw.persistence.hibernate.WatchHibernateDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class WatchHibernateDaoTest {

    @Autowired
    private WatchHibernateDao watchHibernateDao;

    private User user;

    public WatchHibernateDaoTest() {
    }

    @Before
    public void setup() {
        user = InstanceProvider.getUser();
    }

    @Rollback
    @Test
    public void testIsWatched() {
        Media media = InstanceProvider.getWatchedMedia();

        boolean isWatched = watchHibernateDao.isWatched(media, user);

        Assert.assertTrue(isWatched);
    }

    @Rollback
    @Test
    public void testIsNotWatched() {
        Media media = InstanceProvider.getMedia();

        boolean isWatched = watchHibernateDao.isWatched(media, user);

        Assert.assertFalse(isWatched);
    }

    @Rollback
    @Test
    public void testIsToWatch() {
        Media media = InstanceProvider.getToWatchMedia();

        boolean isToWatch = watchHibernateDao.isToWatch(media, user);

        Assert.assertTrue(isToWatch);
    }

    @Rollback
    @Test
    public void testIsNotToWatch() {
        Media media = InstanceProvider.getMedia();

        boolean isToWatch = watchHibernateDao.isToWatch(media, user);

        Assert.assertFalse(isToWatch);
    }

}
