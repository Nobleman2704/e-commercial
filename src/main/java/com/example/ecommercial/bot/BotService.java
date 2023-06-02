package com.example.ecommercial.bot;

import com.example.ecommercial.controller.dto.response.*;
import com.example.ecommercial.domain.entity.ProductEntity;
import com.example.ecommercial.domain.enums.UserState;
import com.example.ecommercial.service.basket.BasketService;
import com.example.ecommercial.service.category.CategoryService;
import com.example.ecommercial.service.history.HistoryService;
import com.example.ecommercial.service.order.OrderService;
import com.example.ecommercial.service.product.ProductService;
import com.example.ecommercial.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BotService {

    private final UserService userService;
    private final ReplyKeyboardService keyboardService;
    private final CategoryService categoryService;
    private final ReplyKeyboardService replyKeyboardService;
    private final ProductService productService;
    private final BasketService basketService;
    private final OrderService orderService;
    private final HistoryService historyService;

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
            case "🧺 Basket" -> userState = UserState.BASKETS;
            case "📪 Orders" -> userState = UserState.ORDERS;
            case "🗒️ History" -> userState = UserState.HISTORIES;
            case "💰️ Get balance" -> userState = UserState.GET_BALANCE;
            case "💸 Add balance" -> userState = UserState.ADD_BALANCE;
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
        if (response.getStatus()!=200){
            sendMessage.setText("There is no categories");
        }else {
            List<ProductCategoryGetResponse> categories = response.getData();
            sendMessage.setReplyMarkup(replyKeyboardService
                    .parseCategoriesIntoInlineKeyboardMarkup(categories));
            sendMessage.setText("categories");
        }
        return sendMessage;
    }
    public SendMessage getBaskets(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        BaseResponse<List<BasketGetResponse>> response = basketService
                .getUserBaskets(chatId);

        if (response.getStatus()!=200){
            message.setText("Your basket is empty");
        }else {
            userService.updateState(chatId, UserState.BASKETS);
            List<BasketGetResponse> basket = response.getData();
            message.setText("basket");
            message.setReplyMarkup(replyKeyboardService
                    .parseBasketIntoInlineKeyboardMarkup(basket));
        }
        return message;
    }

    public SendMessage getHistories(Long chatId) {
        SendMessage message = new SendMessage();
        BaseResponse<List<HistoryGetResponse>> response = historyService
                .findUserHistories(chatId);
        if (response.getStatus()!=200){
            message = getMenu(chatId);
            message.setText("Your history is empty");
        }else {
            message.setChatId(chatId);
            message.setText(historyToString(response.getData()));
        }
        return message;
    }

    private String historyToString(List<HistoryGetResponse> histories) {
        StringBuilder result = new StringBuilder();
        for (HistoryGetResponse history : histories) {
            result.append(getHistoryInfo(history));
        }
        return result.toString();
    }

    private String getHistoryInfo(HistoryGetResponse history) {
        return String.format("""
                Name: %s
                Description: %s
                Type: %s
                Total price: %s
                Amount: %s
                Ordered date: %s
                *******************************************""",
                history.getName(), history.getDescription(),
                history.getCategoryName(), history.getTotalPrice(),
                history.getAmount(), history.getCreatedDate());
    }

    public EditMessageText getProductsByCategoryId(Long categoryId, Long chatId, Integer messageId) {
        EditMessageText sendMessage = new EditMessageText();
        sendMessage.setMessageId(messageId);
        sendMessage.setChatId(chatId);
        sendMessage.setText("products");
        BaseResponse<List<ProductGetResponse>> response =
                productService.getProductsByCategoryId(categoryId);
        if (response.getStatus()!=200){
            sendMessage.setText("There is no products by this category");
        }else {
            List<ProductGetResponse> products = response.getData();
            userService.updateState(chatId, UserState.PRODUCTS);
            sendMessage.setReplyMarkup(replyKeyboardService
                    .parseProductsIntoInlineKeyboardMarkup(products));
        }
        return sendMessage;
    }

    public EditMessageText getProductById(Long productId, Long chatId, Integer messageId) {
        userService.updateState(chatId, UserState.PRODUCT);
        ProductGetResponse product = productService.getById(productId).getData();
        EditMessageText sendMessage = new EditMessageText(getProductInfo(product));
        sendMessage.setChatId(chatId);
        sendMessage.setMessageId(messageId);
        sendMessage.setReplyMarkup(replyKeyboardService.createNumberButton(product.getId()));
        return sendMessage;
    }

    private String getProductInfo(ProductGetResponse product){
        return String.format("""
                Name: %s
                Description: %s
                Price: %s
                Total mount: %s
                Type: %s
                
                Press any numbers below 👇🏻""", product.getName(), product.getDescription(),
                product.getPrice(), product.getAmount(), product.getCategories().getName());
    }

    public EditMessageText addProductToBasket(String data, Long chatId, Integer messageId) {
        basketService.save(data, chatId);
        EditMessageText message = new EditMessageText("Product has been added to your basket");
        message.setChatId(chatId);
        message.setMessageId(messageId);
        return message;
    }

    public SendMessage getMenu(Long chatId) {
        SendMessage sendMessage = new SendMessage(
                chatId.toString(), "Menu");
        sendMessage.setReplyMarkup(keyboardService.mainMenu());
        userService.updateState(chatId, UserState.REGISTERED);
        return sendMessage;
    }

    public EditMessageText getBasketById(Long basketId, Long chatId, Integer messageId) {
        userService.updateState(chatId, UserState.BASKET);
        BasketGetResponse basket = basketService.getById(basketId).getData();
        EditMessageText message = new EditMessageText(getBasketInfo(basket));
        message.setChatId(chatId);
        message.setMessageId(messageId);
        message.setReplyMarkup(replyKeyboardService.getBasketInlineKeyboardMarkup(basket.getId()));
        return message;
    }

    private String getBasketInfo(BasketGetResponse basket) {
        ProductEntity product = basket.getProducts();
        return String.format("""
                Name: %s
                Description: %s
                Price: %s
                Amount: %s
                Type: %s
                Total price: %s
                
                Press any numbers below 👇🏻""", product.getName(), product.getDescription(),
                product.getPrice(), basket.getProductAmount(), product.getCategories().getName(),
                product.getPrice()*basket.getProductAmount());
    }

    public EditMessageText modifyBasket(String data, Long chatId, Integer messageId) {
        EditMessageText message = new EditMessageText();
        message.setChatId(chatId);
        String[] split = data.split(" ");
        int status = Integer.parseInt(split[0]);
        Long basketId = Long.valueOf(split[1]);
        message.setMessageId(messageId);

        if (status==0){
            basketService.delete(basketId);
            message.setText("Basket has been deleted");
        } else if (status==2) {
            BaseResponse<BasketGetResponse> response = orderService
                    .orderProduct(basketId);
            if (response.getStatus()==200){
                message.setText(response.getMessage());
            }else {
                BasketGetResponse basket = response.getData();
                String basketInfo = getBasketInfo(basket) + "  " + response.getMessage();
                message.setText(basketInfo);
                message.setReplyMarkup(replyKeyboardService
                        .getBasketInlineKeyboardMarkup(basketId));
            }
        } else {
            BaseResponse<BasketGetResponse> response = basketService
                    .modifyBasket(status, basketId);
            BasketGetResponse basket = response.getData();

            String basketInfo = getBasketInfo(basket) + "  " + response.getMessage();
            message.setText(basketInfo);
            message.setReplyMarkup(replyKeyboardService
                    .getBasketInlineKeyboardMarkup(basketId));
        }
        return message;
    }


    public SendMessage getOrders(Long chatId) {
        SendMessage message = new SendMessage();
        BaseResponse<List<OrderGetResponse>> response = orderService
                .findUserOrders(chatId);
        if (response.getStatus()!=200){
            message = getMenu(chatId);
            message.setText("Your order is empty");
        }else {
            message.setText("orders");
            message.setChatId(chatId);
            message.setReplyMarkup(replyKeyboardService
                    .parseOrdersIntoInlineKeyboardMarkup(response.getData()));
        }
        return message;
    }

    public SendMessage askUserToWriteBalance(Long chatId) {
        return new SendMessage(chatId.toString(), "Please write amount");
    }

    public SendMessage getUserBalance(Long chatId) {
        BaseResponse<Double> response = userService.getUserBalance(chatId);
        return new SendMessage(chatId.toString(), "Your balance: " + response.getData());
    }

    public SendMessage addBalance(String text, Long chatId) {
        BaseResponse response = userService.addBalance(text, chatId);
        userService.updateState(chatId, UserState.IDLE);
        return new SendMessage(chatId.toString(), response.getMessage());
    }

    public EditMessageText deleteUserOrder(Long orderId, Long chatId, Integer messageId) {
        orderService.delete(orderId);
        EditMessageText message = new EditMessageText("Order has been deleted");
        message.setMessageId(messageId);
        message.setChatId(chatId);
        return message;
    }
}
