package com.qifuxing.fishingwebsite.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This entity represents an account in the system.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-10
 * @version 1.0.0
 */

//marks this Account class as a Java class and used by JPA to recognize as an entity bean.
@Entity
//shows the name of the table that it is mapping to
@Table(name = "account")
public class Account {

    //specifies that id is the primary key
    @Id
    //primary key value will be auto generated by database and 'generationType.IDENTITY' is used for auto incremented
    //columns in MYSql
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //maps name to 'name' column in database and says name can't be null in database.
    @Column(name = "name", nullable = false)
    private String name;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public boolean equals(Object o){
        //first check if objects are of the same instance
        if (this == o) return true;
        //check if other object is not from the same class or is null
        if (o == null || getClass() != o.getClass()) return false;

        //make the other object to current object class.
        Account account = (Account) o;

        /*
        //so it is first seeing if id is null, if not then checks if id equals order id which holds id from the o object.
        return id != null ? id.equals(order.id) : order.id == null;
         */

        //shorter version of using Objects.equal ti handle null checks and equality
        return Objects.equals(id,account.id);
    }

    //so when object are equal from the equals method then they must have same hashcode
    @Override
    public int hashCode(){
        //return hashcode of the id if not null, otherwise return 0.
        return id != null ? id.hashCode() : 0;
    }

}
