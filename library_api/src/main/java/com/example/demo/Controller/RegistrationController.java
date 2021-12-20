package com.example.demo.Controller;

import com.example.demo.Model.Request.RegistrationRequest;
import com.example.demo.Model.dto.RegistrationDTO;
import com.example.demo.Service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("registrations")
@RequiredArgsConstructor

public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping
    public RegistrationDTO registrationDTO(@Valid @RequestBody RegistrationRequest registrationRequest){
        return registrationService.register(registrationRequest);
    }
}
