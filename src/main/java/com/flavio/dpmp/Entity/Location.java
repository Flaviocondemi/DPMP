package com.flavio.dpmp.Entity;

import javax.persistence.*;

@Table(name = "location")
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "city")
    String city;

    @Column(name = "surname")
    String surname;

    @Column(name = "postcode")
    int postcode;

    @Column(name = "state")
    String state;

    @Column(name = "country")
    String country;

    @OneToOne(mappedBy = "location")
    private Person person;


}
