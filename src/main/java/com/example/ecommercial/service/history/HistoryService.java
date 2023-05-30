package com.example.ecommercial.service.history;

import com.example.ecommercial.dao.HistoryDao;
import com.example.ecommercial.controller.dto.response.BaseResponse;
import com.example.ecommercial.controller.dto.response.HistoryGetResponse;
import com.example.ecommercial.domain.entity.HistoryEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService{
    private final HistoryDao historyDao;
    private final ModelMapper modelMapper;

    public BaseResponse save(HistoryEntity history) {
        historyDao.save(history);
        return BaseResponse.builder()
                .message("deleted")
                .build();
    }


    public BaseResponse<List<HistoryGetResponse>> getALl() {
        return BaseResponse.<List<HistoryGetResponse>>builder()
                .data(modelMapper
                        .map(historyDao.findAll(), new TypeToken<List<HistoryGetResponse>>() {
                        }
                                .getType()))
                .build();
    }

    public BaseResponse<List<HistoryGetResponse>> findUserHistories(Long chatId) {
        List<HistoryEntity> histories = historyDao
                .findHistoryEntitiesByUsersChatId(chatId).get();
        if (histories.isEmpty()) {
            return BaseResponse.<List<HistoryGetResponse>>builder()
                    .status(404)
                    .build();
        }
        return BaseResponse.<List<HistoryGetResponse>>builder()
                .status(200)
                .data(modelMapper
                        .map(histories, new TypeToken<List<HistoryGetResponse>>() {}
                                .getType()))
                .build();
    }
}
