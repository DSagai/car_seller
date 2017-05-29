package sagai.dmytro.car.seller.model.authentication;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sagai.dmytro.car.seller.model.advertisements.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Entity class for purpose of user authentication.
 *
 * @author dsagai
 * @version 1.00
 * @since 19.04.2017
 */

@Entity
@Table(name = "sec_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "uuid", columnDefinition = "binary(16)", nullable = false)
    private UUID uuid;

    @Basic
    @Column(name = "username", nullable = false, length = 20)
    @Size(min = 5, max = 20, message = "count of characters must be between 5 and 20")
    @Pattern(regexp = "[A-Za-z0-9\\._-]++", message = "Illegal characters were found")
    @NotNull(message = "This field could not be empty")
    private String username;


    @Basic
    @Column(name = "password", nullable = false, length = 100)
    @NotNull(message = "This field could not be empty")
    private String password;

    @Basic
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private SecurityRole role = SecurityRole.ROLE_USER;

    @Basic
    @Column(name = "first_name", nullable = false, length = 80)
    @Size(min = 3, max = 80, message = "count of characters must be between 3 and 80")
    @Pattern(regexp = "[A-Za-z]++", message = "Illegal characters were found")
    @NotNull(message = "This field could not be empty")
    private String firstName;

    @Basic
    @Column(name = "last_name", nullable = false, length = 80)
    @Size(min = 3, max = 80, message = "count of characters must be between 3 and 80")
    @Pattern(regexp = "[A-Za-z]++", message = "Illegal characters were found")
    private String lastName;

    @Basic
    @Column(nullable = false, unique = true, length = 100)
    @Size(min = 5, max = 20, message = "count of characters must be between 5 and 20")
    @Pattern(regexp = "^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+(\\.[(a-zA-z)]{2,3}){1,2}$",
            message = "Invalid email address")
    @NotNull(message = "This field could not be empty")
    private String email;

    @Basic
    @Column(name = "phone_number", nullable = false, length = 30)
    @Pattern(regexp = "[0-9]++", message = "only digits are allowed")
    private String phoneNumber;

    @Basic
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean enabled;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAdvertisements(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;
    }

    @Override
    public int hashCode() {
        return id;
    }


}
