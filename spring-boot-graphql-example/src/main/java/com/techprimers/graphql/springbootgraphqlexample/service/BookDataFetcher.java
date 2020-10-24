package com.techprimers.graphql.springbootgraphqlexample.service;

import com.techprimers.graphql.springbootgraphqlexample.model.Book;
import com.techprimers.graphql.springbootgraphqlexample.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class BookDataFetcher implements DataFetcher<Book>{
    @Autowired
    BookRepository bookRepository;
    @Override
    public Book get(DataFetchingEnvironment dataFetchingEnvironment){
        String isn = dataFetchingEnvironment.getArgument("id");
        return bookRepository.findOne(isn);
    }
}
