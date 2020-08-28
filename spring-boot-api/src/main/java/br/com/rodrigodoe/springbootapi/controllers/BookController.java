package br.com.rodrigodoe.springbootapi.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import br.com.rodrigodoe.springbootapi.data.vo.BookVO;
import br.com.rodrigodoe.springbootapi.services.BookServices;
import io.swagger.annotations.Api;


@Api(value="Book Endpoint", description = "Description for book", tags = {"book Endpoint"})
@RestController
@RequestMapping("/api/book/v1")
public class BookController {

	@Autowired
	private BookServices services;

	@GetMapping(produces = { "application/json", "application/xml" })
	public List<BookVO> findAll() {
		List<BookVO> books = services.findAll();
		books
		.stream()
		.forEach(p -> p.add(linkTo(methodOn(BookController.class).findByid(p.getKey())).withSelfRel()));
		return books;
	}

	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml" })
	public BookVO findByid(@PathVariable("id") Long id) {
		
		BookVO bookVO =  services.findById(id);
		bookVO.add(linkTo(methodOn(BookController.class).findByid(id)).withSelfRel());
		return bookVO;
	}

	@PostMapping(produces = { "application/json", "application/xml" }, 
				 consumes = { "application/json", "application/xml" })
	public BookVO create(@RequestBody BookVO book) {
		BookVO bookVO =  services.create(book);
		bookVO.add(linkTo(methodOn(BookController.class).findByid(bookVO.getKey())).withSelfRel());
		return bookVO;
	}

	@PutMapping(produces = { "application/json", "application/xml" }, 
				consumes = { "application/json","application/xml" })
	public BookVO update(@RequestBody BookVO book) {
		BookVO bookVO =  services.update(book);
		bookVO.add(linkTo(methodOn(BookController.class).findByid(bookVO.getKey())).withSelfRel());
		return bookVO;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		services.delete(id);
		return ResponseEntity.ok().build();
	}

}
