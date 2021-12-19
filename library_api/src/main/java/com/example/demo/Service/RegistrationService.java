package com.example.demo.Service;

import com.example.demo.Exception.BusinessException;
import com.example.demo.Model.Entity.Registration;
import com.example.demo.Model.Request.RegistrationRequest;
import com.example.demo.Model.dto.BookDTO;
import com.example.demo.Model.dto.RegistrationDTO;
import com.example.demo.Model.dto.UserDTO;
import com.example.demo.Repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.Mapper.RegistrationMapper.REGISTRATION_MAPPER;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final UserService userService;
    private final BookService bookService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public RegistrationDTO register(RegistrationRequest registrationRequest) {
        validateUser(registrationRequest);
        validateBook(registrationRequest);

        bookService.updateAvailability(registrationRequest.getBookId(),false);
        userService.incrementRegistrationsCount(registrationRequest.getUserId());


        Registration registration = REGISTRATION_MAPPER.toRegistration(registrationRequest);
        Registration savedRegistration = registrationRepository.save(registration);
        return REGISTRATION_MAPPER.toRegistrationDto(savedRegistration);
    }

    private void validateBook(RegistrationRequest registrationRequest) {
        BookDTO bookDTO =bookService.getBookDto(registrationRequest.getBookId());
        if(!bookDTO.isAvailable()){ //false ve true iki tane case var.
            throw new BusinessException("Book already registered!");
        }
    }
    private void validateUser(RegistrationRequest registrationRequest) {
        UserDTO userDTO = userService.getUserDto(registrationRequest.getUserId());

        if(userDTO.getRegisteredBookCount() >= 3){ //0 2 3 4 10
            throw new BusinessException("User registration limit is reached!!");
        }
    }
}
