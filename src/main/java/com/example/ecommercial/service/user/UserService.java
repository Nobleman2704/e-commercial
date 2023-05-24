package com.example.ecommercial.service.user;

import com.example.ecommercial.dao.UserDao;
import com.example.ecommercial.domain.entity.UserEntity;
import com.example.ecommercial.dto.request.UserCreatePostRequest;
import com.example.ecommercial.dto.responce.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    public BaseResponse create(UserCreatePostRequest userCreatePostRequest){
        UserEntity userEntity = modelMapper.map(userCreatePostRequest,UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        try {
            userDao.save(userEntity);
        }catch (Exception e){
            return BaseResponse.builder()
                    .message(userCreatePostRequest.getUsername()+" already exists")
                    .status(401)
                    .build();
        }
        return BaseResponse.builder()
                .status(200)
                .message(userCreatePostRequest.getUsername()+"successfully added")
                .build();
    }
}
