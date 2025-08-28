package iuh.fit.se.tutorial.exception;

import iuh.fit.se.tutorial.dto.response.ApiResponse;
import iuh.fit.se.tutorial.entity.enums.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingException(RuntimeException exception){
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.UNCASED.getCode());
        apiResponse.setMessage(ErrorCode.UNCASED.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

//    @ExceptionHandler(value = ResponseStatusException.class)
//    ResponseEntity<String> handlingReponseStatusException(RuntimeException exception){
//        return ResponseEntity.badRequest().body(exception.getMessage());
//    }

//    @ExceptionHandler(value = RuntimeException.class)
//    ResponseEntity<String> handlingRunTimeException(RuntimeException exception){
//        return ResponseEntity.badRequest().body(exception.getMessage());
//    }

//    @ExceptionHandler(value = RuntimeException.class)
//    ResponseEntity<ApiResponse> handlingRunTimeException(RuntimeException exception){
//        ApiResponse apiResponse = new ApiResponse();
//        apiResponse.setCode(1000);
//        apiResponse.setMessage(exception.getMessage());
//
//        return ResponseEntity.badRequest().body(apiResponse);
//    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception){
        ErrorCode errorCode = exception.getErrorCode();

        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(exception.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    ResponseEntity<String> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception){
//        return ResponseEntity.badRequest().body(exception.getFieldError().getDefaultMessage());
//    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        String enumKey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(enumKey);
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }
}
