package com.sap.cc.library.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class DataDurabilityTest {

    @Autowired
    private BookRepository repository;

    @Test
    public void populateDb(){
        repository.save(BookFixtures.designPatterns());
        repository.save(BookFixtures.modernOperatingSystems());
        assertTrue(repository.findAll().size() >= 2);
    }

    @Test
    public void isDbPopulated(){
        assertTrue(repository.findAll().size() >= 2);
    }
}
