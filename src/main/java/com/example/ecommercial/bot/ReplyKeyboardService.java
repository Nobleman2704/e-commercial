package com.example.ecommercial.bot;

import com.example.ecommercial.controller.dto.response.BasketGetResponse;
import com.example.ecommercial.controller.dto.response.OrderGetResponse;
import com.example.ecommercial.controller.dto.response.ProductCategoryGetResponse;
import com.example.ecommercial.controller.dto.response.ProductGetResponse;
import com.example.ecommercial.domain.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyKeyboardService {

    public ReplyKeyboard requestContact() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardButton button = new KeyboardButton("Please share phone number 📞");
        button.setRequestContact(true);
        keyboardRow.add(button);
        markup.setResizeKeyboard(true);
        markup.setKeyboard(List.of(keyboardRow));
        return markup;
    }

    public ReplyKeyboardMarkup mainMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("📋 Categories");
        row.add("🧺 Basket");
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add("📪 Orders");
        row.add("🗒️ History");
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add("💰️ Get balance");
        row.add("💸 Add balance");
        keyboardRows.add(row);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboard parseCategoriesIntoInlineKeyboardMarkup(
            List<ProductCategoryGetResponse> categories) {
        List<List<InlineKeyboardButton>> buttons = new LinkedList<>();
        for (ProductCategoryGetResponse category : categories) {
            buttons.add(createCategoryButton(category));
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }

    private List<InlineKeyboardButton> createCategoryButton(
            ProductCategoryGetResponse category) {
        InlineKeyboardButton button = new InlineKeyboardButton(category.getName());
        button.setCallbackData(category.getId().toString());
        return List.of(button);
    }

    public InlineKeyboardMarkup parseProductsIntoInlineKeyboardMarkup(
            List<ProductGetResponse> products) {
        List<List<InlineKeyboardButton>> buttons = new LinkedList<>();

        for (ProductGetResponse product : products) {
            buttons.add(createProductButton(product));
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }

    private List<InlineKeyboardButton> createProductButton(ProductGetResponse product) {
        InlineKeyboardButton button = new InlineKeyboardButton(product.getName());
        button.setCallbackData(product.getId().toString());
        return List.of(button);
    }

    public InlineKeyboardMarkup createNumberButton(Long productId) {
        List<List<InlineKeyboardButton>> buttons = new LinkedList<>();
        List<InlineKeyboardButton> rows = new LinkedList<>();
        InlineKeyboardButton button = new InlineKeyboardButton("1️⃣");
        button.setCallbackData("1 " + productId);
        rows.add(button);
        button = new InlineKeyboardButton("2️⃣");
        button.setCallbackData("2 " + productId);
        rows.add(button);
        button = new InlineKeyboardButton("3️⃣");
        button.setCallbackData("3 " + productId);
        rows.add(button);
        buttons.add(rows);
        rows = new LinkedList<>();
        button = new InlineKeyboardButton("4️⃣");
        button.setCallbackData("4 " + productId);
        rows.add(button);
        button = new InlineKeyboardButton("5️⃣");
        button.setCallbackData("5 " + productId);
        rows.add(button);
        button = new InlineKeyboardButton("6️⃣");
        button.setCallbackData("6 " + productId);
        rows.add(button);
        buttons.add(rows);
        rows = new LinkedList<>();
        button = new InlineKeyboardButton("7️⃣");
        button.setCallbackData("7 " + productId);
        rows.add(button);
        button = new InlineKeyboardButton("8️⃣");
        button.setCallbackData("8 " + productId);
        rows.add(button);
        button = new InlineKeyboardButton("9️⃣");
        button.setCallbackData("9 " + productId);
        rows.add(button);
        buttons.add(rows);
        InlineKeyboardMarkup inline = new InlineKeyboardMarkup();
        inline.setKeyboard(buttons);
        return inline;
    }

    public ReplyKeyboard parseBasketIntoInlineKeyboardMarkup(List<BasketGetResponse> baskets) {
        List<List<InlineKeyboardButton>> buttons = new LinkedList<>();

        for (BasketGetResponse basket : baskets) {
            buttons.add(createBasketButton(basket));
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }

    private List<InlineKeyboardButton> createBasketButton(BasketGetResponse basket) {
            InlineKeyboardButton button = new InlineKeyboardButton(basket.
                    getProducts().getName());
            button.setCallbackData(basket.getId().toString());
            return List.of(button);
    }

    public InlineKeyboardMarkup getBasketInlineKeyboardMarkup(Long basketId) {
        List<List<InlineKeyboardButton>> buttons = new LinkedList<>();
        List<InlineKeyboardButton> rows = new LinkedList<>();
        InlineKeyboardButton button = new InlineKeyboardButton("➕");
        button.setCallbackData("1 " + basketId);
        rows.add(button);

        button = new InlineKeyboardButton("➖");
        button.setCallbackData("-1 " + basketId);
        rows.add(button);

        buttons.add(rows);

        rows = new LinkedList<>();
        button = new InlineKeyboardButton("❌ Remove");
        button.setCallbackData("0 " + basketId);
        rows.add(button);

        button = new InlineKeyboardButton("🚢 Order");
        button.setCallbackData("2 " + basketId);
        rows.add(button);

        buttons.add(rows);
        InlineKeyboardMarkup inline = new InlineKeyboardMarkup();
        inline.setKeyboard(buttons);
        return inline;
    }

    public ReplyKeyboard parseOrdersIntoInlineKeyboardMarkup(List<OrderGetResponse> orders) {
        List<List<InlineKeyboardButton>> buttons = new LinkedList<>();
        for (OrderGetResponse order : orders) {
            buttons.add(createOrderButton(order));
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }

    private List<InlineKeyboardButton> createOrderButton(OrderGetResponse order) {
        InlineKeyboardButton button = new InlineKeyboardButton(getOrderInfo(order));
        button.setCallbackData(order.getId().toString());
        return List.of(button);
    }

    private String getOrderInfo(OrderGetResponse order) {
        ProductEntity product = order.getProducts();
        return String.format("""
                Name: %s
                Description: %s
                Type: %s
                Total price: %s
                Amount: %s
                
                PRESS THIS IF YOU WANT TO CANCEL ORDER
                """, product.getName(), product.getDescription(), order.getTotalPrice(),
                product.getCategories().getName(), order.getAmount());
    }
}
