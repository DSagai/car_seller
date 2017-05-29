package sagai.dmytro.car.seller.model.advertisements;

import javax.persistence.*;

/**
 * Entity class represents photo attachment for an Advertisement item.
 *
 * @author dsagai
 * @version 1.00
 * @since 23.04.2017
 */
@Entity
@Table(name = "album")
public class AlbumItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(length = 1 << 11)
    private byte[] photo;

    @ManyToOne
    @JoinColumn(name = "advertisement_id", nullable = false, foreignKey = @ForeignKey(name = "ALBUM_ADV_ID_FK"))
    private Advertisement advertisement;

    public AlbumItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }
}
