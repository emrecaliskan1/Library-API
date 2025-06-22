package com.emre.service;

import com.emre.dto.AuthRequest;
import com.emre.dto.AuthResponse;
import com.emre.dto.DtoUser;
import com.emre.dto.RefreshTokenRequest;
import com.emre.model.RefreshToken;
import org.springframework.stereotype.Service;


public interface IAuthenticationService {

    public DtoUser register(AuthRequest input);

    public AuthResponse authenticate(AuthRequest input);

    public AuthResponse refreshToken(RefreshTokenRequest input);
}
