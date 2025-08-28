package iuh.fit.se.L09_SpringSecurity.mapper;

import iuh.fit.se.L09_SpringSecurity.dto.request.UserCreationRequest;
import iuh.fit.se.L09_SpringSecurity.dto.request.UserUpdateRequest;
import iuh.fit.se.L09_SpringSecurity.dto.response.UserResponse;
import iuh.fit.se.L09_SpringSecurity.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
//    @Mapping(source = "firstName", target = "lastName")
    @Mapping(target = "firstName", ignore = true)
    UserResponse toUserResponse(User user);
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
