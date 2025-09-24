package com.web.mapper;

import com.web.dto.request.UserCreationRequest;
import com.web.dto.request.UserUpdateRequest;
import com.web.dto.response.UserResponse;
import com.web.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "0")
    @Mapping(target = "total_score", constant = "0")
    @Mapping(target = "total_quit_game", constant = "0")
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}