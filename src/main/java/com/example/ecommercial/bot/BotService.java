package com.example.ecommercial.bot;

import com.example.ecommercial.domain.dto.response.BaseResponse;
import com.example.ecommercial.domain.dto.response.ProductCategoryGetResponse;
import com.example.ecommercial.domain.dto.response.ProductGetResponse;
import com.example.ecommercial.domain.enums.UserState;
import com.example.ecommercial.service.category.CategoryService;
import com.example.ecommercial.service.product.ProductService;
import com.example.ecommercial.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BotService {

    private final UserService userService;
    private final ReplyKeyboardService keyboardService;
    private final CategoryService categoryService;
    private final ReplyKeyboardService replyKeyboardService;
    private final ProductService productService;

    public UserState checkState(Long chatId) {
        BaseResponse<UserState> response = userService.getUserState(chatId);
        if (response.getStatus() == 200) {
            return response.getData();
        }
        return UserState.NEW;
    }

    public SendMessage shareContact(Long chatId) {
        SendMessage sendMessage = new SendMessage(chatId.toString(), "Please register yourself");
        sendMessage.setReplyMarkup(keyboardService.requestContact());
        return sendMessage;
    }

    public SendMessage registerUser(Long chatId, User user) {
        userService.saveBotUser(chatId, user);
        SendMessage sendMessage = new SendMessage(
                chatId.toString(), user.getFirstName() + " has been saved");
        sendMessage.setReplyMarkup(keyboardService.mainMenu());
        return sendMessage;
    }


    public UserState navigateMenu(String request, Long chatId) {
        UserState userState;
        switch (request) {
            case "📋 Categories" -> userState = UserState.CATEGORIES;
            case "🧺 Basket" -> userState = UserState.BASKET_LIST;
            case "🗒️ Orders history" -> userState = UserState.ORDERS_HISTORY;
//            case "💰️ Get balance" -> userState = UserState.GET_BALANCE;
//            case "💸 Add balance" -> userState = UserState.ADD_BALANCE;
            default -> userState = UserState.IDLE;
        }
        userService.updateState(chatId, userState);
        return userState;
    }

    public SendMessage getCategories(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        BaseResponse<List<ProductCategoryGetResponse>> response =
                categoryService.getALl();
        List<ProductCategoryGetResponse> categories = response.getData();
        if (categories.isEmpty()){
            sendMessage.setText("There is no categories");
        }else {
            sendMessage.setReplyMarkup(replyKeyboardService
                    .parseCategoriesIntoInlineKeyboardMarkup(categories));
            sendMessage.setText("categories");
        }
        return sendMessage;
    }

    public SendMessage getBaskets(Long chatId) {
        return null;
    }

    public SendMessage getHistories(Long chatId) {
        return null;
    }

    public SendMessage getProductsByCategoryId(Long categoryId, Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        BaseResponse<List<ProductGetResponse>> response =
                productService.getProductsByCategoryId(categoryId);
        List<ProductGetResponse> products = response.getData();
        if (products.isEmpty()){
            sendMessage.setText("There is no products by this category");
        }else {
            sendMessage.setReplyMarkup(replyKeyboardService
                    .parseProductsIntoInlineKeyboardMarkup(products));
        }
        return sendMessage;
    }

    public SendMessage getMenu(Long chatId) {
        SendMessage sendMessage = new SendMessage(
                chatId.toString(), "Menu");
        sendMessage.setReplyMarkup(keyboardService.mainMenu());
        userService.updateState(chatId, UserState.REGISTERED);
        return sendMessage;
    }
}
