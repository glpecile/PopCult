package ar.edu.itba.paw.models.lists;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "listelement")
@IdClass(ListElement.listElementKeys.class)
public class ListElement {
    @Id
    private int mediaListId;
    @Id
    private int mediaId;

    public ListElement(int mediaListId, int mediaId) {
        this.mediaListId = mediaListId;
        this.mediaId = mediaId;
    }

    public int getMediaListId() {
        return mediaListId;
    }

    public void setMediaListId(int mediaListId) {
        this.mediaListId = mediaListId;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public ListElement() {
    }

    static public class listElementKeys {
        protected int mediaListId;
        protected int mediaId;

        public listElementKeys() {
            //hibernate
        }

        public listElementKeys(int mediaListId, int mediaId) {
            this.mediaListId = mediaListId;
            this.mediaId = mediaId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            listElementKeys that = (listElementKeys) o;
            return mediaListId == that.mediaListId && mediaId == that.mediaId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(mediaListId, mediaId);
        }
    }
}
