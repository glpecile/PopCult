package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.ImageDao;
import ar.edu.itba.paw.models.image.Image;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Primary
@Repository
public class ImageHibernateDao implements ImageDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Image> getImage(int imageId) {
        return Optional.ofNullable(em.find(Image.class, imageId));
    }

    @Override
    public Image uploadImage(byte[] photoBlob) {
        final Image image = new Image(null, photoBlob);
        em.persist(image);
        return image;
    }

    @Override
    public void deleteImage(Image image) {
        em.remove(image);
    }
}
