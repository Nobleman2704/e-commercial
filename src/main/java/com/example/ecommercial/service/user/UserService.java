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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        return BaseResponse.of(message, status);
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
            message = "updated";
            status = 200;
        } catch (Exception e) {
            message = userEntity.getUsername() + " already exists";
            status = 401;
        }
        return BaseResponse.of(message, status);
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
        Optional<UserEntity> optionalUserEntity = userDao.findById(id);
        if (optionalUserEntity.isEmpty()){
            return BaseResponse.of("empty", 404);
        }
        return BaseResponse.of(
                        "success",
                        200,
                        userConverter.toUserGetDto(optionalUserEntity.get()));
    }

    @Override
    public BaseResponse<List<UserGetResponse>> getALl(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 5);
        Page<UserEntity> userEntityPage = userDao
                .findUserEntitiesByChatIdIsNull(pageable);
        int totalPages = userEntityPage.getTotalPages();

        return BaseResponse.of(
                "success",
                200,
                userConverter.toUserGetDto(userEntityPage.getContent()),
                (totalPages == 0) ? 0 : totalPages - 1);
    }


    public BaseResponse<List<UserGetResponse>> getAllBotUsers(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 5);

        Page<UserEntity> optionalBotUsers = userDao
                .findUserEntitiesByChatIdIsNotNull(pageable);
        int totalPages = optionalBotUsers.getTotalPages();
        return BaseResponse.of(
                "success",
                200,
                userConverter.toUserGetDto(optionalBotUsers.getContent()),
                (totalPages == 0) ? 0 : totalPages - 1);
    }

    public BaseResponse<UserState> getUserState(Long chatId) {
        Optional<UserEntity> optionalUser = userDao.findUserEntitiesByChatId(chatId);
        if (optionalUser.isPresent()) {
            return BaseResponse.of(
                    "success",
                    200,
                    optionalUser.get().getUserState());
        }
        return BaseResponse.of("not found", 404);
    }

    public void saveBotUser(Long chatId, User user) {
        UserEntity userEntity = UserEntity.builder()
                .name(user.getFirstName())
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
        return BaseResponse.of(
                "success",
                200,
                userEntity.getBalance());
    }

    public BaseResponse addBalance(String text, Long chatId) {
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
        return BaseResponse.of(message, status);
    }
}
