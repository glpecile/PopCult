package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.GenreDao;
import ar.edu.itba.paw.models.media.Genre;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Primary
@Repository
public class GenreHibernateDao implements GenreDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Genre> getAllGenre() {
        final Query nativeQuery = em.createNativeQuery("SELECT name FROM genre");
        @SuppressWarnings("unchecked")
        List<String> genres = nativeQuery.getResultList();
        return genres.stream().map(g -> g.replaceAll(" ","")).map(String::toUpperCase).map(Genre::valueOf).collect(Collectors.toList());
    }

}
