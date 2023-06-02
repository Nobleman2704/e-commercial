package com.example.ecommercial.controller.converter;

import com.example.ecommercial.controller.dto.response.HistoryGetResponse;
import com.example.ecommercial.domain.entity.HistoryEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class HistoryConverter {

    private final ModelMapper modelMapper;

    public List<HistoryGetResponse> toHistoryGetDto(List<HistoryEntity> histories) {
        return modelMapper.map(
                histories, new TypeToken<List<HistoryGetResponse>>(){}.getType());
    }
}
