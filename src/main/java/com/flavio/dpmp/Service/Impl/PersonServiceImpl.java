package com.flavio.dpmp.Service.Impl;

import com.flavio.dpmp.Entity.Person;
import com.flavio.dpmp.Repository.PersonRepository;
import com.flavio.dpmp.Service.PersonService;
import io.prometheus.client.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;
    static final Summary requestLatency = Summary.build()
            .name("requests_latency_seconds")
            .help("Request latency in seconds.")
            .labelNames("aLabel")
            .create();

    public PersonServiceImpl(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    @Override
    public Page<Person> getAllPeople(Pageable amount){
        return personRepository.findAllPeople(amount);
    }

    public List<Person> getAllPeopleLivingInASpecificCountry(String country){
        return  personRepository.getAllPeopleLivingInASpecificCountry(country);
    }

    @Override
    public List<Person> getAllPeopleWithSamePostcode(int postocde){
        return  personRepository.getAllPeopleWithSamePostcode(postocde);
    }

    @Override
    public List<Person>getAllPeopleWithPasswordStartingWithASpecificLetter(char letter){
        return personRepository.getAllPeopleWithPasswordStartingWithASpecificLetter(letter);
    }


    @Override
    public Person insertNewPerson(Person person) {
        return personRepository.save(person);
    }


}
