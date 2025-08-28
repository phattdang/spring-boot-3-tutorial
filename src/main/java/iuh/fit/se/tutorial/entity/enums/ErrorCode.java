package iuh.fit.se.tutorial.entity.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SUCCESS(1000, "Successfull!"),
    USER_EXISTED(1002, "User existed"),
    USER_NOT_EXISTED(1003, "User is not existed"),
    UNCASED(9999, "Uncase error!"),
    USERNAME_INVALID(100, "User name must be at least 3 chars!"),
    PASSWORD_INVALID(200, "Password must be at least 8 characters!!!"),
    UNAUTHENTICATED(300, "Unauthenticated!!!");
    private final int code;
    private final String message;

    ErrorCode(int code, String message){
        this.code = code;
        this.message = message;
    }

}
