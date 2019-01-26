package com.danizz.translator.impl;

import com.danizz.PropertiesManager;
import com.danizz.parser.NotationParser;
import com.danizz.parser.impl.NotationParserImpl;
import com.danizz.translator.Translator;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

//TODO: split methods translate(String lang, String input) and detectLanguage(String input) into smaller ones
public class SimpleMultillectTranslator implements Translator {

    private final PropertiesManager propertiesManager;
    private final NotationParser parser;
    private final String ACCOUNT_ID;
    private final String API_KEY;
    private String STRING_URL = "https://api.multillect.com/translate/json/1.0/";

    public SimpleMultillectTranslator() {
        parser = new NotationParserImpl();
        propertiesManager = new PropertiesManager("/home/da-nizz/IdeaProjects/TranslatorPlugin/src/main/resources/config.properties");
        ACCOUNT_ID = propertiesManager.getProperties("multillect.account-id");
        API_KEY = propertiesManager.getProperties("multillect.api-key");
        STRING_URL += ACCOUNT_ID;
    }

    @Override
    public String translate(String lang, String input) throws IOException {
        input = parser.parseCamelNotation(input);
        input = parser.parseSnakeNotation(input);
        URL detectLangUrl = new URL(STRING_URL);

        HttpURLConnection connection = (HttpURLConnection) detectLangUrl.openConnection();
        connection.setDoOutput(true);
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes("method=translate/api/translate" + "&to=" + lang + "&text=" + input + "&sig=" + API_KEY);

        InputStream response = connection.getInputStream();
        String jsonString = new Scanner(response).nextLine();

        Object obj = null;
        try {
            obj = new JSONParser().parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;
        jsonObject = (JSONObject) Objects.requireNonNull(jsonObject).get("result");
        return (String) jsonObject.get("translated");
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
        input = parser.parseCamelNotation(input);
        input = parser.parseSnakeNotation(input);

        URL detectLangUrl = new URL(STRING_URL);
        HttpURLConnection connection = (HttpURLConnection) detectLangUrl.openConnection();
        connection.setDoOutput(true);
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes("method=translate/api/translate" + "&to=" + "ru" + "&text=" + input + "&sig=" + API_KEY);

        InputStream response = connection.getInputStream();
        String jsonString = new Scanner(response).nextLine();

        Object obj = null;
        try {
            obj = new JSONParser().parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;
        jsonObject = (JSONObject) Objects.requireNonNull(jsonObject).get("result");
        jsonObject = (JSONObject) jsonObject.get("language");
        return (String) jsonObject.get("code");
    }


}
