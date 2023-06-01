package com.example.ecommercial.service;

import com.example.ecommercial.dao.BookDao;
import com.example.ecommercial.domain.entity.BookEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookDao bookDao;
    private final ModelMapper modelMapper;

    public List<BookEntity> addBook(
            BookEntity book
    ){
        bookDao.save(book);
        return bookDao.findAll();
    }

    public List<BookEntity> getAll(){
        return bookDao.findAll();
    }

    public List<BookEntity> deleteById(Long bookId) {
        bookDao.deleteById(bookId);
        return bookDao.findAll();
    }

    public BookEntity findById(Long bookId) {
        return bookDao.findById(bookId).get();
    }

    public List<BookEntity> update(BookEntity book) {
        BookEntity book1 = bookDao.findById(book.getId()).get();
        modelMapper.map(book, book1);
        bookDao.save(book1);
        return bookDao.findAll();
    }
}
