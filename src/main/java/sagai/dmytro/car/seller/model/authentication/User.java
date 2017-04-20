package sagai.dmytro.car.seller.model.authentication;

import java.util.List;

import org.hibernate.annotations.Cascade;
import sagai.dmytro.car.seller.model.advertisements.*;

import javax.persistence.*;

/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 19.04.2017
 */

@Entity
@Table(name = "sec_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(nullable = false, length = 45)
    private String login;


    @Basic
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Basic
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "ROLE_ID_FK"))
    private SecurityRole role;

    @Basic
    @Column(name = "first_name", nullable = false, length = 80)
    private String firstName;

    @Basic
    @Column(name = "last_name", nullable = false, length = 80)
    private String lastName;

    @Basic
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Basic
    @Column(name = "phone_number", nullable = false, length = 30)
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "advertisements", joinColumns = @JoinColumn(name = "owner_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private List<Advertisement> advertisements;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SecurityRole getRole() {
        return role;
    }

    public void setRole(SecurityRole role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setAdvertisements(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

}
