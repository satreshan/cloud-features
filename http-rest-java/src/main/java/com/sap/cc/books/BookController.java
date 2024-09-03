package com.sap.cc.books;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/books")
public class BookController {

    BookStorage bookStorage;

    public BookController(BookStorage bookStorage) {
        this.bookStorage = bookStorage;
    }

    @GetMapping
    public List<Book> getAll(){
        return bookStorage.getAll();
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) throws URISyntaxException {
        Book createdBook = bookStorage.save(book);
        return ResponseEntity.created(new URI("/api/v1/books/" + createdBook.getId())).body(book);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getSingleBook(@PathVariable("id") String id){
        if(Long.valueOf(id) < 1)
            throw new IllegalArgumentException("Id must not be less than 1");
        Optional<Book> book = bookStorage.get(Long.valueOf(id));
        return ResponseEntity.ok(book.orElseThrow(NotFoundException::new));
    }

    @DeleteMapping
    public ResponseEntity<Book> deleteBooks(){
        bookStorage.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
