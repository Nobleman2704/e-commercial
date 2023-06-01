package com.example.ecommercial.service;

import com.example.ecommercial.bot.ECommercialBot;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendMessage {
    private final ECommercialBot eCommercialBot;

    public void sendMessage(String message, Long chatId){
        eCommercialBot.sendMessageToUser(message, chatId);
    }

}
