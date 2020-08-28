package br.com.rodrigodoe.springbootapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rodrigodoe.springbootapi.converter.DozerConverter;
import br.com.rodrigodoe.springbootapi.data.model.Book;
import br.com.rodrigodoe.springbootapi.data.vo.BookVO;
import br.com.rodrigodoe.springbootapi.exception.ResourceNotFoundException;
import br.com.rodrigodoe.springbootapi.repository.BookRepository;

@Service
public class BookServices {

	@Autowired
	BookRepository repository;


	public BookVO create(BookVO book) {
		var entity = DozerConverter.parseObject(book, Book.class);
		var vo = DozerConverter.parseObject(repository.save(entity), BookVO.class);
		return vo;
	}
	
	public BookVO update(BookVO book) {
		var entity = repository.findById(book.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());
		
		var vo =  DozerConverter.parseObject(repository.save(entity), BookVO.class);
		return vo;
	}

	public BookVO findById(Long id) {
		var entity =  repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
		
		return DozerConverter.parseObject(entity, BookVO.class);
	}

	public List<BookVO> findAll() {
		return DozerConverter.parseListObjects(repository.findAll(), BookVO.class);
	}

	public void delete(Long id) {
		Book entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
		repository.delete(entity);
	}

}
