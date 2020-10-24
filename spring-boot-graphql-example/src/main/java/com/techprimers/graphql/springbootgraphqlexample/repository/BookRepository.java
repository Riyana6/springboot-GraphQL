package com.techprimers.graphql.springbootgraphqlexample.repository;

import com.techprimers.graphql.springbootgraphqlexample.model.Book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
    
}
