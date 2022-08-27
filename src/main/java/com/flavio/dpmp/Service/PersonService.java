package com.flavio.dpmp.Service;

import com.flavio.dpmp.Entity.Person;

import java.util.List;

public interface PersonService {

    public List<Person> getAllPerson();


    public Person insertNewPerson(Person person);
}
