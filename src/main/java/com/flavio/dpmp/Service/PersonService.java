package com.flavio.dpmp.Service;

import com.flavio.dpmp.Entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonService {

    Page<Person> getAllPeople(Pageable amount);

    Person insertNewPerson(Person person);

    List<Person> getAllPeopleWithSamePostcode(int postocde);

    List<Person> getAllPeopleLivingInASpecificCountry(String city);

    List<Person>getAllPeopleWithPasswordStartingWithASpecificLetter(char letter);
}
