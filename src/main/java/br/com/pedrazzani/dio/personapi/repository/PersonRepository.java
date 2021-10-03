package br.com.pedrazzani.dio.personapi.repository;

import br.com.pedrazzani.dio.personapi.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
