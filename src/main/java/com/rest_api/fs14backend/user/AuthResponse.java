package com.rest_api.fs14backend.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private User user;
    private Map<String,String> token;
}
