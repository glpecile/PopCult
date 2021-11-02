package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.edu.itba.paw.persistence.hibernate.ImageHibernateDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class ImageHibernateDaoTest {

    private static final byte[] byteBlob = {0, 1, 2, 3, 4};

    private static final String IMAGE_TABLE = "image";

    @Autowired
    private ImageHibernateDao imageHibernateDao;

    @Autowired
    private DataSource ds;

    @PersistenceContext
    private EntityManager em;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Rollback
    @Test
    public void testUploadImage() {
        Image image = imageHibernateDao.uploadImage(byteBlob);

        em.flush();

        Assert.assertNotNull(image);
        Assert.assertArrayEquals(byteBlob, image.getImageBlob());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, IMAGE_TABLE, String.format("imageid = '%d'", image.getImageId())));
    }

}
