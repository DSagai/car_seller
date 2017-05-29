package sagai.dmytro.car.seller.model.advertisements;


import sagai.dmytro.car.seller.model.advertisements.attributes.AdvAttribute;
import sagai.dmytro.car.seller.model.authentication.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Entity class represents proposition of car seller.
 *
 * @author dsagai
 * @version 1.00
 * @since 19.04.2017
 */
@Entity
@Table(name = "advertisements")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", nullable = false, foreignKey = @ForeignKey(name = "OWNER_ID_FK"))
    private User owner;

    @Basic
    @Column(name = "description", length = 1000)
    private String description;

    @Basic(fetch = FetchType.EAGER)
    @Column(nullable = false, columnDefinition = "timestamp")
    private Timestamp created;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "STATUS_ID_FK"), nullable = false)
    private AdvAttribute status;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "engine_type",
            foreignKey = @ForeignKey(name = "ENGINE_TYPE_ID_FK"), nullable = false)
    private AdvAttribute engineType;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_body",
            foreignKey = @ForeignKey(name = "CAR_BODY_ID_FK"), nullable = false)
    private AdvAttribute carBodyType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "TRANSMISSION_ID_FK"), nullable = false)
    private AdvAttribute transmission;

    @Basic
    @Column(name = "model_name", length = 50, nullable = false)
    private String modelName;

    @Basic
    @Column(name = "engine_volume", nullable = false)
    private int engineVolume;

    @Basic
    @Column(name = "year_produced", nullable = false)
    private int yearOfProduction;

    @Basic
    @Column(nullable = false)
    private int odometer;

    @Basic
    @Column(name = "horse_powers")
    private int horsePowers;

    @Basic
    @Column(length = 1 << 8, name = "sketch_photo")
    private byte[] sketchPhoto;

    @Basic
    @Column(name = "price", nullable = false)
    private float sellPrice;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "messages", joinColumns = @JoinColumn(name = "advertisement_id"),
    inverseJoinColumns = @JoinColumn(name = "id"))
    private List<Message> messages;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "album", joinColumns = {@JoinColumn(name = "advertisement_id")},
            inverseJoinColumns = @JoinColumn(name = "id"),
            foreignKey = @ForeignKey(name = "ALBUM_ADV_ID_FK")
    )
    private List<AlbumItem> photoAlbum;


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

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public AdvAttribute getStatus() {
        return status;
    }

    public void setStatus(AdvAttribute status) {
        this.status = status;
    }

    public AdvAttribute getEngineType() {
        return engineType;
    }

    public void setEngineType(AdvAttribute engineType) {
        this.engineType = engineType;
    }

    public AdvAttribute getCarBodyType() {
        return carBodyType;
    }

    public void setCarBodyType(AdvAttribute carBodyType) {
        this.carBodyType = carBodyType;
    }

    public AdvAttribute getTransmission() {
        return transmission;
    }

    public void setTransmission(AdvAttribute transmission) {
        this.transmission = transmission;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getEngineVolume() {
        return engineVolume;
    }

    public void setEngineVolume(int engineVolume) {
        this.engineVolume = engineVolume;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public int getOdometer() {
        return odometer;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public int getHorsePowers() {
        return horsePowers;
    }

    public void setHorsePowers(int horsePowers) {
        this.horsePowers = horsePowers;
    }

    public byte[] getSketchPhoto() {
        return sketchPhoto;
    }

    public void setSketchPhoto(byte[] photo) {
        this.sketchPhoto = photo;
    }

    public float getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(float sellPrice) {
        this.sellPrice = sellPrice;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Advertisement that = (Advertisement) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
