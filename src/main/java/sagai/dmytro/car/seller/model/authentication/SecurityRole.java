package sagai.dmytro.car.seller.model.authentication;

import javax.persistence.*;

/**
 * Class represents entity item of collection of
 * security roles
 *
 * @author dsagai
 * @version 1.00
 * @since 19.04.2017
 */
@Entity
@Table(name = "sec_roles")
public class SecurityRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private String name;

    public SecurityRole() {
    }

    public SecurityRole(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SecurityRole{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SecurityRole role = (SecurityRole) o;

        if (id != role.id) return false;
        return name.equals(role.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }
}
