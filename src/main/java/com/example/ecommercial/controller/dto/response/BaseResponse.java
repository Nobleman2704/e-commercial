package com.example.ecommercial.controller.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponse<T> {

    private int status;

    @Override
    public String toString() {
        return "BaseResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }

    private String message;
    private String messageToUser;
    private long chatId;

    private T data;
    private int totalPageAmount;

    public static <T> BaseResponse<T> of(String message, int status, T data){
        return BaseResponse.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }

    public static BaseResponse of(String message, int status){
        return BaseResponse.builder()
                .message(message)
                .status(status)
                .build();
    }

    public static<T> BaseResponse<T> of(String message, int status, T data,
                                        int totalPageAmount){
        return BaseResponse.<T>builder()
                .message(message)
                .status(status)
                .data(data)
                .totalPageAmount(totalPageAmount)
                .build();
    }
}
