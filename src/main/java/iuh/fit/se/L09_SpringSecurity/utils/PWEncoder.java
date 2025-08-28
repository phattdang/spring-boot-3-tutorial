package iuh.fit.se.L09_SpringSecurity.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PWEncoder {
    public static PasswordEncoder get(){
        return new BCryptPasswordEncoder();
    }
}
