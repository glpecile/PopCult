package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.persistence.config.TestConfig;
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
public class ListsDaoJdbcTest {

    private static final int ALREADY_EXISTS_USER_ID = 2;
    private static final String LISTNAME = "List";
    private static final String DESCRIPTION = "Description";
    private static final boolean VISIBILITY  = true;
    private static final boolean COLLABORATIVE = true;

//    @Autowired
//    ListsDaoJdbcImpl listsDaoJdbc;

//    @Rollback
//    @Test
//    public void testCreateMediaList() {
//        //2
//        MediaList mediaList = listsDaoJdbc.createMediaList(ALREADY_EXISTS_USER_ID, LISTNAME, DESCRIPTION, VISIBILITY, COLLABORATIVE);
//
//        //3
//        Assert.assertNotNull(mediaList);
//        Assert.assertEquals(LISTNAME, mediaList.getListName());
//    }





}
