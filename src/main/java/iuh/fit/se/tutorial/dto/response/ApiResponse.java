package iuh.fit.se.tutorial.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@JsonInclude(JsonInclude.Include.NON_NULL)

public class ApiResponse<T>{
    private int code = 1000;
    private String message;
    private T result;
}
