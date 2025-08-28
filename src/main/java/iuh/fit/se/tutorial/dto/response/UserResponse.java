package iuh.fit.se.tutorial.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserResponse {
    String id;
    String username;
    String password;
    String firstName;
    String lastName;
    LocalDate dob;
}
