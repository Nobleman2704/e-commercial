package com.example.ecommercial.bot;

import com.example.ecommercial.domain.dto.response.ProductCategoryGetResponse;
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

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("📋 Categories");
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add("🧺 Basket");
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add("🗒️ Orders history");
        keyboardRows.add(row);
//
//        row = new KeyboardRow();
//        row.add("💰️ Get balance");
//        keyboardRows.add(row);
//
//        row = new KeyboardRow();
//        row.add("💸 Add balance");
//        keyboardRows.add(row);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public InlineKeyboardMarkup parseIntoInlineKeyboardMarkup(
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
}
