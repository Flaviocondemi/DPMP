package com.flavio.dpmp.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Table
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(name = "name")
    String name;

    @Column(name = "surname")
    String surname;

    @Column(name = "gender")
    String gender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    Location location;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credentials_id")
    @JsonManagedReference
    Credentials credentials;

    @Column(name = "mail")
    String mail;

    @Column(name = "age")
    int age;

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

    public String getSurname() {
        return surname;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }


    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
