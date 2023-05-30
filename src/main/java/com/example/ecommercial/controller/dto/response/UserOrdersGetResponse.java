package com.example.ecommercial.controller.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrdersGetResponse {
    private String username;
    private List<OrderGetResponse> orders;
    private double totalSum;
}
