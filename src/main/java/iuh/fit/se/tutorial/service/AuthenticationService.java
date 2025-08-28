package iuh.fit.se.tutorial.service;

import iuh.fit.se.tutorial.dto.request.AuthenticationRequest;
import iuh.fit.se.tutorial.dto.request.IntrospectRequest;
import iuh.fit.se.tutorial.dto.response.AuthenticationResponse;
import iuh.fit.se.tutorial.dto.response.IntrospectResponse;

public interface AuthenticationService {
    boolean authenticate(AuthenticationRequest request);
    AuthenticationResponse authenticateToken(AuthenticationRequest request);

    IntrospectResponse introspect(IntrospectRequest request);
}
