package com.armaan.dematic.repository;

import com.armaan.dematic.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * CRUD Repository for Book Entity
 */
@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
}
