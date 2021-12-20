package com.example.demo.Service;
import com.example.demo.Exception.BusinessException;
import com.example.demo.Model.Entity.Registration;
import com.example.demo.Model.dto.BookDTO;
import com.example.demo.Model.dto.RegistrationDTO;
import com.example.demo.Repository.RegistrationRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import com.example.demo.Model.Request.RegistrationRequest;
import com.example.demo.Model.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {
    @InjectMocks
    private RegistrationService sut;

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private BookService bookService;

    @Captor
    private ArgumentCaptor<Registration> registrationArgumentCaptor;

    @Mock
    private UserService userService;

    private RegistrationRequest registrationRequest;
    private UserDTO userDTO;
    private BookDTO bookDTO;

    @BeforeEach
    void setUp() {
        registrationRequest = RegistrationRequest.builder()
                .userId(1)
                .bookId(1)
                .registrationDay(2)
                .build();
        userDTO = UserDTO.builder()
                .id(1)
                .registeredBookCount(0)
                .build();
        bookDTO = BookDTO.builder()
                .available(true)
                .id(1)
                .build();

    }

    @ParameterizedTest
    @ValueSource(ints={3,4,6})
    void it_should_throw_exception_when_user_has_3_book(int bookCount){
        //Given (datas)
        UserDTO customUserDto = userDTO.builder()
                .registeredBookCount(bookCount)
                .build();
        when(userService.getUserDto(eq(1))).thenReturn(customUserDto);

        //When (call columnn)
          //sut.register(registrationRequest);

        //Then (result)
        Assertions.assertThrows(BusinessException.class, ()-> sut.register(registrationRequest));

    }
    @Test
    void it_should_throw_exception_when_book_is_not_available(){
        //Given
        bookDTO.setAvailable(false);
        when(bookService.getBookDto(eq(1))).thenReturn(bookDTO);
        when(userService.getUserDto(eq(1))).thenReturn(userDTO);

        //When
        //Then
        Assertions.assertThrows(BusinessException.class, ()-> sut.register(registrationRequest));
        verify(registrationRepository,never()).save(any(Registration.class));
        verify(bookService,never()).updateAvailability(anyInt(),anyBoolean());
        verify(userService,never()).incrementRegistrationsCount(anyInt());
    }

    @Test
    void it_should_return_dto_when_everything_ok(){
        Date now = Date.from(ZonedDateTime.now().toInstant());
        Date expectedExpiryDate = Date.from(ZonedDateTime.now().plusDays(registrationRequest.getRegistrationDay()).toInstant());
        //Given
        when(bookService.getBookDto(anyInt())).thenReturn(bookDTO);
        when(userService.getUserDto(anyInt())).thenReturn(userDTO);
        when(registrationRepository.save(registrationArgumentCaptor.capture())).thenReturn(Registration.builder()
                .id(1)
                .userId(1)
                .bookId(1)
                .expiryDate(now)
                .build());

        //when
        RegistrationDTO registrationDTO = sut.register(registrationRequest);

        //Then
        Assertions.assertEquals(1, registrationDTO.getBookId());
        Assertions.assertEquals(1, registrationDTO.getUserId());
        Assertions.assertEquals(1, registrationDTO.getId());
        Assertions.assertEquals(now, registrationDTO.getExpiryDate());

        Registration captorValue = registrationArgumentCaptor.getValue();
        Assertions.assertEquals(1, captorValue.getBookId());
        Assertions.assertEquals(1, captorValue.getUserId());
        Assertions.assertNull(captorValue.getId());
        Assertions.assertEquals(DateUtils.round(expectedExpiryDate, Calendar.SECOND),
                DateUtils.round(captorValue.getExpiryDate(), Calendar.SECOND));

        verify(bookService).updateAvailability(eq(1),eq(false));
        verify(userService).incrementRegistrationsCount(eq(1));

    }

}
