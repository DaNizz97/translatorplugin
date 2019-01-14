package com.danizz.translator.impl;

import com.danizz.parser.NotationParser;
import com.danizz.parser.impl.NotationParserImpl;
import com.danizz.translator.Translator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.Scanner;

public class SimpleYandexTranslator implements Translator {

    private final NotationParser parser;
    private final String STRING_TRNSLATE_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate?" +
            "key=trnsl.1.1.20190104T101334Z.58a19d25ba02f8b6.b11842436bbf4e1997e4dd963bb66413942b40d9";

    private final String STRING_DETECT_URL = "https://translate.yandex.net/api/v1.5/tr.json/detect?" +
            "key=trnsl.1.1.20190104T101334Z.58a19d25ba02f8b6.b11842436bbf4e1997e4dd963bb66413942b40d9";

    public SimpleYandexTranslator() {
        this.parser = new NotationParserImpl();
    }

    @Override
    public String translate(String lang, String input) throws IOException {
        input = parser.parseCamelNotation(input);
        input = parser.parseSnakeNotation(input);

        URL translateYandexApiURL = new URL(STRING_TRNSLATE_URL);

        HttpsURLConnection connection = (HttpsURLConnection) translateYandexApiURL.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

        outputStream.writeBytes("text=" + URLEncoder.encode(input, "UTF-8") + "&lang=" + lang);

        InputStream response = connection.getInputStream();
        String jsonString = new Scanner(response).nextLine();

        Object obj = null;
        try {
            obj = new JSONParser().parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;
        return (String) ((JSONArray) Objects.requireNonNull(jsonObject).get("text")).get(0);
    }

    @Override
    public String translate(String input) throws IOException {
        String lang = detectLanguage(input);
        if (!Objects.requireNonNull(lang).equals("ru")) {
            lang = "ru";
        } else {
            lang = "en";
        }
        return translate(lang, input);
    }

    private String detectLanguage(String input) throws IOException {
        URL detectLangUrl = new URL(STRING_DETECT_URL);
        HttpURLConnection connection = (HttpURLConnection) detectLangUrl.openConnection();
        connection.setDoOutput(true);
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

        outputStream.writeBytes("text=" + URLEncoder.encode(input, "UTF-8"));

        InputStream response = connection.getInputStream();
        String jsonString = new Scanner(response).nextLine();

        Object obj = null;
        try {
            obj = new JSONParser().parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;

        return (String) Objects.requireNonNull(jsonObject).get("lang");
    }
}