package com.flavio.dpmp.Repository;

import com.flavio.dpmp.Entity.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Integer> {

}
