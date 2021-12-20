package com.example.demo.Controller;
import com.example.demo.Exception.BusinessException;
import com.example.demo.Exception.NotFoundException;
import com.example.demo.Exception.handler.GlobalExceptionHandler;
import com.example.demo.Model.Request.RegistrationRequest;
import com.example.demo.Model.dto.RegistrationDTO;
import com.example.demo.Service.RegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RegistrationControllerTest {
    protected MockMvc mockMvc;

    @Mock
    private RegistrationService registrationService;

    @InjectMocks
    private RegistrationController registrationController;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    private ObjectMapper mapper;

    private Map<String, Object> request;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController)
                .setControllerAdvice(globalExceptionHandler)
                .build();
        request = new HashMap<>();
        request.put("userId", 1);
        request.put("bookId", 1);
        request.put("registrationDay", 1);
    }

    @ParameterizedTest
    @MethodSource("prepareNotValidData")
    @SneakyThrows
    void it_should_return_bad_request_when_request_not_valid(Map<String, Object> request) {
        String requestJson = mapper.writeValueAsString(request);

        mockMvc.perform(post("/registrations")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());

    }
    public static Stream<Arguments> prepareNotValidData() {
        Map<String, Object> request1 = new HashMap<>();
        request1.put("userId", 1);
        request1.put("bookId", 1);

        Map<String, Object> request2 = new HashMap<>();
        request2.put("userId", 1);
        request2.put("bookId", 1);
        request2.put("registrationDay", 0);


        return Stream.of(
                Arguments.of(request1),
                Arguments.of(request2)
        );
    }

    @SneakyThrows
    @Test
    void it_should_return_success_when_request_is_valid() {
        //Given
        when(registrationService.register(any(RegistrationRequest.class))).thenReturn(
                RegistrationDTO.builder()
                        .id(1)
                        .build());
        String requestJson = mapper.writeValueAsString(request);
        //When
        //Then
        mockMvc.perform(post("/registrations")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @SneakyThrows
    @Test
    void it_should_return_not_found_when_service_throw_not_found_exception() {
        when(registrationService.register(any(RegistrationRequest.class)))
                .thenThrow(new NotFoundException("test"));
        String requestJson = mapper.writeValueAsString(request);
        //When
        //Then
        mockMvc.perform(post("/registrations")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void it_should_return_bad_request_when_service_throws_business_exception() {
        when(registrationService.register(any(RegistrationRequest.class)))
                .thenThrow(new BusinessException("test"));
        String requestJson = mapper.writeValueAsString(request);
        //When
        //Then
        mockMvc.perform(post("/registrations")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }
}
