package br.com.rodrigodoe.springbootapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.rodrigodoe.springbootapi.converter.DozerConverter;
import br.com.rodrigodoe.springbootapi.data.model.Person;
import br.com.rodrigodoe.springbootapi.data.vo.PersonVO;
import br.com.rodrigodoe.springbootapi.exception.ResourceNotFoundException;
import br.com.rodrigodoe.springbootapi.repository.PersonRepository;

@Service
public class PersonServices {

	@Autowired
	PersonRepository repository;


	public PersonVO create(PersonVO person) {
		var entity = DozerConverter.parseObject(person, Person.class);
		var vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}
	
	public PersonVO update(PersonVO person) {
		var entity = repository.findById(person.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var vo =  DozerConverter.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}

	public PersonVO findById(Long id) {
		var entity =  repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
		
		return DozerConverter.parseObject(entity, PersonVO.class);
	}

	public List<PersonVO> findAll(Pageable pageable) {
		var entities = repository.findAll(pageable).getContent();
		return DozerConverter.parseListObjects(entities, PersonVO.class);
	}

	public void delete(Long id) {
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
		repository.delete(entity);
	}

}
