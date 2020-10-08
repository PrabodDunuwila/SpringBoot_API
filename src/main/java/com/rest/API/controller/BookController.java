package com.rest.API.controller;

import com.rest.API.exception.BookNotFoundException;
import com.rest.API.model.Book;
import com.rest.API.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @PostMapping("/books")
    public Book createBook(@Valid @RequestBody Book book) {
        return bookRepository.save(book);
    }

    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable(value = "id") Long bookId)
            throws BookNotFoundException {
        return bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
    }

    @PutMapping("/books/{id}")
    public Book updateBook(@PathVariable(value = "id") Long bookId, @Valid @RequestBody Book bookDetails)
            throws BookNotFoundException {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        book.setBook_name(bookDetails.getBook_name());
        book.setAuthor_name(bookDetails.getAuthor_name());
        book.setIsbn(bookDetails.getIsbn());
        Book updatedBook = bookRepository.save(book);
        return updatedBook;
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable(value = "id") Long bookId) throws BookNotFoundException {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        bookRepository.delete(book);
        return ResponseEntity.ok().build();
    }


}
