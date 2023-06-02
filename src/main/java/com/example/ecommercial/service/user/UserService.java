package com.example.ecommercial.service.user;

import com.example.ecommercial.controller.converter.UserConverter;
import com.example.ecommercial.controller.dto.request.UserCreateAndUpdateRequest;
import com.example.ecommercial.dao.UserDao;
import com.example.ecommercial.controller.dto.response.BaseResponse;
import com.example.ecommercial.controller.dto.response.UserGetResponse;
import com.example.ecommercial.domain.entity.UserEntity;
import com.example.ecommercial.domain.enums.UserRole;
import com.example.ecommercial.domain.enums.UserState;
import com.example.ecommercial.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements BaseService<
        UserCreateAndUpdateRequest, BaseResponse> {

    private final UserDao userDao;
    private final ModelMapper modelMapper;
    private final UserConverter userConverter;

    @Override
    public BaseResponse save(UserCreateAndUpdateRequest userCreateAndUpdateRequest) {
        UserEntity userEntity = userConverter.toUserEntity(userCreateAndUpdateRequest);
        String message;
        int status;
        try {
            userDao.save(userEntity);
            message = "saved";
            status = 200;
        } catch (Exception e) {
            message = userEntity.getUsername() + " already exists";
            status = 401;
        }
        return BaseResponse.builder()
                .status(status)
                .message(message)
                .build();
    }

    @Override
    public BaseResponse update(UserCreateAndUpdateRequest userCreateAndUpdateRequest) {
        UserEntity userEntity = userConverter.toUserEntity(userCreateAndUpdateRequest);

        Long userId = userEntity.getId();
        UserEntity userEntity1 = userDao.findById(userId).get();
        modelMapper.map(userEntity, userEntity1);

        String message;
        int status;
        try {
            userDao.save(userEntity1);
            message = "success";
            status = 200;
        } catch (Exception e) {
            message = userEntity.getUsername() + " already exists";
            status = 401;
        }
        return BaseResponse.builder()
                .status(status)
                .message(message)
                .build();
    }

    @Override
    public BaseResponse<List<UserGetResponse>> delete(Long id) {
        userDao.deleteById(id);
        BaseResponse<List<UserGetResponse>> response = getALl(0);
        response.setMessage("deleted");
        return response;
    }

    @Override
    public BaseResponse<UserGetResponse> getById(Long id) {
        UserEntity userEntity = userDao.findById(id).get();
        return BaseResponse.<UserGetResponse>builder()
                .message("success")
                .status(200)
                .data(userConverter.toUserGetDto(userEntity))
                .build();
    }

    @Override
    public BaseResponse<List<UserGetResponse>> getALl(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 5);
        Page<UserEntity> userEntityPage = userDao
                .findUserEntitiesByChatIdIsNull(pageable);
        int totalPages = userEntityPage.getTotalPages();

        return BaseResponse.<List<UserGetResponse>>builder()
                .totalPageAmount(totalPages)
                .status(200)
                .message("success")
                .totalPageAmount((totalPages == 0) ? 0 : totalPages - 1)
                .data(userConverter
                        .toUserGetDto(userEntityPage.getContent()))
                .build();
    }


    public BaseResponse<List<UserGetResponse>> getAllBotUsers() {
        List<UserEntity> botUsers = userDao.findAll()
                .stream()
                .filter(userEntity -> userEntity.getUserRoles().contains(UserRole.USER))
                .toList();
        return BaseResponse.<List<UserGetResponse>>builder()
                .status(200)
                .message("success")
                .data(
                        modelMapper.map(botUsers,
                                new TypeToken<List<UserGetResponse>>() {
                                }.getType())
                )
                .build();
    }

    public BaseResponse<UserState> getUserState(Long chatId) {
        Optional<UserEntity> optionalUser = userDao.findUserEntitiesByChatId(chatId);
        if (optionalUser.isPresent()) {
            return BaseResponse.<UserState>builder()
                    .data(optionalUser.get().getUserState())
                    .status(200)
                    .build();
        }
        return BaseResponse.<UserState>builder()
                .status(404)
                .build();
    }

    public void saveBotUser(Long chatId, User user) {
        UserEntity userEntity = UserEntity.builder()
                .name(user.getUserName())
                .username(user.getUserName())
                .userState(UserState.REGISTERED)
                .userRoles(List.of(UserRole.USER))
                .chatId(chatId)
                .build();
        userDao.save(userEntity);
    }

    public void updateState(Long chatId, UserState userState) {
        UserEntity userEntity = userDao.findUserEntitiesByChatId(chatId).get();
        userEntity.setUserState(userState);
        userDao.save(userEntity);
    }

    public BaseResponse<Double> getUserBalance(Long chatId) {
        UserEntity userEntity = userDao.findUserEntitiesByChatId(chatId).get();
        return BaseResponse.<Double>builder()
                .data(userEntity.getBalance())
                .build();
    }

    public BaseResponse<Double> addBalance(String text, Long chatId) {
        String message;
        int status;
        try {
            double balance = Double.parseDouble(text);
            if (balance <= 0) {
                status = 401;
                message = "Amount should be positive";
            } else {
                UserEntity userEntity = userDao.findUserEntitiesByChatId(chatId).get();
                userEntity.setBalance(userEntity.getBalance() + balance);
                userDao.save(userEntity);
                message = "Balance has been changed: " + userEntity.getBalance();
                status = 200;
            }
        } catch (NumberFormatException e) {
            status = 401;
            message = "Please only write number";
        }
        return BaseResponse.<Double>builder()
                .status(status)
                .message(message)
                .build();
    }
}
