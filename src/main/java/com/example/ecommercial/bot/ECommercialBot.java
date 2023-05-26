package com.example.ecommercial.bot;

import com.example.ecommercial.domain.enums.UserState;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class ECommercialBot extends TelegramLongPollingBot {
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final BotService botService;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        executorService.execute(() -> {
            if (update.hasCallbackQuery()){
                CallbackQuery callbackQuery = update.getCallbackQuery();
                Message message = callbackQuery.getMessage();
                Long chatId = message.getChatId();
                String data = callbackQuery.getData();

                UserState userState = botService.checkState(chatId);

                SendMessage sendMessage = null;

                switch (userState){
                    case CATEGORIES -> sendMessage = botService.getProductsByCategoryId(Long.valueOf(data), chatId);
                }

                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }

            }
            else{
                Message message = update.getMessage();
                String text = message.getText();
                Long chatId = message.getChatId();

//                SendMessage sendMessage = new SendMessage(chatId.toString(), text);

                UserState userState = botService.checkState(chatId);


                SendMessage sendMessage = null;

                switch (userState){
                    case NEW -> {
                        if (message.hasContact())
                            sendMessage = botService.registerUser(chatId, message.getFrom());
                        else
                            sendMessage = botService.shareContact(chatId);
                    }
                    case REGISTERED -> {
                        userState = botService.navigateMenu(text, chatId);
                        switch (userState){
                            case CATEGORIES -> sendMessage = botService.getCategories(chatId);
                            case BASKET_LIST -> sendMessage = botService.getBaskets(chatId);
                            case ORDERS_HISTORY -> sendMessage = botService.getHistories(chatId);
                        }
                    }
                }
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public String getBotToken() {
        return "6275294044:AAEzt2h7TZe9D5MEYfia-PsbrXis-ZnbaHY";
    }

    @Override
    public String getBotUsername() {
        return "https://t.me/e_commercial_bot";
    }
}
