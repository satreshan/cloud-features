package com.sap.cc.library.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BookRepositoryTest {
    @Autowired
    private BookRepository repository;

    @BeforeEach
    public void clearDb(){
        repository.deleteAll();
    }

    @Test
    public void findAll_shouldReturn_emptyList(){
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    public void save_shouldReturn_bookCleancode(){
        Book book = BookFixtures.cleanCode();
        Book savedBook = repository.save(book);
        List<Book> fetchBook = repository.findAll();
        assertTrue(fetchBook.size() == 1);
        assertTrue(savedBook.getId().equals(fetchBook.get(0).getId()));
        assertTrue(fetchBook.get(0).getAuthor().getName().equals(book.getAuthor().getName()));
    }

    @Test
    public void save_shouldReturn_bookRefactoring(){
        Book book = BookFixtures.refactoring();
        Book savedBook = repository.save(book);
        List<Book> fetchBook = repository.findAll();
        assertTrue(fetchBook.size() == 1);
        assertTrue(savedBook.getId().equals(fetchBook.get(0).getId()));
    }

    @Test
    public void findByTitle_shouldReturn_bookByTile(){
        repository.save(BookFixtures.cleanCode());
        repository.save(BookFixtures.refactoring());
        List<Book> fetchBook1 = repository.findByTitle("Clean Code");
        assertTrue(fetchBook1.size() == 1);
        assertTrue("Clean Code".equals(fetchBook1.get(0).getTitle()));

        repository.save(BookFixtures.refactoring());
        fetchBook1 = repository.findByTitle("Refactoring");
        assertTrue(fetchBook1.size() == 2);
        assertTrue("Refactoring".equals(fetchBook1.get(0).getTitle()));
    }
}
