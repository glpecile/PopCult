package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.edu.itba.paw.persistence.hibernate.ImageHibernateDao;
import org.junit.Assert;
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
public class ImageHibernateDaoTest {

    private final byte[] byteBlob = {0, 1, 2, 3, 4};

    @Autowired
    private ImageHibernateDao imageHibernateDao;

    @Rollback
    @Test
    public void testUploadImage() {
        Image image = imageHibernateDao.uploadImage(byteBlob);

        Assert.assertArrayEquals(byteBlob, image.getImageBlob());
    }

}
