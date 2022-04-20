import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Locale;

public class MyBot extends TelegramLongPollingBot implements Constants {

    HashMap<String, String> fromLangUser = new HashMap<>();
    HashMap<String, String> toLangUser = new HashMap<>();

    @Override
    public String getBotUsername() {
        return "@islomjon_test_bot";
    }

    @Override
    public String getBotToken() {
        return "5084296930:AAFxAYs5wmuMcf02Bi_2jH2p1bDteIrTO50";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            String chatId = message.getChatId().toString();
            if (message.hasText()) {
                String text = message.getText();
                if (text.equals("/start")) {
                    fromLangUser.put(chatId, "en");
                    toLangUser.put(chatId, "ru");
                    String messageFromBot = EN + tool + RU;
                    SendMessage sendMessage = new SendMessage(chatId, messageFromBot);
                    sendMessage.setReplyMarkup(Service.chooseLang());
                    try {
                        execute(sendMessage);
                        execute(new SendMessage(chatId, "For translation, enter text in the input line"));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else {
                    String translate = Service.getTranslate(fromLangUser.get(chatId).toLowerCase(Locale.ROOT), toLangUser.get(chatId).toLowerCase(Locale.ROOT), text);
                    try {
                        execute(new SendMessage(chatId, translate));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            Message message = callbackQuery.getMessage();
            String chatId = message.getChatId().toString();
            String data = callbackQuery.getData();
            if (data.equals(data.toLowerCase())) {
                fromLangUser.replace(chatId, data);
            } else {
                toLangUser.replace(chatId, data);
            }

            String messageFromBot = Constants.getLang(fromLangUser.get(chatId)) + tool + Constants.getLang(toLangUser.get(chatId));
            EditMessageText editMessageText = editMenu(Service.chooseLang(), message, messageFromBot);
            try {
                execute(editMessageText);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private EditMessageText editMenu(InlineKeyboardMarkup i, Message message, String messageFromBot) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setText(messageFromBot);
        editMessageText.setReplyMarkup(i);
        editMessageText.setMessageId(message.getMessageId()); // asosiy
        editMessageText.setChatId(message.getChatId().toString());
        return editMessageText;
    }
}
