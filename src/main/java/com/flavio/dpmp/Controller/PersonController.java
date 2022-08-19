package com.flavio.dpmp.Controller;

import com.flavio.dpmp.Entity.Person;
import com.flavio.dpmp.Service.Impl.PersonServiceImpl;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

}
