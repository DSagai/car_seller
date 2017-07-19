package sagai.dmytro.car.seller.model.advertisements.attributes;

import javax.persistence.*;

/**
 * Entity class defines an attribute item
 * from collection of available attributes for Advertisement
 *
 * @author dsagai
 * @version 1.00
 * @since 20.04.2017
 */
@Entity
@Table(name = "attributes")
public class AdvAttribute {

    /**
     * method returns instance of AdvAttribute with id property
     * determined from String param.
     * @param stringId String.
     * @return AdvAttribute.
     */
    public static AdvAttribute getInstanceByString(String stringId) {
        int id = Integer.parseInt(stringId);
        AdvAttribute attribute = new AdvAttribute();
        attribute.setId(id);
        return attribute;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttributeTypes type;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    public AdvAttribute() {
    }

    public AdvAttribute(AttributeTypes type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AttributeTypes getType() {
        return type;
    }

    public void setType(AttributeTypes type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AdvAttribute{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdvAttribute attribute = (AdvAttribute) o;

        return id == attribute.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
