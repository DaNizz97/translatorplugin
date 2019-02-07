package com.danizz.translator.impl;

import com.danizz.PropertiesManager;
import com.danizz.parser.NotationParser;
import com.danizz.parser.impl.NotationParserImpl;
import com.danizz.translator.Translator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Objects;
import java.util.Scanner;

public class SimpleYandexTranslator implements Translator {

    private final NotationParser parser;
    private final PropertiesManager propertiesManager;
    private final String STRING_TRNSLATE_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=";
    private final String STRING_DETECT_URL = "https://translate.yandex.net/api/v1.5/tr.json/detect?key=";
    private String API_KEY;

    public SimpleYandexTranslator() {
        this.parser = new NotationParserImpl();
        propertiesManager = new PropertiesManager("/home/da-nizz/IdeaProjects/TranslatorPlugin/src/main/resources/config.properties");
        API_KEY = propertiesManager.getProperties("yandex.api-key");
    }

    @Override
    public String translate(String lang, String input) throws IOException {
        URL translateYandexApiURL = new URL(STRING_TRNSLATE_URL + API_KEY);
        String parameters = "&text=" +
                URLEncoder.encode(input, "UTF-8") + "&lang=" + lang;

        JSONObject jsonObject = getJsonFromConnection(translateYandexApiURL, parameters);

        return (String) ((JSONArray) Objects.requireNonNull(jsonObject).get("text")).get(0);
    }

    @Override
    public String translate(String input) throws IOException {
        input = parser.parseCamelNotation(input);
        input = parser.parseSnakeNotation(input);
        String lang = detectLanguage(input);
        if (!Objects.requireNonNull(lang).equals("ru")) {
            lang = "ru";
        } else {
            lang = "en";
        }
        return translate(lang, input);
    }

    @Override
    public void setApiKey(String apiKey) {
        API_KEY = apiKey;
    }

    private String detectLanguage(String input) throws IOException {
        URL detectLangUrl = new URL(STRING_DETECT_URL + API_KEY);
        String parameters = "&text=" +
                URLEncoder.encode(input, "UTF-8");
        JSONObject jsonObject = getJsonFromConnection(detectLangUrl, parameters);

        return (String) Objects.requireNonNull(jsonObject).get("lang");
    }

    @Override
    public boolean isApiKeyValid(String apiKey) throws IOException {
        URL testConnectionUrl = new URL(STRING_DETECT_URL + apiKey);
        String parameters = "text=" +
                URLEncoder.encode("test text", "UTF-8");
        JSONObject jsonObject = null;
        try {
            jsonObject = getJsonFromConnection(testConnectionUrl, parameters);
        } catch (IOException e) {
            e.printStackTrace();
            if (e.getMessage().contains("403")) {
                return false;
            }
            throw e;
        }
        Long responseCode = getResponseCode(jsonObject);
        return responseCode == 200;
    }

    private Long getResponseCode(JSONObject jsonObject) {
        return (Long) jsonObject.get("code");
    }

    private JSONObject getJsonFromConnection(URL url, String parameters) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(parameters);
        InputStream response = connection.getInputStream();
        String jsonString = new Scanner(response).nextLine();

        //TODO: implement normal handling jsonObject
        Object obj = null;
        try {
            obj = new JSONParser().parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (JSONObject) obj;
    }
}
