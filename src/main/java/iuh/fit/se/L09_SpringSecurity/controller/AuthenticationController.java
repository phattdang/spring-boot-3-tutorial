package iuh.fit.se.L09_SpringSecurity.controller;

import iuh.fit.se.L09_SpringSecurity.dto.request.AuthenticationRequest;
import iuh.fit.se.L09_SpringSecurity.dto.request.IntrospectRequest;
import iuh.fit.se.L09_SpringSecurity.dto.response.ApiResponse;
import iuh.fit.se.L09_SpringSecurity.dto.response.AuthenticationResponse;
import iuh.fit.se.L09_SpringSecurity.dto.response.IntrospectResponse;
import iuh.fit.se.L09_SpringSecurity.entity.enums.ErrorCode;
import iuh.fit.se.L09_SpringSecurity.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        boolean result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .result(
                        AuthenticationResponse
                                .builder()
                                .authenticated(result)
                                .build()
                )
                .build();
    }

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticateToken(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse result = authenticationService.authenticateToken(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticateToken(@RequestBody IntrospectRequest request) {
        IntrospectResponse result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .result(result)
                .build();
    }
}
