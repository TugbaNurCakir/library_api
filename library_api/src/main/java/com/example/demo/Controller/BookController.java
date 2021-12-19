package com.example.demo.Controller;

import com.example.demo.Service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @DeleteMapping("{id}")
    public void removeBook(@PathVariable int id){
        bookService.removeBook(id);
    }

    @GetMapping("{id}")
    public void getBook(@PathVariable int id){
        bookService.getBookDto(id);
    }
}
