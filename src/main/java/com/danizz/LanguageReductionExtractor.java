package com.danizz;

public class LanguageReductionExtractor implements Trimmer {

    @Override
    public String trim(String lang) {
        try {
            return lang.substring(lang.indexOf('(') + 1, lang.indexOf(')')).trim();
        } catch (Exception ex) {
            ex.printStackTrace();
            return lang;
        }
    }
}
