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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttributeTypes type;

    @Basic
    @Column(name = "name", nullable = false)
    private String Name;

    public AdvAttribute() {
    }

    public AdvAttribute(AttributeTypes type, String name) {
        this.type = type;
        Name = name;
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
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "AdvAttribute{" +
                "id=" + id +
                ", type=" + type +
                ", Name='" + Name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdvAttribute attribute = (AdvAttribute) o;

        if (id != attribute.id) return false;
        if (type != attribute.type) return false;
        return Name.equals(attribute.Name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + type.hashCode();
        result = 31 * result + Name.hashCode();
        return result;
    }


}
