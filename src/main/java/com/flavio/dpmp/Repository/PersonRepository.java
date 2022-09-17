package com.flavio.dpmp.Repository;

import com.flavio.dpmp.Entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query(value = "SELECT d FROM Person d")
    Page<Person> findAllPeople(Pageable amount);

    @Query(value = "SELECT d FROM Person d WHERE d.location IN (SELECT d FROM Location d WHERE d.country = ?1)")
    List<Person> getAllPeopleLivingInASpecificCountry(String city);

    @Query(value = "SELECT d FROM Person d WHERE d.credentials IN (SELECT d FROM Credentials d WHERE d.password like ?1 )")
    List<Person>getAllPeopleWithPasswordStartingWithASpecificLetter(char letter);

    @Query(value = "SELECT d FROM Person d WHERE d.location IN (SELECT d FROM Location d WHERE d.postcode = ?1)")
    List<Person> getAllPeopleWithSamePostcode(int postcode);
}
