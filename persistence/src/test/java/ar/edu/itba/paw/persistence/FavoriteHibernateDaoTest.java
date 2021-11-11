package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.edu.itba.paw.persistence.hibernate.FavoriteHibernateDao;
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
public class FavoriteHibernateDaoTest {

    @Autowired
    private FavoriteHibernateDao favoriteHibernateDao;

    private User user;

    @Before
    public void setup() {
        user = InstanceProvider.getUser();
    }

    @Rollback
    @Test
    public void testMediaIsFavorite() {
        Media media = InstanceProvider.getLikedMedia();

        boolean isFavorite = favoriteHibernateDao.isFavorite(media, user);

        Assert.assertTrue(isFavorite);
    }

    @Rollback
    @Test
    public void testMediaIsNotFavorite() {
        Media media = InstanceProvider.getMedia();

        boolean isFavorite = favoriteHibernateDao.isFavorite(media, user);

        Assert.assertFalse(isFavorite);
    }

    @Rollback
    @Test
    public void testMediaListIsFavorite() {
        MediaList mediaList = InstanceProvider.getLikedMediaList();

        boolean isFavorite = favoriteHibernateDao.isFavoriteList(mediaList, user);

        Assert.assertTrue(isFavorite);
    }

    @Rollback
    @Test
    public void testMediaListIsNotFavorite() {
        MediaList mediaList = InstanceProvider.getMediaList();

        boolean isFavorite = favoriteHibernateDao.isFavoriteList(mediaList, user);

        Assert.assertFalse(isFavorite);
    }
}
