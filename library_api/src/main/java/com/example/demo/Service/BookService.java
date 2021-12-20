package com.example.demo.Service;

import com.example.demo.Exception.NotFoundException;
import com.example.demo.Model.Entity.Book;
import com.example.demo.Model.Request.GetPageableRequest;
import com.example.demo.Model.dto.BookDTO;
import com.example.demo.Model.dto.PageableBookDto;
import com.example.demo.Repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static com.example.demo.Mapper.BookMapper.BOOK_MAPPER;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public BookDTO getBookDto(int id){
        Book book = getBook(id);
        return BOOK_MAPPER.toBookDto(book);
    }

    private Book getBook(int id) {
        return bookRepository.findById(id).orElseThrow(()-> new NotFoundException("Book not found"));
    }
    protected void updateAvailability(int id, boolean availability){
        bookRepository.setAvailability(id, availability);
    }

    public void removeBook(int id) {
        bookRepository.deleteById(id);
    }
    public PageableBookDto getBookDtoList(GetPageableRequest request) {
        Page<Book> bookPage = bookRepository.findAll(PageRequest.of(request.getCurrentPage(), request.getPageSize()));
        return PageableBookDto.builder()
                .bookDtoList(BOOK_MAPPER.toBookDtoList(bookPage.getContent()))
                .totalElements(bookPage.getTotalElements())
                .totalPages(bookPage.getTotalPages())
                .build();
    }
}
