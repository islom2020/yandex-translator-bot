import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class Service implements Constants {
    public static String getTranslate(String fromLang, String toLang, String text) {
        if (fromLang == null) fromLang = "en";
        if (toLang == null) toLang = "ru";
        if (fromLang.equals("uz") || toLang.equals("uz")) {
            return "Uzbek language is not supported. Please, choose another language.";
        }

        try {
            URL url = new URL("https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=dict.1.1.20211223T095620Z.9e509001194866a3.a560db7dc684d63123a02550e4009b17ffdce63f&lang=" + fromLang + "-" + toLang + "&text=" + text + "");
            URLConnection urlConnection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder s = new StringBuilder();
            String l;
            while ((l = reader.readLine()) != null) {
                s.append(l);
            }
            JsonElement jsonElement1 = JsonParser.parseString(s.toString());
            JsonElement jsonElement = JsonParser.parseReader(reader);
            reader.close();
            String asString = jsonElement1.getAsJsonObject().getAsJsonArray("def").get(0).getAsJsonObject().getAsJsonArray("tr").get(0).getAsJsonObject().get("text").getAsString();
            if (asString == null) {
                System.out.println("null string");
                return "word don't found";
            } else {
                return asString;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("word don't found!");
            return "Word don't found!";
        }
    }

    public static InlineKeyboardMarkup chooseLang() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButtonUz = new InlineKeyboardButton(UZ);
        inlineKeyboardButtonUz.setCallbackData("uz");
        InlineKeyboardButton inlineKeyboardButtonEn = new InlineKeyboardButton(EN);
        inlineKeyboardButtonEn.setCallbackData("en");
        InlineKeyboardButton inlineKeyboardButtonRu = new InlineKeyboardButton(RU);
        inlineKeyboardButtonRu.setCallbackData("ru");
        InlineKeyboardButton inlineKeyboardButtonTr = new InlineKeyboardButton(TR);
        inlineKeyboardButtonTr.setCallbackData("tr");

        InlineKeyboardButton inlineKeyboardButtonUz2 = new InlineKeyboardButton(UZ);
        inlineKeyboardButtonUz2.setCallbackData("UZ");
        InlineKeyboardButton inlineKeyboardButtonEn2 = new InlineKeyboardButton(EN);
        inlineKeyboardButtonEn2.setCallbackData("EN");
        InlineKeyboardButton inlineKeyboardButtonRu2 = new InlineKeyboardButton(RU);
        inlineKeyboardButtonRu2.setCallbackData("RU");
        InlineKeyboardButton inlineKeyboardButtonTr2 = new InlineKeyboardButton(TR);
        inlineKeyboardButtonTr2.setCallbackData("TR");

        inlineKeyboardMarkup.setKeyboard(List.of(
                List.of(inlineKeyboardButtonUz, inlineKeyboardButtonUz2),
                List.of(inlineKeyboardButtonRu, inlineKeyboardButtonRu2),
                List.of(inlineKeyboardButtonEn, inlineKeyboardButtonEn2),
                List.of(inlineKeyboardButtonTr, inlineKeyboardButtonTr2)
        ));
        return inlineKeyboardMarkup;
    }
}
