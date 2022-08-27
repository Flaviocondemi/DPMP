package com.flavio.dpmp.Controller;

import com.flavio.dpmp.Entity.Person;
import com.flavio.dpmp.Service.Impl.PersonServiceImpl;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class PersonController {

    private PersonServiceImpl personServiceImpl;

    @Autowired
    public PersonController(PersonServiceImpl personServiceImpl){
        this.personServiceImpl = personServiceImpl;

    }

    @GetMapping(value= "/get")
    @Timed(value="api_response_time",description="Api response time")
    public @ResponseBody
    List<Person> getPeople(){
        return (List<Person>) personServiceImpl.getAllPerson();
    }

    @PostMapping(value= "/post")
    @Timed(value="api_response_time",description="Api response time")
    public @ResponseBody Person postPeople(@RequestBody Person person){
        return personServiceImpl.insertNewPerson(person);
    }


}
