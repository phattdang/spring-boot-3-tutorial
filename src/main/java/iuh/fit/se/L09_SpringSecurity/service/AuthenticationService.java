package iuh.fit.se.L09_SpringSecurity.service;

import iuh.fit.se.L09_SpringSecurity.dto.request.AuthenticationRequest;
import iuh.fit.se.L09_SpringSecurity.dto.request.IntrospectRequest;
import iuh.fit.se.L09_SpringSecurity.dto.response.AuthenticationResponse;
import iuh.fit.se.L09_SpringSecurity.dto.response.IntrospectResponse;

public interface AuthenticationService {
    boolean authenticate(AuthenticationRequest request);
    AuthenticationResponse authenticateToken(AuthenticationRequest request);

    IntrospectResponse introspect(IntrospectRequest request);
}
