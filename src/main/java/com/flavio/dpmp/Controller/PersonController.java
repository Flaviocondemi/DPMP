package com.flavio.dpmp.Controller;

import com.flavio.dpmp.Entity.Person;
import com.flavio.dpmp.Service.Impl.PersonServiceImpl;
import com.flavio.dpmp.Utils.TrackExecutionTime;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class PersonController {

    private PersonServiceImpl personServiceImpl;

    @Autowired
    public PersonController(PersonServiceImpl personServiceImpl){
        this.personServiceImpl = personServiceImpl;
    }

    @GetMapping(value= "/get", params = {"amount"})
    @TrackExecutionTime(methodName = "getPeople")
    public @ResponseBody Page<Person> getPeople(@RequestParam(value = "amount",required = false) int amount){
        return personServiceImpl.getAllPeople(PageRequest.of(0, amount));
    }

    @GetMapping(value= "/get/location")
    @TrackExecutionTime(methodName = "getAllPeopleLivingInASpecificPlace")
    public @ResponseBody
    List<Person> getAllPeopleLivingInASpecificCountry(@RequestParam(value = "location",required = false) String location){
        return personServiceImpl.getAllPeopleLivingInASpecificCountry(location);
    }

    @GetMapping(value= "/get/postcode")
    @TrackExecutionTime(methodName = "getAllPeopleWithSamePostcode")
    public @ResponseBody
    List<Person> getAllPeopleWithSamePostcode(@RequestParam int postcode){
        return personServiceImpl.getAllPeopleWithSamePostcode(postcode);
    }

    @PostMapping(value= "/post")
    @TrackExecutionTime(methodName = "postPeople")
    public @ResponseBody Person postPeople(@RequestBody Person person){
        return personServiceImpl.insertNewPerson(person);
    }

    @GetMapping(value= "/get/LetterPassword")
    @TrackExecutionTime(methodName = "getAllPeopleWithPasswordStartingWithASpecificLetter")
    public @ResponseBody List<Person> getAllPeopleWithPasswordStartingWithASpecificLetter(@RequestBody char letter){
        return personServiceImpl.getAllPeopleWithPasswordStartingWithASpecificLetter(letter);
    }



}
