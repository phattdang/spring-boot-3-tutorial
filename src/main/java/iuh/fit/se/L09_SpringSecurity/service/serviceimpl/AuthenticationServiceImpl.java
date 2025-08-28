package iuh.fit.se.L09_SpringSecurity.service.serviceimpl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.github.cdimascio.dotenv.Dotenv;
import iuh.fit.se.L09_SpringSecurity.dto.request.AuthenticationRequest;
import iuh.fit.se.L09_SpringSecurity.dto.request.IntrospectRequest;
import iuh.fit.se.L09_SpringSecurity.dto.response.AuthenticationResponse;
import iuh.fit.se.L09_SpringSecurity.dto.response.IntrospectResponse;
import iuh.fit.se.L09_SpringSecurity.entity.User;
import iuh.fit.se.L09_SpringSecurity.entity.enums.ErrorCode;
import iuh.fit.se.L09_SpringSecurity.exception.AppException;
import iuh.fit.se.L09_SpringSecurity.repository.UserRepository;
import iuh.fit.se.L09_SpringSecurity.service.AuthenticationService;
import iuh.fit.se.L09_SpringSecurity.utils.PWEncoder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class AuthenticationServiceImpl implements AuthenticationService {
    UserRepository userRepository;

    @NonFinal
    private static final String SECRET_KEY = Dotenv.load().get("APP_SECRET_KEY");

    @Override
    public boolean authenticate(AuthenticationRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return PWEncoder.get().matches(request.getPassword(), user.getPassword());
    }

    @Override
    public AuthenticationResponse authenticateToken(AuthenticationRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        boolean authenticated = PWEncoder.get().matches(request.getPassword(), user.getPassword());
        if (!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        String token = generateToken(request.getUsername());

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) {
        String token = request.getToken();

        try {
            JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            boolean verified = signedJWT.verify(verifier);
            return IntrospectResponse.builder()
                    .valid(verified && expiryTime.after(new Date()))
                    .build();
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateToken(String username) {
        // chua noi dung cua thuat toan
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        // body token - noi dung gui di cua token
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username) // co the la username, userid- quan trong
                .issuer("phatdang") // dinh danh token nay do ai isser - thuong la tne domain
                .issueTime(new Date()) // thoi gian tao
                .expirationTime(new Date( // thoi gian het han token
                        Instant.now()
                                .plus(1, ChronoUnit.HOURS)
                                .toEpochMilli())
                )
                .claim("customClaim", "Custom")
                .build();

        // json web signature
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        // tao json web token - JWT
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            // ky token
            jwsObject.sign(new MACSigner(SECRET_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
}
