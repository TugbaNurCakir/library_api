package com.example.demo.Mapper;

import com.example.demo.Model.Entity.Book;
import com.example.demo.Model.Entity.User;
import com.example.demo.Model.dto.BookDTO;
import com.example.demo.Model.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookMapper BOOK_MAPPER = Mappers.getMapper(BookMapper.class);

    BookDTO toBookDto(Book book);
}
