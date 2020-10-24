package com.techprimers.graphql.springbootgraphqlexample.service;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import com.techprimers.graphql.springbootgraphqlexample.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;


@Service
public class GraphQLService {
    @Value("classpath:books.graphql")
    Resource resource;
    @Autowired
    BookRepository bookRepository;
    private GraphQL graphQL;
    @Autowired
    private AllBooksDataFetcher allBooksDataFetcher;
    @Autowired
    private BookDataFetcher bookDataFetcher;

    // load schema at application start up
    @PostConstruct
    private void loadSchema() throws IOException {
        //load book into the bookrepository
        loadDataIntoHSQL();
        //get the schema
        File schemaFile = resource.getFile();
        //parse schema
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry,wiring);
        graphQL = GraphQL.newGraphQL(schema).build();

    }

    private void loadDataIntoHSQL(){
        Stream.of(
            new Book("123", "Book of Clouds", "Kindle Edition",
                new String[] {
                    "Chloe Aridis"
                }, "Nov 2017"),
            new Book("124", "Book of Asia", "Riya Edition",
                new String[] {
                    "Riya", "Saridis"
                }, "JUN 2018"),
            new Book("125", "Java", "Orielly",
                new String[] {
                    "Ben","Ram"
                }, "DEC 2019")]
        ).forEach(book -> {
            bookRepository.save(book);
        });

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                    .type("Query", typeWiring -> typeWiring
                                .dataFetcher("allBooks" , allBooksDataFetcher)
                                .dataFetcher("book", bookDataFetcher))
                    .build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }

}
