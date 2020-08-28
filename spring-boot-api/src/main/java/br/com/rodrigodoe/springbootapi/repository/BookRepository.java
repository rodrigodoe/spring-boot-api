package br.com.rodrigodoe.springbootapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rodrigodoe.springbootapi.data.model.Book;

@Repository
public interface BookRepository extends  JpaRepository<Book, Long>{

}
