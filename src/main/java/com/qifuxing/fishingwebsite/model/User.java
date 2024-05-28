package com.qifuxing.fishingwebsite.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This entity represents a user in the system.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-20
 * @version 1.0.0
 */


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false)
    private String email;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o){
        //first check if objects are of the same instance
        if (this == o) return true;
        //check if other object is not from the same class or is null
        if (o == null || getClass() != o.getClass()) return false;

        //make the other object to current object class.
        User user = (User) o;

        /*
        //so it is first seeing if id is null, if not then checks if id equals order id which holds id from the o object.
        return id != null ? id.equals(order.id) : order.id == null;
         */

        //shorter version of using Objects.equal ti handle null checks and equality
        return Objects.equals(id,user.id);
    }

    //so when object are equal from the equals method then they must have same hashcode
    @Override
    public int hashCode(){
        //return hashcode of the id if not null, otherwise return 0.
        return id != null ? id.hashCode() : 0;
    }

}
