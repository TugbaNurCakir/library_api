package com.example.demo.Model.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class RegistrationRequest {
    private Integer userId;
    private Integer bookId;
    private Integer registrationDay;
}
