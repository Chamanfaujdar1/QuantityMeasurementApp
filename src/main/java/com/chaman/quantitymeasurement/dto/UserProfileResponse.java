package com.chaman.quantitymeasurement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfileResponse {

    private Long id;
    private String username;
    private String role;
    private String provider;
}
