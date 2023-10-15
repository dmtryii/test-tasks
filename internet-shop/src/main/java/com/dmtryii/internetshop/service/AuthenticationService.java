package com.dmtryii.internetshop.service;

import com.dmtryii.internetshop.security.auth.AuthenticationRequest;
import com.dmtryii.internetshop.security.auth.AuthenticationResponse;
import com.dmtryii.internetshop.security.auth.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
