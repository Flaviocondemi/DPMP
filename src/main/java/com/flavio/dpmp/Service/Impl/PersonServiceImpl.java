package com.flavio.dpmp.Service.Impl;

import com.flavio.dpmp.Entity.Person;
import com.flavio.dpmp.Repository.PersonRepository;
import com.flavio.dpmp.Service.PersonService;
import io.prometheus.client.Summary;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Person> getAllPerson(){
        List<Person> personList = (List<Person>) personRepository.findAll();
        return personList;
    }

    @Override
    public Person insertNewPerson(Person person) {
        return personRepository.save(person);
    }


}
