package br.com.rodrigodoe.springbootapi.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodrigodoe.springbootapi.data.vo.PersonVO;
import br.com.rodrigodoe.springbootapi.services.PersonServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="Person Endpoint", description = "Description for person", tags = {"Person Endpoint"})
@RestController
@RequestMapping("/api/person/v1")
public class PersonController {

	@Autowired
	private PersonServices services;
	
	@ApiOperation(value="Find All people")
	@GetMapping(produces = { "application/json", "application/xml" })
	public List<PersonVO> findAll(@RequestParam(value = "page", defaultValue = "0")int page,
								@RequestParam(value = "limite", defaultValue = "12")int limit,
								@RequestParam(value= "direction" , defaultValue = "asc") String direction) {
		var sortDirection = "desc".equalsIgnoreCase(direction) ?  Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));
		List<PersonVO> persons = services.findAll(pageable);
		persons
		.stream()
		.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findByid(p.getKey())).withSelfRel()));
		return persons;
	}

	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml" })
	public PersonVO findByid(@PathVariable("id") Long id) {
		
		PersonVO personVO =  services.findById(id);
		personVO.add(linkTo(methodOn(PersonController.class).findByid(id)).withSelfRel());
		return personVO;
	}

	@PostMapping(produces = { "application/json", "application/xml" }, 
				 consumes = { "application/json", "application/xml" })
	public PersonVO create(@RequestBody PersonVO person) {
		PersonVO personVO =  services.create(person);
		personVO.add(linkTo(methodOn(PersonController.class).findByid(personVO.getKey())).withSelfRel());
		return personVO;
	}

	@PutMapping(produces = { "application/json", "application/xml" }, 
				consumes = { "application/json","application/xml" })
	public PersonVO update(@RequestBody PersonVO person) {
		PersonVO personVO =  services.update(person);
		personVO.add(linkTo(methodOn(PersonController.class).findByid(personVO.getKey())).withSelfRel());
		return personVO;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		services.delete(id);
		return ResponseEntity.ok().build();
	}

}
