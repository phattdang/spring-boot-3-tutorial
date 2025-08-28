package iuh.fit.se.L09_SpringSecurity.configuration;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity // optional : có hoặc không, vì khi add dependency nó đã tự động enable rồi
public class SecurityConfig {
    private static final String SECRET_KEY = Dotenv.load().get("APP_SECRET_KEY");
    private final String[] PUBLIC_ENDPOINT = {
            "/users",
            "/auth/token",
            "/auth/introspect"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request -> {
            request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINT).permitAll()// chấp nhập các endpoint
                    .anyRequest().authenticated(); // các endpoint còn lại phải xác thực
        });

        // đăng ký provider hỗ trợ cho request token
        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())));

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        // Tắt hoàn toàn CSRF. Lý do: Với API dùng token JWT (stateless),
        // không cần CSRF vì token đã bảo vệ
        // Nếu không tắt, các request POST/PUT có thể bị chặn nếu thiếu token CSRF.

        return httpSecurity.build();
    }

    @Bean
    JwtDecoder jwtDecoder(){
        SecretKeySpec spec = new SecretKeySpec(SECRET_KEY.getBytes(), "HS512");
        return NimbusJwtDecoder
                .withSecretKey(spec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }
}