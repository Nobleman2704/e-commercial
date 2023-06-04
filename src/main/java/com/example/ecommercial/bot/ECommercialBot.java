package com.example.ecommercial.bot;

import com.example.ecommercial.domain.enums.UserState;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
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
            if (update.hasCallbackQuery()) {
                CallbackQuery callbackQuery = update.getCallbackQuery();
                Message message = callbackQuery.getMessage();
                Integer messageId = message.getMessageId();
                Long chatId = message.getChatId();
                String data = callbackQuery.getData();


                UserState userState = botService.checkState(chatId);

                EditMessageText editMessageText = null;

                switch (userState) {
                    case CATEGORIES -> editMessageText = botService
                            .getProductsByCategoryId(Long.valueOf(data), chatId, messageId);
                    case PRODUCTS -> editMessageText = botService
                            .getProductById(Long.valueOf(data), chatId, messageId);
                    case PRODUCT -> editMessageText = botService
                            .addProductToBasket(data, chatId, messageId);
                    case BASKETS -> editMessageText = botService
                            .getBasketById(Long.parseLong(data), chatId, messageId);
                    case BASKET -> editMessageText = botService
                            .modifyBasket(data, chatId, messageId);
                    case ORDERS -> editMessageText = botService
                            .deleteUserOrder(Long.valueOf(data), chatId, messageId);
                }

                try {
                    execute(editMessageText);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }

            } else {
                Message message = update.getMessage();
                String text = message.getText();
                Long chatId = message.getChatId();

                UserState userState = botService.checkState(chatId);


                SendMessage sendMessage = null;

                switch (userState) {
                    case NEW -> {
                        if (message.hasContact())
                            sendMessage = botService.registerUser(chatId, message.getFrom());
                        else
                            sendMessage = botService.shareContact(chatId);
                    }
                    case REGISTERED, IDLE, CATEGORIES, PRODUCTS, PRODUCT, BASKETS, ORDERS, BASKET,
                            GET_BALANCE, HISTORIES -> {
                        userState = botService.navigateMenu(text, chatId);
                        switch (userState) {
                            case CATEGORIES -> sendMessage = botService.getCategories(chatId);
                            case BASKETS -> sendMessage = botService.getBaskets(chatId);
                            case HISTORIES -> sendMessage = botService.getHistories(chatId);
                            case ORDERS -> sendMessage = botService.getOrders(chatId);
                            case GET_BALANCE -> sendMessage = botService.getUserBalance(chatId);
                            case ADD_BALANCE -> sendMessage = botService.askUserToWriteBalance(chatId);
                            case IDLE -> sendMessage = botService.getMenu(chatId);
                        }
                    }
                    case ADD_BALANCE -> sendMessage = botService.addBalance(text, chatId);
                }

                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void sendMessageToUser(String message, Long chatId){
        SendMessage sendMessage = new SendMessage(chatId.toString(), message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
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
