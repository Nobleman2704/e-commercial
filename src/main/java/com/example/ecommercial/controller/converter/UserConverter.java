package com.example.ecommercial.controller.converter;

import com.example.ecommercial.controller.dto.request.UserCreateAndUpdateRequest;
import com.example.ecommercial.controller.dto.response.UserGetResponse;
import com.example.ecommercial.domain.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserConverter {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserEntity toUserEntity(UserCreateAndUpdateRequest userCreateAndUpdateRequest){
        UserEntity user = modelMapper.map(userCreateAndUpdateRequest, UserEntity.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserAuthorities(userCreateAndUpdateRequest.getUserAuthorities());
        return user;
    }

    public UserGetResponse toUserGetDto(UserEntity userEntity){
        return modelMapper.map(userEntity, UserGetResponse.class);
    }


    public List<UserGetResponse> toUserGetDto(  List<UserEntity> userEntities) {
        return modelMapper.map(userEntities,
                new TypeToken<List<UserGetResponse>>() {
                }.getType());
    }
}
