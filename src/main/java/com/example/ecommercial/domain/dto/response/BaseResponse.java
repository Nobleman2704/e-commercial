package com.example.ecommercial.domain.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponse<T> {

    private int status;

    private String message;

    private T data;
}
