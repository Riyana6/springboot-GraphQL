package com.techprimers.graphql.springbootgraphqlexample.service;

import com.techprimers.graphql.springbootgraphqlexample.model.Book;

import org.springframework.stereotype.Component;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class BookDataFetcher implements DataFetcher<Book>{
    @Override
    public Book get(DataFetchingEnvironment dataFetchingEnvironment){
        return null;
    }
}
