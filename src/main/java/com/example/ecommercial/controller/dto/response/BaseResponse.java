package com.example.ecommercial.controller.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponse<T> {

    private int status;

    private String message;
    private String messageToUser;
    private long chatId;

    private T data;
    private int totalPageAmount;
}
