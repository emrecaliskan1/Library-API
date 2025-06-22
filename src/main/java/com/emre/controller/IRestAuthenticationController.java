package com.emre.controller;

import com.emre.dto.AuthRequest;
import com.emre.dto.AuthResponse;
import com.emre.dto.DtoUser;
import com.emre.dto.RefreshTokenRequest;

public interface IRestAuthenticationController {
    public RootEntity<DtoUser> register(AuthRequest input);

    public RootEntity<AuthResponse> authenticate(AuthRequest input);

    public RootEntity<AuthResponse> refreshToken(RefreshTokenRequest input);
}
