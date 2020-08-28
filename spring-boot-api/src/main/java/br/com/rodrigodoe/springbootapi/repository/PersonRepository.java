package br.com.rodrigodoe.springbootapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rodrigodoe.springbootapi.data.model.Person;

@Repository
public interface PersonRepository extends  JpaRepository<Person, Long>{

}
