package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.MediaDao;
import ar.edu.itba.paw.models.Media;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MediaDaoJdbcImpl implements MediaDao {
    @Override
    public Optional<Media> getById(int mediaId) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Media>> getMediaList() {
        return Optional.empty();
    }
}
