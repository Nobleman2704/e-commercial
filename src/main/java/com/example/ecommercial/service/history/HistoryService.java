package com.example.ecommercial.service.history;

import com.example.ecommercial.dao.HistoryDao;
import com.example.ecommercial.dao.UserDao;
import com.example.ecommercial.domain.dto.response.BaseResponse;
import com.example.ecommercial.domain.dto.response.HistoryGetResponse;
import com.example.ecommercial.domain.entity.HistoryEntity;
import com.example.ecommercial.domain.entity.UserEntity;
import com.example.ecommercial.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService implements BaseService<HistoryEntity, BaseResponse> {
    private final HistoryDao historyDao;
    private final ModelMapper modelMapper;
    private final UserDao userDao;

    @Override
    public BaseResponse save(HistoryEntity request) {
        return null;
    }

    @Override
    public BaseResponse update(HistoryEntity update) {
        return null;
    }

    @Override
    public BaseResponse delete(Long id) {
        return null;
    }

    @Override
    public BaseResponse getById(Long id) {
        return null;
    }

    @Override
    public BaseResponse<List<HistoryGetResponse>> getALl() {
        return BaseResponse.<List<HistoryGetResponse>>builder()
                .data(modelMapper
                        .map(historyDao.findAll(), new TypeToken<List<HistoryGetResponse>>() {
                        }
                                .getType()))
                .build();
    }

    public BaseResponse<List<HistoryGetResponse>> findUserHistories(Long chatId) {
        UserEntity userEntity = userDao.findUserEntitiesByChatId(chatId).get();
        List<HistoryEntity> histories = userEntity.getHistoryEntities();
        if (histories.isEmpty()) {
            return BaseResponse.<List<HistoryGetResponse>>builder()
                    .status(404)
                    .build();
        }
        return BaseResponse.<List<HistoryGetResponse>>builder()
                .status(200)
                .data(modelMapper
                        .map(histories, new TypeToken<List<HistoryGetResponse>>() {
                        }.getType()))
                .build();
    }
}
