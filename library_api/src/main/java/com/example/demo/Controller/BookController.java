package com.example.demo.Controller;

import com.example.demo.Model.Request.GetPageableRequest;
import com.example.demo.Model.dto.BookDTO;
import com.example.demo.Model.dto.PageableBookDto;
import com.example.demo.Service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @DeleteMapping("{id}")
    public void removeBook(@PathVariable int id) {
        bookService.removeBook(id);
    }

    @GetMapping("{id}")
    public BookDTO getBook(@PathVariable int id) {
        return bookService.getBookDto(id);
    }

    @GetMapping
    public PageableBookDto getBook(GetPageableRequest request) {
        return bookService.getBookDtoList(request);
    }
}
