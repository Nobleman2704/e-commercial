package com.example.ecommercial.controller;

import com.example.ecommercial.bot.BotService;
import com.example.ecommercial.bot.ECommercialBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class StartBotController {
    private final ECommercialBot eCommercialBot;

//    @GetMapping
//    public String startBot(){
//        try {
//            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
//            botsApi.registerBot(eCommercialBot);
//            System.out.println("started");
//        } catch (TelegramApiException e) {
//            throw new RuntimeException(e);
//        }
//        return "home";
//    }


}
