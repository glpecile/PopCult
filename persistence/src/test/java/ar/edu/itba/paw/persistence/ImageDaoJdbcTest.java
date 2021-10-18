package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.persistence.config.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class ImageDaoJdbcTest {

    private final byte[] byteblob = {0, 1, 2, 3, 4};

    @Autowired
    private ImageDaoJdbcImpl imageDaoJdbc;

    @Rollback
    @Test
    public void testUploadImage() {
        //2
        Image image = imageDaoJdbc.uploadImage(byteblob);

        //3
        Assert.assertArrayEquals(byteblob, image.getImageBlob());
    }

}
