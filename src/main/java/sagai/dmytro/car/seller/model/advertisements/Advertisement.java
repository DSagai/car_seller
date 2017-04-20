package sagai.dmytro.car.seller.model.advertisements;

import sagai.dmytro.car.seller.model.authentication.User;

import javax.persistence.*;

/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 19.04.2017
 */
@Entity
@Table(name = "advertisements")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", nullable = false, foreignKey = @ForeignKey(name = "OWNER_ID_FK"))
    private User owner;

    @Basic
    @Column(name = "description", length = 1000)
    private String description;

    public Advertisement() {
    }

    public Advertisement(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
