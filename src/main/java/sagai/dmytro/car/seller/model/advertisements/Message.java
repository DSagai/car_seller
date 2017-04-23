package sagai.dmytro.car.seller.model.advertisements;

import sagai.dmytro.car.seller.model.authentication.User;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Entity class represents message item
 * which appears during correspondence between
 * car owner and potential buyer at advertisement page.
 *
 * @author dsagai
 * @version 1.00
 * @since 20.04.2017
 */
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(length = 255, nullable = false)
    private String body;

    @Basic
    @Column(nullable = false, columnDefinition = "timestamp")
    private Timestamp created;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "advertisement_id", nullable = false, foreignKey = @ForeignKey(name = "ADV_ID_FK"))
    private Advertisement advertisement;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(name = "AUTHOR_ID_FK"))
    private User author;

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        return id == message.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
