package com.example.ecommercial.controller;

import com.example.ecommercial.domain.entity.BookEntity;
import com.example.ecommercial.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @GetMapping("/get_all")
    public ModelAndView getAll(){
        ModelAndView modelAndView = new ModelAndView("dashboard");
        modelAndView.addObject("books", bookService.getAll());
        modelAndView.addObject("status", 5);
        return modelAndView;
    }

    @PostMapping("add_book")
    public ModelAndView addBook(
           @ModelAttribute BookEntity book
    ){
        List<BookEntity> bookEntities = bookService.addBook(book);
        ModelAndView modelAndView = new ModelAndView("dashboard");
        modelAndView.addObject("books", bookEntities);
        modelAndView.addObject("status", 5);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(
            @PathVariable("id")Long bookId
    ){
        List<BookEntity> bookEntities = bookService.deleteById(bookId);
        ModelAndView modelAndView = new ModelAndView("dashboard");
        modelAndView.addObject("books", bookEntities);
        modelAndView.addObject("status", 5);
        return modelAndView;
    }

    @GetMapping("/update/{id}")
    public ModelAndView update(
            @PathVariable("id") Long bookId
    ){
        BookEntity book =  bookService.findById(bookId);
        ModelAndView modelAndView = new ModelAndView("update-book");
        modelAndView.addObject("book", book);
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView update(
            @ModelAttribute BookEntity book
    ){
        List<BookEntity> bookEntities = bookService.update(book);
        ModelAndView modelAndView = new ModelAndView("dashboard");
        modelAndView.addObject("books", bookEntities);
        modelAndView.addObject("status", 5);
        return modelAndView;
    }
}
