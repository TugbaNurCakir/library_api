package com.example.demo;

import com.example.demo.Model.Entity.Book;
import com.example.demo.Repository.AuthorRepository;
import com.example.demo.Repository.BookRepository;
import com.example.demo.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Initializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public void run(String... args) throws Exception {
//        Author author = authorRepository.save(Author.builder()
//                .name("test")
//                .surname("test")
//                .build());
        for (int i = 0; i < 100; i++) {
            bookRepository.save(Book.builder()
                    .title("initialize book " + i)
                    .authorId(1)
                    .build());
        }


//        userRepository.save(User.builder()
//                .username("deneme")
//                .password("12345")
//                .build());
    }
}
